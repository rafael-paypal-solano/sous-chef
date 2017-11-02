package com.souschef.domain.data.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.souschef.dao.BasicDAO;
import com.souschef.dao.DAOException;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;


public class RecipeDAO extends BasicDAO<String, Recipe> {

	public RecipeDAO(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	@Override
	public void persist(EntityManager entityManager, Recipe entity) throws DAOException {
		List<Ingredient> ingredients = entity.getIngredients();
		
		if(ingredients == null || ingredients.size() == 0) {
			throw new DAOException(new NullPointerException("The Recype has no ingredient or the ingredients list is null"));
		}
		
		for(Ingredient ingredient: ingredients) {
			ingredient.setRecipe(entity);
		}
		super.persist(entityManager, entity);
	}
	
	public List<Recipe> allRecipes(EntityManager entityManager) throws DAOException {
		return (List<Recipe>)all(entityManager, "Recype.all");
	}	

}
