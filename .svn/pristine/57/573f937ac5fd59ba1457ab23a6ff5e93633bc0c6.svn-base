package com.wanmi.sbc.marketing.gift.repository;

import com.wanmi.sbc.marketing.gift.model.root.MarketingFullGiftDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingFullGiftDetailRepository extends JpaRepository<MarketingFullGiftDetail, Long>, JpaSpecificationExecutor<MarketingFullGiftDetail> {
    /**
     * 根据营销编号删除营销赠品信息
     *
     * @param marketingId
     * @return
     */
    int deleteByMarketingId(Long marketingId);

    /**
     * 根据营销Id查询满赠详情集合
     *
     * @param marketingId
     * @return
     */
    List<MarketingFullGiftDetail> findByMarketingId(Long marketingId);

    /**
     * 根据营销Id查询满赠详情集合
     *
     * @param marketingId
     * @return
     */
    List<MarketingFullGiftDetail> findByMarketingIdAndGiftLevelId(Long marketingId, Long giftLevelId);

    /**
     * 根据营销Id批量查询满赠详情集合
     *
     * @param marketingIds 批量营销id
     * @param giftLevelIds 批量赠品等级id
     * @return
     */
    List<MarketingFullGiftDetail> findByMarketingIdInAndGiftLevelIdIn(List<Long> marketingIds, List<Long> giftLevelIds);

    /**
     *  更新赠品库存
     *
     * @param marketingId
     * @return
     */
    @Modifying
    @Query("update MarketingFullGiftDetail m set m.productStock = m.productStock-:productStock where m.marketingId = :marketingId " +
            " and m.productId = :productId")
    int decreaseStock(Long marketingId, String productId, Long productStock);
}