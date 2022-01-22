package com.codetreatise.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.codetreatise.bean.CustomerPaymentStatus;
import com.codetreatise.bean.Expense;
import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductStockStatus;
import com.codetreatise.bean.SalaryExpense;
import com.codetreatise.bean.User;
import com.codetreatise.config.StageManager;
import com.codetreatise.service.ExpenseService;
import com.codetreatise.service.ProductStockService;
import com.codetreatise.service.SalaryExpenseService;
import com.codetreatise.view.FxmlView;
import com.jfoenix.controls.JFXButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
public class GeneralReportController implements Initializable {

	// public static void main(String [] args) {
	//
	// org.joda.time.LocalDate l = new org.joda.time.LocalDate();
	//
	// System.out.println(l.toString());
	//
	// DateTimeZone timeZone = DateTimeZone.forID( "Asia/Dhaka" );
	// DateTime now = DateTime.now( timeZone );
	// DateTime todayStart = now.withTimeAtStartOfDay();
	// DateTime todayEnd = now.withTime(23, 59, 59, 999);
	// DateTime tomorrowStart = now.plusDays( 2 ).withTimeAtStartOfDay();
	// DateTime previousDay = now.minusDays(1);
	// DateTime previousDayStart = previousDay.withTimeAtStartOfDay();
	// DateTime previousDayEnd = previousDay.withTime(23, 59, 59, 999);
	// Interval today = new Interval( todayStart, tomorrowStart );
	// org.joda.time.format.DateTimeFormatter dtf =
	// DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
	// System.out.println(timeZone);
	// System.out.println(now);
	// System.out.println(todayStart);
	// System.out.println(tomorrowStart);
	// System.out.println(today);
	// System.out.println(todayEnd.toString(DateTimeFormat.forPattern("yyyy-MM-dd
	// HH:mm:ss")));
	// System.out.println(todayStart.toString(DateTimeFormat.forPattern("yyyy-MM-dd
	// HH:mm:ss")));
	// System.out.println(previousDayStart.toString(DateTimeFormat.forPattern("yyyy-MM-dd
	// HH:mm:ss")));
	// System.out.println(previousDayEnd.toString(DateTimeFormat.forPattern("yyyy-MM-dd
	// HH:mm:ss")));
	// }

	@FXML
	private Label date;
	// @FXML
	// private Label status;
	@FXML
	private JFXButton button_back;
	@FXML
	private JFXButton button_DailyClosingReport;
	@FXML
	private JFXButton button_SellReport;
	@FXML
	private JFXButton button_ExpenseReport;
	@FXML
	private JFXButton button_SalaryExpenseReport;
	@FXML
	private DatePicker fromDate;
	@FXML
	private DatePicker toDate;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private ProductStockService productService;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private SalaryExpenseService salaryExpenseService;

	private static User user;

	public static User getUser() {
		return user;
	}

	public static void setUser(User user) {
		GeneralReportController.user = user;
	}

	@FXML
	void buttonBack_OnAction(ActionEvent event) throws IOException {

		stageManager.switchScene(FxmlView.FIRSTPAGE);

	}

