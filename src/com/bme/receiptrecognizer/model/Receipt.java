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
import javax.persistence.MapKeyClass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Receipt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name = "";
	
	@Column(name = "user", nullable = false)
	private String user = "";

	@Column(name = "chars")
	@ElementCollection(targetClass = XmlChar.class, fetch = FetchType.EAGER)
	@OneToMany(cascade = CascadeType.ALL)
	private List<XmlChar> chars = new ArrayList<>();

	@Column(name = "imgWidth", nullable = false)
	private int imgWidth = 0;

	@Column(name = "imgHeight", nullable = false)
	private int imgHeight = 0;

	@Column(name = "imageUrl", nullable = false)
	private String imageUrl = "";

	@Column(name = "xmlUrl", nullable = false)
	private String xmlUrl = "";

	@Column(name = "lines", nullable = false)
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
