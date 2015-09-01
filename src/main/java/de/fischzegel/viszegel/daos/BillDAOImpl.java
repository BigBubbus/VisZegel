/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.model.Bill;
import java.math.BigInteger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tnowicki
 */
public class BillDAOImpl extends AbstractDAO implements BillDAO {


    public Bill getBill(int id){
        Session session = this.sessionFactory.openSession();
        Bill bill = (Bill) session.get(Bill.class, id);
        session.close();
        return bill;
        
    }


    @Override
    public void save(Bill b) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(b);
        tx.commit();
        session.close();
    }

}
