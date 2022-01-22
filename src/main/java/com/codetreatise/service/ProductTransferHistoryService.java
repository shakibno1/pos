package com.codetreatise.service;

import com.codetreatise.bean.ProductStock;
import com.codetreatise.bean.ProductTransferHistory;
import com.codetreatise.bean.ProductTransferStatus;
import com.codetreatise.bean.Shop;
import com.codetreatise.generic.GenericService;

import java.util.List;


public interface ProductTransferHistoryService extends GenericService<ProductTransferHistory> {

    Integer getMaxTransferBatch();

    List<String> getAllTransferBatches();

    List<ProductTransferHistory> findByTransferBatch(Integer transferBatch);

    List<ProductTransferHistory> findByTransferBatchAndProductTransferStatusAndToShop(Integer transferBatch,ProductTransferStatus productTransferStatus,Shop toShop);

    ProductTransferHistory findByProductStockAndToShopAndProductTransferStatus
            (ProductStock productStock, Shop toShop, ProductTransferStatus productTransferStatus);
}
