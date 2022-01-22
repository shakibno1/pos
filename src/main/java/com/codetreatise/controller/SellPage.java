package com.codetreatise.controller;

import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;

import com.codetreatise.bean.*;
import com.codetreatise.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;
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

@Controller
public class SellPage implements Initializable {
	@FXML
	private Text sellingPoingName;
	@FXML
	private JFXButton button_sell;
	@FXML
	private JFXButton button_Refresh;
	@FXML
	private Text date;
	@FXML
	private JFXTextField customer_id;
	@FXML
	private JFXTextField customer_name;
	@FXML
	private JFXTextField customer_mobile;
	@FXML
	private JFXTextField customer_address;
	@FXML
	private JFXTextField membershipCard;
	@FXML
	private JFXTextField product_number;
	@FXML
	private Text product_description;
	@FXML
	private JFXTextField returnItemNumber;
	@FXML
	private Text productPrice;
	@FXML
	private Text customerDebitCredit;
	@FXML
	private Text productTotalAmount;
	@FXML
	private Text preturnAmount;
	@FXML
	private JFXTextField paidAmount;
	@FXML
	private JFXTextField returnItemAmount;
	@FXML
	private JFXTextField productDiscountAmount;
	@FXML
	private JFXCheckBox discountType;
	@FXML
	private JFXComboBox<String> salesMan;
	@FXML
	private Label sellingStatus;
	@FXML
	private JFXRadioButton paymentType;
	@FXML
	private JFXButton buttonBack;

	public String getSalesMan() {
		return salesMan.getValue();
	}

	public void setSalesMan(JFXComboBox<String> salesMan) {
		this.salesMan = salesMan;
	}

	public String getProductTotalAmount() {
		return productTotalAmount.getText();
	}

	public void setProductTotalAmount(String productTotalAmount) {
		this.productTotalAmount.setText(productTotalAmount);
	}

	public String getCustomer_id() {
		return customer_id.getText();
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id.setText(customer_id);
	}

	public String getCustomer_name() {
		return customer_name.getText();
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name.setText(customer_name);
	}

	public String getCustomer_mobile() {
		return customer_mobile.getText();
	}

	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile.setText(customer_mobile);
	}

	public String getCustomer_address() {
		return customer_address.getText();
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address.setText(customer_address);
	}

	public String getProduct_number() {
		return product_number.getText();
	}

	public void setProduct_number(String product_number) {
		this.product_number.setText(product_number);
	}

	public String getProduct_description() {
		return product_description.getText();
	}

	public void setProduct_description(String product_description) {
		this.product_description.setText(product_description);
	}

	public String getReturnItemNumber() {
		return returnItemNumber.getText();
	}

	public void setReturnItemNumber(String returnItemNumber) {
		this.returnItemNumber.setText(returnItemNumber);
	}

	public String getProductPrice() {
		return productPrice.getText();
	}

	public void setProductPrice(String productPrice) {
		this.productPrice.setText(productPrice);
	}

	public String getCustomerDebitCredit() {
		return customerDebitCredit.getText();
	}

	public void setCustomerDebitCredit(String customerDebitCredit) {
		this.customerDebitCredit.setText(customerDebitCredit);
	}

	public String getPreturnAmount() {
		return preturnAmount.getText();
	}

	public void setPreturnAmount(String preturnAmount) {
		this.preturnAmount.setText(preturnAmount);
	}

	public String getPaidAmount() {
		return paidAmount.getText();
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount.setText(paidAmount);
	}

	public String getReturnItemAmount() {
		return returnItemAmount.getText();
	}

	public void setReturnItemAmount(String returnItemAmount) {
		this.returnItemAmount.setText(returnItemAmount);
	}

	public String getProductDiscountAmount() {
		return productDiscountAmount.getText();
	}

	public void setProductDiscountAmount(String productDiscountAmount) {
		this.productDiscountAmount.setText(productDiscountAmount);
	}

	public String getDiscountType() {
		if (discountType.isSelected()) {
			return "percent";
		} else {
			return "gross";
		}
	}

	public void setDiscountType(Boolean discountType) {
		this.discountType.setSelected(discountType);
	}

	public CustomerPaymentStatus getPaymentType() {
		if (paymentType.isSelected()) {
			return CustomerPaymentStatus.CARD;
		} else {
			return CustomerPaymentStatus.CASH;
		}
	}

	public void setPaymentType(Boolean paymentType) {
		this.paymentType.setSelected(paymentType);
	}

	@FXML
	private TableView<ProductTableBean> productTableView;
	@FXML
	private TableColumn<ProductTableBean, String> productNumberTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productQuantityTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productSizeTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productPriceTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productDescriptionTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productQuantityLeftInStockTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productDeleteTableColumn;

	private ObservableList<ProductTableBean> productTableData = FXCollections.observableArrayList();
	private ObservableList<String> employeeObservableList = FXCollections.observableArrayList();
	private float discount;
	private Float returnItemDiscount;
	private float discountedTotalAmount;
	private float totalAmount;
	private float returnItemAmountHolder;
	private float customerDebitCreditHolder;
	private float changeAmountforProductDiscountOnAction;
	private static String printerName;
	private static Boolean productDuplicateEntryChecker;
	private ConfigurationProperties configurationProperties = new ConfigurationProperties();
	private List<String> productStockIds = new ArrayList<String>();
	private List<String> productParentIds = new ArrayList<String>();

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductStockService productService;

	@Autowired
	private CustomerService customerService;

