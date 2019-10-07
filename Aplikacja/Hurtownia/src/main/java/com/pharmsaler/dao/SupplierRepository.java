package com.pharmsaler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
