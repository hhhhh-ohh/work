package com.wanmi.sbc.goods.brand.repository;

import com.wanmi.sbc.goods.brand.model.root.ContractBrandAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 二次签约品牌数据源
 * @author wangchao
 */
@Repository
public interface ContractBrandAuditRepository extends JpaRepository<ContractBrandAudit, Long>, JpaSpecificationExecutor<ContractBrandAudit> {


    /**
     * 根据店铺id 删除二次签约的品牌
     * @param storeId
     */
    @Query("delete from ContractBrandAudit c where c.storeId = :storeId")
    @Modifying
    void deleteByStoreId(@Param("storeId") Long storeId);
}
