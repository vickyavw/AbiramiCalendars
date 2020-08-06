package com.abirami.model;

public class CategoryDTO {

	private Integer categoryId;
	private String displayName;
	private String description;

	public CategoryDTO() {
		super();
	}
	
	public CategoryDTO(Category category) {
		super();
		this.categoryId = category.getCategoryId();
		this.displayName = category.getDisplayName();
		this.description = category.getDescription();
	}

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
}
