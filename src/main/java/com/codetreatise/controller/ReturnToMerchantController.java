package com.codetreatise.controller;

import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Invoice;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductTableBean;
import com.codetreatise.bean.ProductTransactionLedger;
import com.codetreatise.bean.ProductTransactionType;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.InvoiceService;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ShopService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class ReturnToMerchantController implements Initializable {
	@FXML
	private Text sellingPoingName;
	@FXML
	private JFXButton button_sell;
	@FXML
	private JFXButton button_Refresh;
	@FXML
	private Text date;
	@FXML
	private JFXTextField product_number;
	@FXML
	private JFXComboBox<String> product_selling_point;
	@FXML
	private Label sellingStatus;
	@FXML
	private JFXButton buttonBack;
	private static Boolean productDuplicateEntryChecker;
//	private static String printerName;
//	private ConfigurationProperties configurationProperties = new ConfigurationProperties();

	public String getProduct_number() {
		return product_number.getText();
	}

	public void setProduct_number(String product_number) {
		this.product_number.setText(product_number);
	}

	public String getProduct_selling_point() {
		return product_selling_point.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_point(String product_selling_point) {
		this.product_selling_point.getSelectionModel().select(product_selling_point);
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
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductStockService productService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private InvoiceService invoiceService;

	private static Shop shop;

	private Invoice invoice;

	private static User user;

	private int loadedProduct;

	public static Shop getShop() {
		return shop;
	}

//	public static void setShop(Shop shop) {
//		ReturnToMerchantController.shop = shop;
//	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ReturnToMerchantController.user = user;
	}

//	private void PrintReportToPrinter(JasperPrint jasperPrint, String printerName) throws JRException {
//
//		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//		PrintService selectedService = null;
//
//		// Set the printing settings
//		PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//		printRequestAttributeSet.add(new Copies(1));
//		if (jasperPrint.getOrientationValue() == net.sf.jasperreports.engine.type.OrientationEnum.LANDSCAPE) {
//			printRequestAttributeSet.add(OrientationRequested.LANDSCAPE);
//		} else {
//			printRequestAttributeSet.add(OrientationRequested.PORTRAIT);
//		}
//		PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
//		printServiceAttributeSet.add(new PrinterName(printerName, null));
//
//		JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//		SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
//		configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
//		configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
//		configuration.setDisplayPageDialog(false);
//		configuration.setDisplayPrintDialog(false);
//
//		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//		exporter.setConfiguration(configuration);
//
//		if (services != null && services.length != 0) {
//			for (PrintService service : services) {
//				String existingPrinter = service.getName();
//				if (existingPrinter.equals(printerName)) {
//					selectedService = service;
//					break;
//				}
//			}
//		}
//		if (selectedService != null) {
//			try {
//
//				exporter.exportReport();
//			} catch (Exception e) {
//				System.out.println("JasperReport Error: " + e.getMessage());
//			}
//		} else {
//			System.out.println("JasperReport Error: Printer not found!");
//		}
//	}

	public void printReceipt(List<Map<String, Object>> dataSource) {

		Map<String, Object> jrParameter = new HashMap<String, Object>();

		String customerId = " ";
		String customerName = "Merchant Return";
		String customerMobile = " ";
		String customerAddress = " ";

		String shop;
		shop = ReturnToMerchantController.shop.getName();
		String address = ReturnToMerchantController.shop.getAddress();

		jrParameter.put("customerId", customerId);
		jrParameter.put("customerName", customerName);
		jrParameter.put("customerMobile", customerMobile);
		jrParameter.put("customerAddress", customerAddress);
		jrParameter.put("shop", shop);
		jrParameter.put("address", address);
		jrParameter.put("vat", BigDecimal.ZERO);
		jrParameter.put("bigZero", BigDecimal.ZERO);
		jrParameter.put("invoiceId", invoice.getId().toString());
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
		JasperPrint jp = null;
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog((Stage) button_sell.getScene().getWindow());
		if (file != null) {
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fop = new FileOutputStream(file);
					net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
							.compileReport(getClass().getResourceAsStream("/jrxml/MerchantReturn.jrxml"));
					jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
//					PrinterJob printerJob = PrinterJob.getPrinterJob();
//					PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
//					printerJob.defaultPage(pageFormat);
//					printerName = configurationProperties.configurationReader("printer_pos_name",
//							"rahifashion.properties");
//					PrintReportToPrinter(jp, printerName);
					JasperExportManager.exportReportToPdfStream(jp, fop);
					fop.close();
					clearFields();
				} catch (JRException | IOException e) {
					e.printStackTrace();
					return;

				}
			}
		}

	}

	private void clearFields() {
		if (!productTableData.isEmpty()) {
			productTableData.removeAll(productTableData);
		}
		product_number.requestFocus();
	}

	private ProductStock updateProductStock(Invoice invoice, Long id, String listSize) {
		ProductStock productStock = productService.findById(id);
		productStock.setInvoice(invoice);
		productStock.setProductStockStatus(ProductStockStatus.MERCHANT_RETURN);
		productStock.setUpdatedBy(user.getName());
		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));

		return productStock;
	}

	private ProductTransactionLedger createProductLedger(Invoice invoice, ProductStock productStock,
			ProductTransactionType productTransactionType) {

		ProductTransactionLedger productTransactionLedger = new ProductTransactionLedger();

		productTransactionLedger.setInvoice(invoice);
		productTransactionLedger.setProductStock(productStock);
		productTransactionLedger.setProductTransactionType(productTransactionType);
		productTransactionLedger.setCreatedBy(user.getName());
		productTransactionLedger.setUpdatedBy(user.getName());
		productTransactionLedger.setCreated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setUpdated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setShop(productStock.getShop());
		return productTransactionLedger;
	}

	private Invoice createInvoice() {
		Invoice invoice = new Invoice();
		invoice.setCreatedBy(user.getName());
		invoice.setUpdatedBy(user.getName());
		invoice.setCreated(new Timestamp(System.currentTimeMillis()));
		invoice.setUpdated(new Timestamp(System.currentTimeMillis()));
		invoice.setShop(shop);
		Invoice newInvoice = invoiceService.save(invoice);
		return newInvoice;
	}

	@FXML
	void button_sell_OnAction(ActionEvent event) {
		button_sell.setStyle("-fx-background-color: #FF1C1C");
		button_sell.setDisable(true);
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
		invoice = createInvoice();

		List<ProductTransactionLedger> productTransactionLedgers = new ArrayList<ProductTransactionLedger>();
		List<ProductStock> stocks = new ArrayList<ProductStock>();
		productTableView.getItems().forEach((item) -> {
			ProductStock productStock = updateProductStock(invoice, Long.parseLong(item.getId()), item.getSize());
			stocks.add(productStock);

			ProductTransactionLedger productTransactionLedger = createProductLedger(invoice, productStock,
					ProductTransactionType.MERCHANT_RETURN);
			productTransactionLedgers.add(productTransactionLedger);

			Map<String, Object> map = new HashMap<String, Object>();

			BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceBuying()));
			BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));

			map.put("amount", amountBigDecimal);
			map.put("productType", productStock.getProductType().getType());
			map.put("merchant", productStock.getMerchant().getName());
			map.put("quantity", quantityBigDecimal);
			map.put("productSoldId", productStock.getId().toString());
			dataSource.add(map);

		});
		invoice.setProductStocks(stocks);
		invoice.setProductTransactionLedgers(productTransactionLedgers);
		Invoice newInvoice = invoiceService.save(invoice);
		invoice = newInvoice;
		printReceipt(dataSource);
		productTableData.removeAll(productTableData);
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

		stageManager.switchScene(FxmlView.SELLPAGE);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		product_selling_point.setItems(getShopList());

		product_selling_point.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String shop = product_selling_point.getValue();

				ReturnToMerchantController.shop = shopService.findByName(shop);

			}
		});

		product_number.requestFocus();
		button_sell.defaultButtonProperty().bind(button_sell.focusedProperty());

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
		// sellingPoingName.setText(shop.getName());

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

		product_number.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(shop);
				if (StringUtils.isEmpty(getProduct_number()) || shop == null) {
					sellingStatus.setText("Please Enter Product Id And Select Shop!!!");
				} else {
					ProductStock productStock = productService.findByIdAndShop(Long.parseLong(getProduct_number()),
							shop);
					if (productStock != null) {
						// if(productStock.getShop().getName().equals(user.getShop().getName())){
						loadedProduct = 1;
						productDuplicateEntryChecker = true;
						productTableView.getItems().forEach((item) -> {
							ProductStock previousStock = productService.findById(Long.parseLong(item.getId()));
							if (previousStock.getProductStockParent().getId().longValue() == productStock
									.getProductStockParent().getId().longValue()) {

								loadedProduct++;
							}
							if (item.getId().equals(getProduct_number())) {

								productDuplicateEntryChecker = false;
							}
						});

						if (productDuplicateEntryChecker) {

							if (productStock.getProductStockStatus().equals(ProductStockStatus.STOCK_AVAILABLE)) {
								List<ProductStock> productStocks = productService
										.findByProductStockParentAndProductStockStatus(
												productStock.getProductStockParent(),
												ProductStockStatus.STOCK_AVAILABLE);
								if (productStock.getProductSize() != null) {
									productTableData.add(new ProductTableBean(getProduct_number(),
											productStock.getQuantity().toString(),
											productStock.getProductSize().getSize(),
											productStock.getPriceBuying().toString(),
											Integer.toString(productStocks.size() - loadedProduct),
											productStock.getMerchant().getName()));
								} else {
									productTableData.add(new ProductTableBean(getProduct_number(),
											productStock.getQuantity().toString(), "Not Available",
											productStock.getPriceBuying().toString(),
											Integer.toString(productStocks.size() - loadedProduct),
											productStock.getMerchant().getName()));
								}
							} else {
								sellingStatus.setText("Product Sold !!!");
							}

						} else {
							sellingStatus.setText("Product Already Added !!!");

						}
					} else {
						sellingStatus.setText("Product Not Found !!!");
					}

				}
				product_number.setText("");
			}
		});

	}

}
