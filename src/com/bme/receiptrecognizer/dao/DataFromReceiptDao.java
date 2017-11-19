package com.bme.receiptrecognizer.dao;

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
public class DataFromReceiptDao {

	@PersistenceContext
	private EntityManager em;

	public void persist(DataFromReceipt dataFromReceipt) {
		em.persist(dataFromReceipt);
	}
	
	public void update(DataFromReceipt dataFromReceipt) {
		Query q =  em.createNativeQuery("UPDATE datafromreceipt set datafromreceipt_finalvalue = ?1,datafromreceipt_address = ?2,datafromreceipt_company = ?3, datafromreceipt_date = ?4 where datafromreceipt_id = ?5");
		q.setParameter(1, dataFromReceipt.getFinalValue());
		q.setParameter(2, dataFromReceipt.getAddress());
		q.setParameter(3, dataFromReceipt.getCompany());
		q.setParameter(4, dataFromReceipt.getDate());
		q.setParameter(5, dataFromReceipt.getId());
		q.executeUpdate();
	}

	public List<DataFromReceipt> findAllByUser(String user) {
		Query q =  em.createNativeQuery("SELECT * FROM datafromreceipt where datafromreceipt_user = ?1");
		q.setParameter(1, user);
		return q.getResultList();
	}
	
	public DataFromReceipt findByUser(String name, String user) {
		Query q =  em.createNativeQuery("SELECT * FROM datafromreceipt where datafromreceipt_user = ?1 and datafromreceipt_name = ?2", DataFromReceipt.class);
		q.setParameter(1, user);
		q.setParameter(2, name);
		try {
			return (DataFromReceipt) q.getSingleResult();
		} catch (HsqlException|PersistenceException|SQLGrammarException e) {
			return null;
		}
	}

}