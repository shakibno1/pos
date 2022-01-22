package com.codetreatise.controller;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.ProductReprintTableBean;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.ProductType;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ProductTypeService;
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
import javafx.util.Callback;
import javafx.util.Duration;
import model.ConfigurationProperties;

@Controller
public class EditProductController implements Initializable {

	@FXML
	private JFXTextField product_id;
	@FXML
	private JFXTextField product_quantity;
	@FXML
	private JFXTextField product_buying_price;
	@FXML
	private JFXTextField product_selling_price;
	@FXML
	private JFXComboBox<String> product_type;
	@FXML
	private Text merchant;
	@FXML
	private Text size;
	@FXML
	private JFXButton button_EditProduct;
	@FXML
	private JFXButton button_AddToEditProduct;
	@FXML
	private JFXButton button_Back;
	@FXML
	private Label productInsertStatus;
	@FXML
	private Label currentDateTime;
	@FXML
	private JFXComboBox<String> product_selling_pointCombo;
	private ConfigurationProperties configurationProperties = new ConfigurationProperties();
	private static String printerName;
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productTypeObservableList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<ProductReprintTableBean> productEditTableView;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditNumberTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditSizeTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditBuyingPriceTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditSellingPriceTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditShopTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditMerchantTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productEditDeleteTableColumn;

	private ObservableList<ProductReprintTableBean> productEditTableData = FXCollections.observableArrayList();
	
	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductStockService productService;

	@Autowired
	private ShopService shopService;

	@Autowired
	private ProductTypeService productTypeService;
	
	private ProductType productType;
	private float priceSelling;
	private String type;
	
	public String getProduct_quantity() {
		return product_quantity.getText();
	}

	public void setProduct_quantity(String product_quantity) {
		this.product_quantity.setText(product_quantity);
	}

	public Float getProduct_buying_price() {
		return Float.parseFloat(product_buying_price.getText());
	}

	public void setProduct_buying_price(String product_buying_price) {
		this.product_buying_price.setText(product_buying_price);
	}

	public String getMerchant() {
		return merchant.getText();
	}

	public void setMerchant(String merchant) {
		this.merchant.setText(merchant);
	}

	public String getSize() {
		return size.getText();
	}

	public void setSize(String size) {
		this.size.setText(size);
	}

	public String getProduct_id() {
		return product_id.getText();
	}

	public void setProduct_id(String product_id) {
		this.product_id.setText(product_id);
	}

	public Float getProduct_selling_price() {
		return Float.parseFloat(product_selling_price.getText());
	}

	public void setProduct_selling_price(String product_selling_price) {
		this.product_selling_price.setText(product_selling_price);
	}

