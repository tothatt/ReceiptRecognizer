package com.bme.receiptrecognizer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.bme.receiptrecognizer.model.DataFromReceipt;

@Component
public class DataFromReceiptDao {

	@PersistenceContext
	private EntityManager em;

	public void persist(DataFromReceipt dataFromReceipt) {
		em.persist(dataFromReceipt);
	}

	public List<DataFromReceipt> findAll() {
		return em.createQuery("SELECT data FROM DataFromReceipt data").getResultList();
	}

}