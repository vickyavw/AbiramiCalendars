package com.abirami.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author vicky
 *
 */

@Entity
@Table(name="category")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "categoryId", scope = Category.class)
public class Category {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column
	private String description;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<Product> products;
	
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
    	return ReflectionToStringBuilder.toString(this);
	}
}
