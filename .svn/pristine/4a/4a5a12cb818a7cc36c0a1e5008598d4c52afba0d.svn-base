package com.wanmi.sbc.setting.stockWarning.repository;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.setting.stockWarning.model.root.StockWarning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface StockWarnRepository extends JpaRepository<StockWarning, Long>,
        JpaSpecificationExecutor<StockWarning> {
    /**
     * 批量修改商家库存预警状态
     * @author 修改商家商家库存预警状态
     */
    @Modifying
    @Query("update StockWarning set isWarning = com.wanmi.sbc.common.enums.BoolFlag.NO where storeId = ?1")
    int modifyIsWarning(Long storeId);

    /**
     * 批量修改商家库存预警状态
     * @author 修改商家商家库存预警状态
     */
    @Modifying
    int deleteBySkuId(String skuId);

    /**
     * 查询商家预警状态
     */
    @Query("select isWarning from StockWarning where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and storeId = ?1 and skuId = ?2")
    BoolFlag findIsWarning (Long StoreId,String skuId);

}
