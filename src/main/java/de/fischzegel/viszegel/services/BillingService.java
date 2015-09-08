/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import de.fischzegel.viszegel.daos.interfaces.BillDAO;
import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.Product;
import de.fischzegel.viszegel.model.ShoppingItem;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author tnowicki
 */
@Service
public class BillingService extends AbstractService {

    public BillingService() {

    }

    public void viewBill() {
//// get the JRXML template as a stream
//        InputStream template = JasperReportsApplication.class
//                .getResourceAsStream("/sampleReport.xml");
//// compile the report from the stream
//        JasperReport report = JasperCompileManager.compileReport(template);
//// fill out the report into a print object, ready for export. 
//        JasperPrint print = JasperFillManager.fillReport(report, new HashMap<String, String>());
//// export it!
//        File pdf = File.createTempFile("output.", ".pdf");
//        JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
    }
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    BillDAO billingDAO;

    @Autowired
    CustomerDAO customerDAO;

    public void saveBill(Bill bill) {
        logger.info("Got a bill with name:" + bill.getPayment_method());
        billingDAO.save(bill);
    }

    public Bill fillBill(Bill bill,boolean addShoppingitem, boolean saveBill) {
        int cusID = bill.getCus_bill().getId();
        Customer cust;
        if (cusID > 0) {
            cust = customerDAO.get(cusID);
            bill.setCus_bill(cust);
            cust.getBills().add(bill);
        }
        if(addShoppingitem){
            ShoppingItem item = new ShoppingItem();
            item.setBill(bill);
            bill.getShopping_items().add(item);
            Product p = new Product();            
            item.setProduct(p);
            p.getShopi().add(item);
        } if(saveBill){
            billingDAO.save(bill);
        }
        return bill;
    }

    public JasperPrint generateBill(int id, String path) {
        try {
            //logger.info(servletContext.getContext(null));
            logger.trace("-> start");
            logger.info("START INFO");
            String outDir = path;
            logger.info(outDir);
            Resource resource = new ClassPathResource("report/Fisch_Zegel.jrxml");
            File pathi = resource.getFile();
            try {
                JasperReport jasperReport = null;

                FileInputStream configStream = new FileInputStream(pathi);
                logger.debug("---> retrieved template");

                JasperDesign jasperDesign = JRXmlLoader.load(configStream);
                logger.debug("---> loaded template");

                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                logger.debug("---> loaded template and list");

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("billID", id);
//            parameters.put("vat_number", payment.getVatNo());

                JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
                JasperExportManager.exportReportToPdfFile(print, outDir);
                logger.debug("---> generated output");
                return print;
            } catch (Exception ex) {

                logger.error(ex.getMessage());
            }

            return null;
        } catch (Exception ex) {

            Logger.getLogger(BillingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
