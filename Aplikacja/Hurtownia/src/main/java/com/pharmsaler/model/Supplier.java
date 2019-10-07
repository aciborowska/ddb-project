package com.pharmsaler.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSupplier;

	private String name;

	private String contactNumber;

	private String additionalInfo;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
	private List<SupplierService> supplierService;

	protected Supplier() {
	};

	public Supplier(String name, String contactNumber) {
		this.name = name;
		this.contactNumber = contactNumber;
	}

	public Long getIdSupplier() {
		return idSupplier;
	}

	public void setIdSupplier(Long idSupplier) {
		this.idSupplier = idSupplier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public List<SupplierService> getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(List<SupplierService> supplierService) {
		this.supplierService = supplierService;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionInfo) {
		this.additionalInfo = additionInfo;
	}

}
