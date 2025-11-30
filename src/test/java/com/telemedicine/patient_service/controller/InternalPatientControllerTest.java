package com.telemedicine.patient_service.controller;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.SkeletonPatientDto;
import com.telemedicine.patient_service.service.PatientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InternalPatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private InternalPatientController internalPatientController;

    @Test
    void createPatientSkeleton_success_returnsTrue() {
        SkeletonPatientDto skeleton = new SkeletonPatientDto();
        skeleton.setFirstName("Skel");
        skeleton.setLastName("Pat");
        skeleton.setEmail("skel@example.com");

        Patient saved = new Patient();
        saved.setId(1L);

        when(patientService.createPatientSkeleton(any(SkeletonPatientDto.class))).thenReturn(saved);

        ResponseEntity<Boolean> response = internalPatientController.createPatientSkeleton(skeleton);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void validatePatientWithId_valid_returnsTrueOk() {
        Patient patient = new Patient();
        patient.setId(1L);

        when(patientService.validatePatientWithId(1L)).thenReturn(patient);

        ResponseEntity<Boolean> response = internalPatientController.validatePatientWithId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void validatePatientWithId_invalid_returnsFalseNotFound() {
        Patient empty = new Patient(); // id is null

        when(patientService.validatePatientWithId(99L)).thenReturn(empty);

        ResponseEntity<Boolean> response = internalPatientController.validatePatientWithId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void getPatientEmailById_returnsEmail() {
        when(patientService.getPatientEmailById(1L)).thenReturn("pat@example.com");

        ResponseEntity<String> response = internalPatientController.getPatientEmailById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("pat@example.com", response.getBody());
    }
}
