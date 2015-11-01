/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.daos.interfaces;

import java.util.List;

import de.fischzegel.viszegel.model.ProductConstant;

/**
 *
 * @author tnowicki
 */
public interface ProductDAO {
    public ProductConstant get(int id);
    public ProductConstant getByProductId(int id);
    public ProductConstant getByName(String name);
    public void save(ProductConstant p);
    public void delete(ProductConstant p);
    public List<ProductConstant> list();
    public List<String> getByPartDesc(String name);
}
