package com.codetreatise.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.codetreatise.bean.*;
import javafx.application.Platform;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.service.CustomerBalanceService;
import com.codetreatise.service.ExpenseService;
import com.codetreatise.service.ExpenseTypeService;
import com.codetreatise.service.InvoiceService;
import com.codetreatise.service.MerchantService;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ProductTransactionLedgerService;
import com.codetreatise.service.ProductTypeService;
import com.codetreatise.service.SalaryExpenseService;
import com.codetreatise.service.ShopService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class AllReportController implements Initializable {
	
	public static String getBanglaShopName(String shopName) {
		String banglaName = null;
		if(shopName.equals("ABDULLAHPUR")){
			banglaName = "আব্দুল্লাহপুর";
		}
		if(shopName.equals("CHITTAGONG")){
			banglaName = "চট্টগ্রাম";
		}
		if(shopName.equals("GAZIPUR")){
			banglaName = "গাজীপুর";
		}
		if(shopName.equals("RAHA wholesale")){
			banglaName = "রাহা হোলসেল";
		}
		
		return banglaName;
	}

	@FXML
	private Label date;
	@FXML
	private Label status;
	@FXML
	private JFXButton button_back;
	@FXML
	private JFXButton button_DailyClosingReport;
	@FXML
	private JFXButton button_DailyClosingReportBangla;
	@FXML
	private JFXButton button_SellReport;
	@FXML
	private JFXButton button_SellReportBangla;
	@FXML
	private JFXButton button_SellReportWithProfitBangla;
	@FXML
	private JFXButton button_SellReportWithProfit;
	@FXML
	private JFXButton button_StockReport;
	@FXML
	private JFXButton button_MerchantReport;
	@FXML
	private JFXButton button_StockReportBangla;
	@FXML
	private JFXButton button_ExpenseReport;
	@FXML
	private JFXButton button_SalaryExpenseReport;
	@FXML
	private JFXButton button_StockReportLife;
	@FXML
	private JFXButton button_StockFreezeReport;
	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;
	@FXML
	private JFXComboBox<String> product_selling_pointCombo;
	@FXML
	private JFXComboBox<String> merchantCombo;
	private static User user;
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> merchantObservableList = FXCollections.observableArrayList();

	public static void setUser(User user) {
		AllReportController.user = user;
	}
	
	public String getProduct_selling_pointCombo() {
		return product_selling_pointCombo.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_pointCombo(String product_selling_pointCombo) {
		this.product_selling_pointCombo.getSelectionModel().select(product_selling_pointCombo);
	}
	
	public String getMerchantCombo() {
		return merchantCombo.getSelectionModel().getSelectedItem();
	}

	public void setMerchantCombo(String merchantCombo) {
		this.merchantCombo.getSelectionModel().select(merchantCombo);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductStockService productService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private ProductTransactionLedgerService productTransactionLedgerService;

	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private ExpenseTypeService expenseTypeService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private CustomerBalanceService customerBalanceService;

	@Autowired
	private SalaryExpenseService salaryExpenseService;
	
	@Autowired
	private ShopService shopService;
	
	private Shop shop;
	
	private Merchant merchant;

//	private static User user;
//
//	public static User getUser() {
//		return user;
//	}
//
//	public static void setUser(User user) {
//		AllReportController.user = user;
//	}

	@FXML
	void buttonBack_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}
	
	
	public Shop getShop() {

		shop = shopService.findByName(getProduct_selling_pointCombo());

		return shop;

	}
	
	public Merchant getMerchant() {

		merchant = merchantService.findByName(getMerchantCombo());

		return merchant;

	}
	
	@FXML
	void button_StockReportBangla_OnAction(ActionEvent event) throws IOException {
		if ( toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			LocalDate fromLocalDate = null;
			Timestamp fromTimestamp = null;
			Timestamp toTimestamp = null;
			BigDecimal productCount = new BigDecimal("0");
			BigDecimal productBuying = new BigDecimal("0");
			BigDecimal productSelling = new BigDecimal("0");
			BigDecimal productCountTypeWise = new BigDecimal("0");
			BigDecimal productBuyingTypeWise = new BigDecimal("0");
			BigDecimal productSellingTypeWise = new BigDecimal("0");
			if(fromDate.getValue() != null ){
				jrParameter.put("date", " স্টক প্রতিবেদন ".concat(fromDate.getValue().toString().concat(" হতে "))
						.concat(toDate.getValue().toString().concat(" পর্যন্ত -  ".concat(getBanglaShopName(getShop().getName())))));

				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				
				productCount = productService.getAvailableProductCount(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productSelling = productService.getAvailableProductInSellingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				List<ProductType> productTypes = productTypeService.findAll();
				
				for ( ProductType type : productTypes ) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					
					map.put("productType", type.getType());
					map.put("count", productCountTypeWise);
					map.put("buying", productBuyingTypeWise);
					map.put("selling", productSellingTypeWise);
					dataSource.add(map);
				}
			
			}
			else{
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				jrParameter.put("date", " স্টক প্রতিবেদন ".concat(toDate.getValue().toString().concat(" পর্যন্ত ".concat(getBanglaShopName(getShop().getName())))));
				productCount = productService.getAvailableProductCountOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productSelling = productService.getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				
				List<ProductType> productTypes = productTypeService.findAll();
				
				for ( ProductType type : productTypes ) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					
					map.put("productType", type.getType());
					map.put("count", productCountTypeWise);
					map.put("buying", productBuyingTypeWise);
					map.put("selling", productSellingTypeWise);
					dataSource.add(map);
				}
			
			}
			
			jrParameter.put("productCount", productCount);
			jrParameter.put("productBuying", productBuying);
			jrParameter.put("productSelling", productSelling);
			
			dataSource.add(map);

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/StockReportBangla.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}
		else{
			status.setText("Select Shop And To Date ");
		}

	}

	@FXML
	void button_StockReportLife_OnAction(ActionEvent event) throws IOException {
		if (toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
			final List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			final Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			LocalDate fromLocalDate;
			Timestamp fromTimestamp ;
			Timestamp toTimestamp ;
			BigDecimal productCount;
			BigDecimal productBuying;
			BigDecimal productSelling;
			BigDecimal productCountSold;
			BigDecimal productBuyingSold;
			BigDecimal productSellingSold;
			BigDecimal productCountTypeWise;
			BigDecimal productBuyingTypeWise;
			BigDecimal productSellingTypeWise ;
			BigDecimal productCountTypeWiseSold ;
			BigDecimal productBuyingTypeWiseSold ;
			BigDecimal productSellingTypeWiseSold ;
			if (fromDate.getValue() != null) {
				jrParameter.put("date", "Life time Stock Report ".concat((fromDate.getValue()).toString().concat(" to ")).concat((toDate.getValue()).toString().concat(" of ".concat(this.getShop().getName()))));
				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				productCount = productService.getAvailableProductCount(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productSelling = productService.getAvailableProductInSellingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productCountSold = productService.getAvailableProductCount(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp);
				productBuyingSold = productService.getAvailableProductInBuyingPrice(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp);
				productSellingSold = productService.getAvailableProductInSellingPrice(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp);
				List<ProductType> productTypes = productTypeService.findAll();
				for (ProductType type : productTypes) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productCountTypeWiseSold = productService.getAvailableProductCountTypeWise(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp, type);
					productBuyingTypeWiseSold = productService.getAvailableProductInBuyingPriceTypeWise(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp, type);
					productSellingTypeWiseSold = productService.getAvailableProductInSellingPriceTypeWise(ProductStockStatus.SOLD, getShop(), fromTimestamp, toTimestamp, type);
					map.put("productType", type.getType());
					map.put("count", (productCountTypeWise == null) ? BigDecimal.ZERO : productCountTypeWise.add((productCountTypeWiseSold == null) ? BigDecimal.ZERO : productCountTypeWiseSold));
					map.put("buying", (productBuyingTypeWise == null) ? BigDecimal.ZERO : productBuyingTypeWise.add((productBuyingTypeWiseSold == null) ? BigDecimal.ZERO : productBuyingTypeWiseSold));
					map.put("selling", (productSellingTypeWise == null) ? BigDecimal.ZERO : productSellingTypeWise.add((productSellingTypeWiseSold == null) ? BigDecimal.ZERO : productSellingTypeWiseSold));
					dataSource.add(map);
				}
			}
			else {
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				jrParameter.put("date", "Life time Stock Report upto ".concat((toDate.getValue()).toString().concat(" of ".concat(getShop().getName()))));
				productCount = productService.getAvailableProductCountOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productSelling = productService.getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productCountSold = productService.getAvailableProductCountOnlyToDate(ProductStockStatus.SOLD, getShop(), toTimestamp);
				productBuyingSold = productService.getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus.SOLD, getShop(), toTimestamp);
				productSellingSold = productService.getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus.SOLD, getShop(), toTimestamp);
				List<ProductType> productTypes = productTypeService.findAll();
				for ( ProductType type : productTypes) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productCountTypeWiseSold = productService.getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus.SOLD, getShop(), toTimestamp, type);
					productBuyingTypeWiseSold = productService.getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus.SOLD, getShop(), toTimestamp, type);
					productSellingTypeWiseSold = productService.getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus.SOLD, getShop(), toTimestamp, type);
					map.put("productType", type.getType());
					map.put("count", (productCountTypeWise == null) ? BigDecimal.ZERO : productCountTypeWise.add((productCountTypeWiseSold == null) ? BigDecimal.ZERO : productCountTypeWiseSold));
					map.put("buying", (productBuyingTypeWise == null) ? BigDecimal.ZERO : productBuyingTypeWise.add((productBuyingTypeWiseSold == null) ? BigDecimal.ZERO : productBuyingTypeWiseSold));
					map.put("selling", (productSellingTypeWise == null) ? BigDecimal.ZERO : productSellingTypeWise.add((productSellingTypeWiseSold == null) ? BigDecimal.ZERO : productSellingTypeWiseSold));
					dataSource.add(map);
				}
			}
			jrParameter.put("productCount", (productCount == null) ? BigDecimal.ZERO : productCount.add((productCountSold == null) ? BigDecimal.ZERO : productCountSold));
			jrParameter.put("productBuying", (productBuying == null) ? BigDecimal.ZERO : productBuying.add((productBuyingSold == null) ? BigDecimal.ZERO : productBuyingSold));
			jrParameter.put("productSelling", (productSelling == null) ? BigDecimal.ZERO : productSelling.add((productSellingSold == null) ? BigDecimal.ZERO : productSellingSold));
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null && !file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fop = new FileOutputStream(file);
					net.sf.jasperreports.engine.JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jrxml/StockReport.jrxml"));
					jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
					JasperExportManager.exportReportToPdfStream(jp, fop);
					fop.close();
				}
				catch (JRException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			this.status.setText("Select Shop And To Date ");
		}
	}

	@FXML
	void button_StockFreezeReport_OnAction(ActionEvent event) throws IOException {
		if (toDate.getValue() != null && fromDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
			final List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			final Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			LocalDate fromLocalDate;
			Timestamp fromTimestamp ;
			Timestamp toTimestamp ;
			Float productCount;
			Float productBuying;
			Float productSelling;
				jrParameter.put("date", "Stock Freeze Report ".concat((fromDate.getValue()).toString().concat(" to ")).concat((toDate.getValue()).toString().concat(" of ".concat(this.getShop().getName()))));
				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				productCount = productTransactionLedgerService.getTotalStockInCountFromDateRangeAndShop
						(getShop(),fromTimestamp, toTimestamp);
				productBuying = productTransactionLedgerService.getTotalBuyingPriceFromDateRangeAndShop
						(getShop(),fromTimestamp, toTimestamp);
				productSelling = productTransactionLedgerService.getTotalSellingPriceFromDateRangeAndShop
						(getShop(),fromTimestamp, toTimestamp);

			jrParameter.put("productCount", productCount);
			jrParameter.put("productBuying", productBuying);
			jrParameter.put("productSelling", productSelling);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null && !file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fop = new FileOutputStream(file);
					net.sf.jasperreports.engine.JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/jrxml/StockFreezeReport.jrxml"));
					jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
					JasperExportManager.exportReportToPdfStream(jp, fop);
					fop.close();
				}
				catch (JRException | IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			this.status.setText("Select Shop And To Date And From Date ");
		}
	}
	
	@FXML
	void button_StockReport_OnAction(ActionEvent event) throws IOException {
		if ( toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			LocalDate fromLocalDate = null;
			Timestamp fromTimestamp = null;
			Timestamp toTimestamp = null;
			BigDecimal productCount = new BigDecimal("0");
			BigDecimal productBuying = new BigDecimal("0");
			BigDecimal productSelling = new BigDecimal("0");
			BigDecimal productCountTypeWise = new BigDecimal("0");
			BigDecimal productBuyingTypeWise = new BigDecimal("0");
			BigDecimal productSellingTypeWise = new BigDecimal("0");
			if(fromDate.getValue() != null ){
				jrParameter.put("date", " Stock Report ".concat(fromDate.getValue().toString().concat(" to "))
						.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));

				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				productCount = productService.getAvailableProductCount(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				productSelling = productService.getAvailableProductInSellingPrice(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp);
				
				List<ProductType> productTypes = productTypeService.findAll();
				
				for ( ProductType type : productTypes ) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), fromTimestamp, toTimestamp, type);
					
					map.put("productType", type.getType());
					map.put("count", productCountTypeWise);
					map.put("buying", productBuyingTypeWise);
					map.put("selling", productSellingTypeWise);
					dataSource.add(map);
				}
			}
			else{
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				jrParameter.put("date", " Stock Report upto ".concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));
				productCount = productService.getAvailableProductCountOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productBuying = productService.getAvailableProductInBuyingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				productSelling = productService.getAvailableProductInSellingPriceOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp);
				
				List<ProductType> productTypes = productTypeService.findAll();
				
				for ( ProductType type : productTypes ) {
					map = new HashMap<String, Object>();
					productCountTypeWise = productService.getAvailableProductCountOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productBuyingTypeWise = productService.getAvailableProductInBuyingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					productSellingTypeWise = productService.getAvailableProductInSellingPriceOnlyToDateTypeWise(ProductStockStatus.STOCK_AVAILABLE, getShop(), toTimestamp, type);
					
					map.put("productType", type.getType());
					map.put("count", productCountTypeWise);
					map.put("buying", productBuyingTypeWise);
					map.put("selling", productSellingTypeWise);
					dataSource.add(map);
				}
			
			}
			jrParameter.put("productCount", productCount);
			jrParameter.put("productBuying", productBuying);
			jrParameter.put("productSelling", productSelling);
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/StockReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
		else{
			status.setText("Select Shop And To Date ");
		}

	}
	
	@FXML
	void button_MerchantReport_OnAction(ActionEvent event) throws IOException {
		if ( toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())
				&& StringUtils.isNotEmpty(getMerchantCombo())) {
			getMerchant();
			getShop();
			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			LocalDate fromLocalDate = null;
			Timestamp fromTimestamp = null;
			Timestamp toTimestamp = null;
			BigDecimal productCountTotal = new BigDecimal("0");
			BigDecimal productCountAvailable = new BigDecimal("0");
			BigDecimal productBuyingTotal = new BigDecimal("0");
			BigDecimal productBuyingAvailable = new BigDecimal("0");
			BigDecimal productSellingTotal = new BigDecimal("0");
			BigDecimal productSellingAvailable = new BigDecimal("0");
			BigDecimal merchantPayment = new BigDecimal("0");
			if(fromDate.getValue() != null ){
				jrParameter.put("date", " Merchant Report ".concat(fromDate.getValue().toString().concat(" to "))
						.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))).concat(" of ").concat(merchant.getName()));

				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				
				productCountAvailable = productService.getTotalAvailableProductCountMerchantWise(shop, fromTimestamp, toTimestamp, ProductStockStatus.STOCK_AVAILABLE , merchant);		
				productCountTotal = productService.getTotalProductCountMerchantWise(shop, fromTimestamp, toTimestamp , merchant);
				
				productBuyingAvailable = productService.getAvailableProductInBuyingPriceMerchantWise(ProductStockStatus.STOCK_AVAILABLE, shop, fromTimestamp, toTimestamp, merchant);
				productBuyingTotal = productService.getTotalProductInBuyingPriceMerchantWise(shop, fromTimestamp, toTimestamp, merchant);

				productSellingAvailable = productService.getAvailableProductInSellingPriceMerchantWise(ProductStockStatus.STOCK_AVAILABLE, shop, fromTimestamp, toTimestamp, merchant);
				productSellingTotal = productService.getTotalProductInSellingPriceMerchantWise(shop, fromTimestamp, toTimestamp, merchant);
				
				ExpenseType expenseType = expenseTypeService.findByType("Party Payment");
				
				merchantPayment = expenseService.getTotalExpenseMerchantWise(shop, fromTimestamp, toTimestamp, merchant, expenseType);
			}
			else{
				LocalDate toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				jrParameter.put("date", " Merchant Report upto ".concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))).concat(" of ").concat(merchant.getName()));
				
				productCountAvailable = productService.getTotalAvailableProductCountMerchantWiseToDate(shop, toTimestamp, ProductStockStatus.STOCK_AVAILABLE , merchant);		
				productCountTotal = productService.getTotalProductCountMerchantWiseOnlyToDate(shop, toTimestamp, merchant);
				
				productBuyingAvailable = productService.getAvailableProductInBuyingPriceMerchantWiseOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, shop, toTimestamp, merchant);
				productBuyingTotal = productService.getTotalProductInBuyingPriceMerchantWiseOnlyToDate(shop, toTimestamp, merchant);

				productSellingAvailable = productService.getAvailableProductInSellingPriceMerchantWiseOnlyToDate(ProductStockStatus.STOCK_AVAILABLE, shop, toTimestamp, merchant);
				productSellingTotal = productService.getTotalProductInSellingPriceMerchantWiseOnlyToDate(shop, toTimestamp, merchant);
				
				ExpenseType expenseType = expenseTypeService.findByType("Party Payment");
				
				merchantPayment = expenseService.getTotalExpenseMerchantWiseOnlyToDate(shop, toTimestamp, merchant, expenseType);
				
			
			}
			jrParameter.put("productCountTotal", productCountTotal);
			jrParameter.put("productCountAvailable", productCountAvailable);
			jrParameter.put("productBuyingAvailable", productBuyingAvailable);
			jrParameter.put("productBuyingTotal", productBuyingTotal);
			jrParameter.put("productSellingAvailable", productSellingAvailable);
			jrParameter.put("productSellingTotal", productSellingTotal);
			jrParameter.put("merchantPayment", merchantPayment);
