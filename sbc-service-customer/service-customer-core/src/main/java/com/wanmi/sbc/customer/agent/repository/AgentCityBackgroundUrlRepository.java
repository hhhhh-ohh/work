package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.customer.agent.model.root.AgentCityBackgroundUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 代理商区域背景表数据访问接口
 */
@Repository
public interface AgentCityBackgroundUrlRepository extends JpaRepository<AgentCityBackgroundUrl, Long>, JpaSpecificationExecutor<AgentCityBackgroundUrl> {

    /**
     * 根据城市ID查询背景图信息
     *
     * @param cityId 城市ID
     * @return 背景图信息
     */
    AgentCityBackgroundUrl findByCityId(Long cityId);
}
