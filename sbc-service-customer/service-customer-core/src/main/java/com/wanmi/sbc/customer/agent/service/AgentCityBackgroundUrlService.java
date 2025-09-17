package com.wanmi.sbc.customer.agent.service;

import com.wanmi.sbc.customer.agent.model.root.AgentCityBackgroundUrl;
import com.wanmi.sbc.customer.agent.repository.AgentCityBackgroundUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 代理商区域背景图表服务类
 */
@Service
public class AgentCityBackgroundUrlService {

    @Autowired
    private AgentCityBackgroundUrlRepository agentCityBackgroundUrlRepository;

    /**
     * 根据城市ID查询背景图URL
     *
     * @param cityId 城市ID
     * @return 背景图URL信息
     */
    public AgentCityBackgroundUrl findByCityId(Long cityId) {
        return agentCityBackgroundUrlRepository.findByCityId(cityId);
    }

    /**
     * 根据ID查询背景图URL
     *
     * @param id 主键ID
     * @return 背景图URL信息
     */
    public AgentCityBackgroundUrl findById(Long id) {
        return agentCityBackgroundUrlRepository.findById(id).orElse(null);
    }

    /**
     * 保存背景图URL信息
     *
     * @param backgroundUrl 背景图URL信息
     * @return 保存结果
     */
    @Transactional
    public AgentCityBackgroundUrl save(AgentCityBackgroundUrl backgroundUrl) {
        return agentCityBackgroundUrlRepository.save(backgroundUrl);
    }

    /**
     * 删除背景图URL信息
     *
     * @param id 主键ID
     * @return 删除结果
     */
    @Transactional
    public int delete(Long id) {
        AgentCityBackgroundUrl backgroundUrl = agentCityBackgroundUrlRepository.findById(id).orElse(null);
        if (Objects.isNull(backgroundUrl)) {
            return 0;
        }
        agentCityBackgroundUrlRepository.delete(backgroundUrl);
        return 1;
    }
}
