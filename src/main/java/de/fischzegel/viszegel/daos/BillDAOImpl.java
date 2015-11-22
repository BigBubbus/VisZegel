/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import de.fischzegel.viszegel.daos.interfaces.BillDAO;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.Customer;

/**
 *
 * @author tnowicki
 */
@Transactional
public class BillDAOImpl extends AbstractDAOImpl implements BillDAO {

	@Override
	public Bill getBill(int id) {
		logger.info("retrieving Bill with id : " + id);
		return (Bill) this.sessionFactory.getCurrentSession().get(Bill.class, id);
	}

	@Override
	public void delete(Bill b) {
		this.sessionFactory.getCurrentSession().delete(b);
	}

	@Override
	public void saveOrUpdate(Bill b) {
		logger.info("--> Saving Bill with id : "+b.getBill_id());
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(b);
			tx.commit();

		} catch (RuntimeException ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
	}

}
