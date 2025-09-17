package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBatch;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>礼品卡批次DAO</p>
 * @author 马连峰
 * @date 2022-12-08 20:38:29
 */
@Repository
public interface GiftCardBatchRepository extends JpaRepository<GiftCardBatch, Long>,
        JpaSpecificationExecutor<GiftCardBatch> {

    /**
     * 单个删除礼品卡批次
     * @author 马连峰
     */
    @Modifying
    @Query("update GiftCardBatch set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where giftCardBatchId = ?1")
    void deleteById(Long giftCardBatchId);

    /**
     * 批量删除礼品卡批次
     * @author 马连峰
     */
    @Modifying
    @Query("update GiftCardBatch set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where giftCardBatchId in ?1")
    void deleteByIdList(List<Long> giftCardBatchIdList);

    /**
     * 查询单个礼品卡批次
     * @author 马连峰
     */
    Optional<GiftCardBatch> findByGiftCardBatchIdAndDelFlag(Long id, DeleteFlag delFlag);

}
