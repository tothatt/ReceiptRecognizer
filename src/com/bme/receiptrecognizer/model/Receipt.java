package com.bme.receiptrecognizer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "receipt")
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "receipt_id")
	private int receipt_id;

	@Column(name = "receipt_name")
	private String name = "";

	@Column(name = "receipt_user")
	private String user = "";

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	@JoinColumn(name = "receipt_id")
	private List<XmlChar> chars = new ArrayList<>();

	@Column(name = "receipt_imgwidth")
	private int imgWidth = 0;

	@Column(name = "receipt_imgheight")
	private int imgHeight = 0;

	@Column(name = "receipt_imageurl")
	private String imageUrl = "";

	@Column(name = "receipt_xmlurl")
	private String xmlUrl = "";

	@Column(name = "receipt_lines")
	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@MapKeyClass(Integer.class)
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

	public int getId() {
		return receipt_id;
	}

	public void setId(int id) {
		this.receipt_id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
