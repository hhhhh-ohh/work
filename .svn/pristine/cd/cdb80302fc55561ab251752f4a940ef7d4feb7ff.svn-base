package com.wanmi.sbc.goods.distributionmatter.repository;

import com.wanmi.sbc.goods.distributionmatter.model.root.DistributionGoodsMatter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistributionGoodsMatterRepository extends JpaRepository<DistributionGoodsMatter, String>, JpaSpecificationExecutor<DistributionGoodsMatter> {

    /**
     * 批量删除商品素材
     *
     * @param ids
     * @return
     */
    @Modifying
    @Query("update DistributionGoodsMatter set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES, deleteTime = now() where id in(?1)")
    int deleteByIds(List ids);

    /**
     * 根据id批量查询
     * @param ids
     * @return
     */
    @Query("from DistributionGoodsMatter where id in(?1) and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    List<DistributionGoodsMatter> queryByIds(List ids);

    @Modifying
    @Query("update DistributionGoodsMatter d set d.recommend = :#{#info.recommend},d.matter = :#{#info.matter}," +
            "d.matterType =:#{#info.matterType},d.openFlag=:#{#info.openFlag},d.storeState=:#{#info.storeState}," +
            "d.contractEndDate=:#{#info.contractEndDate},d.updateTime = now() where d.id =:#{#info.id}")
    void update(@Param("info") DistributionGoodsMatter info);

    @Modifying
    @Query("update DistributionGoodsMatter d set d.recommend = :#{#info.recommend} where d.id =:#{#info.id}")
    void updataRecomendNumById(@Param("info") DistributionGoodsMatter info);

    @Query("from DistributionGoodsMatter where id =?1 and delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    DistributionGoodsMatter queryById(String id);

    @Query(value = "select d.* from distribution_goods_matter d left join goods_info sku on d.goods_info_id=sku.goods_info_id " +
            " where d.matter_type=0 and d.del_flag = 0 and d.open_flag = 1 and d.store_state = 0 and d.contract_end_date > now() and " +
            " sku.audit_status =1 and sku.added_flag=1 and sku.del_flag = 0 AND sku.distribution_goods_audit=2 ORDER BY update_time DESC ",
            countQuery = "SELECT COUNT(1) FROM distribution_goods_matter d left join goods_info sku on d.goods_info_id=" +
                    " sku.goods_info_id where d.matter_type=0 and d.del_flag = 0 and d.open_flag = 1 and d.store_state = 0" +
                    " and d.contract_end_date > now() and sku.audit_status =1 and sku.added_flag=1 and sku.del_flag =" +
                    " 0 AND sku.distribution_goods_audit=2"
            , nativeQuery = true)
    Page<DistributionGoodsMatter> queryDistributionGoodsMatterForGoods(Pageable pageable);

    @Modifying
    @Query("update DistributionGoodsMatter d set d.storeState=:#{#info.storeState},d.updateTime = now() " +
            "where d.storeId =:#{#info.storeId}")
    void updateStoreStateByStoreId(@Param("info") DistributionGoodsMatter info);

    @Modifying
    @Query("update DistributionGoodsMatter d set d.openFlag=:#{#info.openFlag},d.updateTime = now() " +
            "where d.storeId =:#{#info.storeId}")
    void updateOpenFlagByStoreId(@Param("info") DistributionGoodsMatter info);

    @Modifying
    @Query("update DistributionGoodsMatter d set d.contractEndDate=:#{#info.contractEndDate},d.updateTime = now() " +
            "where d.storeId =:#{#info.storeId}")
    void updateContractEndDateByStoreId(@Param("info") DistributionGoodsMatter info);

}
