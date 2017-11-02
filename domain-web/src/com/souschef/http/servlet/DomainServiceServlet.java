package com.souschef.http.servlet;

import java.util.List;

import javax.ejb.EJB;


import com.souschef.client.ClientException;
import com.souschef.domain.client.ComponentManager;
import com.souschef.domain.client.RecipeManager;
import com.souschef.domain.data.model.ComponentCategory;
import com.souschef.domain.data.model.Recipe;
import com.souschef.domain.data.model.Component;

/**
 * Servlet implementation class DomainServiceServlet
 */
public class DomainServiceServlet extends ServiceServlet {
	private static final long serialVersionUID = 1L;
	@EJB(beanName="ComponentManagerLocalBean")
	ComponentManager componentManager;
   
	@EJB(beanName="RecipeManagerLocalBean")
	RecipeManager recipeManager;	

    @EndPoint(path="/component-category/all")
    public List<ComponentCategory> allCategories(RequestContext request) throws ClientException{
    	List<ComponentCategory> categories = componentManager.allComponentCategories();
    	return categories;
    }

    @EndPoint(path="/component/by-category/\\S+")
    public List<Component> componentsByCategory(RequestContext request) throws ClientException{    	
    	String id  = request.getUriParts()[2]; //0->component, 1->by-category, 2->\\S+
    	List<Component> categories = componentManager.componentsByCategoryId(id);
    	return categories;
    }    
    
    @EndPoint(path="/component/")
    public Component component(ComponentRequestContext request) throws ClientException{
    	componentManager.saveComponent(request.getBean());
    	return request.getBean();
    } 
    
    @EndPoint(path="/recype/all")
    public List<Recipe> allRecipes(RequestContext request) throws ClientException{    	
    	List<Recipe> recipes = recipeManager.allRecipes();
    	return recipes;
    }    
}
