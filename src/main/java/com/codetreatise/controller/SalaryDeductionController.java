package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Employee;
import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.EmployeeService;
import com.codetreatise.service.SalaryDeductionService;
import com.codetreatise.service.SalaryDeductionTypeService;
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
import javafx.scene.text.Text;
import javafx.util.Duration;

@Controller
public class SalaryDeductionController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXButton button_AddSalaryDeductionType;
	@FXML
	private JFXTextField ledger_month;
	@FXML
	private JFXTextField amount;
	@FXML
	private JFXButton button_AddSalaryDeduction;
	@FXML
	private JFXComboBox<String> selling_point;
	@FXML
	private JFXComboBox<String> employee;
	@FXML
	private JFXComboBox<String> deduction_type;
	@FXML
	private JFXComboBox<Integer> year;
	@FXML
	private JFXComboBox<String> month ;
	@FXML
	private Label salaryExpenseId;
	@FXML
	private Label salaryExpenseInsertStatus;
	@FXML
	private Label currentDateTime;
	@FXML
	private Text salary;
	private static User user;
	private ObservableList<String> paymentTypeObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> employeeObservableList = FXCollections.observableArrayList();
	private ObservableList<Integer> yearObservableList = FXCollections.observableArrayList();
	private ObservableList<String> monthObservableList = FXCollections.observableArrayList();
	public static void setData(User newuser ){
		user = newuser;
	}
	
	public String getLedger_month() {
		return ledger_month.getText();
	}



	public void setLedger_month(String ledger_month) {
		this.ledger_month.setText(ledger_month);
	}



	public Float getAmount() {
		return Float.parseFloat(amount.getText());
	}



	public void setAmount(String amount) {
		this.amount.setText(amount);
	}



	public String getSelling_point() {
		return selling_point.getSelectionModel().getSelectedItem();
	}



	public void setSelling_point(String selling_point) {
		this.selling_point.getSelectionModel().select(selling_point);
	}



	public String getEmployee() {
		return employee.getSelectionModel().getSelectedItem();
	}



	public void setEmployee(String employee) {
		this.employee.getSelectionModel().select(employee);
	}



	public String getDeduction_type() {
		return deduction_type.getSelectionModel().getSelectedItem();
	}



	public void setDeduction_type(String deduction_type) {
		this.deduction_type.getSelectionModel().select(deduction_type) ;
	}
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private SalaryDeductionTypeService salaryDeductionTypeService;
	
	@Autowired
	private SalaryDeductionService salaryDeductionService;
	
	private Shop shop;
	
	private Employee employeeBean;

	
	public SalaryDeductionType getDeductionTypeObject(String type) {

		SalaryDeductionType salaryDeductionType = salaryDeductionTypeService.findByType(type);

		return salaryDeductionType;

	}
	
	private SalaryDeduction createSalaryDeduction(){
		SalaryDeduction salaryDeduction = new SalaryDeduction();
		salaryDeduction.setAmount(getAmount());
		salaryDeduction.setCreatedBy(user.getName());
		salaryDeduction.setUpdatedBy(user.getName());
		salaryDeduction.setCreated(new Timestamp(System.currentTimeMillis()));
		salaryDeduction.setUpdated(new Timestamp(System.currentTimeMillis()));
		salaryDeduction.setEmployee(employeeBean);
		salaryDeduction.setLedgerMonth(getLedger_month());
		salaryDeduction.setSalaryDeductionType(getDeductionTypeObject(getDeduction_type()));
		salaryDeduction.setShop(shop);
		
		SalaryDeduction newSalaryDeduction = salaryDeductionService.save(salaryDeduction);
		
		return newSalaryDeduction;
	}

	@FXML
	void button_AddSalaryExpense_OnAction(ActionEvent event) throws IOException {
		button_AddSalaryDeduction.setStyle("-fx-background-color: #FF6C6C");
		
		if(StringUtils.isNotEmpty(getAmount().toString()) && StringUtils.isNotEmpty(getDeduction_type())
				&& StringUtils.isNotEmpty(getEmployee()) && StringUtils.isNotEmpty(getSelling_point())
				&& StringUtils.isNotEmpty(getLedger_month())){
			SalaryDeduction salaryDeduction = createSalaryDeduction();
			salaryExpenseId.setText(salaryDeduction.getId().toString());
		}
		else{
			salaryExpenseInsertStatus.setText("Please try with proper data");
		}
		
		
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}
	
	@FXML
	void button_AddPaymentType_OnAction(ActionEvent event) throws IOException {
		SalaryDeductionTypeController.setData(user);
		stageManager.switchScene(FxmlView.SALARYDEDUCTIONTYPE);
		
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
	
	public ObservableList<String> getDeductionTypeList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<SalaryDeductionType> types = salaryDeductionTypeService.findAll();
		paymentTypeObservableList = FXCollections.observableArrayList();
		for (SalaryDeductionType type : types) {
			paymentTypeObservableList.add(type.getType());
		}

		return paymentTypeObservableList;
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(user.getPrivilege().equals("0")){
			button_AddSalaryDeductionType.setVisible(false);
			
		}
		
		button_AddSalaryDeduction.defaultButtonProperty().bind(button_AddSalaryDeduction.focusedProperty());
		button_AddSalaryDeduction.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_AddSalaryDeduction.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_AddSalaryDeduction.setStyle("-fx-background-color: #FF6C6C");
			 }
		});
		
		amount.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { 
				if ((!amount.getText().matches("^(0|[1-9][0-9]*)$"))) {
					amount.setText("");
					
					
				}
				else{
					String amountPayable = salary.getText();
					float amountPayableInt = Float.parseFloat(amountPayable);
					float amount = Float.parseFloat(this.amount.getText());
					
					if(amount>amountPayableInt){
						this.amount.setText("");
						salaryExpenseInsertStatus.setText("Deduction Amount Can't be greater than Salary");
					}
				}
			}

		});
		
		deduction_type.setItems(getDeductionTypeList());
		selling_point.setItems(getShopList());
		
		selling_point.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				employee.setItems(null);
				employeeObservableList = FXCollections.observableArrayList();
				shop = shopService.findByName(getSelling_point());
				List<Employee> employees = employeeService.findByShop(shop);
				for(Employee employee : employees){
					employeeObservableList.add(employee.getName().concat(" [").concat(employee.getId().toString()).concat("] "));
				}
				employee.setItems(employeeObservableList);
				
			}
		});
		
		employee.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String employeeStr = getEmployee();
				employeeBean = employeeService.find(Long.parseLong(employeeStr.substring(employeeStr.indexOf("[")+1,employeeStr.indexOf("]"))));
				salary.setText(employeeBean.getSalary().toString());
			}
		});
		
		int yearInt = Calendar.getInstance().get(Calendar.YEAR);
		
		for(int i=yearInt-5;i<yearInt+5;i++){
			yearObservableList.add(i);
		}
		
		year.setItems(yearObservableList);
		
		monthObservableList.add("January");
		monthObservableList.add("February");
		monthObservableList.add("March");
		monthObservableList.add("April");
		monthObservableList.add("May");
		monthObservableList.add("June");
		monthObservableList.add("July");
		monthObservableList.add("August");
		monthObservableList.add("September");
		monthObservableList.add("October");
		monthObservableList.add("November");
		monthObservableList.add("December");
		
		month.setItems(monthObservableList);
		
		month.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(year.getSelectionModel().getSelectedItem()!=null){
					int yearInt = year.getSelectionModel().getSelectedItem();
					String yearStr = Integer.toString(yearInt);
					String ledgerMonth;
					String selectedMonth;
					String monthStr = month.getSelectionModel().getSelectedItem();
					if(monthStr.equals("January")){
						selectedMonth = "01";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("February")){
						selectedMonth = "02";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("March")){
						selectedMonth = "03";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("April")){
						selectedMonth = "04";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("May")){
						selectedMonth = "05";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("June")){
						selectedMonth = "06";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("July")){
						selectedMonth = "07";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("August")){
						selectedMonth = "08";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("September")){
						selectedMonth = "09";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("October")){
						selectedMonth = "10";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("November")){
						selectedMonth = "11";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("December")){
						selectedMonth = "12";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
				}
			}
		});
		
		year.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(month.getSelectionModel().getSelectedItem()!=null){
					int yearInt = year.getSelectionModel().getSelectedItem();
					String yearStr = Integer.toString(yearInt);
					String ledgerMonth;
					String selectedMonth;
					String monthStr = month.getSelectionModel().getSelectedItem();
					if(monthStr.equals("January")){
						selectedMonth = "01";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("February")){
						selectedMonth = "02";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("March")){
						selectedMonth = "03";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("April")){
						selectedMonth = "04";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("May")){
						selectedMonth = "05";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("June")){
						selectedMonth = "06";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("July")){
						selectedMonth = "07";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("August")){
						selectedMonth = "08";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("September")){
						selectedMonth = "09";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("October")){
						selectedMonth = "10";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("November")){
						selectedMonth = "11";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					}
					else if(monthStr.equals("December")){
						selectedMonth = "12";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
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

	}
	
	

}
