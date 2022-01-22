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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Duration;

@Controller
public class AddProductTypeController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField product_type;
	@FXML
	private JFXButton button_AddProductType;
	@FXML
	private Label productInsertStatus;
	@FXML
	private Label currentDateTime;
	@FXML
	private Text typeId;
	@FXML
	private MenuItem deleteType;

	@FXML
	private TableView<ProductType> tableView;
	@FXML
	private TableColumn<ProductType, String> idTableColumn;
	@FXML
	private TableColumn<ProductType, String> productTableColumn;
	@FXML
	private TableColumn<ProductType, Boolean> updateTableColumn;
	private ObservableList<ProductType> tableData = FXCollections.observableArrayList();

	@Lazy
	@Autowired
	private StageManager stageManager;

	private static User user;

	private ProductType productType;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		AddProductTypeController.user = user;
	}

	public String getProduct_type() {
		return product_type.getText();
	}

	public void setProduct_type(String product_type) {
		this.product_type.setText(product_type);
	}

	public String getTypeId() {
		return typeId.getText();
	}

	public void setTypeId(String typeId) {
		this.typeId.setText(typeId);
	}

	@Autowired
	private ProductTypeService productTypeService;

	@Autowired
	private ProductStockService productService;

	@Autowired
	private MerchantService merchantService;

	private ProductType createProductType() {
		ProductType productType = new ProductType();

		productType.setCreatedBy(user.getName());
		productType.setType(getProduct_type());
		productType.setCreated(new Timestamp(System.currentTimeMillis()));
		productType.setUpdatedBy(user.getName());
		productType.setUpdated(new Timestamp(System.currentTimeMillis()));
		ProductType newProductType = productTypeService.save(productType);
		saveAlert(newProductType);
		return newProductType;
	}

	private ProductType updateProductType(String id) {
		productType = productTypeService.find(Long.parseLong(id));

		productType.setType(getProduct_type());
		productType.setUpdatedBy(user.getName());
		productType.setUpdated(new Timestamp(System.currentTimeMillis()));
		
		ProductType updatedProductType = productTypeService.update(productType);
		updateAlert(updatedProductType);
		return updatedProductType;
	}

	@FXML
	void button_AddProduct_OnAction(ActionEvent event) throws IOException {

		if (StringUtils.isNotEmpty(getProduct_type())) {

			if (StringUtils.isEmpty(getTypeId())) {

				if (validateProductType(getProduct_type())) {
					productType = createProductType();
					loadUserDetails();
					setProduct_type("");
					productInsertStatus.setText("Type Created!!!");
				} else {
					productInsertStatus.setText("Duplicate Type!!!");
				}

			} else {
				updateProductType(getTypeId());
				loadUserDetails();

				productInsertStatus.setText("Type Updated!!!");
			}

		} else {
			productInsertStatus.setText("Product Inserted Unccessfull Please Try Again With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ADDPRODUCT);

	}

	Callback<TableColumn<ProductType, Boolean>, TableCell<ProductType, Boolean>> cellFactory = new Callback<TableColumn<ProductType, Boolean>, TableCell<ProductType, Boolean>>() {
		@Override
		public TableCell<ProductType, Boolean> call(final TableColumn<ProductType, Boolean> param) {
			final TableCell<ProductType, Boolean> cell = new TableCell<ProductType, Boolean>() {
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
							ProductType productType = getTableView().getItems().get(getIndex());
							updateUser(productType);
							button_AddProductType.setText("Update Type");
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

				private void updateUser(ProductType productType) {
					setTypeId(Long.toString(productType.getId()));
					setProduct_type(productType.getType());
				}
			};
			return cell;
		}
	};

	private void loadUserDetails() {

		tableData.clear();

		tableData.addAll(productTypeService.findAll());

		tableView.setItems(tableData);
	}

	private void setColumnProperties() {
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		productTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));
		tableView.setItems(tableData);
		tableView.setEditable(true);

		productTableColumn.setCellFactory(TextFieldTableCell.<ProductType>forTableColumn());
		updateTableColumn.setCellFactory(cellFactory);
	}

	@FXML
	private void deleteType(ActionEvent event) {
		List<ProductType> types = tableView.getSelectionModel().getSelectedItems();
		boolean checkAvailable = false;

		for (ProductType type : types) {
			List<ProductStock> productStocks = productService.findByProductType(type);
			if (productStocks.isEmpty()) {
				List<Merchant> merchants = merchantService.findByProductType(type);
				if (!merchants.isEmpty()) {
					checkAvailable = true;
				}
			} else {
				checkAvailable = true;
			}
		}
		if (checkAvailable) {
			productInsertStatus.setText("Can't delete type it is in use");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete selected?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK)
				productTypeService.deleteInBatch(types);

			loadUserDetails();
		}

	}

	private boolean validateProductType(String type) {

		List<ProductType> types = productTypeService.findAll();

		for (ProductType productType : types) {
			if (productType.getType().equals(type)) {
				return false;
			}
		}

		return true;
	}

	private void saveAlert(ProductType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Type " + type.getType() + " " + " has been created and \n" + getProduct_type() + " id is "
				+ type.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(ProductType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The type " + type.getType() + " " + " has been updated.");
		alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

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
