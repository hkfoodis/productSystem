package org.zerock.shoppingcart.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.sql.Date;

@JsonRootName("Product")
public class Product {
	private String code;
	private String name;
	private double price;
	private byte[] image;
	private Date createDate;
	
	public Product() {
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
	    return price;
	}
	 
	public void setPrice(double price) {
	    this.price = price;
	}
	 
	public Date getCreateDate() {
	    return createDate;
	}
	 
	public void setCreateDate(Date createDate) {
	    this.createDate = createDate;
	}
	 
	public byte[] getImage() {
	    return image;
	}
	 
	public void setImage(byte[] image) {
	    this.image = image;
	}
}