	public String getProduct_selling_pointCombo() {
		return product_selling_pointCombo.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_pointCombo(String product_selling_pointCombo) {
		this.product_selling_pointCombo.getSelectionModel().select(product_selling_pointCombo);
	}
	
	public String getProduct_type() {
		return product_type.getSelectionModel().getSelectedItem();
	}

	public void setProduct_type(String product_type) {
		this.product_type.getSelectionModel().select(product_type);
	}

	private static User user;

	public static User getUser() {
		return user;
	}
	
	public static void setUser(User newUser ) {
		EditProductController.user = newUser;
	}

	protected static double fromCMToPPI(double cm) {
		return toPPI(cm * 0.393700787);
	}

	protected static double toPPI(double inch) {
		return inch * 72d;
	}

	public static PrintService findPrintService(String printerName) {
		for (PrintService service : java.awt.print.PrinterJob.lookupPrintServices()) {
			System.out.println(service.getName());
			if (service.getName().equalsIgnoreCase(printerName))
				return service;
		}

		return null;
	}

	public void printLabel(String printerName, String s) {
		// find the printService of name printerName
		PrintService ps = findPrintService(printerName);
		// create a printerJob
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		Paper paper = pf.getPaper();
		double width = fromCMToPPI(3.5);
		double height = fromCMToPPI(3.1);
		paper.setSize(width, height);
		paper.setImageableArea(fromCMToPPI(0.25), fromCMToPPI(0.5), width - fromCMToPPI(0.35), height - fromCMToPPI(1));
		pf.setOrientation(PageFormat.PORTRAIT);
		pf.setPaper(paper);
		try {
			job.setPrintService(ps);

			DocPrintJob docJob = ps.createPrintJob();
			byte[] by = s.getBytes();
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			Doc doc = new SimpleDoc(by, flavor, null);
			docJob.print(doc, null);
		} catch (PrinterException | PrintException e) {
			e.printStackTrace();
		}
	}
	
	private ProductStock updateProduct(Long id , Float sellingPrice , Float buyingPrice , ProductType type){
		
		ProductStock productStock = productService.findById(id);
		
		productStock.setPriceSelling(sellingPrice);
		productStock.setPriceBuying(buyingPrice);
		productStock.setProductType(type);
		productStock.setUpdatedBy(user.getName());
		// commented because edit must not include in stock report
//		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
		ProductStock updatedProduct = productService.update(productStock);
		
		return updatedProduct;
	}
	
	private void clearfields(){
		setMerchant("");
		setProduct_buying_price("");
		setProduct_id("");
		setProduct_selling_price("");
		setProduct_quantity("");
		product_type.getSelectionModel().clearSelection();
		setSize("");
	}

	@FXML
	void button_EditProduct_OnAction(ActionEvent event) throws IOException, InterruptedException {
		button_EditProduct.setStyle("-fx-background-color: #FF6C6C");
		
		if(productEditTableView.getItems()!=null){
			productEditTableView.getItems().forEach((item) -> {
				
				ProductStock productStock = updateProduct(Long.parseLong(item.getId()) ,
						Float.parseFloat(item.getQuantityLeftInStock()) , Float.parseFloat(item.getAmount()) , 
						getProductType(item.getSize()));
				printerName = configurationProperties.configurationReader("printer_label_name","rahifashion.properties");
					String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+item.getId()+"^FS^ADN,10,14^FO300,125^FD"
							+item.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+item.getQuantityLeftInStock()+".00 ^FS^ADN,10,14^FO345,20^FD"+item.getDescription().substring(0, 3)+"^FS^ADN,10,14^FO300,38^FD"
							+item.getSize()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+item.getPrintingStatus()+"^FS^XZ";
							System.out.println("ZPL  " + printingZPLCommand);
							printLabel(printerName,printingZPLCommand);
							
				
			});
			product_id.requestFocus();
			productInsertStatus.setText("Product Edited");
		}
		productEditTableData.clear();

//		if (!productEditTableData.isEmpty()) {
//			
//			ProductStock productStock = updateProduct(Long.parseLong(getProduct_id()));
//			printerName = configurationProperties.configurationReader("printer_label_name","rahifashion.properties");
//			String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+productStock.getId()+"^FS^ADN,10,14^FO300,125^FD"
//					+productStock.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+getProduct_selling_price().intValue()+".00 ^FS^ADN,10,14^FO345,20^FD"+productStock.getShop().getName().substring(0, 3)+"^FS^ADN,10,14^FO300,38^FD"
//					+getProduct_type()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+productStock.getMerchant().getCode()+"^FS^XZ";
//			
//			if(priceSelling != getProduct_selling_price() || (!type.equals(getProduct_type()))){
//				
//				System.out.println("ZPL  " + printingZPLCommand);
//				printLabel(printerName,printingZPLCommand);
//			}
//			clearfields();
//			
//			product_id.requestFocus();
//			
//			productInsertStatus.setText("Product Edited Id = "+productStock.getId().toString());
//			
//		} else {
//			productInsertStatus.setText("Please Select A Product");
//		}

	}
	
	@FXML
	void button_AddToEdit_OnAction(ActionEvent event) throws IOException {
		
		if (StringUtils.isNotEmpty(getProduct_selling_pointCombo()) && StringUtils.isNotEmpty(getProduct_id())) {
			productEditTableData.add(new ProductReprintTableBean(getProduct_id(), "", getProduct_type(),
					Integer.toString(getProduct_buying_price().intValue()), 
					Integer.toString(getProduct_selling_price().intValue()), getProduct_selling_pointCombo(), getMerchant()));
			
			ProductStock loadedProduct =  productService.findById(Long.parseLong(getProduct_id()));
			
			List<ProductStock> similarProducts = productService.findByProductStockParentAndProductStockStatusAndShop(
					loadedProduct.getProductStockParent(), loadedProduct.getProductStockStatus(), loadedProduct.getShop());
			
			if(!similarProducts.isEmpty()){
				for ( ProductStock stock : similarProducts ){
					if(!getProduct_id().equals(stock.getId().toString())){
						productEditTableData.add(new ProductReprintTableBean(stock.getId().toString(), "", getProduct_type(),
								Integer.toString(getProduct_buying_price().intValue()), 
								Integer.toString(getProduct_selling_price().intValue()), getProduct_selling_pointCombo(), getMerchant()));
					}
					
				}
			}
			
			clearfields();
		}
		
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		productEditTableData.clear();
		stageManager.switchScene(FxmlView.FIRSTPAGE);

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
	
	public ProductType getProductType(String type) {

		productType = productTypeService.findByType(type);

		return productType;

	}
	
	public ObservableList<String> getProductTypeList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<ProductType> types = productTypeService.findAll();
		productTypeObservableList = FXCollections.observableArrayList();
		for (ProductType type : types) {
			productTypeObservableList.add(type.getType());
		}

		return productTypeObservableList;
	}
	
	public Shop getShop() {
		
		Shop shop = shopService.findByName(getProduct_selling_pointCombo());
		
		return shop;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Callback<TableColumn<ProductReprintTableBean, String>, TableCell<ProductReprintTableBean, String>> deleteCellFactory = new Callback<TableColumn<ProductReprintTableBean, String>, TableCell<ProductReprintTableBean, String>>() {
			@Override
			public TableCell<ProductReprintTableBean, String> call(final TableColumn<ProductReprintTableBean, String> param) {
				final TableCell<ProductReprintTableBean, String> cell = new TableCell<ProductReprintTableBean, String>() {

					final Button btn = new Button("Remove");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								ProductReprintTableBean ProductReprintTableBeanClass = getTableView().getItems().get(getIndex());

								productEditTableView.getItems().remove(ProductReprintTableBeanClass);

							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		productEditDeleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
		productEditNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productEditSizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
		productEditBuyingPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		productEditSellingPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantityLeftInStock"));
		productEditShopTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		productEditMerchantTableColumn.setCellValueFactory(new PropertyValueFactory<>("printingStatus"));
		productEditTableView.setItems(productEditTableData);
		productEditTableView.setEditable(true);
		productEditNumberTableColumn.setCellFactory(TextFieldTableCell.<ProductReprintTableBean>forTableColumn());
		productEditDeleteTableColumn.setCellFactory(deleteCellFactory);

		product_selling_pointCombo.setItems(getShopList());
		
		product_type.setItems(getProductTypeList());

		button_EditProduct.defaultButtonProperty().bind(button_EditProduct.focusedProperty());
		button_EditProduct.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_EditProduct.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_EditProduct.setStyle("-fx-background-color: #FF6C6C");
			}
		});

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			currentDateTime.setText(
					DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour)
							+ ":" + String.format("%02d", minute) + ":" + String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		product_selling_price.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!product_selling_price.getText().matches("^([1-5]\\.[0-9]|6\\.0|[1-9][0-9]*)$")) {
					product_selling_price.setText("");
				}
			}

		});
		
		product_buying_price.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!product_buying_price.getText().matches("^([1-5]\\.[0-9]|6\\.0|[1-9][0-9]*)$")) {
					product_buying_price.setText("");
				}
			}

		});

		product_id.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isEmpty(getProduct_id())) {
					productInsertStatus.setText("Please Enter Product Id!!!");
				} else {
					if (StringUtils.isNotEmpty(getProduct_selling_pointCombo())) {
						
						ProductStock productStock = productService.findByIdAndProductStockStatusAndShop(Long.parseLong(getProduct_id()),
								ProductStockStatus.STOCK_AVAILABLE, getShop());
						if (productStock != null) {
								List<ProductStock> productStocks = productService
										.findByProductStockParentAndProductStockStatusAndShop(
												productStock.getProductStockParent(),
												ProductStockStatus.STOCK_AVAILABLE ,getShop() );
								if (productStock.getProductSize() != null) {
									priceSelling = productStock.getPriceSelling();
									type = productStock.getProductType().getType();
									setProduct_buying_price(productStock.getPriceBuying().toString());
									setMerchant(productStock.getMerchant().getCode());
									setProduct_selling_price(productStock.getPriceSelling().toString());
									setSize(productStock.getProductSize().getSize());
									setProduct_quantity(Integer.toString(productStocks.size() - 1));
									setProduct_type(productStock.getProductType().getType());
								} else {
									priceSelling = productStock.getPriceSelling();
									type = productStock.getProductType().getType();
									setProduct_buying_price(productStock.getPriceBuying().toString());
									setMerchant(productStock.getMerchant().getCode());
									setProduct_selling_price(productStock.getPriceSelling().toString());
									setProduct_quantity(Integer.toString(productStocks.size() - 1));
									setProduct_type(productStock.getProductType().getType());
								}
						} else {
							productInsertStatus.setText("Product Not Found !!!");
						}
					} else {
						productInsertStatus.setText("Please Select selling point!!!");
					}

				}

			}
		});

	}

}
