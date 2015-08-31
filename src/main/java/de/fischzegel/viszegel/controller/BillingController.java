package de.fischzegel.viszegel.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.services.BillingService;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BillingController extends AbstractController {

    @Autowired
    BillingService billingService;

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
            return "redirect:/create_customer";
        }
        if (mode.toLowerCase().equals("checkbill")) {
            return "redirect:/checkbill";
        }
        return "login";

    }

    /**
     *
     * @param cus Customer that will be added
     * @return filled view
     */
    @RequestMapping(value = "/create_customer", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public ModelAndView add_customer() {

        logger.info("CREATING CUSTOMER VIEW");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("billing/createCustomer");
        mav.addObject("customer-entity", new Customer());
        return mav;

    }

    @RequestMapping(value = "/create_customer_result", method = RequestMethod.POST)
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String add_customer_result(@ModelAttribute Customer cus, Model mod) {
        mod.addAttribute("result", "Kunde hinzugefügt");
        return "billing/add_customer_result";
    }
    
        @RequestMapping(value = "/view_customers", method = {RequestMethod.GET, RequestMethod.POST})
    public String view_customers(Model mod) {
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Customer cus = new Customer();
            cus.setName(i+ " YO");
            customers.add(cus);
        }
        mod.addAttribute("customers", customers);
        mod.addAttribute("user", new Customer());
        return "billing/view_customers";

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
        return "billing/checkBill";

    }
}
