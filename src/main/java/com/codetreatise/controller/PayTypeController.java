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

import com.codetreatise.bean.PayType;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.PayTypeService;
import com.codetreatise.service.SalaryExpenseService;
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
public class PayTypeController implements Initializable {
	

	@FXML
	private JFXButton button_Back;
	@FXML
	private JFXTextField pay_type;
	@FXML
	private JFXButton button_AddPayType;
	@FXML
	private Label payTypeId;
	@FXML
	private Label payTypeInsertStatus;
	@FXML
	private Label currentDateTime;
	private static User user;

	public String getExpenseTypeId() {
		return payTypeId.getText();
	}

	public void setExpenseTypeId(String expenseTypeId) {
		this.payTypeId.setText(expenseTypeId);
	}

	public String getExpense_type() {
		return pay_type.getText();
	}

	public void setExpense_type(String expense_type) {
		this.pay_type.setText(expense_type);
	}

	@FXML
	private TableView<PayType> tableView;
	@FXML
	private TableColumn<PayType, String> idTableColumn;
	@FXML
	private TableColumn<PayType, String> expenseTableColumn;
	@FXML
	private TableColumn<PayType, Boolean> updateTableColumn;
	private ObservableList<PayType> tableData = FXCollections.observableArrayList();

	public static void setData(User newuser) {
		user = newuser;
	}

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PayTypeService expenseTypeService;

	@Autowired
	private SalaryExpenseService expenseService;

