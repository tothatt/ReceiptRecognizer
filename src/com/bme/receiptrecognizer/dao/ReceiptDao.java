package com.bme.receiptrecognizer.dao;

import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.SQLGrammarException;
import org.hsqldb.HsqlException;
import org.springframework.stereotype.Component;

import com.bme.receiptrecognizer.model.DataFromReceipt;
import com.bme.receiptrecognizer.model.Receipt;

@Component
public class ReceiptDao {
	@PersistenceContext
	private EntityManager em;

	public void persist(Receipt receipt) {
		em.persist(receipt);
	}

	public List<Receipt> findAllByUser(String user) {
		Query q = em.createNativeQuery("SELECT data FROM Receipt data where name = ?1");
		q.setParameter(1, user);
		return q.getResultList();
	}

	public Receipt findByUser(String name, String user) {
		Query q = em.createNativeQuery("SELECT * FROM Receipt where user = ?1 and name = ?2");
		q.setParameter(1, user);
		q.setParameter(2, name);
		try {
			return (Receipt) q.getSingleResult();
		} catch (HsqlException|PersistenceException|SQLGrammarException e) {
			return null;
		}
	}
}
