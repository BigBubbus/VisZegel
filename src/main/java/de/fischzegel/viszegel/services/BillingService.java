/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import de.fischzegel.viszegel.daos.HibernateSequenceDAO;
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
public class BillingService extends AbstractService {
	@Autowired
	HibernateSequenceDAO sequenceDAO;
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Autowired
	BillDAO billingDAO;

	@Autowired
	CustomerDAO customerDAO;

	@Autowired
	ProductDAO prodDAO;

	public BillingService() {

	}

	public Bill initBill() {
		Bill bill = new Bill();
		bill.setBill_id(sequenceDAO.getCurrentId().intValue());
		Date myDate = new Date();
		bill.setDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
		ShoppingItem item = new ShoppingItem();
		ProductVariable p = new ProductVariable();
		item.setProduct(p);
		item.setBill(bill);
		bill.getShopping_items().add(item);
		return bill;
	}

	public void saveBill(Bill bill) {
		logger.info("Got a bill with name:" + bill.getPayment_method());
		String curr_date = null;
		List<ShoppingItem> remove = new ArrayList<>();
		for (ShoppingItem item : bill.getShopping_items()) {
			if (item.getDatum() != null && !item.getDatum().isEmpty()){
				logger.info("Setting Datum for products : " +  item.getDatum());
				curr_date = item.getDatum();
				remove.add(item);
			} else {
				logger.info("No Datum");
				item.setBill(bill);
				item.setDatum(curr_date);
				ProductVariable var = item.getProduct();
				var.getShopi().add(item);
			}
		}
		bill.getShopping_items().removeAll(remove);
		billingDAO.saveOrUpdate(bill);
	}

	public Bill getBill(Bill bill) {
		logger.info("Got a bill with name:" + bill.getPayment_method());
		return billingDAO.getBill(bill.getBill_id());
	}

	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional
	public Bill getBillProducts(Bill bill) throws IllegalAccessException, InvocationTargetException {
		logger.info("Filling up Products and resetting ID");
		for (ShoppingItem item : bill.getShopping_items()) {
			ProductVariable pitem = item.getProduct();
			ProductBase p = null;
			if (pitem.getProduct_id() > 0) {
				p = prodDAO.getByProductId(pitem.getProduct_id());
			} else {
				p = prodDAO.getByName(pitem.getDescription());
			}
			ProductVariable copiedEntity = new ProductVariable();
			sessionFactory.getCurrentSession().evict(p);
			BeanUtils.copyProperties(copiedEntity, p);
			if (p != null) {
				logger.info("SETTING PRODUCT FOR BILL : "+p.getDescription());
				item.setProduct(copiedEntity);
			} else
				logger.info("Product was null");
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
			ProductVariable p = new ProductVariable();
			item.setProduct(p);
			p.getShopi().add(item);
			bill.getShopping_items().add(item);
		}
		if (saveBill) {
			billingDAO.saveOrUpdate(bill);
		}
		return bill;
	}

	public byte[] generateBill(int id, String path) {
		try {
			// logger.info(servletContext.getContext(null));
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
				String billid = "bill_id";
				logger.debug(billid +" " +id);
				
				parameters.put("bill_path", new ClassPathResource("report/Bills.jasper").getFile().getPath());
				parameters.put(billid, id);
				// parameters.put("vat_number", payment.getVatNo());

				JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
				logger.debug("---> Creating Printable Bill");
				JasperExportManager.exportReportToPdfFile(print, outDir);
				logger.debug("---> generated output");
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(print, out);
				byte[] data = out.toByteArray();
				return data;
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
