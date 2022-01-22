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

import com.codetreatise.bean.*;
import com.codetreatise.service.ProductTransactionLedgerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.config.StageManager;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ProductTransferHistoryService;
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
public class GoodTransferController implements Initializable {

	@FXML
	private JFXTextField product_id ;
	@FXML
	private JFXButton button_TransferProduct ;
	@FXML
	private JFXButton button_TransferReport ;
	@FXML
	private JFXButton button_ReceiveProduct ;
	@FXML
	private JFXButton button_Back ;
	@Autowired
	private ProductTransactionLedgerService productTransactionLedgerService;
	@FXML
	private Label productInsertStatus ;
	@FXML
	private Label currentDateTime ;
	@FXML
	private JFXComboBox<String> product_baseSelling_pointCombo ;
	@FXML
	private JFXComboBox<String> product_toSelling_pointCombo ;
	@FXML
	private JFXComboBox<String> transferBatches ;
	private ObservableList<String> productSellingToPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productSellingBasePointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> transferBatchesObservableList = FXCollections.observableArrayList();
	@FXML
	private TableView<ProductTableBean> productTableView;
	@FXML
	private TableColumn<ProductTableBean, String> productNumberTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productQuantityTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productSizeTableColumn ;
	@FXML
	private TableColumn<ProductTableBean, String> productPriceTableColumn ;
	@FXML
	private TableColumn<ProductTableBean, String> productDescriptionTableColumn ;
	@FXML
	private TableColumn<ProductTableBean, String> productQuantityLeftInStockTableColumn ;
	@FXML
	private TableColumn<ProductTableBean, String> productDeleteTableColumn ;
	private ObservableList<ProductTableBean> productTableData = FXCollections.observableArrayList();
	
	private int loadedProduct;
	
	private static Boolean productDuplicateEntryChecker;
	
	public String getProduct_id() {
		return product_id.getText();
	}

	public void setProduct_id(String product_id) {
		this.product_id.setText(product_id);
	}
	

	public String getProduct_baseSelling_pointCombo() {
		return product_baseSelling_pointCombo.getSelectionModel().getSelectedItem();
	}

	public void setProduct_baseSelling_pointCombo(String product_baseSelling_pointCombo) {
		this.product_baseSelling_pointCombo.getSelectionModel().select(product_baseSelling_pointCombo);
	}

	public String getProduct_toSelling_pointCombo() {
		return product_toSelling_pointCombo.getSelectionModel().getSelectedItem();
	}

	public void setProduct_toSelling_pointCombo(String product_toSelling_pointCombo) {
		this.product_toSelling_pointCombo.getSelectionModel().select(product_toSelling_pointCombo);
	}

	public void setTransferBatches(String transferBatch) {
		this.transferBatches.getSelectionModel().select(transferBatch);
	}

	public String getTransferBatches() {
		Object batch = transferBatches.getSelectionModel().getSelectedItem();
		return batch.toString();
	}


	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ProductStockService productService;
	
	@Autowired
	private ProductTransferHistoryService productTransferHistoryService;
	
	@Autowired
	private ShopService shopService;
	
	private static User user;
	
	private List<String> productStockIds = new ArrayList<String>();
	private List<String> productParentIds = new ArrayList<String>();
	
	public static void setUser(User user) {
		GoodTransferController.user = user;
	}
	