//	@Autowired
//	private InvoiceService invoiceService;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ExpenseTypeService expenseTypeService;

	@Autowired
	private MembershipCardService membershipCardService;

//	@Autowired
//	private SellPageTransactions sellPageTransactions;

	// @Autowired
	// private InvoiceService invoiceService;

	@Autowired
	private CustomerBalanceService customerBalanceService;

	private static Shop shop;

	// private ProductStock productStock;

	private Customer customer;

	private Invoice invoice;

	private CustomerBalance customerBalance;

	private CustomerPaymentStatus changeProductPaymentType;

	private static User user;

	private int loadedProduct;

	public static Shop getShop() {
		return shop;
	}

	public static void setShop(Shop shop) {
		SellPage.shop = shop;
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		SellPage.user = user;
	}

	public ObservableList<String> getEmployeeList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Employee> employees = employeeService.findAll();
		employeeObservableList = FXCollections.observableArrayList();
		for (Employee employee : employees) {
			if(employee.getStatus()!=null){
				System.out.println(employee.getName());
				System.out.println(employee.getStatus());
				System.out.println(user.getShop().getName());
				System.out.println(employee.getShop().getName());
				if(employee.getStatus() && employee.getShop().getName().equals(user.getShop().getName())){
					employeeObservableList.add(employee.getName());
				}
			}
		}

		return employeeObservableList;
	}

	public Employee getEmployee(){

		if(getSalesMan()!=null){
			Employee employee = employeeService.findByName(getSalesMan());
			return employee;
		}

		return null;
	}

	public Expense createExpense(Float amount, ExpenseType expenseType) {
		Expense expense = new Expense();
		expense.setAmount(amount);
		expense.setCreatedBy(user.getName());
		expense.setUpdatedBy(user.getName());
		expense.setCreated(new Timestamp(System.currentTimeMillis()));
		expense.setUpdated(new Timestamp(System.currentTimeMillis()));
		expense.setExpenseType(expenseType);
		expense.setShop(user.getShop());

		Expense newExpense = expenseService.save(expense);
		return newExpense;
	}

	private void PrintReportToPrinter(JasperPrint jasperPrint, String printerName) throws JRException {

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

	public void printReceipt(List<Map<String, Object>> dataSource) {

		Map<String, Object> jrParameter = new HashMap<String, Object>();

		String customerId = invoice.getCustomer().getId().toString();
		String customerName = invoice.getCustomer().getName();
		String customerMobile = invoice.getCustomer().getMobile();
		String customerAddress = invoice.getCustomer().getAddress();
		String paidAmountStr = StringUtils.isNotEmpty(getPaidAmount()) ? getPaidAmount() : "0";
		String changeAmount = StringUtils.isNotEmpty(getPreturnAmount()) ? getPreturnAmount() : "0";

		String discount = null;
		String customerBalanceAdjustStr = StringUtils.isNotEmpty(getCustomerDebitCredit()) ? getCustomerDebitCredit()
				: "0";
		BigDecimal discountBigDecimal = BigDecimal.ZERO;
		BigDecimal customerBalanceAdjust = BigDecimal.valueOf(Double.valueOf(customerBalanceAdjustStr));
		if (StringUtils.isNotEmpty(getProductDiscountAmount())) {
			if (StringUtils.isNotEmpty(getReturnItemAmount())) {
				discount = Float.toString(Float.parseFloat(getProductPrice())
						- Float.parseFloat(getProductTotalAmount()) + Float.parseFloat(getReturnItemAmount())
						- Float.parseFloat(
						StringUtils.isNotEmpty(getCustomerDebitCredit()) ? getCustomerDebitCredit() : "0"));
				discountBigDecimal = BigDecimal.valueOf(Double.valueOf(discount));
			} else {
				discount = Float.toString(Float.parseFloat(getProductPrice())
						- Float.parseFloat(getProductTotalAmount()) - Float.parseFloat(
						StringUtils.isNotEmpty(getCustomerDebitCredit()) ? getCustomerDebitCredit() : "0"));
				discountBigDecimal = BigDecimal.valueOf(Double.valueOf(discount));
			}
		}

		if (getPaymentType() == CustomerPaymentStatus.CARD) {
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

			createExpense(totalAmountPaidInCard.floatValue(), expenseType);
		}

		String shop;
		shop = SellPage.shop.getName();
		String address = SellPage.shop.getAddress();
		jrParameter.put("paidAmount", BigDecimal.valueOf(Double.valueOf(paidAmountStr)));
		jrParameter.put("changeAmount", BigDecimal.valueOf(Double.valueOf(changeAmount)));

		if(getPaymentType() == CustomerPaymentStatus.CASH){
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
		jrParameter.put("shop", shop);
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
			printerName = configurationProperties.configurationReader("printer_pos_name", "rahifashion.properties");
			PrintReportToPrinter(jp, printerName);
//			 JasperExportManager.exportReportToPdfStream(jp, fop);
//			 fop.close();
			clearFields();
			dataSource = new ArrayList<Map<String, Object>>();
		} catch (JRException e) {
			e.printStackTrace();
			return;

		}
//		 }
//		 }

	}

	private void clearFields() {
		productDiscountAmount.setText("");
		customer_name.setText("");
		customer_mobile.setText("");
		paidAmount.setText("");
		preturnAmount.setText("");
		customerDebitCredit.setText("");
		productPrice.setText("");
		productTotalAmount.setText("");
		returnItemAmount.setText("");
		customer_id.setText("");
		if (!productTableData.isEmpty()) {
			productTableData.removeAll(productTableData);
		}
		productStockIds.clear();
		productParentIds.clear();
		discount = 0f;
		discountedTotalAmount = 0f;
		totalAmount = 0f;
		returnItemAmountHolder = 0f;
		customerDebitCreditHolder = 0f;
		changeAmountforProductDiscountOnAction = 0f;
		product_number.requestFocus();
		invoice = null;
	}

	private Customer createCustomer() {
		Customer customer = new Customer();
		customer.setAddress(getCustomer_address());
		customer.setMobile(getCustomer_mobile());
		customer.setName(getCustomer_name());
		customer.setMembership_status("0");
		customer.setCreatedBy(user.getName());
		customer.setUpdatedBy(user.getName());
		customer.setCreated(new Timestamp(System.currentTimeMillis()));
		customer.setUpdated(new Timestamp(System.currentTimeMillis()));
		// Customer newCustomer = customerService.save(customer);
		return customer;
	}

	private ProductTransactionLedger createProductLedger(Invoice invoice, ProductStock productStock,
														 ProductTransactionType productTransactionType, CustomerPaymentStatus customerPaymentStatus) {

		ProductTransactionLedger productTransactionLedger = new ProductTransactionLedger();
		productTransactionLedger.setCustomerPaymentType(customerPaymentStatus);
		productTransactionLedger.setInvoice(invoice);
		productTransactionLedger.setProductStock(productStock);
		productTransactionLedger.setProductTransactionType(productTransactionType);
		productTransactionLedger.setUpdatedBy(user.getName());
		productTransactionLedger.setCreated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setUpdated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setCreatedBy(user.getName());
		productTransactionLedger.setShop(productStock.getShop());
		// ProductTransactionLedger newProductTransactionLedger =
		// productTransactionLedgerService.save(productTransactionLedger);
		return productTransactionLedger;
	}

	private CustomerBalance createCustomerBalance(String customerBalanceAmount, Customer customer) {
		CustomerBalance customerBalance = new CustomerBalance();
		customerBalance.setCustomer(customer);
		customerBalance.setCreatedBy(user.getName());
		customerBalance.setUpdatedBy(user.getName());
		customerBalance.setCreated(new Timestamp(System.currentTimeMillis()));
		customerBalance.setUpdated(new Timestamp(System.currentTimeMillis()));
		customerBalance.setAmount(Float.parseFloat(customerBalanceAmount));
		customerBalance.setShop(shop);
		CustomerBalance newCustomerBalance = customerBalanceService.save(customerBalance);
		return newCustomerBalance;
	}

	private Invoice createInvoice() {
		Invoice invoice = new Invoice();

		invoice.setCustomer(customer);
		invoice.setCreatedBy(user.getName());
		invoice.setUpdatedBy(user.getName());
		invoice.setCreated(new Timestamp(System.currentTimeMillis()));
		invoice.setUpdated(new Timestamp(System.currentTimeMillis()));
		invoice.setShop(shop);
		// Invoice newInvoice = invoiceService.save(invoice);
		return invoice;
	}

	// private ProductStock updateProductStock(Invoice invoice , Long id){
	//
	//
	// ProductStock productStock = productService.findById(id);
	// productStock.setInvoice(invoice);
	// ProductStock updatedProductStock = productService.update(productStock);
	//
	// return updatedProductStock;
	// }

	private ProductStock updateProductStock(Invoice invoice, CustomerPaymentStatus customerPaymentStatus, Long id,
											String listSize) {
		ProductStock productStock = productService.findById(id);
		// productStock.setUpdatedBy(user.getName());
		productStock.setInvoice(invoice);
		// productStock.setCustomer(customer);
		productStock.setCustomerPaymentType(customerPaymentStatus);
		productStock.setEmployee(getEmployee());
		productStock.setProductStockStatus(ProductStockStatus.SOLD);
		productStock.setUpdatedBy(user.getName());
		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isNotEmpty(getProductDiscountAmount())) {

			float totalAmt = Float.parseFloat(getProductPrice());
			float productPrice = productStock.getPriceSelling();

			float productPercentage = (productPrice / totalAmt) * 100;

			if (discountType.isSelected()) {
				float totalDiscountAmt = Float.parseFloat(getProductPrice())
						* Float.parseFloat(getProductDiscountAmount()) / 100;
				float productWiseDicount = (totalDiscountAmt * productPercentage) / 100;
				productStock.setDiscount(productWiseDicount);
			} else {
				float totalDiscountAmt = Float.parseFloat(getProductDiscountAmount());
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

	private ProductStock updateReturnProduct(Long id, Invoice invoice) {

		ProductStock returnProduct = productService.findById(id);
		returnItemDiscount = returnProduct.getDiscount();
		changeProductPaymentType = returnProduct.getCustomerPaymentType();
		returnProduct.setProductStockStatus(ProductStockStatus.STOCK_AVAILABLE);
		// returnProduct.setCustomer(null);
		returnProduct.setInvoice(invoice);
		returnProduct.setDiscount(null);
		returnProduct.setQuantity(1);
		returnProduct.setEmployee(null);
		returnProduct.setUpdatedBy(user.getName());
		returnProduct.setCustomerPaymentType(null);
		//returnProduct.setUpdated(new Timestamp(System.currentTimeMillis()));
		// ProductStock updatedReturnProduct =
		// productService.update(returnProduct);

		return returnProduct;
	}

	@FXML
	void button_sell_OnAction(ActionEvent event) {
		button_sell.setStyle("-fx-background-color: #FF1C1C");
		button_sell.setDisable(true);

		if (StringUtils.isEmpty(getCustomer_id())) {
			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			if (StringUtils.isEmpty(getCustomer_mobile()) || StringUtils.isEmpty(getCustomer_name())) {
				sellingStatus.setText("Please enter customer mobile and name.");
			} else {
				float totalAmount = Float.parseFloat(getProductTotalAmount());
				String customerBalanceAmount = "0";
				if (totalAmount < 0) {
					customerBalanceAmount = Float.toString(totalAmount);
				}

				customer = createCustomer();
				customerBalance = createCustomerBalance(customerBalanceAmount,customer);
				invoice = createInvoice();

				List<ProductStock> stocks = new ArrayList<ProductStock>();
				List<Invoice> invoices = new ArrayList<Invoice>();
				List<ProductTransactionLedger> productTransactionLedgers = new ArrayList<ProductTransactionLedger>();
				invoices.add(invoice);
				productTableView.getItems().forEach((item) -> {
					ProductStock productStock = updateProductStock(invoice, getPaymentType(),
							Long.parseLong(item.getId()), item.getSize());
					ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
							ProductTransactionType.SELL, getPaymentType());
					productTransactionLedgers.add(productTransactionLedger);
					stocks.add(productStock);
					Map<String, Object> map = new HashMap<String, Object>();

					BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceSelling()));
					BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));

					map.put("amount", amountBigDecimal);
					map.put("productType", productStock.getProductType().getType());
					map.put("quantity", quantityBigDecimal);
					map.put("productSoldId", productStock.getId().toString());
					dataSource.add(map);

				});
				try {
					invoice.setProductStocks(stocks);
					invoice.setProductTransactionLedgers(productTransactionLedgers);
					// Invoice newInvoice = invoiceService.save(invoice);
					customer.setInvoice(invoices);
					Customer newCustomer = customerService.save(customer);
					invoice = newCustomer.getInvoice().get(0);
					printReceipt(dataSource);
					productTableData.removeAll(productTableData);
					sellingStatus.setText("PRODUCT SOLD........Customer Id: "+customer.getId());
				}
				catch(Exception e) {

				};

			}
		} else {

//			String result = sellPageTransactions.sellBlockElse(customer, customerService, getCustomer_id(), customerBalanceService,
//					customerBalance, shop, getProductTotalAmount(), getCustomerDebitCredit(), productTableView, changeProductPaymentType,
//					returnItemDiscount, getPaymentType(), user, productService, getDiscountType(), getProductDiscountAmount(),
//					getProductPrice(), getPaidAmount(), expenseTypeService, expenseService, configurationProperties,
//					getReturnItemAmount());
//
//			if (result.equals("success")) {
//				product_number.setText("");
////				printReceipt(dataSource);
//
//				productTableData.removeAll(productTableData);
//				sellingStatus.setText("PRODUCT SOLD........");
//			}

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();

			customer = customerService.findById(Long.parseLong(getCustomer_id()));

			customerBalance = customerBalanceService.findByCustomerAndShop(customer, shop);
			String customerBalanceAmount = "0";
			if(customerBalance == null){
				customerBalance = createCustomerBalance(customerBalanceAmount,customer);
			}

			invoice = createInvoice();

			float totalAmount = Float.parseFloat(productTotalAmount.getText());

			if (totalAmount < 0 && StringUtils.isEmpty(customerDebitCredit.getText())) {

				customerBalance.setAmount(customerBalance.getAmount() + (totalAmount * (-1)));
				customerBalanceService.update(customerBalance);
			}

			if (StringUtils.isNotEmpty(customerDebitCredit.getText()) && (totalAmount >= 0)
					&& (!customerDebitCredit.getText().equals("0.00"))) {

				customerBalance.setAmount(0f);
				customerBalanceService.update(customerBalance);
			}

			else if (StringUtils.isNotEmpty(customerDebitCredit.getText()) && (totalAmount < 0)) {
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

					ProductStock productStock = updateReturnProduct(Long.parseLong(item.getId()), invoice);
					stocks.add(productStock);
					ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
							ProductTransactionType.CHANGE_RETURN, changeProductPaymentType);
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

					ProductStock productStock = updateProductStock(invoice, getPaymentType(),
							Long.parseLong(item.getId()), item.getSize());
					stocks.add(productStock);
					ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
							ProductTransactionType.SELL, getPaymentType());
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
			product_number.setText("");
			printReceipt(dataSource);

			productTableData.removeAll(productTableData);
			sellingStatus.setText("PRODUCT SOLD........Customer Id: "+customer.getId());
		}
		button_sell.setDisable(false);

	}

	@FXML
	void buttonBack_OnAction(ActionEvent event) throws IOException {

		clearFields();
		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}

	@FXML
	void buttonRefresh_OnAction(ActionEvent event) throws IOException {
		clearFields();
		SellPage.setShop(shop);
		SellPage.setUser(user);

		stageManager.switchScene(FxmlView.SELLPAGE);
	}

	private void productDiscountAmountOnActionTasks(){
		if (StringUtils.isNotEmpty(productDiscountAmount.getText())) {
			if (discountType.isSelected()) {
				discount = Float.parseFloat(productDiscountAmount.getText());

				if (discount <= 100) {

					productTableView.getItems().forEach((item) -> {
						if (item.getDescription().equals("Change Item")) {
							changeAmountforProductDiscountOnAction = Float.parseFloat(item.getAmount());
						}
					});

					discountedTotalAmount = (totalAmount + customerDebitCreditHolder)
							- (((totalAmount + customerDebitCreditHolder
							- changeAmountforProductDiscountOnAction) * discount) / 100)
							- customerDebitCreditHolder;

					productTotalAmount.setText(
							Float.toString(discountedTotalAmount - changeAmountforProductDiscountOnAction));
				} else {
					setProductDiscountAmount("");
					sellingStatus.setText("Discount can't be greater than 100%");
				}

			} else {
				discount = Float.parseFloat(productDiscountAmount.getText());
				if (discount <= totalAmount) {
					discountedTotalAmount = totalAmount - discount;
					productTableView.getItems().forEach((item) -> {
						discount = discount / productTableView.getItems().size();
						if (item.getDescription().equals("Change Item")) {
							changeAmountforProductDiscountOnAction += Float.parseFloat(item.getAmount());
						}
					});
					productTotalAmount.setText(
							Float.toString(discountedTotalAmount - changeAmountforProductDiscountOnAction));
				} else {
					setProductDiscountAmount("");
					sellingStatus.setText("Discount can't be greater than total amount");
				}

			}
		}
	}

	private void customerIdOnActionTasks(){

		customer = customerService.findById(Long.parseLong(getCustomer_id()));

		if(customer != null) {

			customerBalance = customerBalanceService.findByCustomer(customer);

			customer_name.setText(customer.getName());
			customer_mobile.setText(customer.getMobile());
			customer_address.setText(customer.getAddress());

			if(customerBalance != null) {
				customerDebitCredit.setText(customerBalance.getAmount().toString());

				if (customerBalance.getAmount() != null) {
					totalAmount = totalAmount - customerBalance.getAmount();
					if (StringUtils.isEmpty(productTotalAmount.getText())) {
						productTotalAmount.setText(Float.toString(customerBalance.getAmount() * (-1)));
					} else {
						productTotalAmount.setText(Float.toString(
								Float.parseFloat(productTotalAmount.getText()) - customerBalance.getAmount()));
					}
				}
			}

		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		product_number.requestFocus();
		button_sell.defaultButtonProperty().bind(button_sell.focusedProperty());
		salesMan.setItems(getEmployeeList());

		button_sell.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_sell.setStyle("-fx-background-color:#2bff00");

			if (!newValue) {
				button_sell.setStyle("-fx-background-color: #FF1C1C");
			}
		});

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			date.setText(
					DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour)
							+ ":" + String.format("%02d", minute) + ":" + String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		paidAmount.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isNotEmpty(paidAmount.getText())) {

					float paidAmt = Float.parseFloat(paidAmount.getText());
					float totalAmt = Float.parseFloat(productTotalAmount.getText());

					if (paidAmt >= totalAmt) {
						preturnAmount.setText(Float.toString(paidAmt - totalAmt));
					} else {
						setPaidAmount("");
						setPreturnAmount("");
						sellingStatus.setText("Paid Amount Cant Be less than total amount!!!");
					}

				}

			}
		});
		sellingPoingName.setText(shop.getName());

		Callback<TableColumn<ProductTableBean, String>, TableCell<ProductTableBean, String>> deleteCellFactory = new Callback<TableColumn<ProductTableBean, String>, TableCell<ProductTableBean, String>>() {
			@Override
			public TableCell<ProductTableBean, String> call(final TableColumn<ProductTableBean, String> param) {
				final TableCell<ProductTableBean, String> cell = new TableCell<ProductTableBean, String>() {

					final Button btn = new Button("Remove Item");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								ProductTableBean productTableBeanClass = getTableView().getItems().get(getIndex());

								productTableView.getItems().remove(productTableBeanClass);

							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};

		productDeleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
		productNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productQuantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productSizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
		productDescriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		productQuantityLeftInStockTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantityLeftInStock"));
		productTableView.setItems(productTableData);
		productTableView.setEditable(true);
		productQuantityTableColumn.setCellFactory(TextFieldTableCell.<ProductTableBean>forTableColumn());
		productSizeTableColumn.setCellFactory(TextFieldTableCell.<ProductTableBean>forTableColumn());
		productDeleteTableColumn.setCellFactory(deleteCellFactory);

		productTableView.getItems().addListener(new ListChangeListener<ProductTableBean>() {

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends ProductTableBean> c) {
				totalAmount = 0;
				returnItemAmountHolder = 0;
				productTableView.getItems().forEach((item) -> {
					if (!item.getDescription().equals("Change Item")) {
						totalAmount = totalAmount + Float.parseFloat(item.getAmount());

					} else {
						returnItemAmountHolder = returnItemAmountHolder + Float.parseFloat(item.getAmount());
					}

				});
				returnItemAmountHolder = returnItemAmountHolder * (-1);
				setReturnItemAmount(Float.toString(returnItemAmountHolder));
				// returnItemAmount.setText(Float.toString(returnItemAmountHolder
				// * (-1)));
				productPrice.setText(Float.toString(totalAmount));
				if (StringUtils.isNotEmpty(productDiscountAmount.getText())) {
					if (discountType.isSelected()) {
						totalAmount = totalAmount
								- ((totalAmount * Float.parseFloat(productDiscountAmount.getText()) / 100));
					} else {
						totalAmount = totalAmount - Float.parseFloat(productDiscountAmount.getText());
					}
				}
				if (StringUtils.isNotEmpty(customerDebitCredit.getText())) {

					customerDebitCreditHolder = Float.parseFloat(customerDebitCredit.getText());
					totalAmount = totalAmount - customerDebitCreditHolder;

				}
				productTotalAmount.setText(Float.toString(totalAmount + returnItemAmountHolder));
			}
		});

		product_number.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isEmpty(getProduct_number())) {
					sellingStatus.setText("Please Enter Product Id!!!");
				} else {
					ProductStock productStock = productService.findById(Long.parseLong(getProduct_number()));
					if (productStock != null) {
						if (productStock.getShop().getName().equals(user.getShop().getName())) {
							if(productTableData.isEmpty()){
								loadedProduct = 1;
							}
							else{
								loadedProduct = 1;
								for(String s : productParentIds){
									if(s.equals(productStock.getProductStockParent().getId().toString())){
										loadedProduct++;
									}
								}
							}

							productDuplicateEntryChecker = true;

							if(productStockIds.contains(productStock.getId().toString())) {
								productDuplicateEntryChecker = false;
							}

							if (productDuplicateEntryChecker) {

								if (productStock.getProductStockStatus().equals(ProductStockStatus.STOCK_AVAILABLE)) {
									productStockIds.add(productStock.getId().toString());
									productParentIds.add(productStock.getProductStockParent().getId().toString());
									List<ProductStock> productStocks = productService
											.findByProductStockParentAndProductStockStatusAndShop(
													productStock.getProductStockParent(),
													ProductStockStatus.STOCK_AVAILABLE , getShop());
//									System.out.println(productStocks.size());
									if (productStock.getProductSize() != null) {
										productTableData.add(new ProductTableBean(getProduct_number(),
												productStock.getQuantity().toString(),
												productStock.getProductSize().getSize(),
												productStock.getPriceSelling().toString(),
												Integer.toString(productStocks.size() - loadedProduct),
												productStock.getProductDescription()));
									} else {
										productTableData.add(new ProductTableBean(getProduct_number(),
												productStock.getQuantity().toString(), "Not Available",
												productStock.getPriceSelling().toString(),
												Integer.toString(productStocks.size() - loadedProduct),
												productStock.getProductDescription()));
									}
								} else {
									sellingStatus.setText("Product Sold !!!");
								}

							} else {
								sellingStatus.setText("Product Already Added !!!");

							}
						} else {
							sellingStatus.setText("Cannot Sell product of " + productStock.getShop().getName());
						}
					} else {
						sellingStatus.setText("Product Not Found !!!");
					}

				}
				product_number.setText("");
			}
		});

		customer_id.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				customerIdOnActionTasks();

