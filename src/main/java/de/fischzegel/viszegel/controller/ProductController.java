/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.ProductConstant;
import de.fischzegel.viszegel.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tnowicki
 */
@Controller
public class ProductController extends AbstractController {

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = "/create_product_result", method = {RequestMethod.GET, RequestMethod.POST})
    public String add_product(ProductConstant prod, Model mod) {
        logger.info("Adding a product");
        if (prod.getBtwCategory() != null) {
            productDAO.save(prod);
        }
        mod.addAttribute("productEntity", new ProductConstant());
        return "billing/createProduct";
    }

    /**
     * @param cus
     * @param mod
     * @return
     */
    @RequestMapping(value = "/view_products", method = RequestMethod.GET)
    public String mainView(Model mod) {
        mod.addAttribute("products", productDAO.list());
        return "billing/view_products";
    }

    /**
     * @param prod
     * @param mod
     * @param mode
     * @return
     */
    @RequestMapping(value = "/change_delete_result_product", method = RequestMethod.POST)
    public String change_customer(@ModelAttribute ProductConstant prod, Model mod, @RequestParam(value = "mode", required = false) String mode) {
        logger.info("--> Change Product, Mode is set to : " + mode);
        Status state = new Status("OK",mode);
        if (mode.equals("edit")) {
        	logger.info("Saving Product with id : "+prod.getId());
            productDAO.save(prod);
        }
        if (mode.equals("delete")) {
            // ShoppingItems are not passed by form so we have to load
            prod = productDAO.get(prod.getId());
            productDAO.delete(prod);
        }
        mod.addAttribute("status", state);
        mod.addAttribute("products", productDAO.list());
        return "billing/view_products";
    }
}
