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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ExpenseType;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ExpenseService;
import com.codetreatise.service.ExpenseTypeService;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Duration;

@Controller
public class ExpenseTypeController implements Initializable {

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField expense_type;
	@FXML
	private JFXButton button_AddExpenseType;
	@FXML
	private Label expenseTypeId;
	@FXML
	private Label expenseTypeInsertStatus;
	@FXML
	private Label currentDateTime;
	private static User user;

	public String getExpenseTypeId() {
		return expenseTypeId.getText();
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.expenseTypeId.setText(expenseTypeId);
	}

	public String getExpense_type() {
		return expense_type.getText();
	}

	public void setExpense_type(String expense_type) {
		this.expense_type.setText(expense_type);
	}

	@FXML
	private TableView<ExpenseType> tableView;
	@FXML
	private TableColumn<ExpenseType, String> idTableColumn;
	@FXML
	private TableColumn<ExpenseType, String> expenseTableColumn;
	@FXML
	private TableColumn<ExpenseType, Boolean> updateTableColumn;
	private ObservableList<ExpenseType> tableData = FXCollections.observableArrayList();

	public static void setData(User newuser) {
		user = newuser;
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ExpenseTypeService expenseTypeService;

	@Autowired
	private ExpenseService expenseService;

	public ExpenseType createExpenseType() {
		ExpenseType expenseType = new ExpenseType();
		expenseType.setCreatedBy(user.getName());
		expenseType.setUpdatedBy(user.getName());
		expenseType.setCreated(new Timestamp(System.currentTimeMillis()));
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));
		expenseType.setType(getExpense_type());

		ExpenseType newExpenseType = expenseTypeService.save(expenseType);
		saveAlert(newExpenseType);
		return newExpenseType;
	}

	public ExpenseType updateExpenseType(Long id) {
		ExpenseType expenseType = expenseTypeService.find(id);

		expenseType.setUpdatedBy(user.getName());
		expenseType.setType(getExpense_type());
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));

		ExpenseType updatedExpenseType = expenseTypeService.update(expenseType);
		updateAlert(updatedExpenseType);
		return updatedExpenseType;
	}

	private void saveAlert(ExpenseType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Type " + type.getType() + " " + " has been created and \n" + getExpense_type() + " id is "
				+ type.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(ExpenseType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type updated successfully.");
		alert.setHeaderText(null);
		alert.setContentText("The type " + type.getType() + " " + " has been updated.");
		alert.showAndWait();
	}

	private void loadUserDetails() {

		tableData.clear();

		tableData.addAll(expenseTypeService.findAll());

		tableView.setItems(tableData);
	}

	Callback<TableColumn<ExpenseType, Boolean>, TableCell<ExpenseType, Boolean>> cellFactory = new Callback<TableColumn<ExpenseType, Boolean>, TableCell<ExpenseType, Boolean>>() {
		@Override
		public TableCell<ExpenseType, Boolean> call(final TableColumn<ExpenseType, Boolean> param) {
			final TableCell<ExpenseType, Boolean> cell = new TableCell<ExpenseType, Boolean>() {
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
							ExpenseType expenseType = getTableView().getItems().get(getIndex());
							updateUser(expenseType);
							button_AddExpenseType.setText("Update Type");
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

				private void updateUser(ExpenseType expenseType) {
					setExpenseTypeId(Long.toString(expenseType.getId()));
					setExpense_type(expenseType.getType());
				}
			};
			return cell;
		}
	};

	private void setColumnProperties() {
		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		expenseTableColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		updateTableColumn.setCellValueFactory(new PropertyValueFactory<>("UPDATE"));
		tableView.setItems(tableData);
		tableView.setEditable(true);

		expenseTableColumn.setCellFactory(TextFieldTableCell.<ExpenseType>forTableColumn());
		updateTableColumn.setCellFactory(cellFactory);
	}

	@FXML
	private void deleteType(ActionEvent event) {
		List<ExpenseType> types = tableView.getSelectionModel().getSelectedItems();
		boolean checkAvailable = false;

		for (ExpenseType type : types) {
			List<Expense> expenses = expenseService.findByExpenseType(type);
			if (!expenses.isEmpty()) {
				checkAvailable = true;
			}
		}
		if (checkAvailable) {
			expenseTypeInsertStatus.setText("Can't delete type it is in use");
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirmation Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to delete selected?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK)
				expenseTypeService.deleteInBatch(types);

			loadUserDetails();
		}

	}

	private boolean validateExpenseType(String type) {

		List<ExpenseType> types = expenseTypeService.findAll();

		for (ExpenseType expenseType : types) {
			if (expenseType.getType().equals(type)) {
				return false;
			}
		}

		return true;
	}

	@FXML
	void button_AddExpenseType_OnAction(ActionEvent event) throws IOException {

		if (StringUtils.isNotEmpty(getExpense_type()) && user.getPrivilege().equals("1")) {

			if (StringUtils.isEmpty(getExpenseTypeId())) {
				if (validateExpenseType(getExpense_type())) {
					createExpenseType();
					loadUserDetails();
					expenseTypeInsertStatus.setText("Expense Type Created!!!");
				} else {
					expenseTypeInsertStatus.setText("Duplicate Expense Type!!!");
				}

			} else {
//				updateExpenseType(Long.parseLong(getExpenseTypeId()));
//				loadUserDetails();
				expenseTypeInsertStatus.setText("Cant Update Expense Type Please Contact Creator@@@");
			}
		} else {
			expenseTypeInsertStatus.setText("Expense Type Add Unccessfull Please Try Again With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.EXPENSE);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setColumnProperties();

		loadUserDetails();

		button_AddExpenseType.defaultButtonProperty().bind(button_AddExpenseType.focusedProperty());
		button_Back.defaultButtonProperty().bind(button_Back.focusedProperty());

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
