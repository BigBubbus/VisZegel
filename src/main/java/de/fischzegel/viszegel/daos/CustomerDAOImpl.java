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
public class CustomerDAOImpl extends AbstractDAOImpl implements CustomerDAO {

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
    public List<String> getByPartName(String name) {        
        String newName = "%"+name+"%";
        logger.info("retrieving Customer with name : " + newName);
        return (List<String>) this.sessionFactory.getCurrentSession().createQuery("select c.name from Customer c where str(c.name) like :cname").setParameter("cname", newName).list();
    }

    @Override
    public Customer get(int id) {
        logger.info("retrieving Customer with id : " + id);
        return (Customer) this.sessionFactory.getCurrentSession().get(Customer.class, id);

    }

    @Override
    public Customer getByName(String name) {
       return (Customer) this.sessionFactory.getCurrentSession().createQuery("from Customer c where str(c.name) = :cname").setParameter("cname", name).uniqueResult();
    }

}
