package com.codetreatise.controller;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codetreatise.bean.Customer;
import com.codetreatise.bean.CustomerBalance;
import com.codetreatise.bean.CustomerPaymentStatus;
import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductTableBean;
import com.codetreatise.bean.ProductTransactionLedger;
import com.codetreatise.bean.ProductTransactionType;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.service.CustomerBalanceService;
import com.codetreatise.service.CustomerService;
import com.codetreatise.service.ExpenseService;
import com.codetreatise.service.ExpenseTypeService;
import com.codetreatise.service.ProductStockService;

import javafx.scene.control.TableView;
import model.ConfigurationProperties;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;


@Service
public class SellPageTransactions{
	
	private Invoice invoice;
	
	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public Invoice createInvoice(Customer customer , User user , Shop shop) {
		Invoice invoice = new Invoice();

		invoice.setCustomer(customer);
		invoice.setCreatedBy(user.getName());
		invoice.setUpdatedBy(user.getName());
		invoice.setUpdated(new Timestamp(System.currentTimeMillis()));
		invoice.setShop(shop);
		// Invoice newInvoice = invoiceService.save(invoice);
		return invoice;
	}
	
	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public ProductStock updateReturnProduct(Long id, Invoice invoice , ProductStockService productStockService , 
			Float returnItemDiscount , CustomerPaymentStatus changeProductPaymentType , User user) {

		ProductStock returnProduct = productStockService.findById(id);
		returnItemDiscount = returnProduct.getDiscount();
		changeProductPaymentType = returnProduct.getCustomerPaymentType();
		returnProduct.setProductStockStatus(ProductStockStatus.STOCK_AVAILABLE);
		// returnProduct.setCustomer(null);
		returnProduct.setInvoice(invoice);
		returnProduct.setDiscount(null);
		returnProduct.setQuantity(1);
		returnProduct.setUpdatedBy(user.getName());
		returnProduct.setCustomerPaymentType(null);
		returnProduct.setUpdated(new Timestamp(System.currentTimeMillis()));
		// ProductStock updatedReturnProduct =
		// productService.update(returnProduct);

		return returnProduct;
	}
	
	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public ProductTransactionLedger createProductLedger(Invoice invoice, ProductStock productStock,
			ProductTransactionType productTransactionType, CustomerPaymentStatus customerPaymentStatus , User user) {

		ProductTransactionLedger productTransactionLedger = new ProductTransactionLedger();
		productTransactionLedger.setCustomerPaymentType(customerPaymentStatus);
		productTransactionLedger.setInvoice(invoice);
		productTransactionLedger.setProductStock(productStock);
		productTransactionLedger.setProductTransactionType(productTransactionType);
		productTransactionLedger.setUpdatedBy(user.getName());
		productTransactionLedger.setUpdated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setCreatedBy(user.getName());
		productTransactionLedger.setShop(user.getShop());
		// ProductTransactionLedger newProductTransactionLedger =
		// productTransactionLedgerService.save(productTransactionLedger);
		return productTransactionLedger;
	}
	
	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public ProductStock updateProductStock(Invoice invoice, CustomerPaymentStatus customerPaymentStatus, Long id,
			String listSize , ProductStockService productStockService , User user , String productDiscountAmount
			, String productPriceStr , String discountType) {
		ProductStock productStock = productStockService.findById(id);
		// productStock.setUpdatedBy(user.getName());
		productStock.setInvoice(invoice);
		// productStock.setCustomer(customer);
		productStock.setCustomerPaymentType(customerPaymentStatus);
		productStock.setProductStockStatus(ProductStockStatus.SOLD);
		productStock.setUpdatedBy(user.getName());
		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isNotEmpty(productDiscountAmount)) {

			float totalAmt = Float.parseFloat(productPriceStr);
			float productPrice = productStock.getPriceSelling();

			float productPercentage = (productPrice / totalAmt) * 100;

			if (discountType.equals("percent")) {
				float totalDiscountAmt = Float.parseFloat(productPriceStr)
						* Float.parseFloat(productDiscountAmount) / 100;
				float productWiseDicount = (totalDiscountAmt * productPercentage) / 100;
				productStock.setDiscount(productWiseDicount);
			} else {
				float totalDiscountAmt = Float.parseFloat(productDiscountAmount);
				float productWiseDicount = (totalDiscountAmt * productPercentage) / 100;
				productStock.setDiscount(productWiseDicount);
			}
		}
		else{
			productStock.setDiscount(new Float("0"));
		}

