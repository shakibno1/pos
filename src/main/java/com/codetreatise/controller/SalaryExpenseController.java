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
import com.codetreatise.bean.PayType;
import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.EmployeeService;
import com.codetreatise.service.PayTypeService;
import com.codetreatise.service.SalaryDeductionService;
import com.codetreatise.service.SalaryExpenseService;
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
public class SalaryExpenseController implements Initializable {

	@FXML
	private JFXButton button_Back ;
	@FXML
	private JFXButton button_LoadAmountPayable ;
	@FXML
	private JFXTextField salaryExpense_amount ;
	@FXML
	private JFXTextField ledger_month;
	@FXML
	private JFXTextField amount_payable;
	@FXML
	private JFXButton button_AddPaymentType ;
	@FXML
	private JFXButton button_AddSalaryExpense ;
	@FXML
	private JFXComboBox<String> selling_point ;
	@FXML
	private JFXComboBox<String> employee ;
	@FXML
	private JFXComboBox<String> payment_type ;
	@FXML
	private JFXComboBox<Integer> year;
	@FXML
	private JFXComboBox<String> month;
	@FXML
	private Label salaryExpenseId ;
	@FXML
	private Label salaryExpenseInsertStatus ;
	@FXML
	private Label currentDateTime ;
	
	private static User user;
	
	
	
	public String getSalaryExpense_amount() {
		return salaryExpense_amount.getText();
	}

	public void setSalaryExpense_amount(String salaryExpense_amount) {
		this.salaryExpense_amount.setText(salaryExpense_amount);
	}

	public String getLedger_month() {
		return ledger_month.getText();
	}

	public void setLedger_month(String ledger_month) {
		this.ledger_month.setText(ledger_month);
	}

	public String getAmount_payable() {
		return amount_payable.getText();
	}