//				customer = customerService.findById(Long.parseLong(getCustomer_id()));
//
//				if(customer != null) {
//
//					customerBalance = customerBalanceService.findByCustomer(customer);
//
//					customer_name.setText(customer.getName());
//					customer_mobile.setText(customer.getMobile());
//					customer_address.setText(customer.getAddress());
//
//					if(customerBalance != null) {
//						customerDebitCredit.setText(customerBalance.getAmount().toString());
//
//						if (customerBalance.getAmount() != null) {
//							totalAmount = totalAmount - customerBalance.getAmount();
//							if (StringUtils.isEmpty(productTotalAmount.getText())) {
//								productTotalAmount.setText(Float.toString(customerBalance.getAmount() * (-1)));
//							} else {
//								productTotalAmount.setText(Float.toString(
//										Float.parseFloat(productTotalAmount.getText()) - customerBalance.getAmount()));
//							}
//						}
//					}
//
//				}



			}
		});

		customer_mobile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				customer = customerService.findByMobile(getCustomer_mobile());

				if(customer != null) {
					customerBalance = customerBalanceService.findByCustomer(customer);

					customer_name.setText(customer.getName());
					customer_id.setText(customer.getId().toString());
					customer_address.setText(customer.getAddress());

					if(customerBalance != null) {

						customerDebitCredit.setText(customerBalance.getAmount().toString());

						if (customerBalance.getAmount() != null) {
							totalAmount = totalAmount - customerBalance.getAmount();
							if (StringUtils.isEmpty(productTotalAmount.getText())) {
								productTotalAmount.setText(Float.toString(customerBalance.getAmount() * (-1)));
							} else {
								productTotalAmount.setText(Float.toString(
										Float.parseFloat(productTotalAmount.getText()) - customerBalance.getAmount()));
							}
						}

					}
				}

			}
		});

		membershipCard.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isNotEmpty(membershipCard.getText())) {
					List<MembershipCard> membershipCards = membershipCardService.findByMembershipCardNumber(membershipCard.getText());

					if(!membershipCards.isEmpty()){
						MembershipCard membershipCard = membershipCards.get(0);

						setDiscountType(true);

						setCustomer_id(membershipCard.getCustomer().getId().toString());
						customerIdOnActionTasks();

						setProductDiscountAmount(membershipCard.getDiscount().toString());
						productDiscountAmountOnActionTasks();
					}
				}
				else {
					sellingStatus.setText("Card Not Found !!!!!");
				}

			}
		});

		productDiscountAmount.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				productDiscountAmountOnActionTasks();
