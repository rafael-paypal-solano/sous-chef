package com.souschef.domain.data.model;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.souschef.dao.EntityBean;

@Entity
@Table(name = "INGREDIENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "recipe"})
public class Ingredient extends EntityBean<String>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -679098526210798188L;

	@Id
	@Column(name="ID")		
	String id;	
	
	@Column(name="AMOUNT")
	double amount;
	
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.EAGER)
	@JoinColumn(name="COMPONENT_ID")		
	Component component;

	@ManyToOne(cascade={CascadeType.REFRESH}, fetch=FetchType.LAZY)
	@JoinColumn(name="RECYPE_ID")
	private Recipe recipe;
	
	@Enumerated(EnumType.STRING)
	@Column(name="UNIT")
	private Unit unit;	
	
	public Ingredient() {
		
	}
	
	public Ingredient(String id, double amount, Component component, Unit unit) {
		super();
		this.id = id;
		this.amount = amount;
		this.component = component;
		this.unit = unit;
	}
	
	public Ingredient(double amount, Component component, Unit unit) {
		this(UUID.randomUUID().toString().replace(Constants.DASH, Constants.EMPTY_STRING), amount, component, unit);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Component getComponent() {
		return component;
	}

	public void setComponent(Component component) {
		this.component = component;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((component == null) ? 0 : component.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recipe == null) ? 0 : recipe.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (component == null) {
			if (other.component != null)
				return false;
		} else if (!component.equals(other.component))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recipe == null) {
			if (other.recipe != null)
				return false;
		} else if (!recipe.equals(other.recipe))
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}
	
	
	
}