	public void printReceipt(List<Map<String, Object>> dataSource , Integer transferBatch) {

		Map<String, Object> jrParameter = new HashMap<String, Object>();

		String customerId = " ";
		String customerName = "Stock Transfer";
		String customerMobile = " ";
		String customerAddress = " ";

		String shop;
		shop = user.getShop().getName();
		String address = user.getShop().getAddress();

		jrParameter.put("customerId", customerId);
		jrParameter.put("customerName", customerName);
		jrParameter.put("customerMobile", customerMobile);
		jrParameter.put("customerAddress", customerAddress);
		jrParameter.put("shop", shop);
		jrParameter.put("address", address);
		jrParameter.put("vat", BigDecimal.ZERO);
		jrParameter.put("bigZero", BigDecimal.ZERO);
        jrParameter.put("transferBatch", transferBatch);
		JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
		JasperPrint jp = null;
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog((Stage) button_TransferProduct.getScene().getWindow());
		if (file != null) {
			if (!file.exists()) {
				try {
					file.createNewFile();
					FileOutputStream fop = new FileOutputStream(file);
					net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
							.compileReport(getClass().getResourceAsStream("/jrxml/GoodsTransfer.jrxml"));
					jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
					JasperExportManager.exportReportToPdfStream(jp, fop);
					fop.close();
					productTableData.clear();
				} catch (JRException | IOException e) {
					e.printStackTrace();
					return;

				}
			}
		}

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

	@FXML
	void button_EditProduct_OnAction(ActionEvent event) throws IOException, InterruptedException {
		button_TransferProduct.setStyle("-fx-background-color: #FF6C6C");
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
		if (StringUtils.isNotEmpty(getProduct_baseSelling_pointCombo()) && StringUtils.isNotEmpty(getProduct_toSelling_pointCombo())
				&& !productTableData.isEmpty()) {
			ArrayList<String> itemToTransfer = new ArrayList<String>();

            final Integer transferBatch = productTransferHistoryService.getMaxTransferBatch() + 1;

			productTableView.getItems().forEach((item) -> {
				Map<String, Object> map = new HashMap<String, Object>();
				itemToTransfer.add(item.getId());
				
				Shop toShop = shopService.findByName(getProduct_toSelling_pointCombo());
				Shop fromShop = shopService.findByName(getProduct_baseSelling_pointCombo());
				ProductStock productStock = productService.findById(Long.parseLong(item.getId()));
				productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
				productStock.setUpdatedBy(user.getName());
				map.put("fromShop", productStock.getShop().getName());
//                createProductLedger(productStock,
//                        ProductTransactionType.STOCK_TRANSFER_OUT);
//				productStock.setShop(toShop);

				productStock.setProductStockStatus(ProductStockStatus.RECEIVE_PENDING);
				
				ProductStock updatedProductStock = productService.update(productStock);
				
				ProductTransferHistory history = new ProductTransferHistory();
				history.setCreated(new Timestamp(System.currentTimeMillis()));
				history.setFromShop(fromShop);
				history.setToShop(toShop);
				history.setProductStock(updatedProductStock);
				history.setCreatedBy(user.getUserName());
				history.setUpdatedBy(user.getName());
				history.setUpdated(new Timestamp(System.currentTimeMillis()));
				history.setTransferBatch(transferBatch);
				history.setProductTransferStatus(ProductTransferStatus.RECEIVE_PENDING);
				
				ProductTransferHistory newhistory = productTransferHistoryService.save(history);

//				createProductLedger(updatedProductStock,
//						ProductTransactionType.STOCK_TRANSFER_IN);
				
				BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceBuying()));
				BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));
				
				map.put("amount", amountBigDecimal);
				map.put("productType", updatedProductStock.getProductType().getType());
				map.put("merchant", updatedProductStock.getMerchant().getName());
				map.put("toShop", toShop.getName());
				map.put("quantity", quantityBigDecimal);
				map.put("productSoldId", updatedProductStock.getId().toString());
				map.put("transferStatus", "Receive Pending");
				dataSource.add(map);
				productInsertStatus.setText("Transfer Initiated Waiting to receive!!!");
				

			});
			printReceipt(dataSource,transferBatch);
			productStockIds.clear();
			productParentIds.clear();
			
			
		} else {
			productInsertStatus.setText("Please Try With Proper Data");
		}

	}

	@FXML
	void button_GenerateTransferReport_OnAction(ActionEvent event) {

		button_TransferReport.setStyle("-fx-background-color: #FF6C6C");
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();

		if (StringUtils.isNotEmpty(getTransferBatches())) {

			String transferBatch = getTransferBatches();

			List<ProductTransferHistory> productTransferHistories = productTransferHistoryService.findByTransferBatch(Integer.parseInt(transferBatch));

			for (ProductTransferHistory productTransferHistory : productTransferHistories){

				Map<String, Object> map = new HashMap<String, Object>();

				ProductStock productStock = productTransferHistory.getProductStock();

				BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceBuying()));
				BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));

				map.put("fromShop", productTransferHistory.getFromShop().getName());
				map.put("amount", amountBigDecimal);
				map.put("productType", productStock.getProductType().getType());
				map.put("merchant", productStock.getMerchant().getName());
				map.put("toShop", productStock.getShop().getName());
				map.put("quantity", quantityBigDecimal);
				map.put("productSoldId", productStock.getId().toString());
				dataSource.add(map);

			}

			printReceipt(dataSource,Integer.parseInt(transferBatch));


		} else {
			productInsertStatus.setText("Please Try With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}

	@FXML
	void button_ReceiveProduct_OnAction(ActionEvent event) throws IOException {

		GoodReceiveController.setUser(user);
		stageManager.switchScene(FxmlView.GOOD_RECEIVE);
	}
	
	public ObservableList<String> getShopList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = shopService.findAll();
		productSellingBasePointObservableList = FXCollections.observableArrayList();
		productSellingToPointObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			productSellingBasePointObservableList.add(shop.getName());
			productSellingToPointObservableList.add(shop.getName());
		}

		return productSellingBasePointObservableList;
	}

	public ObservableList<String> getAllTransferBatches() {

		transferBatchesObservableList.addAll(productTransferHistoryService.getAllTransferBatches());

		return transferBatchesObservableList;
	}
	
	public void setToSellingPointList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = shopService.findAll();
		productSellingToPointObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			productSellingToPointObservableList.add(shop.getName());
		}
	}
	
	public Shop getBaseShop() {
		
		Shop shop = shopService.findByName(getProduct_baseSelling_pointCombo());
		
		return shop;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Callback<TableColumn<ProductTableBean, String>, TableCell<ProductTableBean, String>> deleteCellFactory = new Callback<TableColumn<ProductTableBean, String>, TableCell<ProductTableBean, String>>() {
			@Override
			public TableCell<ProductTableBean, String> call(
					final TableColumn<ProductTableBean, String> param) {
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
		productDescriptionTableColumn
				.setCellValueFactory(new PropertyValueFactory<>("description"));
		productPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		productQuantityLeftInStockTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantityLeftInStock"));
		productTableView.setItems(productTableData);
		productTableView.setEditable(true);
		productQuantityTableColumn.setCellFactory(TextFieldTableCell.<ProductTableBean>forTableColumn());
		productSizeTableColumn.setCellFactory(TextFieldTableCell.<ProductTableBean>forTableColumn());
		productDeleteTableColumn.setCellFactory(deleteCellFactory);

		product_baseSelling_pointCombo.setItems(getShopList());

		transferBatches.setItems(getAllTransferBatches());

		product_baseSelling_pointCombo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isNotEmpty(getProduct_baseSelling_pointCombo())) {
					setToSellingPointList();
					productSellingToPointObservableList.remove(getProduct_baseSelling_pointCombo());
					product_toSelling_pointCombo.setItems(productSellingToPointObservableList);
				}

			}
		});

		button_TransferProduct.defaultButtonProperty().bind(button_TransferProduct.focusedProperty());
		button_TransferProduct.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_TransferProduct.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_TransferProduct.setStyle("-fx-background-color: #FF6C6C");
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

		product_id.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isEmpty(getProduct_id())) {
					productInsertStatus.setText("Please Enter Product Id!!!");
				} else {
					ProductStock productStock = productService.findById(Long.parseLong(getProduct_id()));
					if(productStock != null){
						if(productStock.getShop().getName().equals(getProduct_baseSelling_pointCombo())){
							
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
							
							if(productDuplicateEntryChecker){
								
									if(productStock.getProductStockStatus().equals(ProductStockStatus.STOCK_AVAILABLE)){
										productStockIds.add(productStock.getId().toString());
										productParentIds.add(productStock.getProductStockParent().getId().toString());
										List<ProductStock> productStocks = productService.findByProductStockParentAndProductStockStatusAndShop(productStock.getProductStockParent() , ProductStockStatus.STOCK_AVAILABLE , getBaseShop());
										if(productStock.getProductSize() != null){
											productTableData.add(new ProductTableBean(getProduct_id(), productStock.getQuantity().toString(),
													productStock.getProductSize().getSize(), productStock.getPriceSelling().toString(),
													Integer.toString(productStocks.size() -loadedProduct), productStock.getProductDescription()));
											productInsertStatus.setText("Product Added!!!"+getProduct_id());
										}
										else{
											productTableData.add(new ProductTableBean(getProduct_id(), productStock.getQuantity().toString(),
													"Not Available", productStock.getPriceSelling().toString(),
													Integer.toString(productStocks.size() -loadedProduct), productStock.getProductDescription()));
											productInsertStatus.setText("Product Added!!!"+getProduct_id());
										}
									}
									else{
										productInsertStatus.setText("Product Sold !!!");
									}
								
							}
							else{
								productInsertStatus.setText("Product Already Added !!!");
								
							}
						}
						else{
							productInsertStatus.setText("Cannot Add product of "+productStock.getShop().getName());
						}
							
					}
					else{
						productInsertStatus.setText("Product Not Found !!!");
					}
					
					
				}
				product_id.setText("");

			}
		});


	}

}
