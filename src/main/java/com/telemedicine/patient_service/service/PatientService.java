package com.telemedicine.patient_service.service;

import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.data.repository.PatientRepository;
import com.telemedicine.patient_service.dto.CreatePatientDto;
import com.telemedicine.patient_service.dto.PatientDto;
import com.telemedicine.patient_service.dto.SkeletonPatientDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients(){
        return patientRepository.findAll();
    }

    public Patient createPatient(CreatePatientDto patientDto) {

        Patient patient = new Patient();
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setBloodGroup(patientDto.getBloodGroup());
        patient.setEmergencyContactName(patientDto.getEmergencyContactName());
        patient.setEmergencyContactPhone(patientDto.getEmergencyContactPhone());
        patient.setStatus("Active");

        return patientRepository.save(patient);

    }

    public Patient createPatientSkeleton(SkeletonPatientDto skeletonPatientDto) {

        Patient patient = new Patient();
        patient.setEmail(skeletonPatientDto.getEmail());
        patient.setFirstName(skeletonPatientDto.getFirstName());
        patient.setLastName(skeletonPatientDto.getLastName());
        patient.setPhoneNumber("0000000000");  // Or null + remove NOT NULL
        patient.setGender("UNKNOWN");
        patient.setDateOfBirth(LocalDate.of(1900, 1, 1));
        patient.setStatus("Pending Setup");

        return patientRepository.save(patient);

    }

    public Patient updatePatient(Long id, PatientDto dto) {
        Patient patient = patientRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (dto.getFirstName() != null) patient.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) patient.setLastName(dto.getLastName());
        if (dto.getEmail() != null) patient.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) patient.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDateOfBirth() != null) patient.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) patient.setGender(dto.getGender());
        if (dto.getAddress() != null) patient.setAddress(dto.getAddress());
        if (dto.getBloodGroup() != null) patient.setBloodGroup(dto.getBloodGroup());
        if (dto.getEmergencyContactName() != null) patient.setEmergencyContactName(dto.getEmergencyContactName());
        if (dto.getEmergencyContactPhone() != null) patient.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        if (dto.getStatus() != null) patient.setStatus(dto.getStatus());

        return patientRepository.save(patient);
    }

    public Patient validatePatientWithId(Long id) {
        return patientRepository.findById(id).orElse(new Patient());
    }
}
