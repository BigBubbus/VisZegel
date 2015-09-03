/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos.interfaces;

import de.fischzegel.viszegel.model.Product;

/**
 *
 * @author tnowicki
 */
public interface ProductDAO {
    public Product get(int id);
    public void save(Product p);
}
