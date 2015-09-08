/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.model.Customer;
import java.util.List;
import javax.transaction.Transactional;


/**
 *
 * @author tnowicki
 */
@Transactional
public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    @Override
    public void save(Customer p) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(p);
    }

    @Override
    public void delete(Customer p) {
        this.sessionFactory.getCurrentSession().delete(p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Customer> list() {
        return this.sessionFactory.getCurrentSession().createQuery("from Customer").list();
    }

    @Override
    public Customer get(int id) {
        logger.info("retrieving Customer with id : " + id);
        return (Customer) this.sessionFactory.getCurrentSession().get(Customer.class, id);

    }

}
