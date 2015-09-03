/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Product;
import de.fischzegel.viszegel.model.ShoppingItem;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author tnowicki
 */
public class ProductDAOImpl extends AbstractDAO implements ProductDAO {
    
    @Override
    public Product get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void save(Product p) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for (ShoppingItem item : p.getShopi()) {
            item.setProduct(p);
        }
        session.saveOrUpdate(p);
        tx.commit();
        session.close();
    }
    
}
