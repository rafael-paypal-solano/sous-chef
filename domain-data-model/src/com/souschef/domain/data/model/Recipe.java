package com.souschef.domain.data.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.souschef.dao.EntityBean;

@Entity
@Table(name = "RECYPE")
@NamedQueries(value={
		@NamedQuery(
		      name = "Recype.all",
		      query="SELECT r from Recipe r"
		)		
	}
)
public class Recipe  extends EntityBean<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5739588212741820240L;

	@Id
	@Column(name="ID")		
	private String id;
	
	@Column(name="NAME")
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="RECYPE_ID", referencedColumnName="ID")
	private List<Ingredient> ingredients;
	
	@Column(name="PRICE")
	private double price;
	
	@Column(name="PHOTO")
	private byte[] photo;
	
	public Recipe() {	
		
	}
		
	
	
	
	private void init(String id, String name, double price, byte[] photo, List<Ingredient> ingredients) {
		this.id =id;
		this.ingredients = ingredients;
		this.name = name;
		this.price = price;
		this.photo = photo;		
	}
	
	private void init(String name, double price, byte[] photo, List<Ingredient> ingredients) {
		init(UUID.randomUUID().toString().replace(Constants.DASH, Constants.EMPTY_STRING),
		name,
		price,
		photo,
		ingredients
		);		
	}
	
	public Recipe(String id, String name, double price) {
		init(id, name, price, photo, null);
	}
	
	public Recipe(String name, double price, File file, List<Ingredient> ingredients) throws IOException {
		FileInputStream input = null;
		byte photo[];
		
		try {
			input = new FileInputStream(file);
			photo = new byte[input.available()];
			input.read(photo);
			init(name, price, photo, ingredients);
		}finally {
			if(input != null)
				try {input.close(); } catch(IOException e) {}
		}
	}
	
	public Recipe(String name, double price, File file) throws IOException {
		FileInputStream input = null;
		byte photo[];
		
		try {
			input = new FileInputStream(file);
			photo = new byte[input.available()];
			input.read(photo);
			init(name, price, photo, new ArrayList<Ingredient>());
		}finally {
			if(input != null)
				try {input.close(); } catch(IOException e) {}
		}
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
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingredients == null) ? 0 : ingredients.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(photo);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Recipe other = (Recipe) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingredients == null) {
			if (other.ingredients != null)
				return false;
		} else if (!ingredients.equals(other.ingredients))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(photo, other.photo))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}
	
	
}
