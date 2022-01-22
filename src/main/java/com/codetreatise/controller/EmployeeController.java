package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.EmployeeService;
import com.codetreatise.service.impl.ShopServiceImpl;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
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
public class EmployeeController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField employee_name;
	@FXML
	private JFXTextField employee_phone;
	@FXML
	private JFXTextField employee_salary;
	@FXML
	private JFXTextArea employee_address;
	@FXML
	private JFXCheckBox employeeStatus;
	@FXML
	private JFXButton button_AddEmployee;
	@FXML
	private Label employeeId;
	@FXML
	private Label employeeInsertStatus;
	@FXML
	private Label currentDateTime;
	@FXML
	private JFXComboBox<String> product_selling_point;
	private ObservableList<String> shopObservableList = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Employee> tableView;
	@FXML
	private TableColumn<Employee, String> idTableColumn;
	@FXML
	private TableColumn<Employee, String> employeeNameTableColumn;
	@FXML
	private TableColumn<Employee, String> employeeSellingPointTableColumn;
	@FXML
	private TableColumn<Employee, String> employeePhoneTableColumn;
	@FXML
	private TableColumn<Employee, String> employeeAddressTableColumn;
	@FXML
	private TableColumn<Employee, String> employeeSalaryTableColumn;
	@FXML
	private TableColumn<Employee, String> employeePreviousSalaryTableColumn;
	@FXML
	private TableColumn<Employee, String> deleteTableColumn;
	@FXML
	private TableColumn<Employee, String> statusTableColumn;
	@FXML
	private TableColumn<Employee, Boolean> updateTableColumn;
	private ObservableList<Employee> tableData = FXCollections.observableArrayList();
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private ShopServiceImpl serviceImpl;
	
	private Shop shop;
	
	private static User user;
	
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		EmployeeController.user = user;
	}

	public String getEmployee_name() {
		return employee_name.getText();
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name.setText(employee_name);
	}

	public String getEmployee_phone() {
		return employee_phone.getText();
	}

	public void setEmployee_phone(String employee_phone) {
		this.employee_phone.setText(employee_phone);
	}

	public Float getEmployee_salary() {
		return Float.parseFloat(employee_salary.getText());
	}

	public void setEmployee_salary(Float employee_salary) {
		this.employee_salary.setText(Float.toString(employee_salary));
	}

	public String getEmployee_address() {
		return employee_address.getText();
	}

	public void setEmployee_address(String employee_address) {
		this.employee_address.setText(employee_address);
	}

	public String getEmployeeId() {
		return employeeId.getText();
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId.setText(employeeId);
	}
	
	public String getProduct_selling_point() {
		return product_selling_point.getSelectionModel().getSelectedItem();
	}

	public void setProduct_selling_point(String product_selling_point) {
		this.product_selling_point.getSelectionModel().select(product_selling_point);
	}
	
	public Boolean getEmployeeStatus() {
		if(employeeStatus.isSelected()){
			return true;
		}
		else{
			return false;
		}
		
	}

	public void setEmployeeStatus(JFXCheckBox employeeStatus) {
		this.employeeStatus = employeeStatus;
	}

	public Shop getShop() {
		
		shop = serviceImpl.findByName(getProduct_selling_point());
		
		return shop;
		
	}
	
	private void saveAlert(Employee employee) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Employee saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The employee " + employee.getName() + " " + employee.getName() + " has been created and \n"
				+ getEmployee_phone() + " id is " + employee.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(Employee employee) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Employee updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The employee " + employee.getName() + " " + employee.getName() + " has been updated.");
		alert.showAndWait();
	}
	
	private void loadUserDetails() {
		
		tableData.clear();
		List<Employee> employees = service.findAll();
		for(Employee employee : employees){
			System.out.println("asdasd  "   +employee.getShop().getName());
			System.out.println("asdasd  "   +employee.getShop());
		}
		
		tableData.addAll(service.findAll());

		tableView.setItems(tableData);
	}
	
	public ObservableList<String> getShopList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = serviceImpl.findAll();
		shopObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			shopObservableList.add(shop.getName());
		}

		return shopObservableList;
	}
	
	private void clearFields() {
		employeeId.setText(null);
		employee_name.clear();
		employee_address.clear();
		employee_phone.clear();
		employee_salary.clear();
		product_selling_point.getSelectionModel().clearSelection();
		button_AddEmployee.setText("Add User");
	}

	@FXML
	void button_AddEmployee_OnAction(ActionEvent event) throws IOException {
	
		if (StringUtils.isNotEmpty(getEmployee_name()) || StringUtils.isNotEmpty(getEmployee_address())
				|| StringUtils.isNotEmpty(Float.toString(getEmployee_salary())) || StringUtils.isNotEmpty(getEmployee_phone())
				|| StringUtils.isNotEmpty(getProduct_selling_point())) {
			
			if (StringUtils.isEmpty(getEmployeeId())) {
				Employee employee = new Employee();
				employee.setName(getEmployee_name());
				employee.setAddress(getEmployee_address());
				employee.setMobile(getEmployee_phone());
				employee.setSalary(getEmployee_salary());
				employee.setShop(getShop());
				employee.setStatus(getEmployeeStatus());
				employee.setCreatedBy(user.getName());
				employee.setUpdatedBy(user.getName());
				employee.setCreated(new Timestamp(System.currentTimeMillis()));
				employee.setUpdated(new Timestamp(System.currentTimeMillis()));
				Employee newEmployee = service.save(employee);
				saveAlert(newEmployee);
				loadUserDetails();
			}
			else{
				Employee employee = service.find(Long.parseLong(getEmployeeId()));
				
				employee.setName(getEmployee_name());
				employee.setAddress(getEmployee_address());
				employee.setStatus(getEmployeeStatus());
				employee.setMobile(getEmployee_phone());
				employee.setUpdatedBy(user.getName());
				employee.setUpdated(new Timestamp(System.currentTimeMillis()));
				employee.setShop(getShop());
				if(!employee.getSalary().equals(getEmployee_salary())){
					employee.setPrevious_salary(employee.getSalary());
				}
				employee.setSalary(getEmployee_salary());
				Employee updatedUser = service.update(employee);
				updateAlert(updatedUser);
				loadUserDetails();
			}

		}
		else{
			
		}
		
		clearFields();
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}
	
	Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>> cellFactory = new Callback<TableColumn<Employee, Boolean>, TableCell<Employee, Boolean>>() {
		@Override
		public TableCell<Employee, Boolean> call(final TableColumn<Employee, Boolean> param) {
			final TableCell<Employee, Boolean> cell = new TableCell<Employee, Boolean>() {
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
							Employee user = getTableView().getItems().get(getIndex());
							updateUser(user);
							button_AddEmployee.setText("Update Employee");
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

				private void updateUser(Employee user) {
					employeeId.setText(Long.toString(user.getId()));
					employee_name.setText(user.getName());
					employee_address.setText(user.getAddress());
					employee_phone.setText(user.getMobile());
					if(user.getStatus()!=null){
						employeeStatus.setSelected(user.getStatus());
					}
					employee_salary.setText(Float.toString(user.getSalary()));
					
					product_selling_point.getSelectionModel().select(user.getShop().getName());
					
					// if(user.getGender().equals("Male"))
					// rbMale.setSelected(true);
					// else rbFemale.setSelected(true);
					// cbRole.getSelectionModel().select(user.getRole());
				}
			};
			return cell;
		}
	};
	
	private void setColumnProperties(){
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		employeePhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		employeePreviousSalaryTableColumn.setCellValueFactory(new PropertyValueFactory<>("previous_salary"));
		employeeSalaryTableColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
		employeeAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		employeeSellingPointTableColumn.setCellValueFactory(new PropertyValueFactory<>("shop"));
		statusTableColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		deleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
		updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));
		
		tableView.setItems(tableData);
		
//		employeeNameTableColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
//		employeePhoneTableColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
//		employeeSellingPointTableColumn.setCellFactory(ComboBoxTableCell.<Employee,String>forTableColumn(getShopList()));
//		employeeAddressTableColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
//		employeeSalaryTableColumn.setCellFactory(TextFieldTableCell.<Employee>forTableColumn());
		
		updateTableColumn.setCellFactory(cellFactory);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		setColumnProperties();
		
		loadUserDetails();
		
		
		employee_phone.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!employee_phone.getText().matches("[0-9]+")) {
					// when it not matches the pattern (1.0 - 6.0)
					// set the textField empty
					employee_phone.setText("");
				}
			}

		});
		
		employee_salary.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { // when focus lost
				if (!employee_salary.getText().matches("[0-9.]*")) {
					// when it not matches the pattern (1.0 - 6.0)
					// set the textField empty
					employee_salary.setText("");
				}
			}

		});
		product_selling_point.setItems(getShopList());
		
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

	}
	
	

}
