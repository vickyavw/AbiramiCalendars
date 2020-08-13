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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author vicky
 *
 */

@Entity
@Table(name="format")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "formatId", scope = Format.class)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Format {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "format_id")
	private Integer formatId;
	
	@Column(name = "display_name")
	private String formatName;
	
	@Column
	private String description;
	
	@Column(name="product_type")
	private String productType;
	
	@OneToMany(mappedBy = "format", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference("formats")
	private Set<Product> products;
	
	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "format", cascade=CascadeType.ALL)
	//@JsonManagedReference
	//@ManyToMany(cascade=CascadeType.ALL)
	//@JoinTable(name="format_category", joinColumns = { @JoinColumn(name="format_id") }, inverseJoinColumns = { @JoinColumn(name="category_id") })
	//private Set<Category> categories;
	
	public Format() {

	}
	
	public Integer getFormatId() {
		return formatId;
	}

	public void setFormatId(Integer formatId) {
		this.formatId = formatId;
	}

	public String getFormatName() {
		return formatName;
	}
	
	public void setFormatName(String formatName) {
		this.formatName = formatName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

//	public Set<Category> getCategories() {
//		return categories;
//	}
//
//	public void setCategories(Set<Category> categories) {
//		this.categories = categories;
//	}

	@Override
	public String toString() {
    	return ReflectionToStringBuilder.toString(this);
	}
}