	@FXML
	void button_SellReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", "Sell Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(user.getShop().getName()))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<ProductStock> stocks = productService.findByShopAndProductStockStatusAndUpdatedBetween(user.getShop(),
					ProductStockStatus.SOLD, fromTimestamp, toTimestamp);

			for (ProductStock stock : stocks) {
				map = new HashMap<String, Object>();
				map.put("id", stock.getId().toString());
				map.put("payType", stock.getCustomerPaymentType().name());
				map.put("discount", stock.getDiscount());
				map.put("price", stock.getPriceSelling());
				map.put("invoice", stock.getInvoice().getId().toString());
				map.put("type", stock.getProductType().getType());
				map.put("merchant", stock.getMerchant().getName());
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/SellReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_ExpenseReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", "Expense Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(user.getShop().getName()))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<Expense> expenses = expenseService.findByShopAndCreatedBetween(user.getShop(), fromTimestamp,
					toTimestamp);

			for (Expense expense : expenses) {
				map = new HashMap<String, Object>();
				map.put("amount", expense.getAmount());
				map.put("payType", expense.getExpenseType().getType());
				if (expense.getMerchant() != null) {
					map.put("merchant", expense.getMerchant().getName());
				}
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/ExpenseReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_SalaryExpenseReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null) {

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();

			jrParameter.put("date", "Salary Expense Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(user.getShop().getName()))));

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			List<SalaryExpense> salaryExpenses = salaryExpenseService.findByShopAndCreatedBetween(user.getShop(),
					fromTimestamp, toTimestamp);

			for (SalaryExpense expense : salaryExpenses) {
				map = new HashMap<String, Object>();
				map.put("amount", expense.getAmount());
				map.put("payType", expense.getPayType().getType());
				Date date = new Date(expense.getCreated().getTime());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				map.put("ledgerMonth", expense.getLedgerMonth()+" "+format.format(date));
				map.put("employee", expense.getEmployee().getName());
				dataSource.add(map);
			}

			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager
								.compileReport(getClass().getResourceAsStream("/jrxml/SalaryExpenseReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}

		}

	}

	@FXML
	void button_DailyClosingReport_OnAction(ActionEvent event) throws IOException {

		if (fromDate.getValue() != null && toDate.getValue() != null) {

			LocalDate fromLocalDate = fromDate.getValue();
			LocalDate toLocalDate = toDate.getValue();
			Timestamp fromTimestamp = Timestamp.valueOf(fromLocalDate.atStartOfDay());
			Timestamp toTimestamp = Timestamp.valueOf(toLocalDate.atTime(23, 59, 59, 999));

			Float sumOfSellPrice = productService.getSumOfPriceSelling();

			System.out.println(sumOfSellPrice);
			
			Float totalSellCash = productService.getTotalSell(ProductStockStatus.SOLD, user.getShop(), 
					CustomerPaymentStatus.CASH, fromTimestamp, toTimestamp);
			
			Float totalSellCard = productService.getTotalSell(ProductStockStatus.SOLD, user.getShop(), 
					CustomerPaymentStatus.CARD, fromTimestamp, toTimestamp);
			
			if (totalSellCard == null) {
				totalSellCard = 0f;
			}
			if (totalSellCash == null) {
				totalSellCash = 0f;
			}

			Float totalSell = totalSellCard + totalSellCash ;
			Float previousTotalSell = productService.getTotalSellBeforeFromDate(ProductStockStatus.SOLD, user.getShop(),
					fromTimestamp);
			System.out.println(totalSell);
			System.out.println(previousTotalSell);

			Float totalExpense = expenseService.getTotalExpenseFromDateRange(user.getShop(), fromTimestamp,
					toTimestamp);
			Float previousTotalExpense = expenseService.getTotalExpenseBeforeFromDate(user.getShop(), fromTimestamp);

			Float totalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseFromDateRange(user.getShop(),
					fromTimestamp, toTimestamp);
			Float previousTotalSalaryExpense = salaryExpenseService.getTotalSalaryExpenseBeforeFromDate(user.getShop(),
					fromTimestamp);

			List<Map<String, Object>> dataSource = new ArrayList<Map<String, Object>>();
			Map<String, Object> jrParameter = new HashMap<String, Object>();
			Map<String, Object> map = new HashMap<String, Object>();
			// map.put("empty", "empty");
			if (previousTotalSell == null) {
				previousTotalSell = 0f;
			}
			if (previousTotalExpense == null) {
				previousTotalExpense = 0f;
			}
			if (totalExpense == null) {
				totalExpense = 0f;
			}
			if (totalSalaryExpense == null) {
				totalSalaryExpense = 0f;
			}
			if (previousTotalSalaryExpense == null) {
				previousTotalSalaryExpense = 0f;
			}
			dataSource.add(map);
			jrParameter.put("date", "Closing Summary Report from ".concat(fromDate.getValue().toString().concat(" to "))
					.concat(toDate.getValue().toString().concat(" of ".concat(user.getShop().getName()))));
			jrParameter.put("totalSellCard", totalSellCard);
			jrParameter.put("totalSellCash", totalSellCash);
			jrParameter.put("totalExpense", totalExpense);
			jrParameter.put("totalSalaryExpense", totalSalaryExpense);
			jrParameter.put("closingBalance", previousTotalSell - previousTotalExpense - previousTotalSalaryExpense);
			jrParameter.put("todayClosingBalance",
					(previousTotalSell - previousTotalExpense - previousTotalSalaryExpense)
							+ (totalSell - totalExpense - totalSalaryExpense));
			JRDataSource jrDataSource = new JRBeanCollectionDataSource(dataSource);
			JasperPrint jp = null;
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
			fileChooser.getExtensionFilters().add(extFilter);
			File file = fileChooser.showSaveDialog((Stage) button_DailyClosingReport.getScene().getWindow());
			if (file != null) {
				if (!file.exists()) {
					try {
						file.createNewFile();
						FileOutputStream fop = new FileOutputStream(file);
						net.sf.jasperreports.engine.JasperReport report = JasperCompileManager.compileReport(
								getClass().getResourceAsStream("/jrxml/DailyClosingSummaryReport.jrxml"));
						jp = JasperFillManager.fillReport(report, jrParameter, jrDataSource);
						JasperExportManager.exportReportToPdfStream(jp, fop);
						fop.close();
					} catch (JRException | IOException e) {
						e.printStackTrace();
						return;

					}
				}
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			Calendar cal = Calendar.getInstance();
			int second = cal.get(Calendar.SECOND);
			int minute = cal.get(Calendar.MINUTE);
			int hour = cal.get(Calendar.HOUR);
			LocalDate localDate = LocalDate.now();
			date.setText(java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(localDate) + "  "
					+ String.format("%02d", hour) + ":" + String.format("%02d", minute) + ":"
					+ String.format("%02d", second));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

}
