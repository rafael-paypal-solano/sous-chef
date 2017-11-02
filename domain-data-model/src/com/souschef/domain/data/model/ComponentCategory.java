package com.souschef.domain.data.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.souschef.dao.EntityBean;


@Entity
@Table(name = "COMPONENT_CATEGORY")
@NamedQueries({
	@NamedQuery(
	      name = "ComponentCategory.all",
	      query="SELECT c from ComponentCategory c")
	}
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ComponentCategory extends EntityBean<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3434852295164916809L;
	
	@Id
	@Column(name="ID")	
	private String id;
		
	@Column(name="NAME")
	private String name;
	
	    
	public ComponentCategory() {
		
	}
	
	public ComponentCategory(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public ComponentCategory(String name) {
		this(java.util.UUID.randomUUID().toString().replace(Constants.DASH, Constants.EMPTY_STRING), name);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
