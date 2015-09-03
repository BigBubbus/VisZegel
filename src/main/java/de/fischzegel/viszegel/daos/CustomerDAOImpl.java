/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.Customer;
import java.util.List;
import org.hibernate.Hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tnowicki
 */
public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    @Override
    public void save(Customer p) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (Bill b : p.getBills()) {
            b.setCus_bill(p);
        }
        session.saveOrUpdate(p);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Customer p) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(p);
        tx.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> list() {
        Session session = this.sessionFactory.openSession();
        List<Customer> personList = session.createQuery("from Customer").list();
        session.close();
        return personList;
    }

    @Override
    public Customer get(int id) {
        logger.info("retrieving Customer with id : " + id);
        Session session = this.sessionFactory.openSession();
        Customer customer;
        try {
            
            logger.info("Starting retrieaval ...");
            customer = (Customer) session.get(Customer.class, id);
            logger.info("Customer retrieved!");
            session.close();
        } catch (NullPointerException e ){
            logger.warn("No User Found");
            return null;
        }

        return customer;
    }

}
