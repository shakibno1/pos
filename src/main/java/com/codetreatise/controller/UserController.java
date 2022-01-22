package com.codetreatise.controller;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.UserService;
import com.codetreatise.service.impl.ShopServiceImpl;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 * @author Ram Alapure
 * @since 05-04-2017
 */

@Controller
public class UserController implements Initializable {

	@FXML
	private JFXTextField userName;
	@FXML
	private JFXTextField userName_real;
	@FXML
	private JFXTextField password;
	@FXML
	private JFXTextField mobile;
	@FXML
	private JFXTextField address;
	@FXML
	private JFXTextField reference;
	@FXML
	private JFXCheckBox administrativePrivilege;
	@FXML
	private JFXButton addUser;
	@FXML
	private JFXButton back;
	@FXML
	private JFXComboBox<String> product_selling_point;
	@FXML
	private Text status;
	@FXML
	private Label userId;
	@FXML
	private MenuItem deleteUsers;

	@FXML
	private TableView<User> tableView;
	@FXML
	private TableColumn<User, String> idTableColumn;
	@FXML
	private TableColumn<User, String> nameTableColumn;
	@FXML
	private TableColumn<User, String> userNameTableColumn;
	@FXML
	private TableColumn<User, String> passwordTableColumn;
	@FXML
	private TableColumn<User, String> mobileTableColumn;
	@FXML
	private TableColumn<User, String> addressTableColumn;
	@FXML
	private TableColumn<User, String> referenceTableColumn;
	@FXML
	private TableColumn<User, String> privilegeTableColumn;
	@FXML
	private TableColumn<User, String> product_selling_point_idTableColumn;
	@FXML
	private TableColumn<User, String> deleteTableColumn;
	@FXML
	private TableColumn<User, Boolean> updateTableColumn;
	private ObservableList<User> tableData = FXCollections.observableArrayList();
	private ObservableList<String> shopObservableList;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private UserService userService;

	private Shop shop;

	@Autowired
	private ShopServiceImpl serviceImpl;

	private String password_hashed;
	
	private static User User;

	public static User getUser() {
		return User;
	}

	public static void setUser(User user) {
		User = user;
	}

	@FXML
	void back(ActionEvent event) {

		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}

	public String getUserName() {
		return userName.getText();
	}

	public void setUserName(String userName) {
		this.userName.setText(userName);
	}

	public String getUserName_real() {
		return userName_real.getText();
	}

	public void setUserName_real(String userName_real) {
		this.userName_real.setText(userName_real);
	}

