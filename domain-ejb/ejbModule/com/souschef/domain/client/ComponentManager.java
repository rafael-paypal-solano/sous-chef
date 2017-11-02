package com.souschef.domain.client;

import java.util.List;

import com.souschef.client.ClientException;
import com.souschef.domain.data.model.Component;
import com.souschef.domain.data.model.ComponentCategory;

public interface ComponentManager {
	List<ComponentCategory> allComponentCategories() throws ClientException;
	List<Component> componentsByCategoryId(String id) throws ClientException;
	
	void saveComponentCategory(ComponentCategory componentCategory) throws ClientException;
	ComponentCategory findComponentCategoryById(String id) throws ClientException;
	void removeComponentCategory(String id) throws ClientException;
	
	void saveComponent(Component component) throws ClientException;
	Component findComponentById(String id) throws ClientException;
	void removeComponent(String id) throws ClientException;
	
	
	Component findComponentByName(String name) throws ClientException;
	
}
