package com.wanmi.sbc.marketing.fullreturn.repository;

import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingFullReturnLevelRepository extends JpaRepository<MarketingFullReturnLevel, Long>, JpaSpecificationExecutor<MarketingFullReturnLevel> {

    /**
     * 根据营销编号删除营销等级信息
     *
     * @param marketingId
     * @return
     */
    int deleteByMarketingId(Long marketingId);

    /**
     * 满返规则列表
     * @param marketingId
     * @return
     */
    List<MarketingFullReturnLevel> findByMarketingIdOrderByFullAmountAsc(Long marketingId);

}