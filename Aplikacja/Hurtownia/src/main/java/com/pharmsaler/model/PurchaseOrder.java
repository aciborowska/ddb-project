package com.pharmsaler.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PurchaseOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idOrder;

	private String payment;

	private String status;

	private Long startDate;

	@ManyToOne
	@JoinColumn(name = "id_pharmacy")
	private Pharmacy pharmacy;

	@ManyToOne
	@JoinColumn(name = "id_supplier_service")
	private SupplierService supplierService;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL,
			fetch = javax.persistence.FetchType.EAGER)
	private List<OrderPosition> orderPositions;

	public Long getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Pharmacy getPharamcy() {
		return pharmacy;
	}

	public void setPharamcy(Pharmacy pharamcy) {
		this.pharmacy = pharamcy;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public List<OrderPosition> getOrderPositions() {
		return orderPositions;
	}

	public void setOrderPositions(List<OrderPosition> orderPositions) {
		this.orderPositions = orderPositions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

}
