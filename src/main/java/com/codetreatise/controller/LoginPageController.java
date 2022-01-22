package com.codetreatise.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import javafx.fxml.Initializable;

@Controller
public class LoginPageController implements Initializable {	
//	
//	
//	@FXML
//	private JFXTextField userName;
//	@FXML
//	private JFXPasswordField password;
//	@FXML
//	private JFXButton login;
//	@FXML
//	private Label warning;
//	@FXML
//	private AnchorPane mainPane;
//	@FXML
//	private Label headerLabel;
//	private String userName_string;
//	private String password_hashed;
//	private ArrayList<String> result_Query;
//	private ArrayList<String> keys;
//	private DatabaseClass databaseClass = new DatabaseClass();
//	private CommonOperation commonOperation = new CommonOperation();
//	/**
//	 * This is LOGIN button action
//	 * @param event
//	 * @throws IOException
//	 */
//	 @FXML
//	 void button_login_OnAction(ActionEvent event) throws IOException {
//		 userName_string = userName.getText();
//		 String passwordStr = password.getText();
//		 MessageDigest digest;
//		try {
//			digest = MessageDigest.getInstance("SHA-256");
//			byte[] hash = digest.digest(passwordStr.getBytes(StandardCharsets.UTF_8));
//			password_hashed = DatatypeConverter.printHexBinary(hash);
//		} catch (NoSuchAlgorithmException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//		 
//		 String sql = "SELECT `password`,`product_selling_point_id` FROM `user_system` WHERE `username`=\""+userName_string+"\";";
//		 result_Query = new ArrayList<String>();
//		 keys = new ArrayList<String>();
//		 keys.add("password");
//		 keys.add("product_selling_point_id");
//		try {
//			result_Query = databaseClass.selectSQL(sql, keys);
//			if(!result_Query.isEmpty()){
//				 if(password_hashed.equals(result_Query.get(0))){
//					 FirstPageController.setData(userName_string,result_Query.get(1),commonOperation.getPrivilligedStatus(userName_string));
//					 	Stage stage;
//						Parent root;
//						stage = (Stage) login.getScene().getWindow();
//						FXMLLoader loader = new FXMLLoader();
//						loader.setLocation(getClass().getResource("FirstPage.fxml"));
//					    root = loader.load();
//						Scene scene = new Scene(root, java.awt.Toolkit.getDefaultToolkit().getScreenSize().width , java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
//						stage.setScene(scene);
//						stage.setOnCloseRequest(e -> Platform.exit());
//						stage.setMaximized(true);
//						stage.show();
//						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//							
//							@Override
//							public void handle(WindowEvent event) {
//							System.out.println("first page closing");
//								
//							}
//						});
//						
//				 }
//				 else{
//					 warning.setText("Invalid password");
//				 }
//			 }
//			 else{
//				 warning.setText("Invalid user name");
//			 }
//		} catch (ClassNotFoundException | SQLException e1) {
//			warning.setText("No internet or Server is Down!!!");
//			logger.error("ERROR",e1);
//			e1.printStackTrace();
//		} 	
//	 }
//
//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		
//		URL log4jConfPath = getClass().getClassLoader().getResource("log4j.properties");
//		PropertyConfigurator.configure(log4jConfPath);
//		Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
//		Scene scene = new Scene(root, java.awt.Toolkit.getDefaultToolkit().getScreenSize().width , java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
//		primaryStage.setScene(scene);
//		primaryStage.setMaximized(true);
//		primaryStage.show();
//		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//			
//			@Override
//			public void handle(WindowEvent arg0) {
//				System.out.println("Window closing");
//				
//			}
//		});
//		
//	}
//	
//	public static void main(String[] args) {
//		
////		URL log4jConfPath = ClassLoader.getSystemResource("log4j.properties");
////		PropertyConfigurator.configure(log4jConfPath);
//		launch(args);
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
