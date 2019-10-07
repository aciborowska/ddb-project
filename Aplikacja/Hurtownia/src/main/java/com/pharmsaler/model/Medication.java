package com.pharmsaler.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idMedication;

	private String name;

	private String latinName;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ingredient_has_medication",
			joinColumns = @JoinColumn(name = "id_medication",
					referencedColumnName = "idMedication"),
			inverseJoinColumns = @JoinColumn(name = "id_ingredient",
					referencedColumnName = "idIngredient"))
	private List<Ingredient> ingredients;

	public Long getIdMedication() {
		return idMedication;
	}

	public void setIdMedication(Long idMedication) {
		this.idMedication = idMedication;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatinName() {
		return latinName;
	}

	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

}
