package com.telemedicine.patient_service.controller;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.SkeletonPatientDto;
import com.telemedicine.patient_service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/patients")
public class InternalPatientController {

    private final PatientService patientService;

    public InternalPatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createPatientSkeleton(@Valid @RequestBody SkeletonPatientDto skeletonPatientDto){
        Patient patient = patientService.createPatientSkeleton(skeletonPatientDto);
        if(patient == null){
            throw new RuntimeException("Patient can not be created");
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/validate/{id}")
    public ResponseEntity<Boolean> validatePatientWithId(@PathVariable("id") Long id){
        Patient patient = patientService.validatePatientWithId(id);
        boolean isValid = patient.getId()!=null && patient.getId().equals(id);
        if(!isValid){
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/email/{id}")
    public ResponseEntity<String> getPatientEmailById(@PathVariable("id") Long id){
        String patientEmail = patientService.getPatientEmailById(id);
        return ResponseEntity.ok(patientEmail);
    }
}