	public String getPassword() {

		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getText().getBytes(StandardCharsets.UTF_8));
			password_hashed = DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}

		return password_hashed;
	}

	public String getMobile() {
		return mobile.getText();
	}

	public void setMobile(String mobile) {
		this.mobile.setText(mobile);
	}

	public String getAddress() {
		return address.getText();
	}

	public void setAddress(String address) {
		this.address.setText(address);
	}

	public String getReference() {
		return reference.getText();
	}

	public void setReference(String reference) {
		this.reference.setText(reference);
	}

	public String getProduct_selling_point() {
		return product_selling_point.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_point(String product_selling_point) {
		this.product_selling_point.getSelectionModel().select(product_selling_point);
	}

	public String getAdministrativePrivilege() {

		if (administrativePrivilege.isSelected()) {
			return "1";
		} else {
			return "0";
		}
	}

	public void setAdministrativePrivilege(String administrativePrivilege) {

		if (administrativePrivilege.equals("1")) {
			this.administrativePrivilege.setSelected(true);
		} else {
			this.administrativePrivilege.setSelected(false);
		}
	}

	public String getUserId() {
		return userId.getText();
	}

	public void setUserId(String userId) {
		this.userId.setText(userId);
	}

	public ObservableList<String> getShopList() {
		
		List<Shop> shops = serviceImpl.findAll();
		shopObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			shopObservableList.add(shop.getName());
		}

		return shopObservableList;
	}

	public Shop getShop() {

		shop = serviceImpl.findByName(getProduct_selling_point());

		return shop;

	}

	private void saveAlert(User user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The user " + user.getName() + " " + user.getUserName() + " has been created and \n"
				+ getMobile() + " id is " + user.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(User user) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("User updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The user " + user.getName() + " " + user.getUserName() + " has been updated.");
		alert.showAndWait();
	}

	private boolean validateUserName(String userName) {

		List<User> users = userService.findAll();

		for (User user : users) {
			if (user.getUserName().equals(userName)) {
				return false;
			}
		}

		return true;
	}

	private void clearFields() {
		userId.setText(null);
		userName.clear();
		userName_real.clear();
		address.clear();
		reference.clear();
		administrativePrivilege.setSelected(false);
		mobile.clear();
		password.clear();
		product_selling_point.getSelectionModel().clearSelection();
		addUser.setText("Add User");
	}

	@FXML
	private void addUser(ActionEvent event) {

		if (StringUtils.isEmpty(getUserName()) || StringUtils.isEmpty(getUserName_real())
				|| StringUtils.isEmpty(getAddress()) || StringUtils.isEmpty(getMobile())
				|| StringUtils.isEmpty(getReference()) || StringUtils.isEmpty(getProduct_selling_point())) {
			status.setText("Try with proper data");
		} else {
			if (StringUtils.isEmpty(getUserId())) {
				if (StringUtils.isNotEmpty(password.getText())) {
					if (validateUserName(getUserName())) {
						User user = new User();
						user.setName(getUserName());
						user.setCreatedBy(User.getName());
						user.setUpdatedBy(User.getName());
						user.setCreated(new Timestamp(System.currentTimeMillis()));
						user.setUpdated(new Timestamp(System.currentTimeMillis()));
						user.setUserName(getUserName_real());
						user.setMobile(getMobile());
						user.setPrivilege(getAdministrativePrivilege());
						user.setPassword(getPassword());
						user.setReference(getReference());
						user.setAddress(getAddress());
						user.setShop(getShop());
						User newUser = userService.save(user);
						saveAlert(newUser);
						loadUserDetails();
					}

				} else {
					status.setText("Try with proper data");
				}
			} else {
				if (StringUtils.isEmpty(password.getText())) {
					User user = userService.find(Long.parseLong(getUserId()));

//					user.setName(getUserName());
//					user.setUserName(getUserName_real());
					user.setMobile(getMobile());
					user.setPrivilege(getAdministrativePrivilege());
					user.setReference(getReference());
					user.setAddress(getAddress());
					user.setUpdatedBy(User.getName());
					user.setUpdated(new Timestamp(System.currentTimeMillis()));
//					user.setShop(getShop());
					User updatedUser = userService.update(user);
					updateAlert(updatedUser);
					loadUserDetails();
				} else {
					User user = userService.find(Long.parseLong(getUserId()));

//					user.setName(getUserName());
//					user.setUserName(getUserName_real());
					user.setMobile(getMobile());
					user.setPrivilege(getAdministrativePrivilege());
					user.setPassword(getPassword());
					user.setReference(getReference());
					user.setAddress(getAddress());
//					user.setShop(getShop());
					User updatedUser = userService.update(user);
					updateAlert(updatedUser);
					loadUserDetails();
				}

			}
			clearFields();
		}

	}

	@FXML
	private void deleteUsers(ActionEvent event) {
		List<User> users = tableView.getSelectionModel().getSelectedItems();
		boolean checkAdmin = false;
		for (User user : users) {
			if (user.getUserName().equals("admin")) {
				checkAdmin = true;
			}
		}
		if (checkAdmin) {
			status.setText("Can't delete Admin user");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete selected?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK)
				userService.deleteInBatch(users);

			loadUserDetails();
		}

	}

	Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
		@Override
		public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
			final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
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
							User user = getTableView().getItems().get(getIndex());
							updateUser(user);
							addUser.setText("Update User");
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

				private void updateUser(User user) {
					userId.setText(Long.toString(user.getId()));
					userName.setText(user.getName());
					userName_real.setText(user.getUserName());
					address.setText(user.getAddress());
					reference.setText(user.getReference());
					mobile.setText(user.getMobile());

					if (user.getPrivilege().equals("1")) {
						administrativePrivilege.setSelected(true);
					} else {
						administrativePrivilege.setSelected(false);
					}

					product_selling_point.getSelectionModel().select(user.getShop().getName());
				}
			};
			return cell;
		}
	};

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		product_selling_point.setItems(getShopList());

		setColumnProperties();
		loadUserDetails();

	}

	private void loadUserDetails() {
		tableData.clear();
		List<User> users = userService.findAll();

		for (User user : users) {
			System.out.println("asdasd  " + user.getShop().getName());
			System.out.println("asdasd  " + user.getShop());
		}

		tableData.addAll(userService.findAll());

		tableView.setItems(tableData);
	}

	private void setColumnProperties() {

		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		userNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		referenceTableColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
		privilegeTableColumn.setCellValueFactory(new PropertyValueFactory<>("privilege"));
		product_selling_point_idTableColumn.setCellValueFactory(new PropertyValueFactory<>("shop"));
		mobileTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		addressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

		deleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
		updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));

		updateTableColumn.setCellFactory(cellFactory);

	}
}
