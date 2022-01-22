package com.codetreatise.controller;


import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.bind.DatatypeConverter;

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
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


@Controller
public class LoginController implements Initializable{
    
	@FXML
	private JFXTextField userName;
	@FXML
	private JFXPasswordField password;
	@FXML
	private JFXButton login;
    @FXML
    private JFXButton payment;
	@FXML
	private Label warning;
	@FXML
	private Label headerLabel;
    
    @Autowired
    private UserService userService;

	@Autowired
	private PaymentInfoService paymentInfoService;
    
    @Lazy
    @Autowired
    private StageManager stageManager;
    
    private String password_hashed;

	private static User user;
	
	
        
	public User getUser() {
		return user;
	}

	public static void setUser(User newUser) {
		LoginController.user = newUser;
	}

	@FXML
    private void button_login_OnAction(ActionEvent event) throws IOException{

		Boolean allowedForLogin = true;

		if(userService.authenticate(getuserName(), getPassword())){

			List<PaymentInfo> paymentInfos = paymentInfoService.findByShopAndPaymentStatus(user.getShop(),false);
			DateTime currentDT = DateTime.now();
			Integer currentYear = currentDT.getYear();
			Integer currentMonth = currentDT.getMonthOfYear();

			if(paymentInfos.size()>0){
				for (PaymentInfo paymentInfo : paymentInfos) {
					Integer year = Integer.parseInt(paymentInfo.getYear());
					Integer month = Integer.parseInt(paymentInfo.getMonth());

					if((year.compareTo(currentYear) == 0) && (month.compareTo(currentMonth) == 0)){
						org.joda.time.format.DateTimeFormatter dateTimeFormatter = org.joda.time.format.DateTimeFormat.
								forPattern("YYYY-MM-dd HH:mm:ss");
						DateTime thresholdDT = DateTime.parse(currentYear.toString().concat("-").
								concat(currentMonth.toString()).concat("-".concat("09 23:59:59")),dateTimeFormatter);
						if(currentDT.isAfter(thresholdDT)){
							allowedForLogin = false;
						}
						else{
							allowedForLogin = true;
						}
					}
					else if((year.compareTo(currentYear) == 0) && (month.compareTo(currentMonth) == -1)){
						allowedForLogin = false;
					}
					else if((year.compareTo(currentYear) == -1)){
						allowedForLogin = false;
					}

				}
			}


			if(allowedForLogin){
				FirstPageController.setUser(user);
				stageManager.switchScene(FxmlView.FIRSTPAGE);
			}
			else{
				warning.setText("Payment Failed.");
				payment.setVisible(true);
			}



		}else{
				warning.setText("Login Failed.");
		}



    }

    @FXML
    private void button_payment_OnAction(ActionEvent event) throws IOException{

        PaymentInfoController.setUser(user);
        stageManager.switchScene(FxmlView.PAYMENTINFO);


    }
	
	public String getPassword() {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getText().getBytes(StandardCharsets.UTF_8));
			password_hashed = DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return password_hashed;
	}

	public String getuserName() {
		return userName.getText();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	    payment.setVisible(false);
	}

}
