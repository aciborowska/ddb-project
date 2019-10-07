package com.pharmsaler.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class SupplierService {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idSupplierService;

	private String name;

	private String price;

	private String restrictions;

	@ManyToOne
	@JoinColumn(name = "id_supplier")
	private Supplier supplier;

	@OneToMany(mappedBy = "supplierService")
	private List<PurchaseOrder> orders;

	public Long getIdSupplierService() {
		return idSupplierService;
	}

	public void setIdSupplierService(Long idSupplierService) {
		this.idSupplierService = idSupplierService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public List<PurchaseOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<PurchaseOrder> orders) {
		this.orders = orders;
	}

	public String getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(String restrictions) {
		this.restrictions = restrictions;
	}

	@Override
	public String toString() {
		return supplier.getName() + ", " + name + ", " + price;
	}

}