	public PayType createExpenseType() {
		PayType expenseType = new PayType();
		expenseType.setCreatedBy(user.getName());
		expenseType.setType(getExpense_type());
		expenseType.setUpdatedBy(user.getName());
		expenseType.setCreated(new Timestamp(System.currentTimeMillis()));
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));

		PayType newExpenseType = expenseTypeService.save(expenseType);
		saveAlert(newExpenseType);
		return newExpenseType;
	}

	public PayType updateExpenseType(Long id) {
		PayType expenseType = expenseTypeService.find(id);

		expenseType.setUpdatedBy(user.getName());
		expenseType.setType(getExpense_type());
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));

		PayType updatedExpenseType = expenseTypeService.update(expenseType);
		updateAlert(updatedExpenseType);
		return updatedExpenseType;
	}

	private void saveAlert(PayType type) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Type saved successfully.");
		alert.setHeaderText(null);
		alert.setContentText("Type " + type.getType() + " " + " has been created and \n" + getExpense_type() + " id is "
				+ type.getId() + ".");
		alert.showAndWait();
	}

	private void updateAlert(PayType type) {

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

	Callback<TableColumn<PayType, Boolean>, TableCell<PayType, Boolean>> cellFactory = new Callback<TableColumn<PayType, Boolean>, TableCell<PayType, Boolean>>() {
		@Override
		public TableCell<PayType, Boolean> call(final TableColumn<PayType, Boolean> param) {
			final TableCell<PayType, Boolean> cell = new TableCell<PayType, Boolean>() {
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
							PayType expenseType = getTableView().getItems().get(getIndex());
							updateUser(expenseType);
							button_AddPayType.setText("Update Type");
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

				private void updateUser(PayType expenseType) {
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

		expenseTableColumn.setCellFactory(TextFieldTableCell.<PayType>forTableColumn());
		updateTableColumn.setCellFactory(cellFactory);
	}

	@FXML
	private void deleteType(ActionEvent event) {
		List<PayType> types = tableView.getSelectionModel().getSelectedItems();
		boolean checkAvailable = false;

		for (PayType type : types) {
			List<SalaryExpense> expenses = expenseService.findByPayType(type);
			if (!expenses.isEmpty()) {
				checkAvailable = true;
			}
		}
		if (checkAvailable) {
			payTypeInsertStatus.setText("Can't delete type it is in use");
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

		List<PayType> types = expenseTypeService.findAll();

		for (PayType expenseType : types) {
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
					payTypeInsertStatus.setText("Expense Type Created!!!");
				} else {
					payTypeInsertStatus.setText("Duplicate Expense Type!!!");
				}

			} else {
				updateExpenseType(Long.parseLong(getExpenseTypeId()));
				loadUserDetails();
				payTypeInsertStatus.setText("Expense Type Updated!!!");
			}
		} else {
			payTypeInsertStatus.setText("Expense Type Add Unccessfull Please Try Again With Proper Data");
		}

	}

	@FXML
	void button_Back_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.SALARYEXPENSE);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		setColumnProperties();

		loadUserDetails();

		button_AddPayType.defaultButtonProperty().bind(button_AddPayType.focusedProperty());
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

	
//	private static Logger logger = Logger.getLogger(PayTypeController.class);
//
//	@FXML
//	private JFXButton button_Back = new JFXButton();
//	@FXML
//	private JFXTextField pay_type = new JFXTextField();
//	@FXML
//	private JFXButton button_AddPayType = new JFXButton();
//	@FXML
//	private Label payTypeId = new Label();
//	@FXML
//	private Label payTypeInsertStatus = new Label();
//	@FXML
//	private Label currentDateTime = new Label();
//	private static String userName_String;
//	private CommonOperation commonOperation = new CommonOperation();
//	private DatabaseClass databaseClass = new DatabaseClass();
//	
//	@FXML
//	private TableView<ExpenseTypeTableBeanClass> tableView = new TableView<ExpenseTypeTableBeanClass>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> idTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> productTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	@FXML
//	private TableColumn<ExpenseTypeTableBeanClass, String> deleteTableColumn = new TableColumn<ExpenseTypeTableBeanClass, String>();
//	private ObservableList<ExpenseTypeTableBeanClass> tableData = FXCollections.observableArrayList();
//	private ArrayList<String> keys;
//	private ArrayList<Map<String, String>> resultArrListOfMap = new ArrayList<Map<String, String>>();
//	private ArrayList<String> resultArrList = new ArrayList<String>();
//	
//	public static void setData(String user){
//		userName_String = user;
//	}
//	
//	@FXML
//	void button_AddExpenseType_OnAction(ActionEvent event) throws IOException {
//		
//		String payType = pay_type.getText();
//
//		String addPayTypeSQL = "INSERT INTO `pay_type` (`type`) VALUES (\""+payType+"\");";
//
//		int checkDatabaseInsertion = 0;
//		List<Map<String, Object>> returnList = FXCollections.observableArrayList();
//		
//		try {
//			if ((!payType.equals("")) && commonOperation.getPrivilligedStatus(userName_String).equals("1")) {
//				
//				try {
//					returnList = databaseClass.executeSQLandReturnAutoKey(addPayTypeSQL);
//					tableData.removeAll(tableData);
//					populateTableData();
//					tableView.refresh();
//				} catch (ClassNotFoundException | SQLException e) {
//					payTypeInsertStatus.setText("No internet or Server is Down!!!");
//					logger.error("ERROR",e);
//					e.printStackTrace();
//				}
//				checkDatabaseInsertion = (int) returnList.get(0).get("response");
//				
//				if (checkDatabaseInsertion != -1) {
//					payTypeInsertStatus.setText("Expense Type Added Successfull!!!");
//					payTypeId.setText(returnList.get(0).get("autoGeneratedKey").toString());
//					pay_type.setText("");
//				} else {
//					payTypeInsertStatus.setText("Expense Type Add Unccessfull Please Try Again With Proper Data");
//				}
//			}
//			else{
//				payTypeInsertStatus.setText("Expense Type Add Unccessfull Please Try Again With Proper Data");
//			}
//		} catch (ClassNotFoundException | SQLException e) {
//			payTypeInsertStatus.setText("No internet or Server is Down!!!");
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
//		loader.setLocation(getClass().getResource("SalaryExpense.fxml"));
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
//	 private void populateTableData(){
//			String sql = "SELECT id,`type` FROM `pay_type`;";
//			keys = new ArrayList<String>();
//			keys.add("id");
//			keys.add("type");
//			try {
//				resultArrListOfMap = databaseClass.selectSQLMultiRowMultiColumn(sql, keys);
//			} catch (ClassNotFoundException | SQLException e) {
//				logger.error("ERROR",e);
//				e.printStackTrace();
//			}
//			
//			for(int i=0;i<resultArrListOfMap.size();i++){
//				tableData.add(new ExpenseTypeTableBeanClass(resultArrListOfMap.get(i).get("id"), resultArrListOfMap.get(i).get("type")));
//			}
//		}
//	
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//		
//		
//		idTableColumn.setCellValueFactory(new PropertyValueFactory<>("expenseTypeId"));
//		productTableColumn.setCellValueFactory(new PropertyValueFactory<>("expenseType"));
//		deleteTableColumn.setCellValueFactory(new PropertyValueFactory<>("DELETE"));
//		tableView.setItems(tableData);
//		tableView.setEditable(true);
//		populateTableData();
//		
//		productTableColumn.setCellFactory(TextFieldTableCell.<ExpenseTypeTableBeanClass>forTableColumn());
//		
//		productTableColumn
//		.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ExpenseTypeTableBeanClass, String>>() {
//
//			@Override
//			public void handle(CellEditEvent<ExpenseTypeTableBeanClass, String> event) {
//				System.out.println("tesesesdgdx");
//				
//				String sql = "UPDATE `pay_type` SET `type`=\""
//						+ event.getNewValue()
//						+ "\" WHERE id=\""
//						+ event.getRowValue().getExpenseTypeId()
//						+ "\";";
//				
//				try {
//					databaseClass.executeSQL(sql);
//					payTypeInsertStatus.setText("Pay Type Updated Successfully!!!");
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
//								String checkTranSQL = "SELECT id FROM `salary_expense` WHERE `pay_type_id`=\""
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
//									String deleteSQL = "DELETE FROM `pay_type` WHERE id=\""
//											+ expenseTypeTableBeanClass.getExpenseTypeId()
//											+ "\";";
//									try {
//										databaseClass.executeSQL(deleteSQL);
//										tableView.getItems().remove(expenseTypeTableBeanClass);
//										payTypeInsertStatus.setText("Pay Type Deleted!!!");
//									} catch (ClassNotFoundException | SQLException e) {
//										logger.error("ERROR",e);
//										e.printStackTrace();
//									}
//								}
//								else{
//									payTypeInsertStatus.setText("Pay Type Is in use and cannot be deleted");
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
//		button_AddPayType.defaultButtonProperty().bind(button_AddPayType.focusedProperty());
//		button_Back.defaultButtonProperty().bind(button_Back.focusedProperty());
//		
//		pay_type.focusedProperty().addListener((arg0, oldValue, newValue) -> {
//			if (!newValue) { // when focus lost
//				if(StringUtils.isNotEmpty(pay_type.getText())){
//					String sql = "SELECT `type` FROM `pay_type` WHERE `type`=\""+pay_type.getText()+"\";";
//					 ArrayList<String> result_Query = new ArrayList<String>();
//					 ArrayList<String> keys = new ArrayList<String>();
//					 keys.add("type");
//					 try {
//						result_Query = databaseClass.selectSQL(sql, keys);
//					} catch (ClassNotFoundException | SQLException e) {
//						e.printStackTrace();
//					}
//					if(!result_Query.isEmpty()){
//						payTypeInsertStatus.setText("pay type not available");
//						pay_type.setText("");
//						
//					}
//					else{
//						payTypeInsertStatus.setText("pay type available");
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
