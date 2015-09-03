/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tnowicki
 */
@Controller
public class CustomerController extends AbstractController {

    @Autowired
    CustomerService customerService;

    /**
     *
     * @param custom Customer that will be added
     * @return filled view
     */
    @RequestMapping(value = "/create_customer_result", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String add_customer(Customer custom, Model mod) {
        customerService.saveCustomer(custom);
        return "redirect:/create_customer_view";
    }

    @RequestMapping(value = "/create_customer_view", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public ModelAndView add_customer_view(Customer custom, Model mod) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("billing/createCustomer");
        mav.addObject("customer-entity", new Customer());
        return mav;
    }

    /**
     *
     * @param cus
     * @param mod
     * @return
     */
    @RequestMapping(value = "/view_customers", method = RequestMethod.GET)
    public String mainView(@ModelAttribute Customer cus, Model mod) {
        mod.addAttribute("customers", customerService.getCustomers());
        return "billing/view_customers";
    }

    /**
     *
     * @param cus Customer that will be changed
     * @param mod Our model
     * @return
     */
    @RequestMapping(value = "/change_delete_result", method = RequestMethod.POST)
    public String change_customer(@ModelAttribute Customer cus, Model mod, @RequestParam(value = "mode", required = false) String mode) {

        logger.info("Mode is set to : " + mode);
        if (mode.equals("edit")) {
            customerService.saveCustomer(cus);
        }
        if (mode.equals("delete")) {
            customerService.deleteCustomer(cus);
        }
        mod.addAttribute("customers", customerService.getCustomers());
        return "billing/view_customers";
    }
}
