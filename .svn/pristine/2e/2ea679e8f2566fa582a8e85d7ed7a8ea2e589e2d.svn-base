package com.wanmi.sbc.marketing.common.repository;

import com.wanmi.sbc.marketing.common.model.root.MarketingScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 营销和商品关联关系
 */
@Repository
public interface MarketingScopeRepository extends JpaRepository<MarketingScope, Long> , JpaSpecificationExecutor<MarketingScope> {
    /**
     * 根据营销编号删除和商品的关联关系
     *
     * @param marketingId
     * @return
     */
    int deleteByMarketingId(Long marketingId);

    /**
     * 根据营销编号查询商品关联
     *
     * @param marketingId
     * @return
     */
    List<MarketingScope> findByMarketingId(Long marketingId);

    /**
     * 根据活动Id批量查询
     * @author  wur
     * @date: 2021/11/30 17:33
     * @param marketingIdList
     * @return
     **/
    List<MarketingScope> findByMarketingIdIn(List<Long> marketingIdList);
}
