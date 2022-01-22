package com.codetreatise.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.codetreatise.bean.PaymentInfo;
import com.codetreatise.service.PaymentInfoService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.UserService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Controller
public class FirstPageController implements Initializable {	
	@FXML
	private JFXButton button_AddItem;
	@FXML
	private JFXButton button_StockRealization;
	@FXML
	private JFXButton button_GeneralReport;
	@FXML
	private JFXButton button_AllReport;
	@FXML
	private JFXButton button_FindBuyingPrice;
	@FXML
	private JFXButton button_AddSalaryExpense;
	@FXML
	private JFXButton button_AddExpense;
	@FXML
	private JFXButton button_CustomerSearch;
	@FXML
	private JFXButton button_Employee;
	@FXML
	private JFXButton button_AddSellingPoint;
	@FXML
	private JFXButton button_SellItem;
	@FXML
	private JFXButton button_EditStock;
	@FXML
	private JFXButton button_AddUser;
	@FXML
	private JFXButton button_AddSalaryDeduction;
	@FXML
	private JFXButton button_GoodTransfer;
	@FXML
	private JFXButton button_ReturnItem;
	@FXML
	private JFXButton button_MembershipCard;
	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXButton button_PaymentInfo;
	@FXML
	private Text user_name = new Text();
	@FXML
	private Text product_selling_point = new Text();
	@FXML
	private Text warning;
	private static User user;
	
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		FirstPageController.user = user;
	}

	@Autowired
	private UserService userService;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@FXML
	void button_AddPaymentInfo_OnAction(ActionEvent event) throws IOException {
		PaymentInfoController.setUser(user);
		stageManager.switchScene(FxmlView.PAYMENTINFO);
	}
	
	@FXML
	void button_GeneralReport_OnAction(ActionEvent event) throws IOException {
		GeneralReportController.setUser(user);
		stageManager.switchScene(FxmlView.GENERALREPORT);
	}
	
	@FXML
	void button_AllReport_OnAction(ActionEvent event) throws IOException {
		AllReportController.setUser(user);
		stageManager.switchScene(FxmlView.ALLREPORT);
	}
	
	@FXML
	void button_FindBuyingPrice_OnAction(ActionEvent event) throws IOException {
		 stageManager.switchScene(FxmlView.FINDBUYINGPRICE);
	}
	
	@FXML
	void button_AddSellingPoint_OnAction(ActionEvent event) throws IOException {
			ShopController.setUser(user);
		 stageManager.switchScene(FxmlView.ADDSHOP);
		
	}
	
	@FXML
	void button_SearchCustomer_OnAction(ActionEvent event) throws IOException {
		CustomerSearchController.setUser(user);
		 stageManager.switchScene(FxmlView.CUSTOMERSEARCH);
		
	}
	
	@FXML
	void button_AddExpense_OnAction(ActionEvent event) throws IOException {
		
		ExpenseController.setData(user);
		stageManager.switchScene(FxmlView.EXPENSE);

	}
	
	@FXML
	void button_SalaryDeduction_OnAction(ActionEvent event) throws IOException {
		SalaryDeductionController.setData(user);
		stageManager.switchScene(FxmlView.SALARYDEDUCTION);

	}
	
	@FXML
	void button_GoodTransfer_OnAction(ActionEvent event) throws IOException {
		
		GoodTransferController.setUser(user);
		 stageManager.switchScene(FxmlView.GOODTRANSFER);
	}
	
	@FXML
	void button_StockRealization_OnAction(ActionEvent event) throws IOException {
		
		 stageManager.switchScene(FxmlView.STOCKREALIZATION);
	}
	
	
	
	@FXML
	void button_AddSalaryExpense_OnAction(ActionEvent event) throws IOException {
		SalaryExpenseController.setData(user);
		stageManager.switchScene(FxmlView.SALARYEXPENSE);
	}
	
	@FXML
	void button_AddEmployee_OnAction(ActionEvent event) throws IOException {
		EmployeeController.setUser(user);
		 stageManager.switchScene(FxmlView.ADDEMPLOYEE);
		

	}
	
	 @FXML
	 void button_AddItem_OnAction(ActionEvent event) throws IOException {
		 
		 AddProductController.setUser(user);
		 stageManager.switchScene(FxmlView.ADDPRODUCT);
		 	
		 
	 }
	 
	 @FXML
	 void button_EditStock_OnAction(ActionEvent event) throws IOException {
		 
		 EditProductController.setUser(user);
		 stageManager.switchScene(FxmlView.EDITPRODUCT);
		 
	 }
	 
	 @FXML
	 void button_SellItem_OnAction(ActionEvent event) throws IOException {
		 
		SellPage.setUser(user);
		SellPage.setShop(user.getShop());
		 stageManager.switchScene(FxmlView.SELLPAGE);
			
		 	
	 }
	 @FXML
	 void button_AddUser_OnAction(ActionEvent event) throws IOException {
		 UserController.setUser(user);
		 stageManager.switchScene(FxmlView.ADDUSER);
		
		 	
	 }
	 @FXML
	 void button_ReturnItem_OnAction(ActionEvent event) throws IOException {
		 ReturnToMerchantController.setUser(user);
//		 ReturnToMerchantController.setShop(user.getShop());
		 stageManager.switchScene(FxmlView.RETURNTOMERCHANT);
	 }
	@FXML
	void button_AddMembershipCard_OnAction(ActionEvent event) throws IOException {
		MembershipCardController.setData(user);
		stageManager.switchScene(FxmlView.MEMBERSHIPCARD);
	}
	 @FXML
	 void button_Back_OnAction(ActionEvent event) throws IOException {
		 	
		 stageManager.switchScene(FxmlView.LOGIN);
	 }

	private PaymentInfo paymentInfo;

	@Autowired
	PaymentInfoService paymentInfoService;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		user_name.setText(user.getName());
		product_selling_point.setText(user.getShop().getName());
		
		if(user.getPrivilege().equals("0")){
			button_AddUser.setVisible(false);
			button_AddSellingPoint.setVisible(false);
			button_AddItem.setVisible(false);
			button_Employee.setVisible(false);
			button_EditStock.setVisible(false);
			button_ReturnItem.setVisible(false);
			button_StockRealization.setVisible(false);
			
		}

		DateTime currentDT = DateTime.now();
		Integer currentYear = currentDT.getYear();
		Integer currentMonth = currentDT.getMonthOfYear();

		List<PaymentInfo> paymentInfoList = paymentInfoService.findByShopAndYearAndMonthAndPaymentStatus(
				user.getShop(),currentYear.toString(),currentMonth.toString(),false);

		if(paymentInfoList.size()>0){
			warning.setText("Please make payment within 09-".concat(currentMonth.toString()).concat("-")
					.concat(currentYear.toString()).concat(" 23:59:59 to avoid interruption in service !!!"));
		}
		
	}
	 
	 
	 

}
