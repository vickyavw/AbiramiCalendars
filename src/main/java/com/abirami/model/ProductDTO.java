package com.abirami.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.Hibernate;

public class ProductDTO {

	private Integer productId;
	
	private String productName;
	
	private String description;
	
	private String productType;
	
	private byte[] image;
	
	private BigDecimal price;
	
	private Integer availabilityCount;
	
	private String base64Image;
	
	private Integer categoryId;
	
	private String categoryName;
	
	private Integer formatId;
	
	private String formatName;
	
	public ProductDTO() {
		
	}
	
	public ProductDTO(Product product) {
		super();
		this.productId = product.getProductId();
		this.productName = product.getProductName();
		this.description = product.getDescription();
		this.productType = product.getProductType();
		this.image = product.getImage();
		this.price = product.getPrice();
		this.availabilityCount = product.getAvailabilityCount();
		//if called from DAO category object will fetched and loaded. else just categoryName will be available from product due to lazy initialization
		if(null != product.getCategory() && Hibernate.isInitialized(product.getCategory())) {
			this.categoryId = product.getCategory().getCategoryId();
			this.categoryName = product.getCategory().getCategoryName();
		}
		else {
			this.categoryName = product.getCategoryName();
		}
		if(null != product.getFormat() && Hibernate.isInitialized(product.getFormat())) {
			this.formatId = product.getFormat().getFormatId();
			this.formatName = product.getFormat().getFormatName();
		}
		else {
			this.formatName = product.getFormatName();
		}
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
