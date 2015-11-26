/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import de.fischzegel.viszegel.Util.Consts;
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

	/**
	 * Initialize a fresh bill
	 * 
	 * @return Bill with shopping item dependencies
	 */
	public Bill initBill() {
		logger.info("--> Initializing Bill");
		Bill bill = new Bill();
		Date myDate = new Date();
		bill.setDate(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
		ShoppingItem item = new ShoppingItem();
		ProductVariable p = new ProductVariable();
		item.setProduct(p);
		item.setBill(bill);
		bill.getShopping_items().add(item);
		return bill;
	}

	public Bill saveBill(Bill bill) {
		logger.info("--> Saving a bill with Method:" + bill.getPayment_method());
		String curr_date = null;
		List<ShoppingItem> remove = new ArrayList<>();
		for (ShoppingItem item : bill.getShopping_items()) {
			if (item.getDatum() != null && !item.getDatum().isEmpty()) {
				logger.info("Setting Datum for products : " + item.getDatum());
				curr_date = item.getDatum();
				remove.add(item);
			} else {
				logger.info("No Date");
				logger.info(item.getProduct().getId());
				item.setBill(bill);
				item.setDatum(curr_date);
				BigDecimal amountItem = item.getProduct().getPrice();
				amountItem = amountItem.multiply(new BigDecimal(item.getProduct().getAmount()));
				item.getProduct().setPrice(amountItem);
				ProductVariable var = item.getProduct();
				var.getShopi().add(item);
				
			}
		}
		logger.info("--> Removing all Date Items");
		bill.getShopping_items().removeAll(remove);
		logger.info("<-- Items Removed");
		billingDAO.saveOrUpdate(bill);
		logger.info("Bill Saved");
		return bill;
	}

	public Bill getBill(Bill bill) {
		logger.info("Got a bill with name:" + bill.getPayment_method());
		Bill fillableBill = billingDAO.getBill(bill.getBill_id());
		String checkDate = "xxx";
		// ShoppingItem dateInsert = new ShoppingItem(checkDate);
		// fillableBill.getShopping_items().add(0, dateInsert);
		for (int i = 0; i < fillableBill.getShopping_items().size(); i++) {
			ShoppingItem billItem = fillableBill.getShopping_items().get(i);
			if (!billItem.getDatum().equals(checkDate)) {
				checkDate = billItem.getDatum();
				ShoppingItem dateInsert = new ShoppingItem(checkDate);
				dateInsert.setBill(fillableBill);
				fillableBill.getShopping_items().add(i, dateInsert);
			} else {
				billItem.setDatum("");
			}

		}
		return fillableBill;
	}

	@Autowired
	protected SessionFactory sessionFactory;

	@Transactional
	public Bill getBillProducts(Bill bill) throws IllegalAccessException, InvocationTargetException {
		logger.info("Filling up Products and resetting ID");
		for (ShoppingItem item : bill.getShopping_items()) {
			if(item.getDatum() != null && !item.getDatum().equals(""))
				continue;
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
			copiedEntity.setId(0);
			if (p != null) {
				logger.info("SETTING PRODUCT FOR BILL : " + p.getDescription());
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

	/**
	 * Fills up the Bill's parameters
	 * 
	 * @param id
	 * @param bill
	 *            the bill required to find out the vat sum for the bill
	 * @throws IOException
	 */
	private void fillParameters(Map<String, Object> parameters, int id, Bill bill) throws IOException {

		String billid = Consts.BILL_PARAMETER_BILL_ID;
		parameters.put("bill_path", new ClassPathResource(Consts.BILL_SUBREPORT_PATH).getFile().getPath());
		parameters.put(billid, id);
		BigDecimal priceNoVat = BigDecimal.ZERO;
		BigDecimal priceVat19 = BigDecimal.ZERO;
		BigDecimal priceVat7 = BigDecimal.ZERO;
		BigDecimal priceTotal = BigDecimal.ZERO;
		for (ShoppingItem item : bill.getShopping_items()) {
			ProductVariable productPrice = item.getProduct();
			// No Vat
			BigDecimal operationVat = productPrice.getPrice();
			priceNoVat = priceNoVat.add(operationVat);
			// Vat 7
			if (productPrice.getBtwCategory().equals("laag")) {
				BigDecimal operationVat7 = productPrice.getPrice();
				operationVat7 = operationVat7.multiply(new BigDecimal(0.07));
				priceVat7 = priceVat7.add(operationVat7);
			}
			// Vat 19
			if (productPrice.getBtwCategory().equals("hoog")) {
				BigDecimal operationVat19 = productPrice.getPrice();
				operationVat19 = operationVat19.multiply(new BigDecimal(0.19));
				priceVat19 = priceVat19.add(operationVat19);
			}

		}
		// Price Total
		logger.debug("Calculation of Vats done " + priceNoVat);
		String btw_number = bill.getCus_bill().getBtw_number();
		if(btw_number == null || btw_number.equals("")){
			priceVat7 = BigDecimal.ZERO;
			priceVat19 = BigDecimal.ZERO;
		}
		priceTotal = priceTotal.add(priceNoVat);
		priceTotal = priceTotal.add(priceVat7);
		priceTotal = priceTotal.add(priceVat19);
		parameters.put(Consts.BILL_PARAMETER_PRICE_NOMWST, priceNoVat.setScale(2, RoundingMode.CEILING));
		parameters.put(Consts.BILL_PARAMETER_PRICE_7, priceVat7.setScale(2, RoundingMode.CEILING));
		parameters.put(Consts.BILL_PARAMETER_PRICE_19, priceVat19.setScale(2, RoundingMode.CEILING));
		parameters.put(Consts.BILL_PARAMETER_PRICE_TOTAL, priceTotal.setScale(2, RoundingMode.CEILING));

	}

	public byte[] generateBill(int id, String path, Bill bill) {
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
				fillParameters(parameters, id, bill);
				logger.debug("---> Processed Parameters");

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
