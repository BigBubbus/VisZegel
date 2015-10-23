/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import de.fischzegel.viszegel.daos.interfaces.BillDAO;
import de.fischzegel.viszegel.daos.interfaces.CustomerDAO;
import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Bill;
import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.ProductBase;
import de.fischzegel.viszegel.model.ProductVariable;
import de.fischzegel.viszegel.model.ShoppingItem;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author tnowicki
 */
@Service
@Transactional
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

    @Autowired
    ProductDAO prodDAO;

    public void saveBill(Bill bill) {
        logger.info("Got a bill with name:" + bill.getPayment_method());
        billingDAO.save(bill);
    }

    public Bill getBill(Bill bill) {
        logger.info("Got a bill with name:" + bill.getPayment_method());
        return billingDAO.getBill(bill.getBill_id());
    }
    @Autowired
    protected SessionFactory sessionFactory;
    public Bill getBillProducts(Bill bill) throws IllegalAccessException, InvocationTargetException {
        logger.info(" Filling up Products and resetting ID");
        for (ShoppingItem item : bill.getShopping_items()) {
            logger.info(item.getProduct().getProduct_id());
            ProductBase p = prodDAO.getByProductId(item.getProduct().getProduct_id());
            ProductVariable pv = new ProductVariable();
            sessionFactory.getCurrentSession().evict(p);
            p.setId(0);
            BeanUtils.copyProperties(pv, p);
            
            if (p != null) {
                logger.info("SETTING PRODUCT FOR BILL : !" + p.getId());
                item.setProduct(pv);
            } else 
                logger.info("Product was null :(");
        }

        return bill;
    }

    /**
     * Fill out our Customer!
     *
     * @param bill
     * @return
     */
    public Bill fillCustomer(Bill bill) {
        Customer cust = bill.getCus_bill();
        if (cust.getName() != null && !cust.getName().equals("")) {
            logger.info("Trying to fill in Customer with name " + cust.getName());
            cust = customerDAO.getByName(cust.getName());
        } else {
            logger.info("Trying to fill in Customer with id " + cust.getId());
            cust = customerDAO.get(cust.getId());
        }
        bill.setCus_bill(cust);
        return bill;
    }

    public Bill fillBill(Bill bill, boolean addShoppingitem, boolean saveBill) {
        if (addShoppingitem) {
            ShoppingItem item = new ShoppingItem();
            item.setBill(bill);
            bill.getShopping_items().add(item);
            ProductVariable p = new ProductVariable();
            item.setProduct(p);
            p.getShopi().add(item);
        }
        if (saveBill) {
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
                parameters.put("billId", id);
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
