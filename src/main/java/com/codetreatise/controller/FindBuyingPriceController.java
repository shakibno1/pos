package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductTableBean;
import com.codetreatise.bean.Shop;
import com.codetreatise.config.StageManager;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

@Controller
public class FindBuyingPriceController implements Initializable {
	
	@FXML
	private Label date;
	@FXML
	private JFXTextField product_id;
	@FXML
	private JFXTextField buying_price;
	@FXML
	private JFXButton button_findBuyingPrice;
	@FXML
	private Label warning;
	@FXML
	private JFXButton buttonBack;
	@FXML
	private TableView<ProductTableBean> productTableView;
	@FXML
	private TableColumn<ProductTableBean, String> productNumberTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productQuantityTableColumn;
	@FXML
	private TableColumn<ProductTableBean, String> productBuyingPriceTableColumn ;
	@FXML
	private TableColumn<ProductTableBean, String> productSellingPrice;
	private ObservableList<ProductTableBean> productTableData = FXCollections.observableArrayList();
	private static String sellingPointId;
	@FXML
	private JFXComboBox<String> product_selling_pointCombo;
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	
	
	
	public String getProduct_id() {
		return product_id.getText();
	}
	public void setProduct_id(String product_id) {
		this.product_id.setText(product_id);
	}
	public String getProduct_selling_pointCombo() {
		return product_selling_pointCombo.getSelectionModel().getSelectedItem();
	}
	public void setProduct_selling_pointCombo(String product_selling_pointCombo) {
		this.product_selling_pointCombo.getSelectionModel().select(product_selling_pointCombo);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ProductStockService productService;
	
	@Autowired
	private ShopService shopService;

	@FXML
	void buttonBack_OnAction(ActionEvent event) throws IOException {
		setProduct_id("");
		if (!productTableData.isEmpty()) {
			productTableData.removeAll(productTableData);
		}
		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}

	@FXML
	void button_findBuyingPrice_OnAction(ActionEvent event) throws IOException {
		button_findBuyingPrice.setStyle("-fx-background-color: #FF6C6C");
		if(StringUtils.isNotEmpty(getProduct_selling_pointCombo())){
			
			if(StringUtils.isNotEmpty(getProduct_id())) {
				
				ProductStock productStock = productService.find(Long.parseLong(getProduct_id()));
				
				productTableData.add(new ProductTableBean(productStock.getId().toString(), productStock.getQuantity().toString(), null,
						productStock.getPriceBuying().toString(), productStock.getPriceSelling().toString(), null));

				setProduct_id("");
			}
			else {
				warning.setText("Please Enter Product Number!!!");
			}
		}
		else{
			warning.setText("Please Select Selling Point!!!");
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		productNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productQuantityTableColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		productBuyingPriceTableColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		productSellingPrice.setCellValueFactory(new PropertyValueFactory<>("quantityLeftInStock"));
		productTableView.setItems(productTableData);
		product_selling_pointCombo.setItems(getShopList());
		
		buying_price.setEditable(false);
		button_findBuyingPrice.defaultButtonProperty().bind(button_findBuyingPrice.focusedProperty());
		button_findBuyingPrice.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_findBuyingPrice.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_findBuyingPrice.setStyle("-fx-background-color: #FF6C6C");
			 }
		});
		buttonBack.defaultButtonProperty().bind(buttonBack.focusedProperty());
		
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
		
	}

}
