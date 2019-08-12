package org.krsnaa.feedback.controller;

import org.krsnaa.feedback.domain.MedicalCenter;
import org.krsnaa.feedback.repository.MedicalCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MedicalCenterController {

    @Autowired
    private MedicalCenterRepository medicalCenterRepository;

    @GetMapping("/medicalCenters")
    public List<MedicalCenter> getAllMedicalCenters(){
        return medicalCenterRepository.findAll();
    }
}
