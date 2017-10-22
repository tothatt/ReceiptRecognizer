package com.bme.receiptrecognizer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "xmlchars")
public class XmlChar {
	
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receipt_id")
    Receipt receipt;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "xmlchars_id")
	private int id;
	
	@Column(name = "xmlchars_b")
	private int b;
	
	@Column(name = "xmlchars_r")
	private int r;

	@Column(name = "xmlchars_t")
	private int t;

	@Column(name = "xmlchars_l")
	private int l;

	@Column(name = "xmlchars_s")
	private String s;

	@Column(name = "xmlchars_suspicious")
	private boolean suspicious = false;
	
	@Column(name = "xmlchars_lineid")
	private int lineId;

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public boolean isSuspicious() {
		return suspicious;
	}

	public void setSuspicious(boolean suspicious) {
		this.suspicious = suspicious;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
