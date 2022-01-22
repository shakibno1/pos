package com.codetreatise.controller;

import com.codetreatise.bean.*;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ProductTransactionLedgerService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class GoodReceiveController implements Initializable {

	@FXML
	private JFXTextField product_id ;
	@FXML
	private JFXTextField transfer_Batch ;
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

	public String getTransfer_Batch() {
		return transfer_Batch.getText();
	}

	public void setTransfer_Batch(String transfer_Batch) {
		this.transfer_Batch.setText(transfer_Batch);
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

	ProductTransferHistory productTransferHistory;
	
	private List<String> productStockIds = new ArrayList<String>();
	private List<String> productParentIds = new ArrayList<String>();
	
	public static void setUser(User user) {
		GoodReceiveController.user = user;
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
		File file = fileChooser.showSaveDialog((Stage) button_ReceiveProduct.getScene().getWindow());
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

	@Transactional
	@FXML
	void button_ReceiveProduct_OnAction(ActionEvent event) throws IOException, InterruptedException {


		button_ReceiveProduct.setStyle("-fx-background-color: #FF6C6C");
		List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
		if (!productTableData.isEmpty()) {
			ArrayList<String> itemToTransfer = new ArrayList<String>();

			productTableView.getItems().forEach((item) -> {
				Map<String, Object> map = new HashMap<String, Object>();
				itemToTransfer.add(item.getId());

				Shop toShop = productTransferHistory.getToShop();
				Shop fromShop = productTransferHistory.getFromShop();
				ProductStock productStock = productService.findById(Long.parseLong(item.getId()));
				productStock.setUpdated(new Timestamp(System.currentTimeMillis()));
				productStock.setUpdatedBy(user.getName());
				map.put("fromShop", productStock.getShop().getName());
                createProductLedger(productStock,
                        ProductTransactionType.STOCK_TRANSFER_OUT);
				productStock.setShop(toShop);
				productStock.setProductStockStatus(ProductStockStatus.STOCK_AVAILABLE);
				ProductStock updatedProductStock = productService.update(productStock);


				productTransferHistory.setUpdatedBy(user.getName());
				productTransferHistory.setUpdated(new Timestamp(System.currentTimeMillis()));
				productTransferHistory.setProductTransferStatus(ProductTransferStatus.TRANSFER_DONE);

				ProductTransferHistory newhistory = productTransferHistoryService.save(productTransferHistory);

				createProductLedger(updatedProductStock,
						ProductTransactionType.STOCK_TRANSFER_IN);

				BigDecimal amountBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getPriceBuying()));
				BigDecimal quantityBigDecimal = BigDecimal.valueOf(Float.valueOf(productStock.getQuantity()));

				map.put("amount", amountBigDecimal);
				map.put("productType", updatedProductStock.getProductType().getType());
				map.put("merchant", updatedProductStock.getMerchant().getName());
				map.put("toShop", updatedProductStock.getShop().getName());
				map.put("quantity", quantityBigDecimal);
				map.put("productSoldId", updatedProductStock.getId().toString());
				map.put("transferStatus", "Received");
				dataSource.add(map);
				productInsertStatus.setText("Transfer Done Received!!!");


			});
			printReceipt(dataSource,productTransferHistory.getTransferBatch());
			productStockIds.clear();
			productParentIds.clear();


		} else {
			productInsertStatus.setText("Please Try With Proper Data");
		}


	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		productTableData.clear();
		productParentIds.clear();
		productStockIds.clear();
		stageManager.switchScene(FxmlView.GOODTRANSFER);
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
					ProductStock productStock = productService.findByIdAndProductStockStatus(Long.parseLong(getProduct_id()),ProductStockStatus.RECEIVE_PENDING);

					if(productStock != null){

						productTransferHistory = productTransferHistoryService.
								findByProductStockAndToShopAndProductTransferStatus(productStock,user.getShop(),ProductTransferStatus.RECEIVE_PENDING);

						if(productTransferHistory != null){

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

								if(productStock.getProductStockStatus().equals(ProductStockStatus.RECEIVE_PENDING)){
									productStockIds.add(productStock.getId().toString());
									productParentIds.add(productStock.getProductStockParent().getId().toString());
									List<ProductStock> productStocks = productService.findByProductStockParentAndProductStockStatusAndShop(productStock.getProductStockParent() , ProductStockStatus.RECEIVE_PENDING , productTransferHistory.getFromShop());
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
									productInsertStatus.setText("Product Not Pending For Receive !!!");
								}

							}
							else{
								productInsertStatus.setText("Product Already Added !!!");

							}
						}
						else{
							productInsertStatus.setText("Product Not Pending For Receive !!!");
						}
					}
					else{
						productInsertStatus.setText("Product Not Found !!!");
					}


				}
				product_id.setText("");

			}
		});


		transfer_Batch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (StringUtils.isEmpty(getTransfer_Batch())) {
					productInsertStatus.setText("Please Enter Transfer Batch !!!");
				} else {

					List<ProductTransferHistory> productTransferHistoryList = productTransferHistoryService.
							findByTransferBatchAndProductTransferStatusAndToShop(Integer.parseInt(getTransfer_Batch()),ProductTransferStatus.RECEIVE_PENDING,user.getShop());

					if(productTransferHistoryList != null) {

						for (ProductTransferHistory productTransferHis: productTransferHistoryList){

							ProductStock productStock = productTransferHis.getProductStock();

							productTransferHistory = productTransferHis;


							if(productStock != null){
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

									if(productStock.getProductStockStatus().equals(ProductStockStatus.RECEIVE_PENDING)){
										productStockIds.add(productStock.getId().toString());
										productParentIds.add(productStock.getProductStockParent().getId().toString());
										List<ProductStock> productStocks = productService.findByProductStockParentAndProductStockStatusAndShop(productStock.getProductStockParent() , ProductStockStatus.RECEIVE_PENDING , productTransferHis.getFromShop());
										if(productStock.getProductSize() != null){
											productTableData.add(new ProductTableBean(productStock.getId().toString(), productStock.getQuantity().toString(),
													productStock.getProductSize().getSize(), productStock.getPriceSelling().toString(),
													Integer.toString(productStocks.size() -loadedProduct), productStock.getProductDescription()));
											productInsertStatus.setText("Product Added!!!"+productStock.getId().toString());
										}
										else{
											productTableData.add(new ProductTableBean(productStock.getId().toString(), productStock.getQuantity().toString(),
													"Not Available", productStock.getPriceSelling().toString(),
													Integer.toString(productStocks.size() -loadedProduct), productStock.getProductDescription()));
											productInsertStatus.setText("Product Added!!!"+productStock.getId().toString());
										}
									}
									else{
										productInsertStatus.setText("Product Not Pending For Receive !!!");
									}

								}
								else{
									productInsertStatus.setText("Product Already Added !!!");

								}
							}

						}

					}

				}
				product_id.setText("");

			}
		});


	}

}
