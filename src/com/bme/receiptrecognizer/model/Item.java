package com.bme.receiptrecognizer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "item")
public class Item {
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "datafromreceipt_id")
	DataFromReceipt datafromreceipt;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "item_id")
	private int id;

	public Item(String name, Integer price) {
		super();
		this.name = name;
		this.price = price;
	}
	
	@Column(name = "item_name")
	private String name;
	
	@Column(name = "item_price")
	private Integer price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	

}
