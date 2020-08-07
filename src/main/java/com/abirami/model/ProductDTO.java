package com.abirami.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ProductDTO {

	private Integer productId;
	
	private String displayName;
	
	private String description;
	
	private String productType;
	
	private byte[] image;
	
	private BigDecimal price;
	
	private Integer availabilityCount;
	
	private String base64Image;
	
	private String categoryName;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(Product product) {
		super();
		this.productId = product.getProductId();
		this.displayName = product.getDisplayName();
		this.description = product.getDescription();
		this.productType = product.getProductType();
		this.image = product.getImage();
		this.price = product.getPrice();
		this.availabilityCount = product.getAvailabilityCount();
		this.categoryName = product.getCategoryName();
	}
	
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
	
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
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

	public String getBase64Image() {
		return base64Image;
	}

	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
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
		ProductDTO other = (ProductDTO) obj;
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