	public void setAmount_payable(String amount_payable) {
		this.amount_payable.setText(amount_payable);
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

	public String getPayment_type() {
		return payment_type.getSelectionModel().getSelectedItem();
	}

	public void setPayment_type(String payment_type) {
		this.payment_type.getSelectionModel().select(payment_type);
	}

	public Integer getYear() {
		return year.getSelectionModel().getSelectedItem();
	}

	public void setYear(Integer year) {
		this.year.getSelectionModel().select(year);
	}

	public String getMonth() {
		return month.getSelectionModel().getSelectedItem();
	}

	public void setMonth(String month) {
		this.month.getSelectionModel().select(month);
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ShopService shopService;

	@Autowired
	private PayTypeService payTypeService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private SalaryExpenseService salaryExpenseService;
	
	@Autowired
	private SalaryDeductionService salaryDeductionService;
	
	private Shop shop;
	
	private Employee employeeBean;
	
	private ObservableList<String> paymentTypeObservableList = FXCollections.observableArrayList();
	private ObservableList<String> productSellingPointObservableList = FXCollections.observableArrayList();
	private ObservableList<String> employeeObservableList = FXCollections.observableArrayList();
	private ObservableList<Integer> yearObservableList = FXCollections.observableArrayList();
	private ObservableList<String> monthObservableList = FXCollections.observableArrayList();

	public static void setData(User newuser) {
		user = newuser;
	}
	
	public PayType getPayTypeObject(String type) {

		PayType payType = payTypeService.findByType(type);

		return payType;

	}
	
	private SalaryExpense createSalaryExpense () {
		
		SalaryExpense salaryExpense = new SalaryExpense();
		salaryExpense.setAmount(Float.parseFloat(getSalaryExpense_amount()));
		salaryExpense.setCreatedBy(user.getName());
		salaryExpense.setUpdatedBy(user.getName());
		salaryExpense.setCreated(new Timestamp(System.currentTimeMillis()));
		salaryExpense.setUpdated(new Timestamp(System.currentTimeMillis()));
		salaryExpense.setEmployee(employeeBean);
		salaryExpense.setLedgerMonth(getLedger_month());
		salaryExpense.setPayType(getPayTypeObject(getPayment_type()));
		salaryExpense.setShop(shop);
		
		SalaryExpense newSalaryExpense = salaryExpenseService.save(salaryExpense);

		salaryExpenseInsertStatus.setText("Salary Expense Added Successfully "+newSalaryExpense.getAmount());
		salaryExpenseId.setText(salaryExpense.getId().toString());

		salaryExpense_amount.setText("");
		
		return newSalaryExpense;
	}

	@FXML
	void button_AddSalaryExpense_OnAction(ActionEvent event) throws IOException {
		button_AddSalaryExpense.setDisable(true);

			if (StringUtils.isNotEmpty(getPayment_type()) && StringUtils.isNotEmpty(getSalaryExpense_amount()) && StringUtils.isNotEmpty(getEmployee())) {
				
				
				if (getPayment_type().equals("Salary")) {
					
					if (getLedger_month().equals("")) {
						salaryExpenseInsertStatus.setText("Please try with proper data");
					} else {
						
						SalaryExpense salaryExpense = createSalaryExpense();
//						salaryExpenseInsertStatus.setText("Salary Expense Added Successfully");
//						salaryExpenseId.setText(salaryExpense.getId().toString());

					}

				} else {
					
					SalaryExpense salaryExpense = createSalaryExpense();
//					salaryExpenseId.setText(salaryExpense.getId().toString());
//					salaryExpenseInsertStatus.setText("Salary Expense Added Successfully");
				}

			} else {
				salaryExpenseInsertStatus.setText("Please try with proper data");
			}
		button_AddSalaryExpense.setDisable(false);
	}

	@FXML
	void button_LoadAmountPayable_OnAction(ActionEvent event) throws IOException {
		
		if (StringUtils.isNotEmpty(getPayment_type()) && StringUtils.isNotEmpty(getSelling_point()) 
				&& StringUtils.isNotEmpty(getEmployee())) {

			if (payment_type.getSelectionModel().getSelectedItem().equals("Salary")
					&& (StringUtils.isNotEmpty(getLedger_month()))) {
				
				List<SalaryExpense> salaryExpense = salaryExpenseService.findByEmployeeAndLedgerMonth(employeeBean, getLedger_month());
				
				List<SalaryDeduction> salaryDeduction = salaryDeductionService.findByEmployeeAndLedgerMonth(employeeBean, getLedger_month());
				Float expenseAmount = 0f;
				Float deductionAmount = 0f;
				
				for(SalaryExpense salaryExpense2 : salaryExpense){
					expenseAmount = expenseAmount + salaryExpense2.getAmount();
				}
				
				for(SalaryDeduction salaryDeduction2 : salaryDeduction){
					deductionAmount = deductionAmount + salaryDeduction2.getAmount();
				}
				
//				Float deductionAmount = salaryDeduction!=null ? salaryDeduction.getAmount() : 0f ;
//				
//				Float expenseAmount = salaryExpense!=null ? salaryExpense.getAmount() : 0f ;
				
				Float netPayable = employeeBean.getSalary() - deductionAmount - expenseAmount;
				
				setAmount_payable(netPayable.toString());
			}

		} else {
			salaryExpenseInsertStatus.setText("Try With Proper data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		FirstPageController.setUser(user);
		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}

	@FXML
	void button_AddPaymentType_OnAction(ActionEvent event) throws IOException {
		PayTypeController.setData(user);
		stageManager.switchScene(FxmlView.PAYTYPE);
	}
	
	public ObservableList<String> getSalaryExpenseTypeList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<PayType> types = payTypeService.findAll();
		paymentTypeObservableList = FXCollections.observableArrayList();
		for (PayType type : types) {
			paymentTypeObservableList.add(type.getType());
		}

		return paymentTypeObservableList;
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
		
		if(user.getPrivilege().equals("0")){
			button_AddPaymentType.setVisible(false);
			
		}

		button_AddSalaryExpense.defaultButtonProperty().bind(button_AddSalaryExpense.focusedProperty());
		button_AddSalaryExpense.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_AddSalaryExpense.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_AddSalaryExpense.setStyle("-fx-background-color: #FF6C6C");
			}
		});
		if (user.getPrivilege().equals("0")) {
			
			selling_point.getSelectionModel().select(user.getShop().getName());
			shop = user.getShop();
			employee.setItems(null);
			employeeObservableList = FXCollections.observableArrayList();
			
			List<Employee> employees = employeeService.findByShopAndStatus(user.getShop(),true);
			for(Employee employee : employees){
				employeeObservableList.add(employee.getName().concat(" [").concat(employee.getId().toString()).concat("] "));
			}
			employee.setItems(employeeObservableList);
			selling_point.setDisable(true);
		}

		salaryExpense_amount.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) {
				if ((!salaryExpense_amount.getText().matches("^(0|[1-9][0-9]*)$"))) {
					salaryExpense_amount.setText("");

				} else if (payment_type.getSelectionModel().getSelectedItem().equals("Salary")) {

					if (amount_payable.getText().equals("")) {
						salaryExpense_amount.setText("");
						salaryExpenseInsertStatus.setText("Please Set Payable Amount First");
					} else {
						String amountPayable = amount_payable.getText();
						float amountPayableInt = Float.parseFloat(amountPayable);
						float amount = Float.parseFloat(salaryExpense_amount.getText());

						if (amount > amountPayableInt) {
							salaryExpense_amount.setText("");
							salaryExpenseInsertStatus.setText("Amount Can't be greater than Amount Payable");
						}
					}
				}
			}

		});

		amount_payable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) {
				if (!salaryExpense_amount.getText().matches("^(0|[1-9][0-9]*)$")) {
					salaryExpense_amount.setText("");
				}
			}

		});
		
		payment_type.setItems(getSalaryExpenseTypeList());
		selling_point.setItems(getShopList());
		
		
		selling_point.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				employee.setItems(null);
				employeeObservableList = FXCollections.observableArrayList();
				shop = shopService.findByName(getSelling_point());
				List<Employee> employees = employeeService.findByShopAndStatus(shop,true);
				for (Employee employee : employees) {
					employeeObservableList
							.add(employee.getName().concat(" [").concat(employee.getId().toString()).concat("] "));
				}
				employee.setItems(employeeObservableList);

			}
		});

		employee.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String employeeStr = getEmployee();
				employeeBean = employeeService.find(Long.parseLong(employeeStr.substring(employeeStr.indexOf("[")+1,employeeStr.indexOf("]"))));
			}
		});

		payment_type.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String paymentType = payment_type.getSelectionModel().getSelectedItem();
				if (!paymentType.equals("Salary")) {
					year.setVisible(false);
					month.setVisible(false);
					ledger_month.setText("");
					ledger_month.setEditable(false);
					amount_payable.setEditable(false);
					amount_payable.setText("");
				} else {
					year.setVisible(true);
					month.setVisible(true);
					ledger_month.setEditable(true);
					amount_payable.setEditable(true);
				}
			}
		});

		int yearInt = Calendar.getInstance().get(Calendar.YEAR);

		for (int i = yearInt - 5; i < yearInt + 5; i++) {
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
				if (year.getSelectionModel().getSelectedItem() != null) {
					int yearInt = year.getSelectionModel().getSelectedItem();
					String yearStr = Integer.toString(yearInt);
					String ledgerMonth;
					String selectedMonth;
					String monthStr = month.getSelectionModel().getSelectedItem();
					if (monthStr.equals("January")) {
						selectedMonth = "01";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("February")) {
						selectedMonth = "02";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("March")) {
						selectedMonth = "03";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("April")) {
						selectedMonth = "04";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("May")) {
						selectedMonth = "05";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("June")) {
						selectedMonth = "06";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("July")) {
						selectedMonth = "07";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("August")) {
						selectedMonth = "08";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("September")) {
						selectedMonth = "09";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("October")) {
						selectedMonth = "10";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("November")) {
						selectedMonth = "11";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("December")) {
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
				if (month.getSelectionModel().getSelectedItem() != null) {
					int yearInt = year.getSelectionModel().getSelectedItem();
					String yearStr = Integer.toString(yearInt);
					String ledgerMonth;
					String selectedMonth;
					String monthStr = month.getSelectionModel().getSelectedItem();
					if (monthStr.equals("January")) {
						selectedMonth = "01";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("February")) {
						selectedMonth = "02";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("March")) {
						selectedMonth = "03";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("April")) {
						selectedMonth = "04";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("May")) {
						selectedMonth = "05";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("June")) {
						selectedMonth = "06";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("July")) {
						selectedMonth = "07";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("August")) {
						selectedMonth = "08";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("September")) {
						selectedMonth = "09";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("October")) {
						selectedMonth = "10";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("November")) {
						selectedMonth = "11";
						ledgerMonth = selectedMonth.concat("-").concat(yearStr);
						ledger_month.setText(ledgerMonth);
					} else if (monthStr.equals("December")) {
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
			currentDateTime.setText(
					DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour)
							+ ":" + String.format("%02d", minute) + ":" + String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

	}

}
