package com.codetreatise.repository;

import java.sql.Timestamp;
import java.util.List;

import com.codetreatise.bean.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTransferHistoryRepository extends JpaRepository<ProductTransferHistory, Long> {

    @Query("SELECT coalesce(max(pth.transferBatch), 0) FROM ProductTransferHistory pth")
    Integer getMaxTransferBatch();

    @Query("SELECT DISTINCT(transferBatch) FROM ProductTransferHistory WHERE transferBatch IS NOT NULL")
    List<String> getAllTransferBatches();

    List<ProductTransferHistory> findByTransferBatch(Integer transferBatch);

    List<ProductTransferHistory> findByTransferBatchAndProductTransferStatusAndToShop(Integer transferBatch,ProductTransferStatus productTransferStatus,Shop toShop);

    ProductTransferHistory findByProductStockAndToShopAndProductTransferStatus
            (ProductStock productStock,Shop toShop,ProductTransferStatus productTransferStatus);
	
}
