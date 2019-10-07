package com.pharmsaler.dao;

import org.springframework.data.repository.CrudRepository;

import com.pharmsaler.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
