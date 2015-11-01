package de.fischzegel.viszegel.controller;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.fischzegel.viszegel.daos.interfaces.BillDAO;
import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.exception.Known_Exceptions;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.ProductVariable;
import de.fischzegel.viszegel.model.ShoppingItem;
import de.fischzegel.viszegel.services.BillingService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
public class BillingController extends AbstractController {

	@Autowired
	BillingService billingService;
	@Autowired
	BillDAO billingDAO;
	@Autowired
	CustomerDAO customerDAO;
	@Autowired
	ProductDAO prodDAO;


	@Autowired
	com.mchange.v2.c3p0.ComboPooledDataSource dataSource;
	@RequestMapping(value = "/viewBill", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/pdf")
	@ResponseBody
	public byte[] viewBill(Bill bill, HttpServletRequest httpServletRequest) {
		byte[] data = null;
		String pdfPath = null;
		try {
			logger.info("--> Checking bills start");
			billingService.saveBill(bill);
			pdfPath = httpServletRequest.getSession().getServletContext()
					.getRealPath("/WEB-INF/report/temporary.pdf");
			data = billingService.generateBill(bill.getBill_id(), pdfPath);
		} catch (Exception ex) {
			Logger.getLogger(BillingController.class.getName()).log(Level.INFO, null, ex);
		}
		
		dataSource.resetPoolManager();
		return data;

	}

	@RequestMapping(value = "/checkbill", method = { RequestMethod.GET, RequestMethod.POST })
	// public String add_customer(@RequestParam Map<String, String> allParams,
	// Model model) {
	public String checkBill(@RequestParam(value = "billID", required = false) Integer billID,
			HttpServletResponse response, HttpServletRequest httpServletRequest) {
		Bill test = new Bill();
		billingService.saveBill(test);

		return "billing/checkBill";

	}

	@RequestMapping(value = "/createbill", method = { RequestMethod.GET, RequestMethod.POST })
	public String createBill(Model model) throws ParseException {
		logger.info("Creating a new Bill");		
		model.addAttribute("billEntity", billingService.initBill());
		return "billing/createBill";

	}

	/**
	 * Called from createBill.jsp
	 *
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/customer_name_change", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<String> changeCustomerName(Model model, @RequestParam(value = "cusName") String cusName)
			throws ParseException {
		logger.info("--> customer_name_change detected");
		List<String> customersList = customerDAO.getByPartName(cusName);
		return customersList;
	}
	@RequestMapping(value = "/product_description_change", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<String> changeProductDescriptionName(Model model, @RequestParam(value = "shoppingItemName") String shoppingItemName)
			throws ParseException {
		logger.info("--> product_description_change detected");
		List<String> productList = prodDAO.getByPartDesc(shoppingItemName);
		return productList;
	}
	@RequestMapping(value = "/fill_customer", method = { RequestMethod.GET, RequestMethod.POST })
	public String createBill(Bill bill, Model model) throws ParseException {
		logger.info("Filling Customer!");
		try {
			bill = billingService.fillCustomer(bill);
		} catch (Exception e) {
			logger.error(Known_Exceptions.NO_SUCH_CUSTOMER);
		}
		model.addAttribute("billEntity", bill);
		return "billing/createBill";
	}

	@RequestMapping(value = "/fill_bill", method = { RequestMethod.GET, RequestMethod.POST })
	public String fillBill(Bill bill, Model model) throws ParseException {
		model.addAttribute("billEntity", billingService.getBill(bill));
		return "billing/createBill";
	}

	@RequestMapping(value = "/fill_bill_products", method = { RequestMethod.GET, RequestMethod.POST })
	public String fillBillProducts(Bill bill, Model model) throws ParseException, IllegalAccessException, InvocationTargetException {
		model.addAttribute("billEntity", billingService.getBillProducts(bill));
		return "billing/createBill";
	}

	@RequestMapping(value = "/createbill_result", method = { RequestMethod.GET, RequestMethod.POST })
	public String createBill(Bill bill, Model model,
			@RequestParam(value = "addShoppingItem", required = false) boolean addShoppingItem,
			@RequestParam(value = "saveBill", required = false) boolean saveBill) throws ParseException {
		logger.info("Bill received" + bill.getDate());
		bill = billingService.fillBill(bill, true, saveBill);
		model.addAttribute("billEntity", bill);

		return "billing/createBill";

	}
	
	@RequestMapping(value = "/delete_bill_product", method = { RequestMethod.GET, RequestMethod.POST })
	public String deleteShoppingItem(Bill bill, Model model,
			@RequestParam(value = "shoppingItemId", required = true) int shoppingItemId) throws ParseException {
		logger.info("Removing Shopping Item: " + bill.getShopping_items().get(shoppingItemId).getProduct().getDescription());
		bill.getShopping_items().remove(shoppingItemId);
		model.addAttribute("billEntity", bill);
		return "billing/createBill";

	}
}
