package com.codetreatise.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.codetreatise.bean.*;
import com.codetreatise.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.CustomerTableBean;
import com.codetreatise.bean.Customer;
import com.codetreatise.config.StageManager;
import com.codetreatise.repository.CustomerRepository;
import com.codetreatise.specification.CustomerSpecification;
import com.codetreatise.specification.SearchOperation;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Duration;

@Controller
public class CustomerSearchController implements Initializable {
	
	@FXML
	private Label date;
	@FXML
	private JFXTextField customer_name;
	@FXML
	private JFXTextField customer_mobile;
	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;
	@FXML
	private JFXCheckBox customerBalanceSearch;
	@FXML
	private JFXButton button_findCustomer;
	@FXML
	private Label warning;
	@FXML
	private JFXButton buttonBack;
	@FXML
	private TableView<CustomerTableBean> customerTableView;
	@FXML
	private TableColumn<CustomerTableBean, String> customerIdTableColumn;
	@FXML
	private TableColumn<CustomerTableBean, String> customerNameTableColumn;
	@FXML
	private TableColumn<CustomerTableBean, String> customerMobileTableColumn;
	@FXML
	private TableColumn<CustomerTableBean, String> customerAddressTableColumn ;
	@FXML
	private TableColumn<CustomerTableBean, String> customerPcsAmountTableColumn ;
	@FXML
	private TableColumn<CustomerTableBean, String> customerBalanceTableColumn ;
	@FXML
	private TableColumn<CustomerTableBean, String> customerSattleBalanceTableColumn ;
	private ObservableList<CustomerTableBean> customerTableData = FXCollections.observableArrayList();
	private static String sellingPointId;
	@FXML
	private JFXComboBox<String> selling_pointCombo;
	private ObservableList<String> sellingPointObservableList = FXCollections.observableArrayList();
	private static User user;
	
	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		CustomerSearchController.user = user;
	}
	

	public String getCustomer_name() {
		return customer_name.getText();
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name.setText(customer_name);
	}
	public String getCustomer_mobile() {
		return customer_mobile.getText();
	}
	public void setCustomer_mobile(String customer_mobile) {
		this.customer_mobile.setText(customer_mobile);
	}
	public DatePicker getFromDate() {
		return fromDate;
	}
	public void setFromDate(DatePicker fromDate) {
		this.fromDate = fromDate;
	}
	public DatePicker getToDate() {
		return toDate;
	}
	public void setToDate(DatePicker toDate) {
		this.toDate = toDate;
	}
	public String getSelling_pointCombo() {
		return selling_pointCombo.getSelectionModel().getSelectedItem();
	}
	public void setSelling_pointCombo(String selling_pointCombo) {
		this.selling_pointCombo.getSelectionModel().select(selling_pointCombo);
	}

	public JFXCheckBox getCustomerBalanceSearch() {
		return customerBalanceSearch;
	}
	public void setCustomerBalanceSearch(JFXCheckBox customerBalanceSearch) {
		this.customerBalanceSearch = customerBalanceSearch;
	}



	@Lazy
	@Autowired
	private StageManager stageManager;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductStockService productStockService;

	@Autowired
	private ProductTransactionLedgerService productTransactionLedgerService;
	
	@Autowired
	private ExpenseTypeService expenseTypeService;
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Autowired
	private CustomerBalanceService customerBalanceService;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@FXML
	void buttonBack_OnAction(ActionEvent event) throws IOException {
		if (!customerTableData.isEmpty()) {
			customerTableData.removeAll(customerTableData);
		}
		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}
	@FXML
	void button_findCustomer_OnAction(ActionEvent event) throws IOException {
		button_findCustomer.setStyle("-fx-background-color: #FF6C6C");
		if(StringUtils.isNotEmpty(getSelling_pointCombo())){
			
			Shop shop = shopService.findByName(getSelling_pointCombo());
			
			
			String customerName=null;
			String customerMobile=null;
			LocalDate fromLocalDate = null;
			Timestamp fromTimestamp = null;
			Timestamp toTimestamp = null;
			LocalDate toLocalDate = null;
			Date fromD = null;
			Date toD = null;
			
			if(!getCustomer_name().isEmpty()){
				customerName=getCustomer_name();
			}
			
			if(!getCustomer_mobile().isEmpty()){
				customerMobile=getCustomer_mobile();
			}
			
			if(fromDate.getValue() != null ){
				fromLocalDate = fromDate.getValue();
				fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
				fromD = fromTimestamp;
			}
			if(toDate.getValue() != null ){
				toLocalDate = toDate.getValue();
				toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));
				toD = toTimestamp;
			}
			
			CustomerSpecification spec1 = new CustomerSpecification(new SearchCriteria("name", "%", customerName));
			CustomerSpecification spec2 = new CustomerSpecification(new SearchCriteria("mobile","=" ,customerMobile));
			CustomerSpecification spec3 = new CustomerSpecification(new SearchCriteria("created",">",fromD));
			CustomerSpecification spec4 = new CustomerSpecification(new SearchCriteria("created","<",toD));
			List<Customer> results = customerRepository.findAll(Specification.where(spec1).and(spec2).and(spec3).and(spec4));
			
			for (Customer customer : results ) {
				Float totalAmount = 0f;
				Float totalPcs = 0f;
				System.out.println(customer.getId());
				if(getCustomerBalanceSearch().isSelected()){
					CustomerBalance cb = customerBalanceService.findByCustomerAndShopAndAmountNot(customer, shop, 0f);
					
					if(cb != null){
						List<Invoice> invoices = invoiceService.findByCustomer(customer);
						
						for ( Invoice invoice : invoices ) {
							List<ProductStock> stocks = productStockService.findByInvoice(invoice);
							totalPcs = (float) stocks.size() + totalPcs;
							for ( ProductStock stock : stocks ) {
								if(stock.getProductStockStatus() == ProductStockStatus.SOLD &&
										stock.getInvoice().getCustomer() == customer){
									totalAmount = totalAmount + stock.getPriceSelling() - (stock.getDiscount() == null ? 0f : stock.getDiscount());
								}
//								ProductTransactionLedger productTransactionLedger = productTransactionLedgerService.findByInvoiceAndProductStockAndProductTransactionType(invoice,stock,ProductTransactionType.CHANGE_RETURN);
//								if(productTransactionLedger == null){
//									totalAmount = totalAmount + stock.getPriceSelling() - (stock.getDiscount() == null ? 0f : stock.getDiscount());
//								}
							}
						} 
						customerTableData.add(new CustomerTableBean(customer.getId().toString(), customer.getName(),
								customer.getMobile(), customer.getAddress(), totalPcs.toString().concat("/").concat(totalAmount.toString()), cb.getAmount().toString()));
//						System.out.println(cb.getAmount());
					}
//					else{
//						customerTableData.add(new CustomerTableBean(customer.getId().toString(), customer.getName(),
//								customer.getMobile(), customer.getAddress(), totalPcs.toString().concat("/").concat(totalAmount.toString()), ""));
//					}
				}
				else{
					CustomerBalance cb = customerBalanceService.findByCustomerAndShop(customer, shop);
					List<Invoice> invoices = invoiceService.findByCustomer(customer);
					
					for ( Invoice invoice : invoices ) {
						List<ProductStock> stocks = productStockService.findByInvoice(invoice);
						totalPcs = (float) stocks.size() + totalPcs;
						for ( ProductStock stock : stocks ){
                            if(stock.getProductStockStatus() == ProductStockStatus.SOLD &&
                                    stock.getInvoice().getCustomer().getId() == customer.getId()){
                                totalAmount = totalAmount + stock.getPriceSelling() - (stock.getDiscount() == null ? 0f : stock.getDiscount());
                            }
//							totalAmount = totalAmount + stock.getPriceSelling() - (stock.getDiscount() == null ? 0f : stock.getDiscount());
						}
					} 
					if(cb != null){
						customerTableData.add(new CustomerTableBean(customer.getId().toString(), customer.getName(),
								customer.getMobile(), customer.getAddress(), totalPcs.toString().concat("/").concat(totalAmount.toString()), cb.getAmount().toString()));
//						System.out.println(cb.getAmount());
					}
//					else{
//						customerTableData.add(new CustomerTableBean(customer.getId().toString(), customer.getName(),
//								customer.getMobile(), customer.getAddress(), totalPcs.toString().concat("/").concat(totalAmount.toString()), ""));
//					}
				}
				
				
			}
		}
		else{
			warning.setText("Please Select Selling Point!!!");
		}
		
	}
	
	public ObservableList<String> getShopList() {

		// shopObservableList = (ObservableList<Shop>) serviceImpl.findAll();
		List<Shop> shops = shopService.findAll();
		sellingPointObservableList = FXCollections.observableArrayList();
		for (Shop shop : shops) {
			sellingPointObservableList.add(shop.getName());
		}

		return sellingPointObservableList;
	}
	
	public ExpenseType createExpenseType(String type) {
		ExpenseType expenseType = new ExpenseType();
		expenseType.setCreatedBy(user.getName());
		expenseType.setUpdatedBy(user.getName());
		expenseType.setCreated(new Timestamp(System.currentTimeMillis()));
		expenseType.setUpdated(new Timestamp(System.currentTimeMillis()));
		expenseType.setType(type);

		ExpenseType newExpenseType = expenseTypeService.save(expenseType);
		return newExpenseType;
	}
	
	public Expense createExpense(Float expenseAmount,ExpenseType expenseType){
		Expense expense = new Expense();
		expense.setAmount(expenseAmount);
		expense.setCreatedBy(user.getName());
		expense.setUpdatedBy(user.getName());
		expense.setExpenseType(expenseType);
		expense.setShop(user.getShop());
		
		expense.setCreated(new Timestamp(System.currentTimeMillis()));
		expense.setUpdated(new Timestamp(System.currentTimeMillis()));
		expense.setExpenseDate(new Timestamp(System.currentTimeMillis()));
		
		Expense newExpense = expenseService.save(expense);
		return newExpense;
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		customerIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		customerMobileTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
		customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		customerPcsAmountTableColumn.setCellValueFactory(new PropertyValueFactory<>("pcsAmount"));
		customerBalanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
		customerSattleBalanceTableColumn.setCellValueFactory(new PropertyValueFactory<>("SETTLE"));
		customerTableView.setItems(customerTableData);
		selling_pointCombo.setItems(getShopList());
		
		Callback<TableColumn<CustomerTableBean, String>, TableCell<CustomerTableBean, String>> deleteCellFactory = new Callback<TableColumn<CustomerTableBean, String>, TableCell<CustomerTableBean, String>>() {
			@Override
			public TableCell<CustomerTableBean, String> call(final TableColumn<CustomerTableBean, String> param) {
				final TableCell<CustomerTableBean, String> cell = new TableCell<CustomerTableBean, String>() {

					final Button btn = new Button("Settle Balance");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn.setOnAction(event -> {
								CustomerTableBean customerTableBeanClass = getTableView().getItems().get(getIndex());
								Customer customer = customerService.findById(Long.parseLong(customerTableBeanClass.getId()));
								CustomerBalance customerBalance = customerBalanceService.findByCustomer(customer);
								
								ExpenseType expenseType = expenseTypeService.findByType("Customer Cash Return");
								
								if(expenseType == null){
									expenseType = createExpenseType("Customer Cash Return");
								}
								
								Expense expense = createExpense(customerBalance.getAmount(), expenseType);
								
								customerBalance.setAmount(0f);
								customerBalance.setUpdated(new Timestamp(System.currentTimeMillis()));
								customerBalance.setUpdatedBy(user.getName());
								customerBalanceService.save(customerBalance);
								
								customerTableBeanClass.setBalance("0");
								customerTableView.refresh();
								//customerTableView.getItems().remove(productTableBeanClass);

							});
							setGraphic(btn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		customerSattleBalanceTableColumn.setCellFactory(deleteCellFactory);
		button_findCustomer.defaultButtonProperty().bind(button_findCustomer.focusedProperty());
		button_findCustomer.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			button_findCustomer.setStyle("-fx-background-color:#2bff00");
			if (!newValue) {
				button_findCustomer.setStyle("-fx-background-color: #FF6C6C");
			 }
		});
		buttonBack.defaultButtonProperty().bind(buttonBack.focusedProperty());
		
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			date.setText(
					DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  " + String.format("%02d", hour)
							+ ":" + String.format("%02d", minute) + ":" + String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
		
	}

}
