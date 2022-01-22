package com.codetreatise.controller;

import com.codetreatise.bean.*;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.*;
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
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class MembershipCardController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField customerId;
	@FXML
	private JFXTextField discountPercentage;
    @FXML
    private JFXTextField membershipCardNumber;
	@FXML
	private JFXButton button_AddMembershipCard;
	@FXML
	private JFXComboBox<String> membershipCardType;
	@FXML
	private Label membershipCardId;
	@FXML
	private Label expenseInsertStatus;
	@FXML
	private Label currentDateTime;

	private ObservableList<String> membershipCardTypeObservableList = FXCollections.observableArrayList();
	private static User user;
	
	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private MembershipCardService membershipCardService;
	
	@Autowired
	private CustomerService customerService;
	
	public static void setData(User newuser){
		user = newuser;
	}

    public String getCustomerId() {
        return customerId.getText();
    }

    public void setCustomerId(String customerId) {
        this.customerId.setText(customerId);
    }

    public String getDiscountPercentage() {
        return discountPercentage.getText();
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage.setText(discountPercentage);
    }

    public String getMembershipCardType() {
        return membershipCardType.getSelectionModel().getSelectedItem();
    }

    public void setMembershipCardType(String membershipCardType) {
        this.membershipCardType.getSelectionModel().select(membershipCardType);
    }

    public String getMembershipCardId() {
        return membershipCardId.getText();
    }

    public void setMembershipCardId(String membershipCardId) {
        this.membershipCardId.setText(membershipCardId) ;
    }

    public String getExpenseInsertStatus() {
        return expenseInsertStatus.getText();
    }

    public void setExpenseInsertStatus(String expenseInsertStatus) {
        this.expenseInsertStatus.setText(expenseInsertStatus);
    }

    public String getCurrentDateTime() {
        return currentDateTime.getText();
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime.setText(currentDateTime) ;
    }

    public ObservableList<String> getMembershipCardTypeObservableList() {
        return membershipCardTypeObservableList;
    }

    public void setMembershipCardTypeObservableList(ObservableList<String> membershipCardTypeObservableList) {
        this.membershipCardTypeObservableList = membershipCardTypeObservableList;
    }

    public String getMembershipCardNumber() {
        return membershipCardNumber.getText();
    }

    public void setMembershipCardNumber(String membershipCardNumber) {
        this.membershipCardNumber.setText(membershipCardNumber);
    }

    public ObservableList<String> getMembershipCardTypeList() {

	    membershipCardTypeObservableList.add(MembershipCardType.GOLD.toString());
        membershipCardTypeObservableList.add(MembershipCardType.SILVER.toString());

		return membershipCardTypeObservableList;
	}

	@FXML
	void button_AddMembershipCard_OnAction(ActionEvent event) throws IOException {

        button_AddMembershipCard.setStyle("-fx-background-color: #FF6C6C");
        button_AddMembershipCard.setDisable(true);
			
			if (StringUtils.isNotEmpty(getCustomerId()) && StringUtils.isNotEmpty(getMembershipCardNumber())
                    && StringUtils.isNotEmpty(getMembershipCardType()) && StringUtils.isNotEmpty(getDiscountPercentage())) {
                Customer customer = customerService.findById(Long.parseLong(getCustomerId()));

                if(customer != null){
                    MembershipCard membershipCard = new MembershipCard();
                    membershipCard.setCustomer(customer);
                    membershipCard.setCreatedBy(user.getName());
                    membershipCard.setUpdatedBy(user.getName());
                    membershipCard.setCreated(new Timestamp(System.currentTimeMillis()));
                    membershipCard.setUpdated(new Timestamp(System.currentTimeMillis()));
                    membershipCard.setMembershipCardNumber(getMembershipCardNumber());
                    if(getMembershipCardType().equals("GOLD")){
                        membershipCard.setMembershipCardType(MembershipCardType.GOLD);
                    }
                    else{
                        membershipCard.setMembershipCardType(MembershipCardType.SILVER);
                    }
                    membershipCard.setDiscount(Float.parseFloat(getDiscountPercentage()));

                    membershipCardService.save(membershipCard);
                    expenseInsertStatus.setText("Membership card entry successfull.......");
                }
                else{
                    expenseInsertStatus.setText("Invalid Customer Id.......");
                }
				
			}
			else{
				expenseInsertStatus.setText("Membership Card Add Unccessfull Please Try Again With Proper Data");
			}


        button_AddMembershipCard.setDisable(false);
	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.FIRSTPAGE);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {


        button_AddMembershipCard.defaultButtonProperty().bind(button_AddMembershipCard.focusedProperty());
        button_AddMembershipCard.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            button_AddMembershipCard.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
                button_AddMembershipCard.setStyle("-fx-background-color: #FF6C6C");
			 }
		});

        membershipCardType.setItems(getMembershipCardTypeList());

        membershipCardType.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if(getMembershipCardType().equals("GOLD")){
                    setDiscountPercentage("5");
                }
                else{
                    setDiscountPercentage("7");
                }

            }
        });

        discountPercentage.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Float discountPer = Float.parseFloat(getDiscountPercentage());
                if(discountPer<0 || discountPer>100 ){
                    setDiscountPercentage("");
                }
            }
        });

        discountPercentage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Float discountPer = Float.parseFloat(getDiscountPercentage());
                if(discountPer<0 || discountPer>100 ){
                    setDiscountPercentage("");
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
