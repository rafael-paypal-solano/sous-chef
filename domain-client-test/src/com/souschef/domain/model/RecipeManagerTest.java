package com.souschef.domain.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.client.RecipeManager;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;
import com.souschef.ejb.client.DomainClientFactory;



@FixMethodOrder(MethodSorters.JVM)
public class RecipeManagerTest {
	Logger logger = LogManager.getLogger(RecipeManagerTest.class);
	
	DomainClientFactory domainClientFactory;
	RecipeManager recipeManager;
	ComponentManager componentManager;
	
	public RecipeManagerTest() throws NamingException {
		domainClientFactory = new DomainClientFactory();
		recipeManager = domainClientFactory.getRecipeManager();
		componentManager = domainClientFactory.getComponentManager();
	}
	
	@Test
	public void testAddRecipes() {
		Recipe recypes[] = DomainModelTestSuite.recipes;
		
		logger.debug("testAddRecypes START");
		
		for(Recipe recipe: recypes) {
			List<Ingredient> ingredients = recipe.getIngredients();
			List<Ingredient> ingredientWithComponentIds = new ArrayList<Ingredient>();
			
			for(Ingredient ingredient: ingredients) {
				Component component = componentManager.findComponentByName(ingredient.getComponent().getName());
				ingredient.setComponent(component);
				ingredientWithComponentIds.add(ingredient);
			}
			
			recipe.setIngredients(ingredientWithComponentIds);
			recipeManager.saveRecipe(recipe);
			logger.debug(String.format("Added recype for '%s'", recipe.getName()));
		}
		
		logger.debug("testAddRecypes END");
	}
	
	public void logRecipes(List<Recipe> recipes) {
		for(Recipe recipe: recipes) {			
			List<Ingredient> ingredients = recipeManager.findIngredientsForRecype(recipe.getId());
			
			logger.debug(String.format("fetched recype for '%s', listing ingredients", recipe.getName()));
			
			for(Ingredient ingredient: ingredients) {
				logger.debug(String.format("'%s'/'%s': %8.2f", recipe.getName(), ingredient.getComponent().getName(), ingredient.getAmount()));
			}			
		}		
	}
	@Test
	public void testFetchAllRecipes() {
		List<Recipe> recipes = recipeManager.allRecipes();
		
		logger.debug("testFetchAllRecypes START");
		logRecipes(recipes);
		logger.debug("testFetchAllRecypes END");
	}
	
	@Test
	public void testModifyIngredients() {
		List<Recipe> recipes = recipeManager.allRecipes();
		
		logger.debug("testModifyRecypeIngredients START");
		
		for(Recipe recipe: recipes) {
			List<Ingredient> ingredients = recipe.getIngredients();
			
			for(Ingredient ingredient: ingredients) {
				ingredient.setAmount(0);
			}
			
			recipeManager.saveRecipe(recipe);
		}	
		
		logger.debug("testModifyRecypeIngredients END");
	}
	
	@Test
	public void testModifiedIngredients() {
		List<Recipe> recipes = recipeManager.allRecipes();
		
		logger.debug("testModifiedIngredients START");
		
		for(Recipe recipe: recipes) {
			List<Ingredient> ingredients = recipe.getIngredients();
			
			for(Ingredient ingredient: ingredients) {
				logger.debug(String.format("Amount for '%s'/'%s' set to %8.2f", recipe.getName(), ingredient.getComponent().getName(), ingredient.getAmount()));
			}
		}	
		
		logger.debug("testModifiedIngredients END");		
	}
	
	
	@Test
	public void testRemoveFirstIngredient() {
		List<Recipe> recipes = recipeManager.allRecipes();
		
		logger.debug("testRemoveIngredients START");
		
		for(Recipe recipe: recipes) {
			List<Ingredient> ingredients = recipe.getIngredients();
			ingredients.remove(0);
			recipe.setIngredients(ingredients);
			recipeManager.saveRecipe(recipe);
		}	
		logRecipes(recipes);
		logger.debug("testRemoveIngredients END");
	}
	
}
