package com.codetreatise.bean;

public class ProductReprintTableBean {
	
	private String id;
	private String quantity;
	private String size;
	private String amount;
	private String quantityLeftInStock;
	private String description;
	private String printingStatus;
	
	public ProductReprintTableBean(String id , String quantity , String size , String amount , 
			String quantityLeftInStock , String description , String printingStatus){
		
		this.id = id;
		this.quantity = quantity;
		this.size = size;
		this.amount = amount;
		this.quantityLeftInStock = quantityLeftInStock;
		this.description = description;
		this.printingStatus = printingStatus;
		
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getQuantityLeftInStock() {
		return quantityLeftInStock;
	}
	public void setQuantityLeftInStock(String quantityLeftInStock) {
		this.quantityLeftInStock = quantityLeftInStock;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrintingStatus() {
		return printingStatus;
	}
	public void setPrintingStatus(String printingStatus) {
		this.printingStatus = printingStatus;
	}
	
	
	
	
	
}
