/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.sqltest;

import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.ShoppingItem;
import de.fischzegel.viszegel.services.BillingService;
import junit.framework.Assert;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author tnowicki
 */
public class NewClass {
    @Autowired
    BillingService billingService;
    
    
        @Test
    public void ShoppingItemTest() throws Exception {
        Bill billy = new Bill();
        ShoppingItem shopi = new ShoppingItem();
        shopi.setBill(billy);
        billy.getShopping_items().add(shopi);
        billingService.saveBill(billy);
        
    }
        
        @Test
    public void stupidtest() throws Exception {
       assertTrue(true == true);
        
    }
}