		// ProductStock updatedProductStock =
		// productService.update(productStock);

		return productStock;
	}


	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public String sellBlockElse (Customer customer, CustomerService customerService , String customerId,
			CustomerBalanceService customerBalanceService , CustomerBalance customerBalance , Shop shop , String productTotalAmount , String customerDebitCredit , TableView<ProductTableBean> productTableView ,
			CustomerPaymentStatus changeProductPaymentType , Float returnItemDiscount , CustomerPaymentStatus customerPaymentStatus
			, User user , ProductStockService productStockService , String discountType , String productDiscountAmount , 
			String productPrice , String paidAmount , ExpenseTypeService expenseTypeService , ExpenseService expenseService,
			ConfigurationProperties configurationProperties , String returnItemAmount) {
		
		
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();

		customer = customerService.findById(Long.parseLong(customerId));

		customerBalance = customerBalanceService.findByCustomerAndShop(customer, shop);

		invoice = createInvoice(customer , user , shop);

		float totalAmount = Float.parseFloat(productTotalAmount);

		if (totalAmount < 0 && StringUtils.isEmpty(customerDebitCredit)) {

			customerBalance.setAmount(customerBalance.getAmount() + (totalAmount * (-1)));
			customerBalanceService.update(customerBalance);
		}

		if (StringUtils.isNotEmpty(customerDebitCredit) && (totalAmount >= 0)
				&& (!customerDebitCredit.equals("0.00"))) {

			customerBalance.setAmount(0f);
			customerBalanceService.update(customerBalance);
		}

		else if (StringUtils.isNotEmpty(customerDebitCredit) && (totalAmount < 0)) {
			totalAmount = totalAmount * (-1);

			customerBalance.setAmount(totalAmount);
			customerBalanceService.update(customerBalance);
		}

		List<ProductStock> stocks = new ArrayList<ProductStock>();
		List<Invoice> invoices = new ArrayList<Invoice>();
		List<ProductTransactionLedger> productTransactionLedgers = new ArrayList<ProductTransactionLedger>();
		invoices.add(invoice);
		productTableView.getItems().forEach((item) -> {

			if (item.getDescription().equals("Change Item")) {

				// productStock = updateProductStock(invoice,
				// Long.parseLong(item.getId()));

				ProductStock productStock = updateReturnProduct(Long.parseLong(item.getId()), invoice , 
						productStockService , returnItemDiscount , changeProductPaymentType , user);
				stocks.add(productStock);
				ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
						ProductTransactionType.CHANGE_RETURN, changeProductPaymentType , user);
				productTransactionLedgers.add(productTransactionLedger);
				Map<String, Object> map = new HashMap<String, Object>();
				BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceSelling()));
				BigDecimal discount = new BigDecimal("0");
				if(returnItemDiscount != null) {
					discount =  BigDecimal.valueOf(Float.valueOf(returnItemDiscount)) ;
				}
				amountBigDecimal = amountBigDecimal.subtract(discount);
				BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));
				map.put("amount", amountBigDecimal.negate());
				map.put("productType", "Change");
				map.put("quantity", quantityBigDecimal);
				map.put("productSoldId", productStock.getId().toString());
				dataSource.add(map);

			} else {

				ProductStock productStock = updateProductStock(invoice, customerPaymentStatus,
						Long.parseLong(item.getId()), item.getSize() , productStockService , user , productDiscountAmount , 
						productPrice , discountType);
				stocks.add(productStock);
				ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
						ProductTransactionType.SELL, customerPaymentStatus , user);
				productTransactionLedgers.add(productTransactionLedger);
				Map<String, Object> map = new HashMap<String, Object>();

				BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceSelling()));
				BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));

				map.put("amount", amountBigDecimal);
				map.put("productType", productStock.getProductType().getType());
				map.put("quantity", quantityBigDecimal);
				map.put("productSoldId", productStock.getId().toString());
				dataSource.add(map);
			}

		});
		invoice.setProductStocks(stocks);
		invoice.setProductTransactionLedgers(productTransactionLedgers);
		customer.setInvoice(invoices);
		Customer newCustomer = customerService.save(customer);
		invoice = newCustomer.getInvoice().get(0);
