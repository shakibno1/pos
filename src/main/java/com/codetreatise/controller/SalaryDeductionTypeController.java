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

import com.codetreatise.bean.SalaryDeduction;
import com.codetreatise.bean.SalaryDeductionType;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.SalaryDeductionService;
import com.codetreatise.service.SalaryDeductionTypeService;
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
public class SalaryDeductionTypeController implements Initializable {
	

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
	private TableView<SalaryDeductionType> tableView;
	@FXML
	private TableColumn<SalaryDeductionType, String> idTableColumn;
	@FXML
	private TableColumn<SalaryDeductionType, String> expenseTableColumn;
	@FXML
	private TableColumn<SalaryDeductionType, Boolean> updateTableColumn;
	private ObservableList<SalaryDeductionType> tableData = FXCollections.observableArrayList();

	public static void setData(User newuser) {
		user = newuser;
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private SalaryDeductionTypeService expenseTypeService;

	@Autowired
	private SalaryDeductionService expenseService;

	public SalaryDeductionType createExpenseType() {
		SalaryDeductionType expenseType = new SalaryDeductionType();
		expenseType.setCreatedBy(user.getName());
		expenseType.setType(getExpense_type());
		expenseType.setUpdatedBy(user.getName());
		expenseType.setCreated(new Timestamp(System.currentTimeMillis()));
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));
		SalaryDeductionType newExpenseType = expenseTypeService.save(expenseType);
		saveAlert(newExpenseType);
		return newExpenseType;
	}

	public SalaryDeductionType updateExpenseType(Long id) {
		SalaryDeductionType expenseType = expenseTypeService.find(id);

		expenseType.setUpdatedBy(user.getName());
		expenseType.setType(getExpense_type());
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));

		SalaryDeductionType updatedExpenseType = expenseTypeService.update(expenseType);
		updateAlert(updatedExpenseType);
		return updatedExpenseType;
	}

	private void saveAlert(SalaryDeductionType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Type " + type.getType() + " " + " has been created and \n" + getExpense_type() + " id is "
				+ type.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(SalaryDeductionType type) {

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

	Callback<TableColumn<SalaryDeductionType, Boolean>, TableCell<SalaryDeductionType, Boolean>> cellFactory = new Callback<TableColumn<SalaryDeductionType, Boolean>, TableCell<SalaryDeductionType, Boolean>>() {
		@Override
		public TableCell<SalaryDeductionType, Boolean> call(final TableColumn<SalaryDeductionType, Boolean> param) {
			final TableCell<SalaryDeductionType, Boolean> cell = new TableCell<SalaryDeductionType, Boolean>() {
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
							SalaryDeductionType expenseType = getTableView().getItems().get(getIndex());
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

				private void updateUser(SalaryDeductionType expenseType) {
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

		expenseTableColumn.setCellFactory(TextFieldTableCell.<SalaryDeductionType>forTableColumn());
		updateTableColumn.setCellFactory(cellFactory);
	}

	@FXML
	private void deleteType(ActionEvent event) {
		List<SalaryDeductionType> types = tableView.getSelectionModel().getSelectedItems();
		boolean checkAvailable = false;

		for (SalaryDeductionType type : types) {
			List<SalaryDeduction> expenses = expenseService.findBySalaryDeductionType(type);
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

		List<SalaryDeductionType> types = expenseTypeService.findAll();

		for (SalaryDeductionType expenseType : types) {
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
				updateExpenseType(Long.parseLong(getExpenseTypeId()));
				loadUserDetails();
				expenseTypeInsertStatus.setText("Expense Type Updated!!!");
			}
		} else {
			expenseTypeInsertStatus.setText("Expense Type Add Unccessfull Please Try Again With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.SALARYDEDUCTION);

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

	
	
	
//	private static Logger logger = Logger.getLogger(SalaryDeductionTypeController.class);
//
//	@FXML
//	private JFXButton button_Back = new JFXButton();
//	@FXML
//	private JFXTextField expense_type = new JFXTextField();
//	@FXML
//	private JFXButton button_AddExpenseType = new JFXButton();
//	@FXML
//	private Label expenseTypeId = new Label();
//	@FXML
//	private Label expenseTypeInsertStatus = new Label();
//	@FXML
//	private Label currentDateTime = new Label();
//	private static String userName_String;
//	private DatabaseClass databaseClass = new DatabaseClass();
//	private CommonOperation commonOperation = new CommonOperation();
//	
//	@FXML
//	private TableView<ExpenseTypeTableBeanClass> tableView = new TableView<ExpenseTypeTableBeanClass>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> idTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> expenseTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> deleteTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	private ObservableList<ExpenseTypeTableBeanClass> tableData = FXCollections.observableArrayList();
//	private ArrayList<String> keys;
//	private ArrayList<Map<String, String>> resultArrListOfMap = new ArrayList<Map<String, String>>();
//	private ArrayList<String> resultArrList = new ArrayList<String>();
//	public static void setData(String user){
//		userName_String = user;
//	}
//	
//	@FXML
//	void button_AddExpenseType_OnAction(ActionEvent event) throws IOException {
//		
//		String expenseType = expense_type.getText();
//
//		String addExpenseTypeSQL = "INSERT INTO `salary_deduction_type` (`type`) VALUES (\""+expenseType+"\");";
//
//		int checkDatabaseInsertion = 0;
//		List<Map<String, Object>> returnList = FXCollections.observableArrayList();
//		
//		try {
//			if ((!expenseType.equals("")) && commonOperation.getPrivilligedStatus(userName_String).equals("1")) {
//				
//				returnList = databaseClass.executeSQLandReturnAutoKey(addExpenseTypeSQL);
//				checkDatabaseInsertion = (int) returnList.get(0).get("response");
//				
//				if (checkDatabaseInsertion != -1) {
//					expenseTypeInsertStatus.setText("Deduction Type Added Successfull!!!");
//					expenseTypeId.setText(returnList.get(0).get("autoGeneratedKey").toString());
//					expense_type.setText("");
//					tableData.removeAll(tableData);
//					populateTableData();
//					tableView.refresh();
//				} else {
//					expenseTypeInsertStatus.setText("Deduction Type Add Unccessfull Please Try Again With Proper Data");
//				}
//			}
//			else{
//				expenseTypeInsertStatus.setText("Deduction Type Add Unccessfull Please Try Again With Proper Data");
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			expenseTypeInsertStatus.setText("No internet or Server is Down!!!");
//			logger.error("ERROR",e);
//			e.printStackTrace();
//		}
//		
//		
//	}
//
//	@FXML
//	void button_Back_OnAction(ActionEvent event) throws IOException {
//
//		Stage stage;
//		Parent root;
//		stage = (Stage) button_Back.getScene().getWindow();
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(getClass().getResource("SalaryDeduction.fxml"));
//		root = loader.load();
//		Scene scene = new Scene(root, java.awt.Toolkit.getDefaultToolkit().getScreenSize().width , java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
//		stage.setScene(scene);
//		stage.setMaximized(true);
//		stage.show();
//		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//
//			@Override
//			public void handle(WindowEvent event) {
//				Platform.exit();
//				System.exit(0);
//			}
//		});
//
//	}
//	
//	private void populateTableData(){
//		String sql = "SELECT id,`type` FROM `salary_deduction_type`;";
//		keys = new ArrayList<String>();
//		keys.add("id");
//		keys.add("type");
//		try {
//			resultArrListOfMap = databaseClass.selectSQLMultiRowMultiColumn(sql, keys);
//		} catch (ClassNotFoundException | SQLException e) {
//			logger.error("ERROR",e);
//			e.printStackTrace();
//		}
//		
//		for(int i=0;i<resultArrListOfMap.size();i++){
//			tableData.add(new ExpenseTypeTableBeanClass(resultArrListOfMap.get(i).get("id"), resultArrListOfMap.get(i).get("type")));
//		}
//	}
//	
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		
//		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("expenseTypeId"));
//		expenseTableColumn.setCellValueFactory(new PropertyValueFactory<>("expenseType"));
//		deleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
//		tableView.setItems(tableData);
//		tableView.setEditable(true);
//		populateTableData();
//		
//		expenseTableColumn.setCellFactory(TextFieldTableCell.<ExpenseTypeTableBeanClass>forTableColumn());
//		
//		expenseTableColumn
//		.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ExpenseTypeTableBeanClass, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<ExpenseTypeTableBeanClass, String> event) {
//				
//				String sql = "UPDATE `salary_deduction_type` SET `type`=\""
//						+ event.getNewValue()
//						+ "\" WHERE id=\""
//						+ event.getRowValue().getExpenseTypeId()
//						+ "\";";
//				
//				try {
//					databaseClass.executeSQL(sql);
//				} catch (ClassNotFoundException | SQLException e) {
//					logger.error("ERROR",e);
//					e.printStackTrace();
//				}
//				
//				tableData.removeAll(tableData);
//				populateTableData();
//				tableView.refresh();
//			}
//		});
//		
//		Callback<TableColumn<ExpenseTypeTableBeanClass, String>, TableCell<ExpenseTypeTableBeanClass, String>> deleteCellFactory = new Callback<TableColumn<ExpenseTypeTableBeanClass, String>, TableCell<ExpenseTypeTableBeanClass, String>>() {
//			@Override
//			public TableCell<ExpenseTypeTableBeanClass, String> call(
//					final TableColumn<ExpenseTypeTableBeanClass, String> param) {
//				final TableCell<ExpenseTypeTableBeanClass, String> cell = new TableCell<ExpenseTypeTableBeanClass, String>() {
//
//					final Button btn = new Button("Delete");
//
//					@Override
//					public void updateItem(String item, boolean empty) {
//						super.updateItem(item, empty);
//						if (empty) {
//							setGraphic(null);
//							setText(null);
//						} else {
//							btn.setOnAction(event -> {
//								ExpenseTypeTableBeanClass expenseTypeTableBeanClass = getTableView().getItems().get(getIndex());
//								
//								String checkTranSQL = "SELECT id FROM `salary_deduction` WHERE `salary_deduction_type_id`=\""
//										+ expenseTypeTableBeanClass.getExpenseTypeId()
//										+ "\";";
//								keys = new ArrayList<String>();
//								keys.add("id");
//								try {
//									resultArrList = databaseClass.selectSQLMultiRow(checkTranSQL, keys);
//								} catch (ClassNotFoundException | SQLException e) {
//									logger.error("ERROR",e);
//									e.printStackTrace();
//								}
//								
//								if(resultArrList.isEmpty()){
//									String deleteSQL = "DELETE FROM `salary_deduction_type` WHERE id=\""
//											+ expenseTypeTableBeanClass.getExpenseTypeId()
//											+ "\";";
//									try {
//										databaseClass.executeSQL(deleteSQL);
//										tableView.getItems().remove(expenseTypeTableBeanClass);
//										expenseTypeInsertStatus.setText("Deduction Type Deleted!!!");
//									} catch (ClassNotFoundException | SQLException e) {
//										logger.error("ERROR",e);
//										e.printStackTrace();
//									}
//								}
//								else{
//									expenseTypeInsertStatus.setText("Deduction Type Is in use and cannot be deleted");
//								}
//								
//								
//
//							});
//							setGraphic(btn);
//							setText(null);
//						}
//					}
//				};
//				return cell;
//			}
//		};
//		
//		deleteTableColumn.setCellFactory(deleteCellFactory);
//		
//		button_AddExpenseType.defaultButtonProperty().bind(button_AddExpenseType.focusedProperty());
//		button_Back.defaultButtonProperty().bind(button_Back.focusedProperty());
//		
//		expense_type.focusedProperty().addListener((arg0, oldValue, newValue) -> {
//			if (!newValue) { // when focus lost
//				if(StringUtils.isNotEmpty(expense_type.getText())){
//					String sql = "SELECT `type` FROM `expense_type` WHERE `type`=\""+expense_type.getText()+"\";";
//					 ArrayList<String> result_Query = new ArrayList<String>();
//					 ArrayList<String> keys = new ArrayList<String>();
//					 keys.add("type");
//					 try {
//						result_Query = databaseClass.selectSQL(sql, keys);
//					} catch (ClassNotFoundException | SQLException e) {
//						e.printStackTrace();
//					}
//					if(!result_Query.isEmpty()){
//						expenseTypeInsertStatus.setText("expense type not available");
//						expense_type.setText("");
//						
//					}
//					else{
//						expenseTypeInsertStatus.setText("expense type available");
//					}
//				}
//			}
//
//		});
//		
//		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//			Calendar cal = Calendar.getInstance();
//			int second = cal.get(Calendar.SECOND);
//			int minute = cal.get(Calendar.MINUTE);
//			int hour = cal.get(Calendar.HOUR);
//			LocalDate localDate = LocalDate.now();
//			currentDateTime.setText(DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour) + ":"
//					+ String.format("%02d", minute) + ":" + String.format("%02d", second));
//		}), new KeyFrame(Duration.seconds(1)));
//		clock.setCycleCount(Animation.INDEFINITE);
//		clock.play();
//
//	}
//	
//	

}
