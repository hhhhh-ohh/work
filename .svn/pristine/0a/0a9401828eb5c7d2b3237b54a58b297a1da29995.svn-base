package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.marketing.bean.enums.GiftCardBusinessType;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author wur
 * @className GiftCardBillRepository
 * @description 礼品卡交易流水
 * @date 2022/12/8 16:16
 **/
@Repository
public interface GiftCardBillRepository extends JpaRepository<GiftCardBill, Long>, JpaSpecificationExecutor<GiftCardBill> {

    Optional<GiftCardBill> findByUserGiftCardIdAndBusinessType(Long userGiftCardId, GiftCardBusinessType businessType);

    /**
     * 删除礼品卡交易记录
     * @param customerId      用户Id
     * @param userGiftCardId  用户礼品卡Id
     * @param businessType    业务类型
     * @param businessId      业务ID
     */
    @Modifying
    @Query("delete GiftCardBill where customerId = ?1 and userGiftCardId=?2 and businessType = ?3 and businessId in (?4)")
    void deleteBusiness(String customerId, Long userGiftCardId, GiftCardBusinessType businessType, List<String> businessId);
}