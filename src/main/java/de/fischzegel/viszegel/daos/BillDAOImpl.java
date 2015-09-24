/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.BillDAO;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.ShoppingItem;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tnowicki
 */
@Transactional
public class BillDAOImpl extends AbstractDAO implements BillDAO {

    @Override
    public Bill getBill(int id) {
        logger.info("retrieving Bill with id : " + id);
        return (Bill) this.sessionFactory.getCurrentSession().get(Bill.class, id);
    }

    @Override
    public void save(Bill b) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (ShoppingItem it : b.getShopping_items()) {
            it.setBill(b);
        }
        b.getCus_bill().getBills().add(b);
        logger.info("SAVING BILL !!! FOR :  " + b.getCus_bill().getName());
        session.saveOrUpdate(b);
        tx.commit();
        session.close();
    }

}
