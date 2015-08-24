package de.fischzegel.viszegel.controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	public String billingMain(HttpServletRequest request, @RequestParam(value = "mode", required = false) String mode,
			Model model) {
		if (mode == null) {
			return "billingIndex";
		} else {
			return "login";
		}

	}

	@RequestMapping(value = "/operation", method = RequestMethod.GET)
	public String superAdminPage(HttpServletRequest request,
			@RequestParam(value = "mode", required = false) String mode,
			@RequestParam(value = "id", required = false) Integer id, Model model) {
		if (mode.toLowerCase().equals("createcustomer")) {
			return "billing/createCustomer";
		}
		return "login";

	}

	@RequestMapping(value = "/create_customer", method = RequestMethod.GET)
	public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
		Iterator it = allParams.entrySet().iterator();
		Customer cus = new Customer();
		while (it.hasNext()) {
			Entry thisEntry = (Entry) it.next();
			String key = (String) thisEntry.getKey();
			String value = (String) thisEntry.getValue();
			if (key.equals("customer_name"))
				cus.setName(value);
			if (key.equals("customer_address"))
				cus.setAdress(value);
			if (key.equals("customer_extra_rules"))
				cus.setExtra_rules(value);
			if (key.equals("customer_house_number"))
				cus.setHouse_number(Integer.parseInt(value));
			if (key.equals("customer_postcode"))
				cus.setPostcode(Integer.parseInt(value));
			if (key.equals("customer_location"))
				cus.setName(value);
			if (key.equals("customer_email"))
				cus.setEmail(value);
			if (key.equals("customer_website"))
				cus.setWebsite(value);
			if (key.equals("customer_btw"))
				cus.setBtw_number(Integer.parseInt(value));
		}
		model.addAttribute("result", "Success!!");
		return "billing/result";

	}

}
