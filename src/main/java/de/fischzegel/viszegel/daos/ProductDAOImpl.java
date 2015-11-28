/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import java.nio.charset.Charset;
import java.util.List;

import javax.transaction.Transactional;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.ProductConstant;

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
    public List<String> getByPartDesc(String product_description) {        
        String newName = "%"+product_description+"%";
        Charset.forName("UTF-8").encode(newName);
        logger.info("retrieving Product with name : " + newName);
        return (List<String>) this.sessionFactory.getCurrentSession().createQuery("select p.description from ProductConstant p where str(p.description) like :cname").setParameter("cname", newName).list();
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
    @Override
    public ProductConstant getByName(String name) {
       return (ProductConstant) this.sessionFactory.getCurrentSession().createQuery("from ProductConstant p where str(p.description) = :cname").setParameter("cname", name).list().get(0);
    }
}
