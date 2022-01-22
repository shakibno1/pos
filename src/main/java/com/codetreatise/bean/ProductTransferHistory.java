package com.codetreatise.bean;

import javax.persistence.*;

@Entity
@Table(name="product_transfer_history")
public class ProductTransferHistory extends BaseModel {
	
	private ProductStock productStock;
	private Shop fromShop;
	private Shop toShop;
	private Integer transferBatch;

	@Enumerated(EnumType.STRING)
	private ProductTransferStatus productTransferStatus;
	
	@ManyToOne //(cascade=CascadeType.ALL)
	public ProductStock getProductStock() {
		return productStock;
	}
	public void setProductStock(ProductStock productStock) {
		this.productStock = productStock;
	}
	@ManyToOne
	public Shop getFromShop() {
		return fromShop;
	}
	public void setFromShop(Shop fromShop) {
		this.fromShop = fromShop;
	}
	@ManyToOne
	public Shop getToShop() {
		return toShop;
	}
	public void setToShop(Shop toShop) {
		this.toShop = toShop;
	}

    public Integer getTransferBatch() {
        return transferBatch;
    }

    public void setTransferBatch(Integer transferBatch) {
        this.transferBatch = transferBatch;
    }

	@Enumerated (EnumType.STRING)
	public ProductTransferStatus getProductTransferStatus() {
		return productTransferStatus;
	}

	public void setProductTransferStatus(ProductTransferStatus productTransferStatus) {
		this.productTransferStatus = productTransferStatus;
	}
}
