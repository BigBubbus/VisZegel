/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.model.Customer;
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
public class CustomerController extends AbstractController {

    @Autowired
    CustomerDAO customerDAO;
    /**
     * @param custom Customer that will be added to Database
     * @return Add Customer Form createCustomer.jsp
     */
    @RequestMapping(value = "/create_customer_result", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String add_customer(Customer custom, Model mod) {
        logger.info("Adding Customer to Database");
        customerDAO.save(custom);
        mod.addAttribute("customer-entity", new Customer());
        return "billing/createCustomer";
    }

    /**
     * @param mod Our Model
     * @return List of Customers
     */
    @RequestMapping(value = "/view_customers", method = RequestMethod.GET)
    public String mainView(Model mod) {
        logger.info("--> Showing all Customers");
        mod.addAttribute("customers", customerDAO.list());
        return "billing/view_customers";
    }

    /**
     * @param cus Customer that will be changed
     * @param mod Our model
     * @return Our List view yet again
     */
    @RequestMapping(value = "/change_delete_result", method = RequestMethod.POST)
    public String change_customer(@ModelAttribute Customer cus, Model mod, @RequestParam(value = "mode", required = false) String mode) {
        logger.info("--> Change Customer, Mode is set to : " + mode);
        if (mode.equals("edit")) {
            customerDAO.save(cus);
        }
        if (mode.equals("delete")) {
            customerDAO.delete(cus);
        }
        mod.addAttribute("customers", customerDAO.list());
        return "billing/view_customers";
    }
}
