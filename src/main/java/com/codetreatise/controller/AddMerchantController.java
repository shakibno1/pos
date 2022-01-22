package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductType;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.MerchantService;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.ProductTypeService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Duration;

@Controller
public class AddMerchantController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField merchant_name;
	@FXML
	private JFXTextField merchant_phone;
	@FXML
	private JFXTextField merchant_code;
	@FXML
	private JFXTextArea merchant_address;
	@FXML
	private JFXButton button_AddMerchant;
	@FXML
	private Label merchantInsertStatus;
	@FXML
	private Label merchantId;
	@FXML
	private Label currentDateTime;
	@FXML
	private MenuItem deleteMerchant;
	@FXML
	private JFXComboBox<String> product_type;
	@FXML
	private TableView<Merchant> tableView;
	@FXML
	private TableColumn<Merchant, String> idTableColumn;
	@FXML
	private TableColumn<Merchant, String> merchantNameTableColumn;
	@FXML
	private TableColumn<Merchant, String> merchantCodeTableColumn;
	@FXML
	private TableColumn<Merchant, String> merchantPhoneTableColumn;
	@FXML
	private TableColumn<Merchant, String> merchantAddressTableColumn;
	@FXML
	private TableColumn<Merchant, String> merchantProductTypeTableColumn;
	@FXML
	private TableColumn<Merchant, Boolean> updateTableColumn;
	private ObservableList<Merchant> tableData = FXCollections.observableArrayList();
	private ObservableList<String> productTypeObservableList;
	private ProductType productType;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private MerchantService merchantService;

	private Merchant merchant;

	private static User user;

	public String getMerchantId() {
		return merchantId.getText();
	}

	public void setMerchantId(String merchantId) {
		this.merchantId.setText(merchantId);
	}

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		AddMerchantController.user = user;
	}

	public String getMerchant_name() {
		return merchant_name.getText();
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name.setText(merchant_name);
	}

	public String getMerchant_phone() {
		return merchant_phone.getText();
	}

	public void setMerchant_phone(String merchant_phone) {
		this.merchant_phone.setText(merchant_phone);
	}

	public String getMerchant_code() {
		return merchant_code.getText();
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code.setText(merchant_code);
	}

	public String getMerchant_address() {
		return merchant_address.getText();
	}

	public void setMerchant_address(String merchant_address) {
		this.merchant_address.setText(merchant_address);
	}

	public String getProduct_type() {
		return product_type.getSelectionModel().getSelectedItem();
	}

	public void setProduct_type(String product_type) {
		this.product_type.getSelectionModel().select(product_type);
	}

	public ProductType getProductType() {

		productType = productTypeService.findByType(getProduct_type());

		return productType;

	}

	private Merchant createMerchant() {

		Merchant merchant = new Merchant();
		merchant.setAddress(getMerchant_address());
		merchant.setCode(getMerchant_code());
		merchant.setCreatedBy(user.getName());
		merchant.setUpdatedBy(user.getName());
		merchant.setUpdated(new Timestamp(System.currentTimeMillis()));
		merchant.setCreated(new Timestamp(System.currentTimeMillis()));
		merchant.setName(getMerchant_name());
		merchant.setPhone(getMerchant_phone());
		merchant.setProductType(getProductType());

		Merchant newMerchant = merchantService.save(merchant);
		saveAlert(newMerchant);
		return newMerchant;
	}

	private Merchant updateMerchant(String id) {

		merchant = merchantService.findById(Long.parseLong(id));
		merchant.setAddress(getMerchant_address());
		merchant.setCode(getMerchant_code());
		merchant.setUpdatedBy(user.getName());
		merchant.setName(getMerchant_name());
		merchant.setPhone(getMerchant_phone());
		merchant.setProductType(getProductType());
		merchant.setUpdated(new Timestamp(System.currentTimeMillis()));
		Merchant updatedMerchant = merchantService.update(merchant);
		updateAlert(updatedMerchant);
		return updatedMerchant;
	}

	private void saveAlert(Merchant merchant) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Merchant " + merchant.getName() + " " + " has been created and \n" + getMerchantId()
				+ " id is " + merchant.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Merchant merchant) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The Merchant " + merchant.getName() + " " + " has been updated.");
		alert.showAndWait();
	}

	@FXML
	void button_AddMerchant_OnAction(ActionEvent event) throws IOException {

		if (StringUtils.isNotEmpty(getProduct_type()) && StringUtils.isNotEmpty(getMerchant_name())
				&& StringUtils.isNotEmpty(getMerchant_phone()) && StringUtils.isNotEmpty(getMerchant_address())
				&& StringUtils.isNotEmpty(getMerchant_code())) {

			if (StringUtils.isEmpty(getMerchantId())) {
				merchant = createMerchant();
				loadUserDetails();
				merchantInsertStatus.setText("Merchant Added!!!");
			} else {
				merchant = updateMerchant(getMerchantId());
				loadUserDetails();
				merchantInsertStatus.setText("Merchant Updated!!!");
			}

		} else {
			merchantInsertStatus.setText("Product Inserted Unccessfull Please Try Again With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADDPRODUCT);
	}

	Callback<TableColumn<Merchant, Boolean>, TableCell<Merchant, Boolean>> cellFactory = new Callback<TableColumn<Merchant, Boolean>, TableCell<Merchant, Boolean>>() {
		@Override
		public TableCell<Merchant, Boolean> call(final TableColumn<Merchant, Boolean> param) {
			final TableCell<Merchant, Boolean> cell = new TableCell<Merchant, Boolean>() {
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
							Merchant merchant = getTableView().getItems().get(getIndex());
							updateMerchant(merchant);
							button_AddMerchant.setText("Update Merchant");
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

				private void updateMerchant(Merchant merchant) {
					setMerchant_address(merchant.getAddress());
					setMerchant_code(merchant.getCode());
					setMerchant_name(merchant.getName());
					setMerchant_phone(merchant.getPhone());
					product_type.getSelectionModel().select(merchant.getProductType().getType());
					setMerchantId(Long.toString(merchant.getId()));
				}
			};
			return cell;
		}
	};

	@FXML
	private void deleteMerchant(ActionEvent event) {
		List<Merchant> merchants = tableView.getSelectionModel().getSelectedItems();
		boolean checkAvailable = false;
		for (Merchant merchant : merchants) {
			List<ProductStock> stocks = productStockService.findByMerchant(merchant);

			if (!stocks.isEmpty()) {
				checkAvailable = true;
			}

		}
		if (checkAvailable) {
			merchantInsertStatus.setText("Can't delete Merchant it is in use");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete selected?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK)
				merchantService.deleteInBatch(merchants);

			loadUserDetails();
		}

	}

	private void loadUserDetails() {
		tableData.clear();

		tableData.addAll(merchantService.findAll());

		tableView.setItems(tableData);
	}

	private void setColumnProperties() {
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		merchantNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		merchantPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		merchantProductTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("productType"));
		merchantAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		merchantCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));

		updateTableColumn.setCellFactory(cellFactory);

		tableView.setItems(tableData);
		tableView.setEditable(true);

	}
	
public ObservableList<String> getProductTypeList() {
		
		List<ProductType> types = productTypeService.findAll();
		productTypeObservableList = FXCollections.observableArrayList();
		for (ProductType type : types) {
			productTypeObservableList.add(type.getType());
		}

		return productTypeObservableList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		product_type.setItems(getProductTypeList());
		
		setColumnProperties();
		loadUserDetails();

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
	}

}
