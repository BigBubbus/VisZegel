/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tnowicki
 */
@Service
public class ProductService extends AbstractService {

    @Autowired
    ProductDAO productDAO;

    public void saveProduct(Product prod) {
        logger.info("Got a Product with description : " + prod.getDescription());
        productDAO.save(prod);
    }

}
