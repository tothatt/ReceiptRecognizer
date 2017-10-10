package com.bme.receiptrecognizer.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataFromReceipt {
	private Date date;
	
	private String address;
	
	private String company;
	
	private Integer finalValue;
	
	Map<String, Integer> items = new HashMap<>();

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(Integer finalValue) {
		this.finalValue = finalValue;
	}

	public Map<String, Integer> getItems() {
		return items;
	}

	public void setItems(Map<String, Integer> items) {
		this.items = items;
	}
	
	
}
