package com.bme.receiptrecognizer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bme.receiptrecognizer.dao.ReceiptDao;
import com.bme.receiptrecognizer.model.Receipt;

@Component
public class ReceiptService {

	private ReceiptDao receiptDao;

	@Autowired
	public void setDataFromReceiptDao(ReceiptDao receiptDao) {
		this.receiptDao = receiptDao;
	}
	
	@Transactional
	public void add(Receipt receipt) {
		receiptDao.persist(receipt);
	}
	
	@Transactional
	public void addAll(Collection<Receipt> receipts) {
		for (Receipt receipt : receipts) {
			receiptDao.persist(receipt);
		}
	}

	@Transactional(readOnly = true)
	public List<Receipt> listAll() {
		return receiptDao.findAll();

	}

}