package com.pharmsaler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPharmacyAccount;

	private String login;

	private String password;

	private boolean external = false;

	protected Account() {
	}

	public Account(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public Long getId() {
		return idPharmacyAccount;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return String.format("Account[id=%d, login='%s', password='%s']", idPharmacyAccount, login,
				password);
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

}