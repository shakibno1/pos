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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Duration;
import model.ConfigurationProperties;
import model.ReferenceCodeGenerator;

@Controller
public class AddProductController implements Initializable {
	
	@FXML
	private JFXButton button_AddMerchant;
	@FXML
	private JFXButton button_Reprint;
	@FXML
	private JFXButton button_AddProductType;
	@FXML
	private JFXComboBox<String> product_type;
	@FXML
	private JFXTextField reprintProductAdd;
	@FXML
	private JFXTextField product_description;
	@FXML
	private JFXTextField product_quantity;
	@FXML
	private JFXTextField size_table;
	@FXML
	private JFXTextField quantity_table;
	@FXML
	private JFXTextField product_buying_price;
	@FXML
	private JFXTextField product_selling_price;
	@FXML
	private JFXComboBox<String> product_selling_point;
	@FXML
	private JFXComboBox<String> merchant;
	@FXML
	private JFXButton button_AddProduct;
	@FXML
	private JFXButton button_Back;
	@FXML
	private Label productInsertStatus;
	@FXML
	private Label currentDateTime;
	@FXML
	private JFXCheckBox isSizeAvailable;
	@FXML
	private TableView<ProductSizeQuantityTableBean> sizeQuantityTableView;
	@FXML
	private TableColumn<ProductSizeQuantityTableBean, String> sizeTableColumn;
	@FXML
	private TableColumn<ProductSizeQuantityTableBean, String> quantityTableColumn;
	private ObservableList<ProductSizeQuantityTableBean> sizeQuantityTableData = FXCollections.observableArrayList();
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productMerchantObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productSizeObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productTypeObservableList = FXCollections.observableArrayList();
	private static String printerName;
	private ConfigurationProperties configurationProperties = new ConfigurationProperties();
	private boolean duplicateSizeCheck = true;
	
	
	@FXML
	private TableView<ProductReprintTableBean> productReprintTableView;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productReprintNumberTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productReprintSizeTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productReprintPriceTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productReprintCheckBoxTableColumn;
	@FXML
	private TableColumn<ProductReprintTableBean, String> productReprintDeleteTableColumn;

	private ObservableList<ProductReprintTableBean> productReprintTableData = FXCollections.observableArrayList();
	
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ProductStockService productService;

	@Autowired
	private ProductTransactionLedgerService productTransactionLedgerService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ProductStockParentService productStockParentService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@Autowired
	private ProductSizeService productSizeService;
	
	private Shop shop;
	
	private ProductType productType;
	
	private Merchant merchantBean;
	
	private ProductStockParent productStockParent;
	
	private ProductSize productSize;
	
