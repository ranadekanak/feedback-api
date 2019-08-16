package org.krsnaa.feedback.controller;

import org.krsnaa.feedback.domain.Company;
import org.krsnaa.feedback.domain.Designation;
import org.krsnaa.feedback.domain.MedicalCenter;
import org.krsnaa.feedback.domain.Region;
import org.krsnaa.feedback.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pub/lookup")
public class LookupController {

    final private MedicalCenterRepository medicalCenterRepository;
    final private CompanyRepository companyRepository;
    final private RegionRepository regionRepository;
    final private DesignationRepository designationRepository;
    final private EmployeeRepository employeeRepository;

    @Autowired
    public LookupController(CompanyRepository companyRepository, RegionRepository regionRepository, DesignationRepository designationRepository, EmployeeRepository employeeRepository, MedicalCenterRepository medicalCenterRepository){
        this.companyRepository = companyRepository;
        this.regionRepository = regionRepository;
        this.designationRepository = designationRepository;
        this.employeeRepository = employeeRepository;
        this.medicalCenterRepository = medicalCenterRepository;
    }

    @GetMapping("/companies")
    public List<Company> getCompanies(){
        return companyRepository.findAll();
    }

    @GetMapping("/regions")
    public List<Region> getRegions(){
        return regionRepository.findAll();
    }

    @GetMapping("/designations")
    public List<Designation> getDesignations(){
        return designationRepository.findAll();
    }

    @GetMapping("/medicalCenters")
    public List<MedicalCenter> getMedicalCenters(){
        return medicalCenterRepository.findAll();
    }
}
