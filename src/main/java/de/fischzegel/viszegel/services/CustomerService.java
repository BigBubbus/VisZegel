/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import de.fischzegel.viszegel.daos.CustomerDAO;
import de.fischzegel.viszegel.model.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tnowicki
 */
@Service
public class CustomerService extends AbstractService {

    @Autowired
    CustomerDAO customerDAO;

    public void saveCustomer(Customer cus) {
        logger.info("Got a customer with name : " + cus.getName());
        customerDAO.save(cus);
    }

    public List<Customer> getCustomers() {
        return customerDAO.list();
    }
}
