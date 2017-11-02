package com.souschef.http.servlet.jaxrs;


import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.souschef.client.ClientException;
import com.souschef.domain.client.RecipeManagerLocal;
import com.souschef.domain.data.model.Ingredient;
import com.souschef.domain.data.model.Recipe;
import com.souschef.json.parser.JSONParserException;

@Path("/recipe")
public class RecipeManagerService {
	
	
	@javax.ws.rs.core.Context
	UriInfo uriInfo;
	InitialContext context;
	
	public RecipeManagerService() throws JSONParserException, NamingException {
		context = new InitialContext();
	}
	
	private RecipeManagerLocal getRecipeManager() throws NamingException {
		RecipeManagerLocal recipeManager = (RecipeManagerLocal) context.lookup(
				"ejblocal:sous-chef/domain-ejb.jar/RecipeManagerLocalBean#com.souschef.domain.client.RecipeManagerLocal"
			);
		
		return recipeManager;
	}
	
	@Path("{recipeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public Recipe findRecipeById(@PathParam("recipeId") String recipeId) throws ClientException {
		RecipeManagerLocal recipeManagerLocal;
		try {
			recipeManagerLocal = getRecipeManager();
			Recipe recipe = recipeManagerLocal.findRecipeById(recipeId);
			return recipe;			
		} catch (NamingException e) {
			throw new ClientException(e);
		}

	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Recipe> allRecipes() throws ClientException {
		RecipeManagerLocal recipeManagerLocal;
		try {
			recipeManagerLocal = getRecipeManager();
			List<Recipe> recipes = recipeManagerLocal.allRecipes();
			return recipes;			
		} catch (NamingException e) {
			throw new ClientException(e);
		}		
	}
	
	@Path("{recipeId}/ingredients")
	@GET
	@Produces(MediaType.APPLICATION_JSON)	
	public List<Ingredient> findIngredientsForRecype(@PathParam("recipeId") String recipeId) throws ClientException {
		RecipeManagerLocal recipeManagerLocal;
		try {
			recipeManagerLocal = getRecipeManager();
			List<Ingredient> ingredients = recipeManagerLocal.findIngredientsForRecype(recipeId);
			return ingredients;
		} catch (NamingException e) {
			throw new ClientException(e);
		}		
	}
	
	@Path("{recipeId}/remove")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)	
	public Recipe removeRecipe(@PathParam("recipeId") String recipeId) throws ClientException {
		RecipeManagerLocal recipeManagerLocal;
		Recipe recipe;
		try {
			recipeManagerLocal = getRecipeManager();
			recipe = recipeManagerLocal.removeRecipe(recipeId);
			return recipe;
		} catch (NamingException e) {
			throw new ClientException(e);
		}
		
	}
	
	@Path("insert")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)	
	public void insertRecipe(Recipe recipe) throws ClientException {
		RecipeManagerLocal recipeManagerLocal;
		try {
			recipeManagerLocal = getRecipeManager();
			recipeManagerLocal.insertRecipe(recipe);
		} catch (NamingException e) {
			throw new ClientException(e);
		}
		
	}
}
