package com.souschef.domain.client;

import java.util.List;

import com.souschef.client.ClientException;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;

public interface RecipeManager {
	void saveRecipe(Recipe recipe) throws ClientException;
	void insertRecipe(Recipe recipe) throws ClientException;
	void updateRecipe(Recipe recipe) throws ClientException;
	Recipe findRecipeById(String recypeId) throws ClientException;
	Recipe removeRecipe(String recypeId) throws ClientException;	
	List<Recipe> allRecipes() throws ClientException;
	List<Ingredient> findIngredientsForRecype(String recypeId) throws ClientException;
}
