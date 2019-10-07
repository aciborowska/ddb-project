package com.pharmsaler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idPharmacy;

	private String name;

	private String address;

	private String contactNumber;

	private String email;

	@OneToOne
	@JoinColumn(name = "id_pharmacy_account")
	private Account account;

	protected Pharmacy() {
	}

	public Pharmacy(String name, String address, String contactNumber, String email,
			Account account) {
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.email = email;
		this.account = account;
	}

	public Long getIdPharmacy() {
		return idPharmacy;
	}

	public void setIdPharmacy(Long idPharmacy) {
		this.idPharmacy = idPharmacy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return String.format("Account[id=%d, name='%s', address='%s', contactNumber=%s, email=%s]",
				idPharmacy, name, address, contactNumber, email);
	}

}
