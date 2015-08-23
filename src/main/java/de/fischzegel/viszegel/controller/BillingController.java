package de.fischzegel.viszegel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.fischzegel.viszegel.model.Customer;


@Controller
public class BillingController extends AbstractController {
	@RequestMapping(value = "/billingIndex", method = RequestMethod.GET)
	public String billingMain(HttpServletRequest request,
			@RequestParam(value = "mode", required = false) String mode, Model model) {
		if (mode == null) {
			return "billingIndex";
		} else {
			return "login";
		}

	}
	@RequestMapping(value = "/operation", method = RequestMethod.GET)
	public String superAdminPage(HttpServletRequest request,
			@RequestParam(value = "mode", required = false) String mode, Model model) {
		if(mode.toLowerCase().equals("createcustomer")){
			return "billing/createCustomer";
		}
		return "login";

	}
}