	private ProductStock productStock;
	
	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		AddProductController.user = user;
	}

	public String getProduct_type() {
		return product_type.getSelectionModel().getSelectedItem();
	}

	public void setProduct_type(String product_type) {
		this.product_type.getSelectionModel().select(product_type);
	}

	public String getProduct_description() {
		return product_description.getText();
	}

	public void setProduct_description(String product_description) {
		this.product_description.setText(product_description);
	}

	public Integer getProduct_quantity() {
		return Integer.parseInt(product_quantity.getText());
	}

	public void setProduct_quantity(String product_quantity) {
		this.product_quantity.setText(product_quantity);
	}

	public String getSize_table() {
		return size_table.getText();
	}

	public void setSize_table(String size_table) {
		this.size_table.setText(size_table);
	}

	public Integer getQuantity_table() {
		return Integer.parseInt(quantity_table.getText());
	}

	public void setQuantity_table(String quantity_table) {
		this.quantity_table.setText(quantity_table);
	}

	public Float getProduct_buying_price() {
		return Float.parseFloat(product_buying_price.getText());
	}

	public void setProduct_buying_price(String product_buying_price) {
		this.product_buying_price.setText(product_buying_price);
	}

	public Float getProduct_selling_price() {
		return Float.parseFloat(product_selling_price.getText());
	}

	public void setProduct_selling_price(String product_selling_price) {
		this.product_selling_price.setText(product_selling_price);
	}

	public String getProduct_selling_point() {
		return product_selling_point.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_point(String product_selling_point) {
		this.product_selling_point.getSelectionModel().select(product_selling_point);
	}

	public String getMerchant() {
		return merchant.getSelectionModel().getSelectedItem();
	}

	public void setMerchant(String merchant) {
		this.merchant.getSelectionModel().select(merchant);
	}
	
	public String getReprintProductAdd() {
		return reprintProductAdd.getText();
	}

	public void setReprintProductAdd(String reprintProductAdd) {
		this.reprintProductAdd.setText(reprintProductAdd);
	}

	public Shop getShop() {
		
		shop = shopService.findByName(getProduct_selling_point());
		
		return shop;
		
	}
	
	public ProductType getProductType() {

		productType = productTypeService.findByType(getProduct_type());

		return productType;

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
		productMerchantObservableList = FXCollections.observableArrayList();
		for (Merchant merchant : merchants) {
			productMerchantObservableList.add(merchant.getName());
		}

		return productMerchantObservableList;
	}
	
	public ObservableList<String> getProductSizeList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<ProductSize> sizes = productSizeService.findAll();
		
		productSizeObservableList = FXCollections.observableArrayList();
		for (ProductSize size : sizes) {
			productSizeObservableList.add(size.getSize());
		}

		return productSizeObservableList;
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

	protected static double fromCMToPPI(double cm) {
		return toPPI(cm * 0.393700787);
	}

	protected static double toPPI(double inch) {
		return inch * 72d;
	}
	
	public static PrintService findPrintService(String printerName) {
		for (PrintService service : PrinterJob.lookupPrintServices()) {
			System.out.println(service.getName());
			if (service.getName().equalsIgnoreCase(printerName))
				return service;
		}

		return null;
	}
	
	public void printLabel(String printerName,String s) {
		// find the printService of name printerName
		PrintService ps = findPrintService(printerName);
		// create a printerJob
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		Paper paper = pf.getPaper();
		double width = fromCMToPPI(3.5);
		double height = fromCMToPPI(3.1);
		paper.setSize(width, height);
		paper.setImageableArea(fromCMToPPI(0.25), fromCMToPPI(0.5), width
				- fromCMToPPI(0.35), height - fromCMToPPI(1));
		pf.setOrientation(PageFormat.PORTRAIT);
		pf.setPaper(paper);
		try {
			job.setPrintService(ps);
			
			DocPrintJob docJob =ps.createPrintJob();
			byte[] by = s.getBytes();
			DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
			Doc doc = new SimpleDoc(by, flavor,null);
			docJob.print(doc, null);
		} catch (PrinterException | PrintException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void button_AddProductType_OnAction(ActionEvent event) throws IOException {
		
		AddProductTypeController.setUser(user);
		stageManager.switchScene(FxmlView.ADDPRODUCTTYPE);

	}

	@FXML
	void button_AddMerchant_OnAction(ActionEvent event) throws IOException {
		
		AddMerchantController.setUser(user);
		stageManager.switchScene(FxmlView.ADDMERCHANT);

	}
	
	private ProductStock addProduct(ProductSize productSize , ProductStockParent productStockParent){
		ProductStock productStock = new ProductStock();
//		productStock.setId(Integer.parseInt(id));
		productStock.setId(Long.parseLong(ReferenceCodeGenerator.getUniqueReferenceCode()));
		productStock.setMerchant(merchantBean);
		productStock.setPriceBuying(getProduct_buying_price());
		productStock.setPriceSelling(getProduct_selling_price());
		productStock.setProductDescription(getProduct_description());
		productStock.setShop(getShop());
		productStock.setProductType(getProductType());
		productStock.setQuantity(1);
		productStock.setProductStockStatus(ProductStockStatus.STOCK_AVAILABLE);
		productStock.setProductSize(productSize);
		productStock.setProductStockParent(productStockParent);
		productStock.setCreatedBy(user.getName());
		productStock.setUpdatedBy(user.getName());
		productStock.setCreated(new Timestamp(System.currentTimeMillis()));
		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
		ProductStock newProduct = productService.save(productStock);
		
		return newProduct;
	}

	private ProductTransactionLedger createProductLedger(ProductStock productStock,
														 ProductTransactionType productTransactionType) {

		ProductTransactionLedger productTransactionLedger = new ProductTransactionLedger();
		productTransactionLedger.setProductStock(productStock);
		productTransactionLedger.setProductTransactionType(productTransactionType);
		productTransactionLedger.setUpdatedBy(user.getName());
		productTransactionLedger.setCreated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setUpdated(new Timestamp(System.currentTimeMillis()));
		productTransactionLedger.setCreatedBy(user.getName());
		productTransactionLedger.setShop(productStock.getShop());
		ProductTransactionLedger newProductTransactionLedger =
		 productTransactionLedgerService.save(productTransactionLedger);
		return productTransactionLedger;
	}
	
	private ProductStock addProduct(ProductStockParent productStockParent){
		ProductStock productStock = new ProductStock();
//		productStock.setId(Integer.parseInt(id));
		productStock.setId(Long.parseLong(ReferenceCodeGenerator.getUniqueReferenceCode()));
		productStock.setMerchant(merchantBean);
		productStock.setPriceBuying(getProduct_buying_price());
		productStock.setPriceSelling(getProduct_selling_price());
		productStock.setProductDescription(getProduct_description());
		productStock.setShop(getShop());
		productStock.setProductType(getProductType());
		productStock.setQuantity(1);
		productStock.setProductStockStatus(ProductStockStatus.STOCK_AVAILABLE);
		productStock.setProductStockParent(productStockParent);
		productStock.setCreatedBy(user.getName());
		productStock.setUpdatedBy(user.getName());
		productStock.setCreated(new Timestamp(System.currentTimeMillis()));
		productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
		ProductStock newProduct = productService.save(productStock);
		
		return newProduct;
	}

	@FXML
	void button_AddProduct_OnAction(ActionEvent event) throws IOException, InterruptedException {
		productReprintTableData.clear();
		button_AddProduct.setStyle("-fx-background-color: #FF6C6C");
		button_AddProduct.setDisable(true);
		if ((StringUtils.isNotEmpty(getProduct_type())) && (StringUtils.isNotEmpty(getMerchant()))
				&& (StringUtils.isNotEmpty(getProduct_selling_point()))
				&& (StringUtils.isNotEmpty(getProduct_selling_price().toString()))
				&& (StringUtils.isNotEmpty(getProduct_buying_price().toString()))) {
			
			merchantBean = merchantService.findByName(getMerchant());
			productStockParent = new ProductStockParent();
			ProductStockParent NewProductStockParent = productStockParentService.save(productStockParent);
			printerName = configurationProperties.configurationReader("printer_label_name","rahifashion.properties");
			
			if(isSizeAvailable.isSelected()) {
				
				sizeQuantityTableView.getItems().forEach((item) -> {
						
					int itemQuantity = Integer.parseInt(item.getQuantity());
					if(getProductSizeList().contains(item.getSize())){
						productSize = productSizeService.findBySize(item.getSize());
						for (int i=0;i<itemQuantity;i++){
							productStock = addProduct(productSize , NewProductStockParent);

							createProductLedger(productStock,
									ProductTransactionType.STOCK_IN);
							
							productReprintTableData.add(new ProductReprintTableBean(productStock.getId().toString(),
									productStock.getQuantity().toString(), productStock.getShop().getName().substring(0, 3),
									Integer.toString(productStock.getPriceSelling().intValue()), productStock.getProductType().getType(),
									productStock.getMerchant().getCode(),"N"));
							
							String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+productStock.getId()+"^FS^ADN,10,14^FO300,125^FD"
									+productStock.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+getProduct_selling_price().intValue()+".00 ^FS^ADN,10,14^FO345,20^FD"+getProduct_selling_point().substring(0, 3)+"^FS^ADN,10,14^FO300,38^FD"
									+getProduct_type()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+merchantBean.getCode()+"^FS^XZ";
							System.out.println("ZPL  " + printingZPLCommand);
							printLabel(printerName,printingZPLCommand);
//							System.out.println(printingZPLCommand);
						}
						
					}
					else{
						productSize = new ProductSize();
						productSize.setSize(item.getSize());
						ProductSize newProductSize = productSizeService.save(productSize);
						for (int i=0;i<itemQuantity;i++){
							productStock = addProduct(newProductSize , NewProductStockParent);

							createProductLedger(productStock,
									ProductTransactionType.STOCK_IN);
							
							productReprintTableData.add(new ProductReprintTableBean(productStock.getId().toString(),
									productStock.getQuantity().toString(), productStock.getShop().getName().substring(0, 3),
									Integer.toString(productStock.getPriceSelling().intValue()), productStock.getProductType().getType(),
									productStock.getMerchant().getCode(),"N"));
							
							String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+productStock.getId()+"^FS^ADN,10,14^FO300,125^FD"
									+productStock.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+getProduct_selling_price().intValue()+".00 ^FS^ADN,10,14^FO345,20^FD"+getProduct_selling_point().substring(0, 3)+"^FS^ADN,10,14^FO300,38^FD"
									+getProduct_type()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+merchantBean.getCode()+"^FS^XZ";
							System.out.println("ZPL  " + printingZPLCommand);
							printLabel(printerName,printingZPLCommand);
//							System.out.println(printingZPLCommand);
						}
						
					}
				});
						productInsertStatus.setText("Product Inserted Successfull!!!");
			}
			else {
				
//						printerName = configurationProperties.configurationReader("printer_label_name","rahifashion.properties");
						for(int i=0;i<getProduct_quantity();i++){
							productStock = addProduct(NewProductStockParent);

							createProductLedger(productStock,
									ProductTransactionType.STOCK_IN);
							
							productInsertStatus.setText("Product Inserted Successfull!!!");
							
							productReprintTableData.add(new ProductReprintTableBean(productStock.getId().toString(),
									productStock.getQuantity().toString(), productStock.getShop().getName().substring(0, 3),
									Integer.toString(productStock.getPriceSelling().intValue()), productStock.getProductType().getType(),
									productStock.getMerchant().getCode(),"N"));
							
							String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+productStock.getId()+"^FS^ADN,10,14^FO300,125^FD"
							+productStock.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+getProduct_selling_price().intValue()+".00 ^FS^ADN,10,14^FO345,20^FD"+getProduct_selling_point().substring(0, 3)+"^FS^ADN,10,14^FO300,38^FD"
							+getProduct_type()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+merchantBean.getCode()+"^FS^XZ";
							System.out.println("ZPL  " + printingZPLCommand);
							printLabel(printerName,printingZPLCommand);
//							System.out.println(printingZPLCommand);
							
						}
			
			}
			clearFields();
			product_quantity.requestFocus();

		} else {
			productInsertStatus.setText("Please Insert All Information To Add Product");
		}
		button_AddProduct.setDisable(false);
		sizeQuantityTableData.clear();
	}
	
	@FXML
	void button_Reprint_OnAction(ActionEvent event) throws IOException {
		
		printerName = configurationProperties.configurationReader("printer_label_name","rahifashion.properties");

		if(productReprintTableView.getItems()!=null){
			productReprintTableView.getItems().forEach((item) -> {
				if(item.getPrintingStatus().equals("Y")){
					String printingZPLCommand = "^XA^ADB,30,14^FO350,60^BY1,10,10^BCN,50,N,N,N,D^FD"+item.getId()+"^FS^ADN,10,14^FO300,125^FD"
							+item.getId()+"^FS^ADN,28,14^FO310,150^FDTK: "+item.getAmount()+".00 ^FS^ADN,10,14^FO345,20^FD"+item.getSize()+"^FS^ADN,10,14^FO300,38^FD"
							+item.getQuantityLeftInStock()+"^FS^ADB,10,14^FO310,65^FDRAHA^FS^ADB,10,14^FO530,80^FD"+item.getDescription()+"^FS^XZ";
							System.out.println("ZPL  " + printingZPLCommand);
							printLabel(printerName,printingZPLCommand);
				}
				
			});
		}
		
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		clearFields();
		productReprintTableData.clear();
		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}
	
	private void clearFields(){
//		setProduct_quantity("");
		setQuantity_table("");
		setProduct_buying_price("");
		setProduct_selling_price("");
		setSize_table("");
		sizeQuantityTableData.clear();
		
	
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

								productReprintTableView.getItems().remove(ProductReprintTableBeanClass);

							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		Callback<TableColumn<ProductReprintTableBean, String>, TableCell<ProductReprintTableBean, String>> checkBoxCellFactory = new Callback<TableColumn<ProductReprintTableBean, String>, TableCell<ProductReprintTableBean, String>>() {
			@Override
			public TableCell<ProductReprintTableBean, String> call(final TableColumn<ProductReprintTableBean, String> param) {
				final TableCell<ProductReprintTableBean, String> cell = new TableCell<ProductReprintTableBean, String>() {

					final CheckBox btn = new CheckBox();
					
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							if(getTableView().getItems()!=null){
								ProductReprintTableBean productReprintTableBeanClass = getTableView().getItems().get(getIndex());
								if(productReprintTableBeanClass.getPrintingStatus().equals("Y")){
									btn.setSelected(true);
								}
								else{
									btn.setSelected(false);
								}
							}
							btn.setOnAction(event -> {
								if(btn.isSelected()){
									ProductReprintTableBean ProductReprintTableBeanClass = getTableView().getItems().get(getIndex());
									ProductReprintTableBeanClass.setPrintingStatus("Y");
								}
								else{
									ProductReprintTableBean ProductReprintTableBeanClass = getTableView().getItems().get(getIndex());
									ProductReprintTableBeanClass.setPrintingStatus("N");
								}
								

							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		
		
		productReprintDeleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
		productReprintNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productReprintSizeTableColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
		productReprintCheckBoxTableColumn.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
		productReprintPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		productReprintTableView.setItems(productReprintTableData);
		productReprintTableView.setEditable(true);
		productReprintNumberTableColumn.setCellFactory(TextFieldTableCell.<ProductReprintTableBean>forTableColumn());
		productReprintDeleteTableColumn.setCellFactory(deleteCellFactory);
		productReprintCheckBoxTableColumn.setCellFactory(checkBoxCellFactory);
		
		
		
		button_AddProduct.defaultButtonProperty().bind(button_AddProduct.focusedProperty());
		button_AddProduct.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_AddProduct.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_AddProduct.setStyle("-fx-background-color: #FF6C6C");
			 }
		});
		
		sizeTableColumn.setCellValueFactory(new PropertyValueFactory<ProductSizeQuantityTableBean,String>("size"));
		quantityTableColumn.setCellValueFactory(new PropertyValueFactory<ProductSizeQuantityTableBean,String>("quantity"));
		sizeQuantityTableView.setItems(sizeQuantityTableData);
		sizeQuantityTableView.setEditable(false);
		quantityTableColumn.setCellFactory(TextFieldTableCell.<ProductSizeQuantityTableBean>forTableColumn());
		sizeTableColumn.setCellFactory(TextFieldTableCell.<ProductSizeQuantityTableBean>forTableColumn());
		
		quantityTableColumn
		.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductSizeQuantityTableBean, String>>() {

			@Override
			public void handle(CellEditEvent<ProductSizeQuantityTableBean, String> event) {
				
				((ProductSizeQuantityTableBean) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setQuantity(event.getNewValue());

			}
		});
		
		sizeTableColumn
		.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductSizeQuantityTableBean, String>>() {

			@Override
			public void handle(CellEditEvent<ProductSizeQuantityTableBean, String> event) {
				
				((ProductSizeQuantityTableBean) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setSize(event.getNewValue());

			}
		});
		
		size_table.setEditable(false);
		quantity_table.setEditable(false);
		
		isSizeAvailable.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(isSizeAvailable.isSelected()) {
					sizeQuantityTableView.setEditable(true);
					size_table.setEditable(true);
					quantity_table.setEditable(true);
					product_quantity.setEditable(false);
				}
				else {
					size_table.setEditable(false);
					quantity_table.setEditable(false);
					sizeQuantityTableView.setEditable(false);
					product_quantity.setEditable(true);
				}
			}
		});
		
		quantity_table.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				duplicateSizeCheck = true;
				if(StringUtils.isEmpty(size_table.getText())) {
					productInsertStatus.setText("Please select size first!!!");
				}
				else {
					sizeQuantityTableView.getItems().forEach((item) -> {
						if(item.getSize().equals(size_table.getText())){
							duplicateSizeCheck = false;
						}
					});
					if(duplicateSizeCheck){
						sizeQuantityTableData.add(new ProductSizeQuantityTableBean(quantity_table.getText() , size_table.getText()));
						productInsertStatus.setText("Size Added!!!");
					}
					else{
						productInsertStatus.setText("Duplicate Size!!! Please Use Quantity");
					}
				}
			}
		});

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			currentDateTime.setText(DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour) + ":"
					+ String.format("%02d", minute) + ":" + String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		product_selling_point.setItems(getShopList());
		merchant.setItems(getMerchantList());
		product_type.setItems(getProductTypeList());

		product_buying_price.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!product_buying_price.getText().matches("^([1-5]\\.[0-9]|6\\.0|[1-9][0-9]*)$")) {
					// when it not matches the pattern (1.0 - 6.0)
					// set the textField empty
					product_buying_price.setText("");
				}
			}

		});

		product_selling_price.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!product_selling_price.getText().matches("^([1-5]\\.[0-9]|6\\.0|[1-9][0-9]*)$")) {
					// when it not matches the pattern (1.0 - 6.0)
					// set the textField empty
					product_selling_price.setText("");
				}
			}

		});

		product_quantity.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!product_quantity.getText().matches("^(0|[1-9][0-9]*)$")) {
					// when it not matches the pattern (1.0 - 6.0)
					// set the textField empty
					product_quantity.setText("");
				}
			}

		});
		
		
		reprintProductAdd.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isEmpty(getReprintProductAdd())) {
					productInsertStatus.setText("Please Enter Product Id!!!");
				} else {
					ProductStock productStock = productService.findById(Long.parseLong(getReprintProductAdd()));
					List<ProductStock> stocks = productService.findByProductStockParentAndProductStockStatus(productStock.getProductStockParent(), ProductStockStatus.STOCK_AVAILABLE);
					if (stocks != null) {
						for (ProductStock stock : stocks ){
							if (stock.getProductSize() != null) {
								productReprintTableData.add(new ProductReprintTableBean(stock.getId().toString(),
										stock.getQuantity().toString(), stock.getShop().getName().substring(0, 3),
										Integer.toString(stock.getPriceSelling().intValue()), stock.getProductType().getType(),
										stock.getMerchant().getCode(),"Y"));
							} else {
								productReprintTableData.add(new ProductReprintTableBean(stock.getId().toString(),
										stock.getQuantity().toString(), stock.getShop().getName().substring(0, 3),
										Integer.toString(stock.getPriceSelling().intValue()), stock.getProductType().getType(),
										stock.getMerchant().getCode(),"Y"));
							}
						}
									
					} else {
						productInsertStatus.setText("Product Not Found !!!");
					}

				}
				reprintProductAdd.setText("");
			}
		});

	}

}
