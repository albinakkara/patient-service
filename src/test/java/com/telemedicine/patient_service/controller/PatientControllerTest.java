package com.telemedicine.patient_service.controller;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.CreatePatientDto;
import com.telemedicine.patient_service.dto.PatientDto;
import com.telemedicine.patient_service.service.PatientService;
import com.telemedicine.patient_service.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientControllerTest {

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Test
    void getAllPatients_returnsOk() {
        Patient patient = new Patient();
        patient.setId(1L);
        when(patientService.getAllPatients()).thenReturn(List.of(patient));

        ResponseEntity<List<Patient>> response = patientController.getAllPatients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getPatientProfile_validHeader_returnsOk() {
        PatientDto dto = new PatientDto();
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john@example.com");

        when(patientService.getPatientProfile("john@example.com")).thenReturn(dto);

        ResponseEntity<PatientDto> response = patientController.getPatientProfile("john@example.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("john@example.com", response.getBody().getEmail());
    }

    @Test
    void getPatientProfile_blankHeader_throwsRuntimeException() {
        assertThrows(RuntimeException.class, () -> patientController.getPatientProfile(""));
    }

    @Test
    void createPatient_success_returnsCreated() {
        CreatePatientDto dto = new CreatePatientDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");

        Patient saved = new Patient();
        saved.setId(1L);

        when(patientService.createPatient(any(CreatePatientDto.class))).thenReturn(saved);

        ResponseEntity<String> response = patientController.createPatient(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Constants.PATIENT_CREATION_SUCCESS, response.getBody());
    }

    @Test
    void updatePatient_success_returnsCreated() {
        PatientDto dto = new PatientDto();
        dto.setFirstName("Updated");

        Patient updated = new Patient();
        updated.setId(1L);
        updated.setFirstName("Updated");

        when(patientService.updatePatient("john@example.com", dto)).thenReturn(updated);

        ResponseEntity<String> response = patientController.updatePatient("john@example.com", dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Constants.PATIENT_UPDATE_SUCCESS, response.getBody());
    }

    @Test
    void updatePatient_nullFromService_throwsRuntimeException() {
        PatientDto dto = new PatientDto();
        when(patientService.updatePatient("john@example.com", dto)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> patientController.updatePatient("john@example.com", dto));
    }
}
