package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.model.Bill;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.Product;
import de.fischzegel.viszegel.model.ShoppingItem;
import de.fischzegel.viszegel.services.BillingService;
import de.fischzegel.viszegel.services.CustomerService;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class BillingController extends AbstractController {

    @Autowired
    BillingService billingService;
    @Autowired
    CustomerService customerService;

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

        if (mode.toLowerCase().equals("createbill")) {
            return "redirect:/createbill";
        }
        if (mode.toLowerCase().equals("createcustomer")) {
            return "redirect:/create_customer_view";
        }
        if (mode.toLowerCase().equals("viewcustomers")) {
            return "redirect:/view_customers";
        }
        if (mode.toLowerCase().equals("createproduct")) {
            return "redirect:/create_product_view";
        }
        return "login";

    }

    @RequestMapping(value = "/viewBill", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String viewBill(@RequestParam(value = "billID", required = false) Integer billID, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            logger.info("--> Checking bills start");
            billID = 10;
            String pdfResult = httpServletRequest.getSession().getServletContext().getRealPath("/WEB-INF/report/temporary.pdf");
            JasperPrint test = billingService.generateBill(billID, pdfResult);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            JasperExportManager.exportReportToPdfStream(test, out);
            byte[] data = out.toByteArray();

            //To make it a download change "inline" to "attachment"
            logger.info(pdfResult);
            //response.setContentType("application/pdf");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-control", "private");
            response.setDateHeader("Expires", 0);
            response.setContentType("application/pdf");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + pdfResult + "");
            response.setContentLength(data.length);
            logger.info("Content-disposition"
                    + "attachment; filename=" + pdfResult + "");
            response.getOutputStream().write(data);
            response.getOutputStream().flush();
            response.getOutputStream().close();
            response.flushBuffer();
        } catch (Exception ex) {
            Logger.getLogger(BillingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @RequestMapping(value = "/checkbill", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String checkBill(@RequestParam(value = "billID", required = false) Integer billID, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        Bill test = new Bill();
        ShoppingItem st = new ShoppingItem();
        st.setDelivery_text("YO");
        st.setBill(test);
        test.getShopping_items().add(st);
        Customer cus = new Customer();
        cus.setName("FritzCascadus");
        test.setCus_bill(cus);
        cus.getBills().add(test);
        logger.info(test.getBill_id() + " OUR BILL IDDDD");
        billingService.saveBill(test);

        return "billing/checkBill";

    }

    @RequestMapping(value = "/createbill", method = {RequestMethod.GET, RequestMethod.POST})
    public String createBill(Model model) throws ParseException {
        logger.info("Creating a new Bill");
        Bill bill = new Bill();
        Date myDate = new Date();
        bill.setDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
        ShoppingItem item = new ShoppingItem();
        item.setDelivery_text("TESTINGDELIVERY");
        Product p = new Product();
        p.setDescription("SOMETHING NICE");
        item.setProduct(p);
        bill.getShopping_items().add(item);
        model.addAttribute("billEntity", bill);

        return "billing/createBill";

    }

    @RequestMapping(value = "/createbill_result", method = {RequestMethod.GET, RequestMethod.POST})
    public String createBill(Bill bill, Model model, @RequestParam(value = "addShoppingItem", required = false) boolean addShoppingItem) throws ParseException {
        logger.info("Bill received" + bill.getDate());
        logger.info(addShoppingItem);
        for (ShoppingItem item : bill.getShopping_items()) {
            logger.info(item.getDelivery_text());
        }
        bill = billingService.fillBill(bill, addShoppingItem);
        model.addAttribute("billEntity", bill);

        return "billing/createBill";

    }
}
