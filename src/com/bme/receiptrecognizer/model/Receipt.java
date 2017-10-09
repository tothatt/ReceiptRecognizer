package com.bme.receiptrecognizer.model;

import java.util.*;

public class Receipt {
	private String name = "";
	
	private  List<XmlChar> chars = new ArrayList<>();
	
	private int imgWidth = 0;
	
	private int imgHeight = 0;
	
	private String imageUrl = "";
	
	private String xmlUrl = "";
	
	private Map<Integer, String> lines;
	
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

	public Map<Integer, String> getLines() {
		return lines;
	}

	public void setLines(Map<Integer, String> lines) {
		this.lines = lines;
	}

	
	
	
}
