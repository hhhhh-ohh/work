package com.wanmi.sbc.marketing.pluginconfig.repository;

import com.wanmi.sbc.marketing.marketingsuitssku.model.root.MarketingSuitsSku;
import com.wanmi.sbc.marketing.pluginconfig.model.root.MarketingPluginConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhanggaolei
 * @className MarketingPluginConfigRepository
 * @description TODO
 * @date 2021/6/9 17:27
 **/
@Repository
public interface MarketingPluginConfigRepository extends JpaRepository<MarketingPluginConfig, Long>,
        JpaSpecificationExecutor<MarketingPluginConfig>{

    List<MarketingPluginConfig> findAllByOrderBySortAsc();
}

