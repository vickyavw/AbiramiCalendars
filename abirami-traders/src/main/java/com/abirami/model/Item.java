package com.abirami.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author vicky
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item implements Serializable {

	private static final long serialVersionUID = 2086133073035476282L;
	
	private Long itemId;
	private String displayName;
	private String description;
	private byte[] image;
	private BigDecimal price;
	private Integer availabilityCount;
	private Integer timeToPrint;
	
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
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
	
	@Override
    	public String toString() {
        	return ReflectionToStringBuilder.toString(this);
    	}
	
}
