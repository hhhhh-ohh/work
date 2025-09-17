package com.wanmi.sbc.customer.agent.service;

import com.wanmi.sbc.customer.agent.model.root.AgentUpdatePosterAuth;
import com.wanmi.sbc.customer.agent.repository.AgentUpdatePosterAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 代理商权限服务类
 */
@Service
public class AgentUpdatePosterAuthService {

    @Autowired
    private AgentUpdatePosterAuthRepository agentUpdatePosterAuthRepository;

    /**
     * 通过ID查询代理商权限信息
     *
     * @param id 主键ID
     * @return 代理商权限信息
     */
    public AgentUpdatePosterAuth findOne(Long id) {
        return agentUpdatePosterAuthRepository.findById(id).orElse(null);
    }

    /**
     * 通过联系电话查询代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息
     */
    public AgentUpdatePosterAuth findByContactPhone(String contactPhone) {
        return agentUpdatePosterAuthRepository.findByContactPhone(contactPhone);
    }

    /**
     * 通过联系电话查询所有代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息列表
     */
    public List<AgentUpdatePosterAuth> findAllByContactPhone(String contactPhone) {
        return agentUpdatePosterAuthRepository.findAllByContactPhone(contactPhone);
    }

    /**
     * 根据城市ID查询代理商权限列表
     *
     * @param cityId 城市ID
     * @return 代理商权限列表
     */
    public List<AgentUpdatePosterAuth> findByCityId(Long cityId) {
        return agentUpdatePosterAuthRepository.findByCityId(cityId);
    }

    /**
     * 保存代理商权限信息
     *
     * @param agentUpdatePosterAuth 代理商权限信息
     */
    @Transactional
    public void save(AgentUpdatePosterAuth agentUpdatePosterAuth) {
        agentUpdatePosterAuthRepository.save(agentUpdatePosterAuth);
    }

    /**
     * 更新代理商权限信息
     *
     * @param agentUpdatePosterAuth 代理商权限信息
     * @return 更新结果
     */
    @Transactional
    public int update(AgentUpdatePosterAuth agentUpdatePosterAuth) {
        AgentUpdatePosterAuth existing = agentUpdatePosterAuthRepository.findById(agentUpdatePosterAuth.getId()).orElse(null);
        if (Objects.isNull(existing)) {
            return 0;
        }

        existing.setContactPhone(agentUpdatePosterAuth.getContactPhone());
        existing.setCityId(agentUpdatePosterAuth.getCityId());

        agentUpdatePosterAuthRepository.save(existing);
        return 1;
    }

    /**
     * 删除代理商权限
     *
     * @param id 主键ID
     * @return 删除结果
     */
    @Transactional
    public int delete(Long id) {
        AgentUpdatePosterAuth agentUpdatePosterAuth = agentUpdatePosterAuthRepository.findById(id).orElse(null);
        if (Objects.isNull(agentUpdatePosterAuth)) {
            return 0;
        }
        agentUpdatePosterAuthRepository.delete(agentUpdatePosterAuth);
        return 1;
    }

    /**
     * 批量删除代理商权限
     *
     * @param ids 主键ID列表
     * @return 删除结果
     */
    @Transactional
    public int deleteBatch(List<Long> ids) {
        agentUpdatePosterAuthRepository.deleteAllById(ids);
        return ids.size();
    }

    /**
     * 分页查询代理商权限信息
     *
     * @param specification 查询条件
     * @param pageRequest 分页信息
     * @return 代理商权限分页列表
     */
    public Page<AgentUpdatePosterAuth> findPage(Specification<AgentUpdatePosterAuth> specification, PageRequest pageRequest) {
        return agentUpdatePosterAuthRepository.findAll(specification, pageRequest);
    }
}
