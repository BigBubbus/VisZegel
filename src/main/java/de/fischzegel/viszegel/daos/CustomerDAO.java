/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos;

import de.fischzegel.viszegel.model.Customer;
import java.util.List;

/**
 *
 * @author tnowicki
 */

public interface CustomerDAO {

 
    public void save(Customer p);
     
    public List<Customer> list();
     

}