//				if (StringUtils.isNotEmpty(productDiscountAmount.getText())) {
//					if (discountType.isSelected()) {
//						discount = Float.parseFloat(productDiscountAmount.getText());
//
//						if (discount <= 100) {
//
//							productTableView.getItems().forEach((item) -> {
//								if (item.getDescription().equals("Change Item")) {
//									changeAmountforProductDiscountOnAction = Float.parseFloat(item.getAmount());
//								}
//							});
//
//							discountedTotalAmount = (totalAmount + customerDebitCreditHolder)
//									- (((totalAmount + customerDebitCreditHolder
//											- changeAmountforProductDiscountOnAction) * discount) / 100)
//									- customerDebitCreditHolder;
//
//							productTotalAmount.setText(
//									Float.toString(discountedTotalAmount - changeAmountforProductDiscountOnAction));
//						} else {
//							setProductDiscountAmount("");
//							sellingStatus.setText("Discount can't be greater than 100%");
//						}
//
//					} else {
//						discount = Float.parseFloat(productDiscountAmount.getText());
//						if (discount <= totalAmount) {
//							discountedTotalAmount = totalAmount - discount;
//							productTableView.getItems().forEach((item) -> {
//								discount = discount / productTableView.getItems().size();
//								if (item.getDescription().equals("Change Item")) {
//									changeAmountforProductDiscountOnAction = Float.parseFloat(item.getAmount());
//								}
//							});
//							productTotalAmount.setText(
//									Float.toString(discountedTotalAmount - changeAmountforProductDiscountOnAction));
//						} else {
//							setProductDiscountAmount("");
//							sellingStatus.setText("Discount can't be greater than total amount");
//						}
//
//					}
//				}

			}
		});

		returnItemNumber.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				ProductStock returnStock = productService
						.findByIdAndProductStockStatus(Long.parseLong(getReturnItemNumber()), ProductStockStatus.SOLD);
				if (returnStock != null) {

					if (returnStock.getShop().getName().equals(user.getShop().getName())) {
						customer_id.setText(returnStock.getInvoice().getCustomer().getId().toString());
						customer_address.setText(returnStock.getInvoice().getCustomer().getAddress());
						customer_mobile.setText(returnStock.getInvoice().getCustomer().getMobile());
						customer_name.setText(returnStock.getInvoice().getCustomer().getName());

						if (customerBalanceService.findByCustomerAndShop(returnStock.getInvoice().getCustomer(),
								shop) != null) {
							customerDebitCredit.setText(customerBalanceService
									.findByCustomerAndShop(returnStock.getInvoice().getCustomer(), shop).getAmount()
									.toString());
						}

						if (returnStock.getProductSize() != null) {
							productTableData.add(new ProductTableBean(returnStock.getId().toString(),
									returnStock.getQuantity().toString(), returnStock.getProductSize().getSize(),
									Float.toString(returnStock.getPriceSelling()
											- (returnStock.getDiscount() != null ? returnStock.getDiscount() : 0f)),
									"", "Change Item"));
						} else {
							productTableData.add(new ProductTableBean(returnStock.getId().toString(),
									returnStock.getQuantity().toString(), "Not Available",
									Float.toString(returnStock.getPriceSelling()
											- (returnStock.getDiscount() != null ? returnStock.getDiscount() : 0f)),
									"", "Change Item"));
						}
					} else {
						sellingStatus.setText("Cant return product of " + returnStock.getShop().getName());
					}

				} else {
					sellingStatus.setText("Not Found!!!");
				}

				returnItemNumber.setText("");

			}
		});

	}

}
