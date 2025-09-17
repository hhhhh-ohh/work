package com.wanmi.sbc.marketing.preferential.repository;

import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @description  加价购档次阶梯
 * @author edz
 * @date 2022/11/17 18:09
 */
@Repository
public interface MarketingPreferentialLevelRepository extends JpaRepository<MarketingPreferentialLevel, Long>,
        JpaSpecificationExecutor<MarketingPreferentialLevel> {

    /**
     * @description 活动ID查询等级阶梯
     * @author  edz
     * @date: 2022/12/9 15:15
     * @param marketingId
     * @return java.util.List<com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel>
     */
    List<MarketingPreferentialLevel> findByMarketingId(Long marketingId);

    /**
     * @description 批量活动ID查询等级阶梯
     * @author  edz
     * @date: 2022/12/9 15:15
     * @param marketingId
     * @return java.util.List<com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialLevel>
     */
    List<MarketingPreferentialLevel> findByMarketingIdIn(List<Long> marketingId);

    /**
     * @description 活动ID删除等级阶梯信息
     * @author  edz
     * @date: 2022/12/9 15:16
     * @param marketingId
     * @return int
     */
    int deleteByMarketingId(Long marketingId);
}
