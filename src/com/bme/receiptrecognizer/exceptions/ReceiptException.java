package com.bme.receiptrecognizer.exceptions;

public class ReceiptException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errCode;
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}


	public ReceiptException(String errCode, String msg) {
		this.errCode = errCode;
		this.errMsg = msg;
	}

	public ReceiptException(String errCode) {
		this.errCode = errCode;
		this.errMsg = "unknown error";
		if ("1000".equals(errCode)) {
			this.errMsg = "receipt not found";
		}
		else if ("1001".equals(errCode)) {
			this.errMsg = "picture not found";
		}
		else if ("1002".equals(errCode)) {
			this.errMsg = "error connecting to the database";
		}
	}

}
