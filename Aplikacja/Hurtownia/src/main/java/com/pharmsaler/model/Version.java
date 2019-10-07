package com.pharmsaler.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Version {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idVersion;

	private String type;

	private String unit;

	private Integer amount;

	private Float price;

	private Integer inStockAmount;

	@ManyToOne
	@JoinColumn(name = "id_medication")
	private Medication medication;

	public Long getIdVersion() {
		return idVersion;
	}

	public void setIdVersion(Long idVersion) {
		this.idVersion = idVersion;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getInStockAmount() {
		return inStockAmount;
	}

	public void setInStockAmount(int inStockAmount) {
		this.inStockAmount = inStockAmount;
	}

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}

}
