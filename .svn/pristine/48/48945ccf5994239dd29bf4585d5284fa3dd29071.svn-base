package com.wanmi.sbc.marketing.common.repository;

import com.wanmi.sbc.marketing.fullreturn.model.root.MarketingFullReturnStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 营销和店铺关联关系
 * @author xufeng
 */
@Repository
public interface MarketingStoreRepository extends JpaRepository<MarketingFullReturnStore, Long> {
    /**
     * 根据营销编号删除和店铺的关联关系
     *
     * @param marketingId
     * @return
     */
    int deleteByMarketingId(Long marketingId);

    /**
     * 根据营销编号查询店铺关联
     *
     * @param marketingId
     * @return
     */
    List<MarketingFullReturnStore> findByMarketingId(Long marketingId);

}
