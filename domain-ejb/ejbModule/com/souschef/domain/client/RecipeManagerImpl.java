package com.souschef.domain.client;


import java.util.List;

import javax.persistence.EntityManager;

import com.souschef.client.ClientException;
import com.souschef.dao.DAOException;
import com.souschef.domain.data.dao.RecipeDAO;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;

public class RecipeManagerImpl extends DAOWrapperClient implements RecipeManager{
	protected RecipeDAO recipeDAO;		
	
	
	public RecipeManagerImpl() {
		super(); //Superfluous
		try {
			recipeDAO = new RecipeDAO(getEntityManagerFactory());
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Override
	public void saveRecipe(Recipe recipe) throws ClientException {
		try {
			recipeDAO.persist(recipe);			
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}finally {
			
		}		
	}

	public void insertRecipe(Recipe recipe) throws ClientException {
		saveRecipe(recipe);
	}
	
	public void updateRecipe(Recipe recipe) throws ClientException {
		saveRecipe(recipe);
	}
	
	@Override
	public Recipe findRecipeById(String id) throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			Recipe recipe = recipeDAO.findById(entityManager, id);
			return recipe;
		}catch(DAOException e){
			throw new ClientException(e);
		}		
	}

	@Override
	public Recipe removeRecipe(String id) throws ClientException {
		Recipe recipe;
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try {
			recipe = recipeDAO.findById(entityManager, id);
			if(recipe != null)
				recipeDAO.remove(entityManager, recipe);
			return recipe;
		}catch(DAOException e) {
			throw new ClientException(e);
		}catch(IllegalStateException e){
			throw new ClientException(e);
		}
		
	}

	@Override
	public List<Recipe> allRecipes() throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			List<Recipe> recipes =  recipeDAO.allRecipes(entityManager);		
			return recipes;
		}catch(DAOException e){
			throw new ClientException(e);
		}
	}

	@Override
	public List<Ingredient> findIngredientsForRecype(String recypeId) throws ClientException {
		EntityManager entityManager = getEntityManagerFactory().createEntityManager();
		try{
			Recipe recipe = recipeDAO.findById(entityManager, recypeId);
			List<Ingredient> ingredients = recipe.getIngredients() ;			
			return ingredients;			
		}catch(DAOException e){
			throw new ClientException(e);
		}		
	}
	
	

}
