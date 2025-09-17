package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.marketing.giftcard.model.root.GiftCardScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wur
 * @className GiftCardScopeRepository
 * @description 礼品卡对应商品关联表
 * @date 2022/12/8 16:16
 **/
@Repository
public interface GiftCardScopeRepository extends JpaRepository<GiftCardScope, Long>, JpaSpecificationExecutor<GiftCardScope> {

    @Query(" from GiftCardScope c where c.giftCardId = ?1")
    List<GiftCardScope> queryListByGiftCardId(Long giftCardId);

    @Query(" from GiftCardScope c where c.giftCardId in ?1")
    List<GiftCardScope> queryListByGiftCardIdIn(List<Long> giftCardIdList);

}