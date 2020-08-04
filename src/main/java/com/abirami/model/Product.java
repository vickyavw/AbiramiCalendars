package com.abirami.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author vicky
 *
 */

@Entity
@Table(name="product")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "productId", scope = Product.class)
public class Product {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Integer productId;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column
	private String description;
	
	@Lob
	@Column(name = "image", columnDefinition="BLOB")
	private byte[] image;
	
	@Transient
	private String base64Image;
	
	@Column
	private BigDecimal price;
	
	@Column(name = "availability_count")
	private Integer availabilityCount;
	
	@Column(name = "time_to_print")
	private Integer timeToPrint;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "category_id")
	@JsonBackReference
	private Category category;
	
	@Transient
	private String categoryName;
	
	public Integer getProductId() {
		return productId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
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
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getBase64Image() {
		return base64Image;
	}
	
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
	}
	
	public BigDecimal getPrice() {
		return price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public Integer getAvailabilityCount() {
		return availabilityCount;
	}
	
	public void setAvailabilityCount(Integer availabilityCount) {
		this.availabilityCount = availabilityCount;
	}
	
	public Integer getTimeToPrint() {
		return timeToPrint;
	}
	
	public void setTimeToPrint(Integer timeToPrint) {
		this.timeToPrint = timeToPrint;
	}
	
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}

	@Override
    	public String toString() {
        	return ReflectionToStringBuilder.toString(this);
    	}
}
