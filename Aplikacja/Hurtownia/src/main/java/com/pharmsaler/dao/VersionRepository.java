package com.pharmsaler.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Medication;
import com.pharmsaler.model.Version;

public interface VersionRepository extends JpaRepository<Version, Long> {

	List<Version> findByMedication(Medication medication);

}
