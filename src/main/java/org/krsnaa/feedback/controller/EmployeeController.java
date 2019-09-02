package org.krsnaa.feedback.controller;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;
import org.krsnaa.feedback.domain.*;
import org.krsnaa.feedback.dto.GenericResponse;
import org.krsnaa.feedback.repository.*;
import org.krsnaa.feedback.dto.EmployeeDTO;
import org.krsnaa.feedback.services.S3Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/pub/")
public class EmployeeController {

    final private CompanyRepository companyRepository;
    final private RegionRepository regionRepository;
    final private DesignationRepository designationRepository;
    final private EmployeeRepository employeeRepository;
    final private S3Wrapper awsService;
    final private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    public EmployeeController(CompanyRepository companyRepository, RegionRepository regionRepository,
                              DesignationRepository designationRepository, EmployeeRepository employeeRepository,
                              S3Wrapper awsService, MedicalCenterRepository medicalCenterRepository){
        this.companyRepository = companyRepository;
        this.regionRepository = regionRepository;
        this.designationRepository = designationRepository;
        this.employeeRepository = employeeRepository;
        this.awsService = awsService;
        this.medicalCenterRepository = medicalCenterRepository;
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity getEmployee(@PathVariable(name = "id") Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent()){
            return ResponseEntity.status(400).body("No Record Found!");
        }
        return ResponseEntity.ok(employee.get());
    }

    @GetMapping("/employees/center/{centerId}")
    public ResponseEntity getEmployeesForMedicalCenter(@PathVariable(name = "centerId") Integer centerId){
        Optional<MedicalCenter> center = medicalCenterRepository.findById(centerId);
        if(!center.isPresent()){
            return ResponseEntity.badRequest().body(new GenericResponse(HttpStatus.BAD_REQUEST.value(), "Center with Id " + centerId + " does not exist"));
        }
        Region region = regionRepository.findById(center.get().getRegion().getId()).get();
        return ResponseEntity.ok(employeeRepository.findAllByRegion(region));
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
        employee.setRegion(regionRepository.findById(employeeDTO.getRegionId()).orElse(new Region()));
        employee.setProfilePhotoPath("");

        employee.setCreatedBy(1L);
        employee.setCreatedDate(new Date());

        return ResponseEntity.ok(employeeRepository.save(employee));

    }

    @PutMapping("/employee/{id}")
    public ResponseEntity saveEmployee(@PathVariable(name = "id") Integer id, @RequestParam("file") MultipartFile multipartFile){
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
    public void download(@PathVariable(name = "id") Integer employeeId, HttpServletResponse response) throws IOException {
        S3ObjectInputStream s3ObjectInputStream = awsService.download(employeeId.toString(), response);
        IOUtils.copy(s3ObjectInputStream, response.getOutputStream());
    }
}
