/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

import javax.transaction.Transactional;

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
	public void delete(Bill b) {
		this.sessionFactory.getCurrentSession().delete(b);
	}

	@Override
	public void saveOrUpdate(Bill b) {
		logger.info("--> Saving Bill with id : "+b.getBill_id());

		this.sessionFactory.getCurrentSession().saveOrUpdate(b);
	}

}