//			jrParameter.put("merchantBalance", productBuyingTotal.subtract(merchantPayment));
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/MerchantReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
		else{
			status.setText("Select Shop, To Date and Merchant ");
		}
	}
	
	@FXML
	void button_SellReportWithProfitBangla_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", " বিক্রয় প্রতিবেদন ".concat(fromDate.getValue().toString().concat(" হতে "))
					.concat(toDate.getValue().toString().concat(" পর্যন্ত - ".concat(getBanglaShopName(getShop().getName())))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<ProductStock> stocks = productService.findByShopAndProductStockStatusAndUpdatedBetween(getShop(),
					ProductStockStatus.SOLD, fromTimestamp, toTimestamp);

			for (ProductStock stock : stocks) {
				map = new HashMap<String, Object>();
				map.put("id", stock.getId().toString());
				map.put("payType", stock.getCustomerPaymentType().name());
				map.put("discount", stock.getDiscount());
				map.put("buyingPrice", stock.getPriceBuying());
				map.put("price", stock.getPriceSelling());
				map.put("invoice", stock.getInvoice().getId().toString());
				map.put("type", stock.getProductType().getType());
				map.put("merchant", stock.getMerchant().getName());
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/SellReportWithProfitBangla.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;
					}
				}
			}
		}
	}
	
	@FXML
	void button_SellReportWithProfit_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
			status.setText("Report loading please wait....");
			button_SellReportWithProfit.setDisable(true);
			button_back.setDisable(true);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
					Map<String, Object> jrParameter = new HashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();

					jrParameter.put("date", "Sell Report With Profit from ".concat(fromDate.getValue().toString().concat(" to "))
							.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));

					LocalDate fromLocalDate = fromDate.getValue();
					LocalDate toLocalDate = toDate.getValue();
					Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
					Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

					List<ProductStock> stocks = productService.findByShopAndProductStockStatusAndUpdatedBetween(getShop(),
							ProductStockStatus.SOLD, fromTimestamp, toTimestamp);

					for (ProductStock stock : stocks) {
						map = new HashMap<String, Object>();
						map.put("id", stock.getId().toString());
						map.put("payType", stock.getCustomerPaymentType().name());
						map.put("discount", stock.getDiscount());
						map.put("buyingPrice", stock.getPriceBuying());
						map.put("price", stock.getPriceSelling());
						map.put("invoice", stock.getInvoice().getId().toString());
						map.put("type", stock.getProductType().getType());
						map.put("merchant", stock.getMerchant().getName());
						dataSource.add(map);
					}

					JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);



					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							JasperPrint jp = null;
							FileChooser fileChooser = new FileChooser();
							FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
							fileChooser.getExtensionFilters().add(extFilter);
							File file = fileChooser.showSaveDialog((Stage) button_back.getScene().getWindow());
							if (file != null) {
								if (!file.exists()) {
									try {
										file.createNewFile();
										FileOutputStream fop = new FileOutputStream(file);
										net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
												.compileReport(getClass().getResourceAsStream("/jrxml/SellReportWithProfit.jrxml"));
										jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
										JasperExportManager.exportReportToPdfStream(jp, fop);
										fop.close();
										status.setText("Report loading done....");
										button_SellReportWithProfit.setDisable(false);
										button_back.setDisable(false);
									} catch (JRException | IOException e) {
										e.printStackTrace();
										return;
									}
								}
							}

						}
					});
				}
			});

			thread.start();
		}
	}
	
	@FXML
	void button_SellReportWithBangla_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", " বিক্রয় প্রতিবেদন ".concat(fromDate.getValue().toString().concat(" হতে "))
					.concat(toDate.getValue().toString().concat(" - ".concat(getBanglaShopName(getShop().getName())))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<ProductStock> stocks = productService.findByShopAndProductStockStatusAndUpdatedBetween(getShop(),
					ProductStockStatus.SOLD, fromTimestamp, toTimestamp);

			for (ProductStock stock : stocks) {
				map = new HashMap<String, Object>();
				map.put("id", stock.getId().toString());
				map.put("payType", stock.getCustomerPaymentType().name());
				map.put("discount", stock.getDiscount());
				map.put("price", stock.getPriceSelling());
				map.put("invoice", stock.getInvoice().getId().toString());
				map.put("type", stock.getProductType().getType());
				map.put("merchant", stock.getMerchant().getName());
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/SellReportBangla.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_SellReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			status.setText("Report loading please wait....");
			button_SellReport.setDisable(true);
			button_back.setDisable(true);

			Thread thread= new Thread(new Runnable() {
				@Override
				public void run() {

					List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
					Map<String, Object> jrParameter = new HashMap<String, Object>();
					Map<String, Object> map = new HashMap<String, Object>();

					jrParameter.put("date", "Sell Report from ".concat(fromDate.getValue().toString().concat(" to "))
							.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));

					LocalDate fromLocalDate = fromDate.getValue();
					LocalDate toLocalDate = toDate.getValue();
					Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
					Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

					List<ProductStock> stocks = productService.findByShopAndProductStockStatusAndUpdatedBetween(getShop(),
							ProductStockStatus.SOLD, fromTimestamp, toTimestamp);

//					if(getShop().getName().equals("CHITTAGONG")) {
//						int totalSellCount = stocks.size();
//						int halfSellCount = totalSellCount / 2;
//
//						for (ProductStock stock : stocks) {
//							if(halfSellCount>0){
//								map = new HashMap<String, Object>();
//								map.put("id", stock.getId().toString());
//								map.put("payType", stock.getCustomerPaymentType().name());
//								map.put("discount", stock.getDiscount());
//								map.put("price", stock.getPriceSelling());
//								map.put("invoice", stock.getInvoice().getId().toString());
//								map.put("type", stock.getProductType().getType());
//								map.put("merchant", stock.getMerchant().getName());
//								dataSource.add(map);
//								halfSellCount--;
//							}
//						}
//					}
//					else{
						for (ProductStock stock : stocks) {
							map = new HashMap<String, Object>();
							map.put("id", stock.getId().toString());
							map.put("payType", stock.getCustomerPaymentType().name());
							map.put("discount", stock.getDiscount());
							map.put("price", stock.getPriceSelling());
							map.put("invoice", stock.getInvoice().getId().toString());
							map.put("type", stock.getProductType().getType());
							map.put("merchant", stock.getMerchant().getName());
							dataSource.add(map);
						}
//					}



					JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);

					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							JasperPrint jp = null;
							FileChooser fileChooser = new FileChooser();
							FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
							fileChooser.getExtensionFilters().add(extFilter);
							File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
							if (file != null) {
								if (!file.exists()) {
									try {
										file.createNewFile();
										FileOutputStream fop = new FileOutputStream(file);
										net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
												.compileReport(getClass().getResourceAsStream("/jrxml/SellReport.jrxml"));
										jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
										JasperExportManager.exportReportToPdfStream(jp, fop);
										fop.close();
										status.setText("Report loading done....");
										button_SellReport.setDisable(false);
										button_back.setDisable(false);
									} catch (JRException | IOException e) {
										e.printStackTrace();
										return;

									}
								}
							}

						}
					});

				}
			});


			thread.start();

		}

	}

	@FXML
	void button_ExpenseReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", "Expense Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<Expense> expenses = expenseService.findByShopAndCreatedBetween(getShop(), fromTimestamp,
					toTimestamp);

			for (Expense expense : expenses) {
				map = new HashMap<String, Object>();
				map.put("amount", expense.getAmount());
				map.put("payType", expense.getExpenseType().getType());
				if (expense.getMerchant() != null) {
					map.put("merchant", expense.getMerchant().getName());
				}
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/ExpenseReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_SalaryExpenseReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", "Salary Expense Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<SalaryExpense> salaryExpenses = salaryExpenseService.findByShopAndCreatedBetween(getShop(),
					fromTimestamp, toTimestamp);

			for (SalaryExpense expense : salaryExpenses) {
				map = new HashMap<String, Object>();
				map.put("amount", expense.getAmount());
				map.put("payType", expense.getPayType().getType());
				Date date = new Date(expense.getCreated().getTime());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				map.put("ledgerMonth", expense.getLedgerMonth()+" "+format.format(date));
				map.put("employee", expense.getEmployee().getName());
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/SalaryExpenseReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_DailyClosingReportBangla_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			Float sumOfSellPrice = productService.getSumOfPriceSelling();

			System.out.println(sumOfSellPrice);

			Float totalSellCash = productService.getTotalSell(ProductStockStatus.SOLD, getShop(),
					CustomerPaymentStatus.CASH, fromTimestamp, toTimestamp);
			
			Float totalDiscount = productService.getTotalDiscount(ProductStockStatus.SOLD, getShop(),
					fromTimestamp, toTimestamp);

			Float totalSellCard = productService.getTotalSell(ProductStockStatus.SOLD, getShop(),
					CustomerPaymentStatus.CARD, fromTimestamp, toTimestamp);
			
			Float totalChangeAmountCard = productTransactionLedgerService.getTotalReturnFromDateRangeAndCustomerPaymentStatus(getShop(), fromTimestamp, toTimestamp, 
					CustomerPaymentStatus.CARD.toString());
			
			Float totalChangeAmountCash = productTransactionLedgerService.getTotalReturnFromDateRangeAndCustomerPaymentStatus(getShop(), fromTimestamp, toTimestamp, 
					CustomerPaymentStatus.CASH.toString());

			if (totalSellCard == null) {
				totalSellCard = 0f;
			}
			if (totalSellCash == null) {
				totalSellCash = 0f;
			}
			if (totalChangeAmountCard == null) {
				totalChangeAmountCard = 0f;
			}
			if (totalChangeAmountCash == null) {
				totalChangeAmountCash = 0f;
			}
			totalSellCard = totalSellCard + totalChangeAmountCard;
			totalSellCash = totalSellCash + totalChangeAmountCash;
			Float totalChangeAmount = totalChangeAmountCard + totalChangeAmountCash;
			Float totalSell = totalSellCard + totalSellCash;
			Float previousTotalSell = productService.getTotalSellBeforeFromDate(ProductStockStatus.SOLD, getShop(),
					fromTimestamp);
			System.out.println(totalSell);
			System.out.println(previousTotalSell);
			
			Float totalCustomerBalanceUptoFrom = customerBalanceService.getTotalCustomerBalanceUptoFromTimeStamp(getShop() ,fromTimestamp );
			Float totalCustomerBalanceDateRange = customerBalanceService.getTotalCustomerBalance(getShop() , fromTimestamp, toTimestamp );
			Float totalExpense = expenseService.getTotalExpenseFromDateRange(getShop(), fromTimestamp,
					toTimestamp);
			Float previousTotalExpense = expenseService.getTotalExpenseBeforeFromDate(getShop(), fromTimestamp);

			Float totalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseFromDateRange(getShop(),
					fromTimestamp, toTimestamp);
			Float previousTotalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseBeforeFromDate(getShop(),
					fromTimestamp);

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("empty", "empty");
			if (previousTotalSell == null) {
				previousTotalSell = 0f;
			}
			if (previousTotalExpense == null) {
				previousTotalExpense = 0f;
			}
			if (totalExpense == null) {
				totalExpense = 0f;
			}
			if (totalSalaryExpense == null) {
				totalSalaryExpense = 0f;
			}
			if (previousTotalSalaryExpense == null) {
				previousTotalSalaryExpense = 0f;
			}
			dataSource.add(map);
			jrParameter.put("date", "দৈনিক সমাপনি প্রতিবেদন ".concat(fromDate.getValue().toString().concat(" হতে "))
					.concat(toDate.getValue().toString().concat(" পর্যন্ত -  ".concat(getBanglaShopName(getShop().getName())))));
			jrParameter.put("totalCustomerBalanceUptoFrom", totalCustomerBalanceUptoFrom);
			jrParameter.put("totalCustomerBalanceDateRange", totalCustomerBalanceDateRange);
			jrParameter.put("totalChangeAmount", totalChangeAmount);
			jrParameter.put("totalDiscount", totalDiscount);
			jrParameter.put("totalSellCard", totalSellCard);
			jrParameter.put("totalSellCash", totalSellCash);
			jrParameter.put("totalExpense", totalExpense);
			jrParameter.put("totalSalaryExpense", totalSalaryExpense);
			jrParameter.put("closingBalance", previousTotalSell - previousTotalExpense - previousTotalSalaryExpense);
			jrParameter.put("todayClosingBalance",
					(previousTotalSell - previousTotalExpense - previousTotalSalaryExpense)
							+ (totalSell - totalExpense - totalSalaryExpense - totalChangeAmount));
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager.compileReport(
								getClass().getResourceAsStream("/jrxml/DailyClosingSummaryReportBangla.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}
		}

	}
	
	
	
	@FXML
	void button_DailyClosingReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null && StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			Float sumOfSellPrice = productService.getSumOfPriceSelling();

			System.out.println(sumOfSellPrice);

			Float totalSellCash = productService.getTotalSell(ProductStockStatus.SOLD, getShop(),
					CustomerPaymentStatus.CASH, fromTimestamp, toTimestamp);
			
			Float totalDiscount = productService.getTotalDiscount(ProductStockStatus.SOLD, getShop(),
					fromTimestamp, toTimestamp);

			Float totalSellCard = productService.getTotalSell(ProductStockStatus.SOLD, getShop(),
					CustomerPaymentStatus.CARD, fromTimestamp, toTimestamp);
			
			Float totalChangeAmountCard = productTransactionLedgerService.getTotalReturnFromDateRangeAndCustomerPaymentStatus(getShop(), fromTimestamp, toTimestamp, 
					CustomerPaymentStatus.CARD.toString());
			
			Float totalChangeAmountCash = productTransactionLedgerService.getTotalReturnFromDateRangeAndCustomerPaymentStatus(getShop(), fromTimestamp, toTimestamp, 
					CustomerPaymentStatus.CASH.toString());

			if (totalSellCard == null) {
				totalSellCard = 0f;
			}
			if (totalSellCash == null) {
				totalSellCash = 0f;
			}
			if (totalChangeAmountCard == null) {
				totalChangeAmountCard = 0f;
			}
			if (totalChangeAmountCash == null) {
				totalChangeAmountCash = 0f;
			}
			totalSellCard = totalSellCard + totalChangeAmountCard;
			totalSellCash = totalSellCash + totalChangeAmountCash;
			Float totalChangeAmount = totalChangeAmountCard + totalChangeAmountCash;
			Float totalSell = totalSellCard + totalSellCash;
			Float previousTotalSell = productService.getTotalSellBeforeFromDate(ProductStockStatus.SOLD, getShop(),
					fromTimestamp);
			System.out.println(totalSell);
			System.out.println(previousTotalSell);
			
			Float totalCustomerBalanceUptoFrom = customerBalanceService.getTotalCustomerBalanceUptoFromTimeStamp(getShop() , fromTimestamp );
			Float totalCustomerBalanceDateRange = customerBalanceService.getTotalCustomerBalance(getShop(),fromTimestamp, toTimestamp );
			Float totalExpense = expenseService.getTotalExpenseFromDateRange(getShop(), fromTimestamp,
					toTimestamp);
			Float previousTotalExpense = expenseService.getTotalExpenseBeforeFromDate(getShop(), fromTimestamp);

			Float totalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseFromDateRange(getShop(),
					fromTimestamp, toTimestamp);
			Float previousTotalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseBeforeFromDate(getShop(),
					fromTimestamp);

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("empty", "empty");
			if (previousTotalSell == null) {
				previousTotalSell = 0f;
			}
			if (previousTotalExpense == null) {
				previousTotalExpense = 0f;
			}
			if (totalExpense == null) {
				totalExpense = 0f;
			}
			if (totalSalaryExpense == null) {
				totalSalaryExpense = 0f;
			}
			if (previousTotalSalaryExpense == null) {
				previousTotalSalaryExpense = 0f;
			}
			dataSource.add(map);
			jrParameter.put("date", "Closing Summary Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(getShop().getName()))));
			jrParameter.put("totalCustomerBalanceUptoFrom", totalCustomerBalanceUptoFrom);
			jrParameter.put("totalCustomerBalanceDateRange", totalCustomerBalanceDateRange);
			jrParameter.put("totalChangeAmount", totalChangeAmount);
			jrParameter.put("totalDiscount", totalDiscount);
			jrParameter.put("totalSellCard", totalSellCard);
			jrParameter.put("totalSellCash", totalSellCash);
			jrParameter.put("totalExpense", totalExpense);
			jrParameter.put("totalSalaryExpense", totalSalaryExpense);
			jrParameter.put("closingBalance", previousTotalSell - previousTotalExpense - previousTotalSalaryExpense);
			jrParameter.put("todayClosingBalance",
					(previousTotalSell - previousTotalExpense - previousTotalSalaryExpense)
							+ (totalSell - totalExpense - totalSalaryExpense - totalChangeAmount));
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager.compileReport(
								getClass().getResourceAsStream("/jrxml/DailyClosingSummaryReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}
		}

	}
	
	public ObservableList<String> getShopList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = shopService.findAll();
		productSellingPointObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			productSellingPointObservableList.add(shop.getName());
		}

		return productSellingPointObservableList;
	}
	
	public ObservableList<String> getMerchantList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Merchant> merchants = merchantService.findAll();
		merchantObservableList = FXCollections.observableArrayList();
		for (Merchant merchant : merchants) {
			merchantObservableList.add(merchant.getName());
		}

		return merchantObservableList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		product_selling_pointCombo.setItems(getShopList());
		merchantCombo.setItems(getMerchantList());

		if(user.getShop().getName().equals("CHITTAGONG")){
			button_SellReportBangla.setVisible(false);
			button_SellReportWithProfitBangla.setVisible(false);
			button_SellReportWithProfit.setVisible(false);
		}

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			date.setText(java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  "
					+ String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":"
					+ String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

}
