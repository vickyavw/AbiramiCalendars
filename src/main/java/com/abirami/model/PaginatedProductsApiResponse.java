package com.abirami.model;

import java.io.Serializable;
import java.util.List;

public class PaginatedProductsApiResponse implements Serializable {

	private static final long serialVersionUID = -6595425364623757000L;
	
	private Integer resultSize;
	private Integer pageNumber;
	private Integer pageSize;
	private List<ProductDTO> products;
	
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
	
	public List<ProductDTO> getProducts() {
		return products;
	}
	
	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	
}
