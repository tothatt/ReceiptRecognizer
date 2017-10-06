package com.bme.receiptrecognizer.model;

import java.util.*;

public class Receipt {
	String name = "";
	
	public List<XmlChar> chars = new ArrayList<>();
	
	int imgWidth = 0;
	
	int imgHeight = 0;
	
	String imageUrl = "";
	
	String xmlUrl = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<XmlChar> getChars() {
		return chars;
	}

	public void setChars(List<XmlChar> chars) {
		this.chars = chars;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getXmlUrl() {
		return xmlUrl;
	}

	public void setXmlUrl(String xmlUrl) {
		this.xmlUrl = xmlUrl;
	}

	public int getImgWidth() {
		return imgWidth;
	}

	public void setImgWidth(int imgWidth) {
		this.imgWidth = imgWidth;
	}

	public int getImgHeight() {
		return imgHeight;
	}

	public void setImgHeight(int imgHeight) {
		this.imgHeight = imgHeight;
	} 
	
	
}
