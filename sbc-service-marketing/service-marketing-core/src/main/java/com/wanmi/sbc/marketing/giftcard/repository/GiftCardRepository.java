package com.wanmi.sbc.marketing.giftcard.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author wur
 * @className GiftCardRepository
 * @description 礼品卡信息
 * @date 2022/12/8 16:16
 **/
@Repository
public interface GiftCardRepository extends JpaRepository<GiftCard, Long>, JpaSpecificationExecutor<GiftCard> {

    /**
     * @description 根据id和删除标识查询礼品卡
     * @author malianfeng
     * @date 2023/1/3 14:30
     * @param giftCardId 主键id
     * @param delFlag 删除标识
     * @return com.wanmi.sbc.marketing.giftcard.model.root.GiftCard
     */
    GiftCard findByGiftCardIdAndDelFlag(Long giftCardId, DeleteFlag delFlag);
    GiftCard findByNameAndDelFlag(String name, DeleteFlag delFlag);
    /**
     * @description   根据Id批量查询不过滤删除类型
     * @author  wur
     * @date: 2022/12/12 14:45
     * @param giftCardIdList
     * @return
     **/
    List<GiftCard> queryAllByGiftCardIdIn(List<Long> giftCardIdList);

    /**
     * @description 扣减库存
     * @author malianfeng
     * @date 2022/12/10 14:08
     * @param giftCardId 礼品卡id
     * @param subStock 扣减制卡库存
     * @return int
     **/
    @Modifying
    @Query("update GiftCard set stock = stock - ?2 where giftCardId = ?1 and stockType = 0 and stock - ?2 >= 0")
    int subStock(Long giftCardId, Long subStock);

    /**
     * @description 扣减已制卡数量
     * @author malianfeng
     * @date 2022/12/10 14:08
     * @param giftCardId 礼品卡id
     * @param subStock 扣减制卡库存
     * @return int
     **/
    @Modifying
    @Query("update GiftCard set makeNum = makeNum - ?2 where giftCardId = ?1 and makeNum - ?2 >= 0")
    int subMakeNum(Long giftCardId, Long subStock);

    /**
     * @description 扣减已发卡数量
     * @author malianfeng
     * @date 2022/12/10 14:08
     * @param giftCardId 礼品卡id
     * @param subStock 扣减制卡库存
     * @return int
     **/
    @Modifying
    @Query("update GiftCard set sendNum = sendNum - ?2 where giftCardId = ?1 and sendNum - ?2 >= 0")
    int subSendNum(Long giftCardId, Long subStock);
}