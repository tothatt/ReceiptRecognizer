package com.bme.receiptrecognizer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.bme.receiptrecognizer.model.Receipt;

@Component
public class ReceiptDao {
	@PersistenceContext
	private EntityManager em;
	
	public void persist(Receipt receipt) {
		em.persist(receipt);
	}

	public List<Receipt> findAll() {
		return em.createQuery("SELECT data FROM Receipt data").getResultList();
	}
}
