package com.telemedicine.patient_service.data.repository;

import com.telemedicine.patient_service.data.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);

    @Query("select p.email from Patient p where p.id = :id")
    Optional<String> findEmailById(@Param("id") Long id);
}
