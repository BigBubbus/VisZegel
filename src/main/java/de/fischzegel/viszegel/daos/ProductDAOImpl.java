/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.ProductConstant;
import java.util.List;
import javax.transaction.Transactional;

/**
 *
 * @author tnowicki
 */
@Transactional
public class ProductDAOImpl extends AbstractDAOImpl implements ProductDAO {

    @Override
    public void save(ProductConstant p) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(p);
    }

    @Override
    public void delete(ProductConstant p) {
        this.sessionFactory.getCurrentSession().delete(p);
    }

    @Override
    public ProductConstant getByProductId(int id) {
        @SuppressWarnings("unchecked")
		List<ProductConstant> prods = this.sessionFactory.getCurrentSession()
                .createQuery("from ProductConstant p WHERE p.product_id = :prodid ")
                .setParameter("prodid", id).list();
        if (prods.size() > 0) {
            return prods.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProductConstant> list() {
        return this.sessionFactory.getCurrentSession().createQuery("from ProductConstant").list();
    }

    @Override
    public ProductConstant get(int id) {
        logger.info("retrieving Product with id : " + id);
        return (ProductConstant) this.sessionFactory.getCurrentSession().get(ProductConstant.class, id);

    }

}
