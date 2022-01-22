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

import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.Merchant;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ExpenseService;
import com.codetreatise.service.ExpenseTypeService;
import com.codetreatise.service.MerchantService;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;

@Controller
public class ExpenseController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private DatePicker expenseDate;
	@FXML
	private JFXTextField expense_amount ;
	@FXML
	private JFXButton button_AddExpense;
	@FXML
	private JFXButton button_AddExpenseType;
	@FXML
	private JFXComboBox<String> expenseType ;
	@FXML
	private JFXComboBox<String> merchant;
	@FXML
	private Label expenseId;
	@FXML
	private Label expenseInsertStatus;
	@FXML
	private Label currentDateTime; 
	private ObservableList<String> expenseTypeObservableList = FXCollections.observableArrayList();
	private static User user;
	private ObservableList<String> productMerchantObservableList = FXCollections.observableArrayList();
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ExpenseTypeService expenseTypeService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private ExpenseService expenseService;
	
	public static void setData(User newuser){
		user = newuser;
	}
	
	
	
	public Float getExpense_amount() {
		return Float.parseFloat(expense_amount.getText());
	}



	public void setExpense_amount(Float expense_amount) {
		this.expense_amount.setText(expense_amount.toString());
	}



	public String getExpenseType() {
		return expenseType.getSelectionModel().getSelectedItem();
	}



	public void setExpenseType(String expenseType) {
		this.expenseType.getSelectionModel().select(expenseType);
	}



	public String getMerchant() {
		return merchant.getSelectionModel().getSelectedItem();
	}



	public void setMerchant(String merchant) {
		this.merchant.getSelectionModel().select(merchant);
	}



	public String getExpenseId() {
		return expenseId.getText();
	}



	public void setExpenseId(String expenseId) {
		this.expenseId.setText(expenseId);
	}
	
	
	public DatePicker getExpenseDate() {
		return expenseDate;
	}



	public void setExpenseDate(DatePicker expenseDate) {
		this.expenseDate = expenseDate;
	}



	public ExpenseType getExpenseTypeObject(String expenseType){
		
		ExpenseType expenseType2 = expenseTypeService.findByType(expenseType);
		
		return expenseType2;
		
	}
	
	public Merchant getMerchantObject(String merchant) {

		Merchant merchant2 = merchantService.findByName(merchant);

		return merchant2;

	}
	
	public Expense createExpense(){
		Expense expense = new Expense();
		expense.setAmount(getExpense_amount());
		expense.setCreatedBy(user.getName());
		expense.setUpdatedBy(user.getName());
		expense.setExpenseType(getExpenseTypeObject(getExpenseType()));
		expense.setMerchant(getMerchantObject(getMerchant()));
		expense.setShop(user.getShop());
		
		if(getExpenseDate().getValue()!=null){
			LocalDate localExpenseDate = getExpenseDate().getValue();
			Timestamp expenseTimestamp = Timestamp.valueOf(localExpenseDate.atTime(21, 59, 59, 999));
			expense.setCreated(expenseTimestamp);
			expense.setUpdated(expenseTimestamp);
			expense.setExpenseDate(expenseTimestamp);
		}
		else{
			expense.setCreated(new Timestamp(System.currentTimeMillis()));
			expense.setUpdated(new Timestamp(System.currentTimeMillis()));
			expense.setExpenseDate(new Timestamp(System.currentTimeMillis()));
		}
		
		Expense newExpense = expenseService.save(expense);
		expenseInsertStatus.setText("Expense Added Successfully!!! "+newExpense.getAmount());
		setExpenseId(newExpense.getId().toString());
		expense_amount.setText("");
		return newExpense;
	}
	
	public ObservableList<String> getMerchantList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Merchant> merchants = merchantService.findAll();
		productMerchantObservableList = FXCollections.observableArrayList();
		for (Merchant merchant : merchants) {
			productMerchantObservableList.add(merchant.getName());
		}

		return productMerchantObservableList;
	}
	
	public ObservableList<String> getProductTypeList() {
		
		List<ExpenseType> types = expenseTypeService.findAll();
		expenseTypeObservableList = FXCollections.observableArrayList();
		for (ExpenseType type : types) {
			expenseTypeObservableList.add(type.getType());
		}

		return expenseTypeObservableList;
	}



	@FXML
	void button_AddExpense_OnAction(ActionEvent event) throws IOException {
		
		button_AddExpense.setStyle("-fx-background-color: #FF6C6C");
		button_AddExpense.setDisable(true);
			
			if (StringUtils.isNotEmpty(getExpenseType()) && (StringUtils.isNotEmpty(getExpense_amount().toString()))) {
				if(getExpenseType().equals("Party Payment")){
					if(StringUtils.isNotEmpty(getMerchant())){
						createExpense();
//						expenseInsertStatus.setText("Expense Added Successfully!!!");
					}
					else{
						expenseInsertStatus.setText("Please select merchant!!!");
					}
				}
				else{
					createExpense();
//					expenseInsertStatus.setText("Expense Added Successfully!!!");
				}
				
			}
			else{
				expenseInsertStatus.setText("Expense Add Unccessfull Please Try Again With Proper Data");
			}
			
		
		button_AddExpense.setDisable(false);
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}
	
	@FXML
	void button_AddExpenseType_OnAction(ActionEvent event) throws IOException {
		ExpenseTypeController.setData(user);
		stageManager.switchScene(FxmlView.EXPENSETYPE);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(user.getPrivilege().equals("0")){
			button_AddExpenseType.setVisible(false);
			
		}
		
		button_AddExpense.defaultButtonProperty().bind(button_AddExpense.focusedProperty());
		button_AddExpense.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_AddExpense.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_AddExpense.setStyle("-fx-background-color: #FF6C6C");
			 }
		});
		expense_amount.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue) { 
				if (!expense_amount.getText().matches("^(0|[1-9][0-9]*)$")) {
					expense_amount.setText("");
				}
			}

		});
		
		expenseType.setItems(getProductTypeList());
		
		expenseType.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(!getExpenseType().equals("Party Payment")){
					merchant.setDisable(true);
				}
				else{
					merchant.setDisable(false);
				}
				
			}
		});
		
		merchant.setItems(getMerchantList());
		
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
