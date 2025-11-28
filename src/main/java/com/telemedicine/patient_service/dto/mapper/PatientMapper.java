package com.telemedicine.patient_service.dto.mapper;


import com.telemedicine.patient_service.data.entity.Patient;
import com.telemedicine.patient_service.dto.PatientDto;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

//@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public class PatientMapper {


    public static PatientDto toDto(Patient patient) {
        if (patient == null) return null;

        PatientDto dto = new PatientDto();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmail(patient.getEmail());
        dto.setPhoneNumber(patient.getPhoneNumber());
        dto.setDateOfBirth(patient.getDateOfBirth());
        dto.setGender(patient.getGender());
        dto.setAddress(patient.getAddress());
        dto.setBloodGroup(patient.getBloodGroup());
        dto.setEmergencyContactName(patient.getEmergencyContactName());
        dto.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        dto.setStatus(patient.getStatus());
        return dto;
    }

    // DTO -> Entity
    public static Patient toEntity(PatientDto dto) {
        if (dto == null) return null;

        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        patient.setAddress(dto.getAddress());
        patient.setBloodGroup(dto.getBloodGroup());
        patient.setEmergencyContactName(dto.getEmergencyContactName());
        patient.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        patient.setStatus(dto.getStatus() != null ? dto.getStatus() : "Active");
        return patient;
    }


//    Map structut not working

//    // Entity -> DTO
//    PatientDto toDto(Patient patient);
//
//    // DTO -> Entity (id will not be set from DTO)
//    @Mapping(target = "id", ignore = true)
//    Patient toEntity(PatientDto dto);
//
//    // List mapping
//    java.util.List<PatientDto> toDtoList(java.util.List<Patient> patients);

}
