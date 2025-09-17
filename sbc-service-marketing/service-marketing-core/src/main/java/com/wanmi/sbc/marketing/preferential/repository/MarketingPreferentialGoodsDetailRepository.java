package com.wanmi.sbc.marketing.preferential.repository;

import com.wanmi.sbc.marketing.preferential.model.root.MarketingPreferentialDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/***
 * @description 加价购关联商品
 * @author edz
 * @date 2022/11/17 18:09
 */
@Repository
public interface MarketingPreferentialGoodsDetailRepository extends JpaRepository<MarketingPreferentialDetail, Long>,
        JpaSpecificationExecutor<MarketingPreferentialDetail> {

    List<MarketingPreferentialDetail> findByMarketingId(Long marketingId);

    List<MarketingPreferentialDetail> findByMarketingIdIn(List<Long> marketingId);

    int deleteByMarketingId(Long marketingId);

    List<MarketingPreferentialDetail> findByMarketingIdInAndPreferentialLevelIdIn(List<Long> marketingIds,
                                                                                  List<Long> levelids);
}
