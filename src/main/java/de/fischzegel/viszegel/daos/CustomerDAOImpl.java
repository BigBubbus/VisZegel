/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.model.Customer;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author tnowicki
 */

public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO{

     
    @Override
    public void save(Customer p) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(p);
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


}
