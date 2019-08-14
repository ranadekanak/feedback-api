package org.krsnaa.feedback.controller;

import org.krsnaa.feedback.domain.Company;
import org.krsnaa.feedback.domain.Designation;
import org.krsnaa.feedback.domain.MedicalCenter;
import org.krsnaa.feedback.domain.Region;
import org.krsnaa.feedback.repository.CompanyRepository;
import org.krsnaa.feedback.repository.DesignationRepository;
import org.krsnaa.feedback.repository.MedicalCenterRepository;
import org.krsnaa.feedback.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pub/lookup")
public class LookupController {

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private DesignationRepository designationRepository;

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
