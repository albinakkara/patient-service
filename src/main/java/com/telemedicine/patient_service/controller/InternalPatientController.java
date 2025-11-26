package com.telemedicine.patient_service.controller;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.SkeletonPatientDto;
import com.telemedicine.patient_service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/patients")
public class InternalPatientController {

    private final PatientService patientService;

    public InternalPatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createPatientWithEmail(@Valid @RequestBody SkeletonPatientDto skeletonPatientDto){
        Patient patient = patientService.createPatientSkeleton(skeletonPatientDto);
        if(patient == null){
            throw new RuntimeException("Patient can not be created");
        }
        return ResponseEntity.ok(true);
    }
}
