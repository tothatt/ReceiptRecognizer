package com.bme.receiptrecognizer.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bme.receiptrecognizer.dao.DataFromReceiptDao;
import com.bme.receiptrecognizer.model.DataFromReceipt;

@Component
public class DataFromReceiptService {

	private DataFromReceiptDao dataFromReceiptDao;

	@Autowired
	public void setDataFromReceiptDao(DataFromReceiptDao dataFromReceiptDao) {
		this.dataFromReceiptDao = dataFromReceiptDao;
	}
	
	@Transactional
	public void add(DataFromReceipt dataFromReceipt) {
		dataFromReceiptDao.persist(dataFromReceipt);
	}
	
	@Transactional
	public void addAll(Collection<DataFromReceipt> dataFromReceipts) {
		for (DataFromReceipt dataFromReceipt : dataFromReceipts) {
			dataFromReceiptDao.persist(dataFromReceipt);
		}
	}

	@Transactional(readOnly = true)
	public List<DataFromReceipt> listAll() {
		return dataFromReceiptDao.findAll();

	}

}