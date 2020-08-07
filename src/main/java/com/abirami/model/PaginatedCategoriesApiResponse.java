package com.abirami.model;

import java.io.Serializable;
import java.util.List;

public class PaginatedCategoriesApiResponse implements Serializable {

	private static final long serialVersionUID = 6345316715707590890L;

	private Integer resultSize;
	private Integer pageNumber;
	private Integer pageSize;
	private List<CategoryDTO> categories;
	
	public Integer getResultSize() {
		return resultSize;
	}
	
	public void setResultSize(Integer resultSize) {
		this.resultSize = resultSize;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	
}
