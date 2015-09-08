/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Product;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author tnowicki
 */
@Transactional
public class ProductDAOImpl extends AbstractDAO implements ProductDAO {

    @Override
    public void save(Product p) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(p);
    }

    @Override
    public void delete(Product p) {
        this.sessionFactory.getCurrentSession().delete(p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Product> list() {
        return this.sessionFactory.getCurrentSession().createQuery("from Product").list();
    }

    @Override
    public Product get(int id) {
        logger.info("retrieving Product with id : " + id);
        return (Product) this.sessionFactory.getCurrentSession().get(Product.class, id);

    }

}
