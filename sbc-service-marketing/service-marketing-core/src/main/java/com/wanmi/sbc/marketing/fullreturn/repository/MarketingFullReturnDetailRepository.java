package com.wanmi.sbc.marketing.fullreturn.repository;

import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingFullReturnDetailRepository extends JpaRepository<MarketingFullReturnDetail, Long>, JpaSpecificationExecutor<MarketingFullReturnDetail> {
    /**
     * 根据营销编号删除营销赠品信息
     *
     * @param marketingId
     * @return
     */
    int deleteByMarketingId(Long marketingId);

    /**
     * 根据营销Id查询满返详情集合
     *
     * @param marketingId
     * @return
     */
    List<MarketingFullReturnDetail> findByMarketingId(Long marketingId);

    /**
     * 根据营销Id查询满返详情集合
     *
     * @param marketingId
     * @return
     */
    List<MarketingFullReturnDetail> findByMarketingIdAndReturnLevelId(Long marketingId, Long returnLevelId);

    /**
     * 根据营销Id批量查询满返详情集合
     *
     * @param marketingIds 批量营销id
     * @param returnLevelIds 批量赠品等级id
     * @return
     */
    List<MarketingFullReturnDetail> findByMarketingIdInAndReturnLevelIdIn(List<Long> marketingIds, List<Long> returnLevelIds);
}