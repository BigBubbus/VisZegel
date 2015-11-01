/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos.interfaces;

import de.fischzegel.viszegel.model.Bill;

/**
 *
 * @author tnowicki
 */
public interface BillDAO {

    public Bill getBill(int id);

    public void saveOrUpdate(Bill b);
}
