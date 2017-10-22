package com.bme.receiptrecognizer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "datafromreceipt")
public class DataFromReceipt {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "datafromreceipt_id")
	private int id;
	
	@Column(name = "datafromreceipt_user")
	private String user;

	@Column(name = "datafromreceipt_name")
	private String name;

	@Column(name = "datafromreceipt_date")
	private Date date;

	@Column(name = "datafromreceipt_address")
	private String address;

	@Column(name = "datafromreceipt_company")
	private String company;

	@Column(name = "datafromreceipt_finalvalue")
	private String finalValue;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "datafromreceipt_id")
	List<Item> items = new ArrayList<>();

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

	public String getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(String finalValue) {
		this.finalValue = finalValue;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
