package com.pharmsaler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
