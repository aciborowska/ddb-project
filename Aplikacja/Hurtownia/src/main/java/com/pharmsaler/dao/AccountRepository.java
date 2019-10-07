package com.pharmsaler.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pharmsaler.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	List<Account> findByLoginAndPassword(String login, String password);
}