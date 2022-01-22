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
import com.codetreatise.bean.ProductStockStatus;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

@Controller
public class StockRealizationController implements Initializable {
	
	@FXML
	private JFXTextField product_id ;
	@FXML
	private JFXButton button_RealizeProduct ;
	@FXML
	private JFXButton button_Back ;
	@FXML
	private Label productInsertStatus ;
	@FXML
	private Label currentDateTime ;
	@FXML
	private JFXComboBox<String> product_baseSelling_pointCombo ;
	
	private ObservableList<String> productSellingBasePointObservableList = FXCollections.observableArrayList();
	
	
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



	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ProductStockService productService;
	
	@Autowired
	private ShopService shopService;

	@FXML
	void button_RealizeProduct_OnAction(ActionEvent event) throws IOException, InterruptedException {
		button_RealizeProduct.setStyle("-fx-background-color: #FF6C6C");
		if (StringUtils.isNotEmpty(getProduct_baseSelling_pointCombo())) {
			
				ProductStock productStock = productService.findByIdAndProductStockStatusAndShop(Long.parseLong(getProduct_id()), ProductStockStatus.STOCK_AVAILABLE,getBaseShop());
				
				if(productStock != null) {
					productStock.setStockRealization(1);
					productService.save(productStock);
					productInsertStatus.setText("Stock Realization Successfull of id: "+productStock.getId());
				}
				else{
					productInsertStatus.setText("No product Found for realization");
				}
			
			product_id.setText("");
			product_id.requestFocus();
			
		} else {
			productInsertStatus.setText("Please Try With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}
	
	public ObservableList<String> getShopList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = shopService.findAll();
		productSellingBasePointObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			productSellingBasePointObservableList.add(shop.getName());
		}

		return productSellingBasePointObservableList;
	}
	
	public Shop getBaseShop() {
		
		Shop shop = shopService.findByName(getProduct_baseSelling_pointCombo());
		
		return shop;
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		

		product_baseSelling_pointCombo.setItems(getShopList());

		

		button_RealizeProduct.defaultButtonProperty().bind(button_RealizeProduct.focusedProperty());
		button_RealizeProduct.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_RealizeProduct.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_RealizeProduct.setStyle("-fx-background-color: #FF6C6C");
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
				
				if(StringUtils.isNotEmpty(getProduct_baseSelling_pointCombo())){
					ProductStock productStock = productService.findByIdAndProductStockStatusAndShop(Long.parseLong(getProduct_id()), ProductStockStatus.STOCK_AVAILABLE,getBaseShop());
					
					if(productStock != null) {
						productStock.setStockRealization(1);
						productService.save(productStock);
						productInsertStatus.setText("Stock Realization Successfull of id: "+productStock.getId());
					}
					else{
						productInsertStatus.setText("No product Found for realization");
					}
				}else {
					productInsertStatus.setText("Please Try With Proper Data");
				}
				
				product_id.setText("");
				product_id.requestFocus();

			}
		});


	}

}
