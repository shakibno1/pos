package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ShopService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Duration;

@Controller
public class ShopController implements Initializable {
	

	@FXML
	private JFXTextField product_selling_point;
	@FXML
	private JFXTextArea address;
	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXButton button_AddProductSellingPoint;
	@FXML
	private Label productInsertStatus;
	@FXML
	private Label date;
	@FXML
	private Label shopId;
	
	@FXML
	private TableView<Shop> tableView;
	@FXML
	private TableColumn<Shop, String> idTableColumn;
	@FXML
	private TableColumn<Shop, String> sellingPointNameTableColumn;
	@FXML
	private TableColumn<Shop, String> sellingPointAddressTableColumn;
	@FXML
	private TableColumn<Shop, String> deleteTableColumn;
	@FXML
	private TableColumn<Shop, Boolean> updateTableColumn;
	private ObservableList<Shop> tableData = FXCollections.observableArrayList();
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ShopService service;
	
	private static User user;
	
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		ShopController.user = user;
	}

	public String getProduct_selling_point() {
		return product_selling_point.getText();
	}

	public void setProduct_selling_point(String product_selling_point) {
		this.product_selling_point.setText(product_selling_point);
	}

	public String getAddress() {
		return address.getText();
	}

	public void setAddress(String address) {
		this.address.setText(address);
	}

	public String getShopId() {
		return shopId.getText();
	}

	public void setShopId(String shopId) {
		this.shopId.setText(shopId);
	}
	
	private void saveAlert(Shop employee) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Shop saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The employee " + employee.getName() + " " + employee.getName() + " has been created and \n"
				+ getAddress() + " id is " + employee.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Shop employee) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Shop updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The employee " + employee.getName() + " " + employee.getName() + " has been updated.");
		alert.showAndWait();
	}
	
	private void loadUserDetails() {

		tableData.clear();

		tableData.addAll(service.findAll());

		tableView.setItems(tableData);
	}

	private void clearFields() {
		address.setText(null);
		product_selling_point.setText(null);
		shopId.setText(null);
		button_AddProductSellingPoint.setText("Add User");
	}

	@FXML
	void button_AddProductSellingPoint_OnAction(ActionEvent event) throws IOException {
				
				if(StringUtils.isEmpty(getAddress()) || StringUtils.isEmpty(getProduct_selling_point())){
					productInsertStatus.setText("Product Selling Point Add Unccessfull Please Try Again With Proper Data");
				}
				else{
					
					if (StringUtils.isEmpty(getShopId())) {
						Shop shop = new Shop();
						shop.setName(getProduct_selling_point());
						shop.setAddress(getAddress());
						shop.setCreatedBy(user.getName());
						shop.setUpdatedBy(user.getName());
						shop.setCreated(new Timestamp(System.currentTimeMillis()));
						shop.setUpdated(new Timestamp(System.currentTimeMillis()));
						Shop newEmployee = service.save(shop);
						saveAlert(newEmployee);
						loadUserDetails();
					}
					else{
						Shop shop = service.find(Long.parseLong(getShopId()));
						
						shop.setName(getProduct_selling_point());
						shop.setUpdatedBy(user.getName());
						shop.setUpdated(new Timestamp(System.currentTimeMillis()));
						shop.setAddress(getAddress());
						Shop updatedUser = service.update(shop);
						updateAlert(updatedUser);
						loadUserDetails();
					}
				}

				clearFields();
	}
	
	 @FXML
	 void button_Back_OnAction(ActionEvent event) throws IOException {
		 
		 stageManager.switchScene(FxmlView.FIRSTPAGE);
		 
	 }
	 
	 Callback<TableColumn<Shop, Boolean>, TableCell<Shop, Boolean>> cellFactory = new Callback<TableColumn<Shop, Boolean>, TableCell<Shop, Boolean>>() {
			@Override
			public TableCell<Shop, Boolean> call(final TableColumn<Shop, Boolean> param) {
				final TableCell<Shop, Boolean> cell = new TableCell<Shop, Boolean>() {
					Image imgEdit = new Image(getClass().getResourceAsStream("/images/edit.png"));
					final Button btnEdit = new Button();

					@Override
					public void updateItem(Boolean check, boolean empty) {
						super.updateItem(check, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btnEdit.setOnAction(e -> {
								Shop user = getTableView().getItems().get(getIndex());
								updateUser(user);
								button_AddProductSellingPoint.setText("Update Shop");
							});

							btnEdit.setStyle("-fx-background-color: transparent;");
							ImageView iv = new ImageView();
							iv.setImage(imgEdit);
							iv.setPreserveRatio(true);
							iv.setSmooth(true);
							iv.setCache(true);
							btnEdit.setGraphic(iv);

							setGraphic(btnEdit);
							setAlignment(Pos.CENTER);
							setText(null);
						}
					}

					private void updateUser(Shop user) {
						shopId.setText(Long.toString(user.getId()));
						product_selling_point.setText(user.getName());
						address.setText(user.getAddress());
					}
				};
				return cell;
			}
		};
		
		private void setColumnProperties(){
			idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			sellingPointNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			sellingPointAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
			deleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
			updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));
			tableView.setItems(tableData);
			tableView.setEditable(true);
			
			updateTableColumn.setCellFactory(cellFactory);
		}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setColumnProperties();
		
		loadUserDetails();
		
		button_AddProductSellingPoint.defaultButtonProperty().bind(button_AddProductSellingPoint.focusedProperty());
		button_Back.defaultButtonProperty().bind(button_Back.focusedProperty());
		
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
