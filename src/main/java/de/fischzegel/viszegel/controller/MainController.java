/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author tnowicki
 */
@Controller
public class MainController extends AbstractController {

    @RequestMapping(value = "/operation", method = RequestMethod.GET)
    public String superAdminPage(HttpServletRequest request,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "id", required = false) Integer id, Model model) {
        logger.info("Forwarding request for : " + mode);

        // -------------------
        // Calls to Billing Controller
        // -------------------
        if (mode.toLowerCase().equals("createbill")) {
            return "redirect:/createbill";
        }

        // -------------------
        // Calls to Customer Controller
        // -------------------
        if (mode.toLowerCase().equals("createcustomer")) {
            return "redirect:/create_customer_view";
        }
        if (mode.toLowerCase().equals("viewcustomers")) {
            return "redirect:/view_customers";
        }

        // -------------------
        // Calls to Product Controller
        // -------------------
        if (mode.toLowerCase().equals("createproduct")) {
            return "redirect:/create_product_view";
        }
        if (mode.toLowerCase().equals("viewproducts")) {
            return "redirect:/view_products";
        }
        return "login";

    }
}
