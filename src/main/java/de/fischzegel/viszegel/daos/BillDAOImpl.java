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
	public void saveOrUpdate(Bill b) {
		logger.info("OPENING SESSION");
		Session session = this.sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			logger.info("SAVING");
			if (getBill(b.getBill_id()) != null)
				session.merge(b);
			else
				session.save(b);
			logger.info("SAVED");
			tx.commit();

		} catch (RuntimeException ex) {
			if (tx != null) {
				tx.rollback();
			}
			throw ex;
		} finally {
			session.close();
		}
		// logger.info(dataSource.);
		//

		/*
		 * logger.info("Saving or Updating bill : " + b.getBill_id()); Bill temp
		 * = getBill(b.getBill_id()); if(temp!= null){
		 * this.sessionFactory.getCurrentSession().merge(b); } else {
		 * this.sessionFactory.getCurrentSession().save(b); }
		 */
	}

}
