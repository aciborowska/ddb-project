package com.pharmsaler.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Account;
import com.pharmsaler.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

	Pharmacy findByAccount(Account account);
}
