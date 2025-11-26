package com.telemedicine.patient_service.controller;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.CreatePatientDto;
import com.telemedicine.patient_service.dto.PatientDto;
import com.telemedicine.patient_service.service.PatientService;
import com.telemedicine.patient_service.util.Constants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping("")
    public ResponseEntity<List<Patient>> getAllPatients(){
        return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<String> createPatient(@Valid @RequestBody CreatePatientDto patientDto){
        Patient patient = patientService.createPatient(patientDto);
        if(patient == null){
            throw new RuntimeException(Constants.PATIENT_CREATION_FAILURE);
        }
        return new ResponseEntity<>(Constants.PATIENT_CREATION_SUCCESS, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePatient(@PathVariable Long id, @RequestBody PatientDto patientDto){
        Patient patient = patientService.updatePatient(id, patientDto);
        if(patient == null){
            throw new RuntimeException(Constants.PATIENT_UPDATE_FAILURE);
        }
        return new ResponseEntity<>(Constants.PATIENT_UPDATE_SUCCESS, HttpStatus.CREATED);
    }

}
