package com.wanmi.sbc.goods.cate.repository;

import com.wanmi.sbc.goods.cate.model.root.ContractCateAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 二次签约分类数据源
 * @author wangchao
 */
@Repository
public interface ContractCateAuditRepository extends JpaRepository<ContractCateAudit, Long>, JpaSpecificationExecutor<ContractCateAudit> {


    /**
     * 根据分类Ids查询签约分类数量
     * @param ids
     * @return
     */
    @Query("select count(c.contractCateId) from ContractCateAudit c where c.goodsCate.cateId in (:ids) ")
    int findCountByIds(@Param("ids") List<Long> ids);


    /**
     * 根据店铺id删除二次签约分类数据
     * @param storeId
     * @return
     */
    @Modifying
    @Query("delete from ContractCateAudit c where c.storeId = :storeId ")
    int deleteByStoreId(@Param("storeId") Long storeId);
}
