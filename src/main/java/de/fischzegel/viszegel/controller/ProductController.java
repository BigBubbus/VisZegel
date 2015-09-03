/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.Product;
import de.fischzegel.viszegel.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tnowicki
 */
@Controller
public class ProductController extends AbstractController {

    
    @Autowired
    ProductService productService;
    
    @RequestMapping(value = "/create_product_view", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView add_customer_view(Customer custom, Model mod) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("billing/createProduct");
        mav.addObject("productEntity", new Product());
        return mav;
    }
    
        @RequestMapping(value = "/create_product_result", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String add_customer(Product prod, Model mod) {
        productService.saveProduct(prod);
        return "redirect:/create_customer_view";
    }
}
