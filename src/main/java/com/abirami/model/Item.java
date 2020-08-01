package com.abirami.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author vicky
 *
 */

@Entity
@Table(name="item")
public class Item {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private Integer itemId;
	
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
	
	public Integer getItemId() {
		return itemId;
	}
	
	public void setItemId(Integer itemId) {
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
	
	@Override
    	public String toString() {
        	return ReflectionToStringBuilder.toString(this);
    	}
}
