package de.fischzegel.viszegel.controller;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import de.fischzegel.viszegel.exception.Known_Exceptions;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.ProductBase;
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

	@RequestMapping(value = "/viewBill", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/pdf")
	@ResponseBody
	public byte[] viewBill(Bill bill, HttpServletRequest httpServletRequest) {
		byte[] data = null;
		String pdfPath = null;
		try {
			logger.info("--> Checking bills start");
			billingDAO.save(bill);
			pdfPath = httpServletRequest.getSession().getServletContext()
					.getRealPath("/WEB-INF/report/temporary.pdf");
			JasperPrint test = billingService.generateBill(bill.getBill_id(), pdfPath);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(test, out);
			data = out.toByteArray();
		} catch (Exception ex) {
			Logger.getLogger(BillingController.class.getName()).log(Level.SEVERE, null, ex);
		}
		return data;

	}

	@RequestMapping(value = "/checkbill", method = { RequestMethod.GET, RequestMethod.POST })
	// public String add_customer(@RequestParam Map<String, String> allParams,
	// Model model) {
	public String checkBill(@RequestParam(value = "billID", required = false) Integer billID,
			HttpServletResponse response, HttpServletRequest httpServletRequest) {
		Bill test = new Bill();
		ShoppingItem st = new ShoppingItem();
		st.setDelivery_text("YO");
		st.setBill(test);
		test.getShopping_items().add(st);
		Customer cus = new Customer();
		cus.setName("FritzCascadus");
		test.setCus_bill(cus);
		logger.info(test.getBill_id() + " OUR BILL IDDDD");
		billingService.saveBill(test);

		return "billing/checkBill";

	}

	@RequestMapping(value = "/createbill", method = { RequestMethod.GET, RequestMethod.POST })
	public String createBill(Model model) throws ParseException {
		logger.info("Creating a new Bill");
		Bill bill = new Bill();
		Date myDate = new Date();
		bill.setDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
		ShoppingItem item = new ShoppingItem();
		item.setDelivery_text("TESTINGDELIVERY");
		ProductVariable p = new ProductVariable();
		p.setDescription("SOMETHING NICE");
		item.setProduct(p);
		bill.getShopping_items().add(item);
		model.addAttribute("billEntity", bill);

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
		logger.info(cusName);
		List<String> lili = customerDAO.getByPartName(cusName);
		return lili;
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
}
