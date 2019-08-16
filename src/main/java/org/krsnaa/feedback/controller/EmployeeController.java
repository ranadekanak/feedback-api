package org.krsnaa.feedback.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.krsnaa.feedback.domain.Company;
import org.krsnaa.feedback.domain.Designation;
import org.krsnaa.feedback.domain.Employee;
import org.krsnaa.feedback.domain.Region;
import org.krsnaa.feedback.repository.CompanyRepository;
import org.krsnaa.feedback.repository.DesignationRepository;
import org.krsnaa.feedback.repository.EmployeeRepository;
import org.krsnaa.feedback.repository.RegionRepository;
import org.krsnaa.feedback.request.EmployeeDTO;
import org.krsnaa.feedback.services.S3Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pub/")
public class EmployeeController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private S3Wrapper awsService;

    @GetMapping("/employees/{id}")
    public ResponseEntity getEmployee(@PathVariable(name = "id") Long id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            return ResponseEntity.status(400).body("No Record Found!");
        }
        return ResponseEntity.ok(employee.get());
    }

    @PostMapping("/employee")
    public ResponseEntity saveEmployee(@RequestBody EmployeeDTO employeeDTO){
        Employee existingEmployee = employeeRepository.findByEmployeeCode(employeeDTO.getEmployeeCode());
        if(existingEmployee == null){
            return ResponseEntity.status(400).body("Employee already exists with code " + employeeDTO.getEmployeeCode());
        }
        Employee employee = new Employee();
        employee.setCompanyId(companyRepository.findById(employeeDTO.getCompanyId()).orElse(new Company()));
        employee.setEmployeeCode(employeeDTO.getEmployeeCode());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setDesignationId(designationRepository.findById(employeeDTO.getDesignationId()).orElse(new Designation()));
        employee.setRegionId(regionRepository.findById(employeeDTO.getRegionId()).orElse(new Region()));
        employee.setProfilePhotoPath("");

        employee.setCreatedBy(1L);
        employee.setCreatedDate(new Date());

        return ResponseEntity.ok(employeeRepository.save(employee));

    }

    @PutMapping("/employee/{id}")
    public ResponseEntity saveEmployee(@PathVariable(name = "id") Long id, @RequestParam("file") MultipartFile multipartFile){
        Employee employee = employeeRepository.findById(id).orElse(new Employee());
        if(employee.getId() == null){
            return ResponseEntity.badRequest().build();
        }
        try {
            PutObjectResult result = awsService.upload(multipartFile.getInputStream(), employee.getId().toString());
            employee.setProfilePhotoPath("/api/pub/employee/" + employee.getId() + "/photo");
            employee.setModifiedBy(1L);
            employee.setModifiedDate(new Date());
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body("Profile photo could not be saved");
        }
        return ResponseEntity.ok(employeeRepository.save(employee));

    }

    @RequestMapping(value = "/employee/{id}/photo", method = RequestMethod.GET)
    public void download(@PathVariable(name = "id") Long employeeId, HttpServletResponse response) throws IOException {
        S3ObjectInputStream s3ObjectInputStream = awsService.download(employeeId.toString(), response);
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        IOUtils.copy(s3ObjectInputStream, response.getOutputStream());
    }
}
