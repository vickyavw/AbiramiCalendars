package com.abirami.model;

public class FormatDTO {

	private Integer formatId;
	private String formatName;
	private String description;
	private String productType;

	public FormatDTO() {
		super();
	}
	
	public FormatDTO(Format format) {
		super();
		this.formatId = format.getFormatId();
		this.formatName = format.getFormatName();
		this.description = format.getDescription();
		this.productType = format.getProductType();
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
}
