package com.bme.receiptrecognizer.model;

public class XmlChar {
	private int b;
	private int r;
	private int t;
	private int l;
	private String s;
	private boolean suspicious = false;
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
	
	
}
