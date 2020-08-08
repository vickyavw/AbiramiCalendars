package com.abirami.model;

public class CategoryDTO {

	private Integer categoryId;
	private String categoryName;
	private String description;

	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(Category category) {
		super();
		this.categoryId = category.getCategoryId();
		this.categoryName = category.getCategoryName();
		this.description = category.getDescription();
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
