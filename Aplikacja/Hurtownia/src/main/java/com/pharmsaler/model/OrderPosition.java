package com.pharmsaler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrderPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idOrderPosition;

	private Integer amount;

	private Float price;

	@ManyToOne
	@JoinColumn(name = "id_order")
	private PurchaseOrder order;

	@ManyToOne
	@JoinColumn(name = "id_version")
	private Version version;

	public Long getIdOrderPosition() {
		return idOrderPosition;
	}

	public void setIdOrderPosition(Long idOrderPosition) {
		this.idOrderPosition = idOrderPosition;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
		setPrice(this.amount * version.getPrice());
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public PurchaseOrder getOrder() {
		return order;
	}

	public void setOrder(PurchaseOrder order) {
		this.order = order;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getDescription() {
		return version.getMedication().getName() + " (" + version.getMedication().getLatinName()
				+ ")" + " " + version.getType() + ", " + version.getAmount() + " "
				+ version.getUnit();
	}

}
