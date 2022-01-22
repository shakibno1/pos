package com.codetreatise.controller;

import com.codetreatise.bean.PaymentInfo;
import com.codetreatise.bean.Shop;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.EmailService;
import com.codetreatise.service.PaymentInfoService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

@Controller
public class PaymentInfoController implements Initializable {

    @FXML
    private JFXComboBox<Integer> payment_year;

    @FXML
    private JFXComboBox<String> payment_month;

    @FXML
    private Label employeeInsertStatus;

    @FXML
    private Label paymentId;

    @FXML
    private Label shopId;

    @FXML
    private Label paymentStatus;

    @FXML
    private JFXTextField fromAccount;

    @FXML
    private JFXTextField toAccount;

    @FXML
    private JFXTextField amount;

    @FXML
    private JFXTextField transactionId;

    @FXML
    private JFXButton button_AddPayment;

    @FXML
    private JFXButton button_Back;

    @FXML
    private Label currentDateTime;

    private Shop shop;

    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        PaymentInfoController.user = user;
    }

    public JFXTextField getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(JFXTextField fromAccount) {
        this.fromAccount = fromAccount;
    }

    public JFXTextField getToAccount() {
        return toAccount;
    }

    public void setToAccount(JFXTextField toAccount) {
        this.toAccount = toAccount;
    }

    public JFXTextField getAmount() {
        return amount;
    }

    public void setAmount(JFXTextField amount) {
        this.amount = amount;
    }

    public JFXTextField getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(JFXTextField transactionId) {
        this.transactionId = transactionId;
    }

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    PaymentInfoService paymentInfoService;

    private ObservableList<Integer> yearObservableList = FXCollections.observableArrayList();
    private ObservableList<String> monthObservableList = FXCollections.observableArrayList();

    private PaymentInfo paymentInfo;

    @Autowired
    EmailService emailService;


    @FXML
    void button_AddPayment_OnAction(ActionEvent event) throws IOException {

        String fromAccount = getFromAccount().getText();
        String toAccount = getToAccount().getText();
        String transactionId = getTransactionId().getText();


        if(paymentInfo != null){
            paymentInfo.setFromAccount(fromAccount);
            paymentInfo.setToAccount(toAccount);
            paymentInfo.setTransactionId(transactionId);
            paymentInfo.setPaymentStatus(true);
            paymentInfo.setCreatedBy(user.getName());
            paymentInfo.setUpdatedBy(user.getName());
            paymentInfo.setCreated(new Timestamp(System.currentTimeMillis()));
            paymentInfo.setUpdated(new Timestamp(System.currentTimeMillis()));

            paymentInfo = paymentInfoService.save(paymentInfo);

            employeeInsertStatus.setText("Payment done waiting for verification!!!");
            paymentId.setText(paymentInfo.getId().toString());

            String monthStr = paymentInfo.getMonth();
            String month = null;

            if (monthStr.equals("01")) {
                month = "January";
            } else if (monthStr.equals("02")) {
                month = "February";
            } else if (monthStr.equals("03")) {
                month = "March";
            } else if (monthStr.equals("04")) {
                month = "April";
            } else if (monthStr.equals("05")) {
                month = "May";
            } else if (monthStr.equals("06")) {
                month = "June";
            } else if (monthStr.equals("07")) {
                month = "July";
            } else if (monthStr.equals("08")) {
                month = "August";
            } else if (monthStr.equals("09")) {
                month = "September";
            } else if (monthStr.equals("10")) {
                month = "October";
            } else if (monthStr.equals("11")) {
                month = "November";
            } else if (monthStr.equals("12")) {
                month = "December";
            }

            emailService.sendSimpleMessage("shakibno1@gmail.com","TahaShop Payment of-".concat(paymentInfo.getYear()).concat("-").
                    concat(month),"A Payment has been made against Taha Shop from ".concat(user.getShop().getName())
                    .concat(" for the month of ").concat(month).concat(" for year ").concat(paymentInfo.getYear())
                    .concat(" sender bkash number ").concat(paymentInfo.getFromAccount()).concat(" transaction id "
                            .concat(paymentInfo.getTransactionId())));


        }

        clearFields();
    }

    @FXML
    void button_Back_OnAction(ActionEvent event) throws IOException {


        clearFields();
        stageManager.switchScene(FxmlView.LOGIN);

    }

    private void clearFields() {

        paymentStatus.setText("");
        fromAccount.setText("");
        toAccount.setText("");
        transactionId.setText("");
        amount.setText("");
        yearObservableList.clear();
        monthObservableList.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_AddPayment.setDisable(true);
        shopId.setText(user.getShop().getName());

        int yearInt = Calendar.getInstance().get(Calendar.YEAR);

        for (int i = yearInt - 5; i < yearInt + 10; i++) {
            yearObservableList.add(i);
        }

        payment_year.setItems(yearObservableList);

        payment_month.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (payment_year.getSelectionModel().getSelectedItem() != null) {
                    int yearInt = payment_year.getSelectionModel().getSelectedItem();
                    String yearStr = Integer.toString(yearInt);
                    String selectedMonth = null;
                    String monthStr = payment_month.getSelectionModel().getSelectedItem();
                    if (monthStr.equals("January")) {
                        selectedMonth = "01";
                    } else if (monthStr.equals("February")) {
                        selectedMonth = "02";
                    } else if (monthStr.equals("March")) {
                        selectedMonth = "03";
                    } else if (monthStr.equals("April")) {
                        selectedMonth = "04";
                    } else if (monthStr.equals("May")) {
                        selectedMonth = "05";
                    } else if (monthStr.equals("June")) {
                        selectedMonth = "06";
                    } else if (monthStr.equals("July")) {
                        selectedMonth = "07";
                    } else if (monthStr.equals("August")) {
                        selectedMonth = "08";
                    } else if (monthStr.equals("September")) {
                        selectedMonth = "09";
                    } else if (monthStr.equals("October")) {
                        selectedMonth = "10";
                    } else if (monthStr.equals("November")) {
                        selectedMonth = "11";
                    } else if (monthStr.equals("December")) {
                        selectedMonth = "12";
                    }

                    paymentInfo = paymentInfoService.findByShopAndYearAndMonth(user.getShop(),yearStr,selectedMonth);

                    if(paymentInfo != null){
                        Boolean payment = paymentInfo.getPaymentStatus();
                        if(payment){
                            employeeInsertStatus.setText("Payment already done or in verification Thanks !!!");
                            paymentStatus.setText("PAID");
                        }
                        else{
                            employeeInsertStatus.setText("Not Paid please make the payment !!!");
                            paymentStatus.setText("NOT PAID");
                            button_AddPayment.setDisable(false);
                            amount.setText(paymentInfo.getAmount().toString());
                            amount.setDisable(true);
                            toAccount.setText("01835711755");
                            toAccount.setDisable(true);
                        }
                    }
                    else{
                        employeeInsertStatus.setText("Bill Not Generated Yet !!!");
                    }
                }
                else{
                    employeeInsertStatus.setText("Select Year First !!!");
                }
            }
        });

        monthObservableList.addAll("January","February","March","April","May","June",
                "July","August","September","October","November","December");

        payment_year.setItems(yearObservableList);
        payment_month.setItems(monthObservableList);

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