//		product_number.setText("");
		printReceipt(dataSource , invoice , paidAmount , customerDebitCredit , productDiscountAmount , returnItemAmount
				, productPrice , productTotalAmount , customerPaymentStatus , expenseTypeService , shop , user , expenseService,
				configurationProperties);
		return "success";

//		productTableData.removeAll(productTableData);
//		sellingStatus.setText("PRODUCT SOLD........");
		
		
	}
	
	@Transactional (rollbackFor = Exception.class , propagation = Propagation.REQUIRED ,isolation =Isolation.DEFAULT)
	public Expense createExpense(Float amount, ExpenseType expenseType , User user , ExpenseService expenseService) {
		Expense expense = new Expense();
		expense.setAmount(amount);
		expense.setCreatedBy(user.getName());
		expense.setExpenseType(expenseType);
		expense.setShop(user.getShop());

		Expense newExpense = expenseService.save(expense);
		return newExpense;
	}
	
	public void printReceipt(List<Map<String, Object>> dataSource , Invoice invoice , String paidAmount , 
			String customerDebitCredit , String productDiscountAmount , String returnItemAmount , String productPrice
			, String productTotalAmount , CustomerPaymentStatus customerPaymentStatus , ExpenseTypeService expenseTypeService
			,Shop shop , User user , ExpenseService expenseService , ConfigurationProperties configurationProperties) {

		Map<String, Object> jrParameter = new HashMap<String, Object>();

		String customerId = invoice.getCustomer().getId().toString();
		String customerName = invoice.getCustomer().getName();
		String customerMobile = invoice.getCustomer().getMobile();
		String customerAddress = invoice.getCustomer().getAddress();
		String paidAmountStr = StringUtils.isNotEmpty(paidAmount) ? paidAmount : "0";
		String changeAmount = StringUtils.isNotEmpty(paidAmount) ? paidAmount : "0";

		String discount = null;
		String customerBalanceAdjustStr = StringUtils.isNotEmpty(customerDebitCredit) ? customerDebitCredit
				: "0";
		BigDecimal discountBigDecimal = BigDecimal.ZERO;
		BigDecimal customerBalanceAdjust = BigDecimal.valueOf(Double.valueOf(customerBalanceAdjustStr));
		if (StringUtils.isNotEmpty(productDiscountAmount)) {
			if (StringUtils.isNotEmpty(returnItemAmount)) {
				discount = Float.toString(Float.parseFloat(productPrice)
						- Float.parseFloat(productTotalAmount) + Float.parseFloat(returnItemAmount)
						- Float.parseFloat(
								StringUtils.isNotEmpty(customerDebitCredit) ? customerDebitCredit : "0"));
				discountBigDecimal = BigDecimal.valueOf(Double.valueOf(discount));
			} else {
				discount = Float.toString(Float.parseFloat(productPrice)
						- Float.parseFloat(productTotalAmount) - Float.parseFloat(
								StringUtils.isNotEmpty(customerDebitCredit) ? customerDebitCredit : "0"));
				discountBigDecimal = BigDecimal.valueOf(Double.valueOf(discount));
			}
		}

		if (customerPaymentStatus == CustomerPaymentStatus.CARD) {
			BigDecimal totalSell = BigDecimal.ZERO;
			BigDecimal totalAmountPaidInCard = BigDecimal.ZERO;
			for (Map<String, Object> map : dataSource) {
				String productType = (String) map.get("productType");
				if (!productType.equals("Change")) {
					totalSell = totalSell.add((BigDecimal) map.get("amount"));
				}
			}
			totalAmountPaidInCard = totalSell.subtract(discountBigDecimal);

			ExpenseType expenseType = expenseTypeService.findByType("CardToBank");

			createExpense(totalAmountPaidInCard.floatValue(), expenseType , user , expenseService);
		}

		String shopStr;
		shopStr = shop.getName();
		String address = shop.getAddress();
		jrParameter.put("paidAmount", BigDecimal.valueOf(Double.valueOf(paidAmountStr)));
		jrParameter.put("changeAmount", BigDecimal.valueOf(Double.valueOf(changeAmount)));
		
		if(customerPaymentStatus == CustomerPaymentStatus.CASH){
			jrParameter.put("paymentType", "CASH");
		}
		else{
			jrParameter.put("paymentType", "CARD");
		}
		
//		if (invoice.getStocks().get(0).getCustomerPaymentType() != null) {
//			jrParameter.put("paymentType", invoice.getStocks().get(0).getCustomerPaymentType().name());
//		} else {
//			jrParameter.put("paymentType", "");
//		}

		jrParameter.put("customerId", customerId);
		jrParameter.put("customerName", customerName);
		jrParameter.put("customerMobile", customerMobile);
		jrParameter.put("customerAddress", customerAddress);
		jrParameter.put("shop", shopStr);
		jrParameter.put("address", address);
		jrParameter.put("vat", BigDecimal.ZERO);
		jrParameter.put("discountBigDecimal", discountBigDecimal);
		jrParameter.put("customerBalanceAdjust", customerBalanceAdjust);
		jrParameter.put("bigZero", BigDecimal.ZERO);
		jrParameter.put("invoiceId", invoice.getId().toString());
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
		JasperPrint jp = null;
//		 FileChooser fileChooser = new FileChooser();
//		 FileChooser.ExtensionFilter extFilter = new
//		 FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
//		 fileChooser.getExtensionFilters().add(extFilter);
//		 File file = fileChooser.showSaveDialog((Stage)
//		 customer_id.getScene().getWindow());
//		 if (file != null) {
//		 if (!file.exists()) {
		try {
//			 file.createNewFile();
//			 FileOutputStream fop = new FileOutputStream(file);
			net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
					.compileReport(getClass().getResourceAsStream("/jrxml/SalesReceipt.jrxml"));
			jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
			printerJob.defaultPage(pageFormat);
			String printerName = configurationProperties.configurationReader("printer_pos_name", "rahifashion.properties");
			PrintReportToPrinter(jp, printerName);
//			 JasperExportManager.exportReportToPdfStream(jp, fop);
//			 fop.close();
//			clearFields();
		} catch (JRException e) {
//			logger.error("error", e);
			e.printStackTrace();
			return;

		}
//		 }
//		 }

	}
	
	public void PrintReportToPrinter(JasperPrint jasperPrint, String printerName) throws JRException {

		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService selectedService = null;

		// Set the printing settings
		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(new Copies(1));
		if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
			printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
		} else {
			printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
		}
		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(printerName, null));

		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
		configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
		configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
		configuration.setDisplayPageDialog(false);
		configuration.setDisplayPrintDialog(false);

		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setConfiguration(configuration);

		if (services != null && services.length != 0) {
			for (PrintService service : services) {
				String existingPrinter = service.getName();
				if (existingPrinter.equals(printerName)) {
					selectedService = service;
					break;
				}
			}
		}
		if (selectedService != null) {
			try {

				exporter.exportReport();
			} catch (Exception e) {
				System.out.println("JasperReport Error: " + e.getMessage());
			}
		} else {
			System.out.println("JasperReport Error: Printer not found!");
		}
	}

}
