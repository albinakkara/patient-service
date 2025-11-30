package com.telemedicine.patient_service.service;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.data.repository.PatientRepository;
import com.telemedicine.patient_service.dto.CreatePatientDto;
import com.telemedicine.patient_service.dto.PatientDto;
import com.telemedicine.patient_service.dto.SkeletonPatientDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setEmail("john@example.com");
        patient.setPhoneNumber("+911234567890");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setGender("Male");
        patient.setAddress("Some address");
        patient.setBloodGroup("O+");
        patient.setEmergencyContactName("Jane");
        patient.setEmergencyContactPhone("9999999999");
        patient.setStatus("Active");
    }

    @Test
    void getAllPatients_returnsList() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john@example.com", result.get(0).getEmail());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void createPatient_savesAndReturnsPatient() {
        CreatePatientDto dto = new CreatePatientDto();
        dto.setFirstName("Alice");
        dto.setLastName("Smith");
        dto.setEmail("alice@example.com");
        dto.setPhoneNumber("+911112223334");
        dto.setDateOfBirth(LocalDate.of(1985, 5, 20));
        dto.setGender("Female");
        dto.setAddress("City");
        dto.setBloodGroup("A+");
        dto.setEmergencyContactName("Bob");
        dto.setEmergencyContactPhone("8888888888");

        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        Patient savedPatient = patientService.createPatient(dto);

        assertNotNull(savedPatient);
        assertEquals(10L, savedPatient.getId());
        assertEquals("alice@example.com", savedPatient.getEmail());
        assertEquals("A+", savedPatient.getBloodGroup());
        assertEquals("Active", savedPatient.getStatus());
    }

    @Test
    void createPatientSkeleton_setsDefaultFields() {
        SkeletonPatientDto skeleton = new SkeletonPatientDto();
        skeleton.setFirstName("Skel");
        skeleton.setLastName("Pat");
        skeleton.setEmail("skel@example.com");

        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> {
            Patient saved = invocation.getArgument(0);
            saved.setId(5L);
            return saved;
        });

        Patient created = patientService.createPatientSkeleton(skeleton);

        assertNotNull(created);
        assertEquals(5L, created.getId());
        assertEquals("skel@example.com", created.getEmail());
        assertEquals("Skel", created.getFirstName());
        assertEquals("Pat", created.getLastName());
        assertEquals("UNKNOWN", created.getGender());
        assertEquals(LocalDate.of(1900, 1, 1), created.getDateOfBirth());
        assertEquals("Pending Setup", created.getStatus());
    }

    @Test
    void updatePatient_updatesNonNullFields() {
        when(patientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(patient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PatientDto dto = new PatientDto();
        dto.setFirstName("Updated");
        dto.setEmail("updated@example.com");
        dto.setBloodGroup("B+");

        Patient updated = patientService.updatePatient("john@example.com", dto);

        assertNotNull(updated);
        assertEquals("Updated", updated.getFirstName());
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals("B+", updated.getBloodGroup());
    }

    @Test
    void validatePatientWithId_returnsPatientIfFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.validatePatientWithId(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void validatePatientWithId_returnsEmptyPatientIfNotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        Patient result = patientService.validatePatientWithId(99L);

        assertNotNull(result);
        assertNull(result.getId());
    }

    @Test
    void getPatientProfile_returnsDto() {
        when(patientRepository.findByEmail("john@example.com")).thenReturn(Optional.of(patient));

        PatientDto dto = patientService.getPatientProfile("john@example.com");

        assertNotNull(dto);
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("john@example.com", dto.getEmail());
    }

    @Test
    void getPatientEmailById_returnsEmail() {
        when(patientRepository.findEmailById(1L)).thenReturn(Optional.of("john@example.com"));

        String email = patientService.getPatientEmailById(1L);

        assertEquals("john@example.com", email);
    }
}
