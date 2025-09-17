package com.wanmi.sbc.customer.agent.service;

import com.wanmi.sbc.customer.agent.model.root.AgentAuditAuth;
import com.wanmi.sbc.customer.agent.repository.AgentAuditAuthRepository;
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
public class AgentAuditAuthService {

    @Autowired
    private AgentAuditAuthRepository agentAuditAuthRepository;

    /**
     * 通过ID查询代理商权限信息
     *
     * @param id 主键ID
     * @return 代理商权限信息
     */
    public AgentAuditAuth findOne(Long id) {
        return agentAuditAuthRepository.findById(id).orElse(null);
    }

    /**
     * 通过联系电话查询代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息
     */
    public AgentAuditAuth findByContactPhone(String contactPhone) {
        return agentAuditAuthRepository.findByContactPhone(contactPhone);
    }


    /**
     * 通过联系电话查询代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息
     */
    public List<AgentAuditAuth>  findAllByContactPhone(String contactPhone) {
        return agentAuditAuthRepository.findAllByContactPhone(contactPhone);
    }

    /**
     * 根据区ID查询代理商权限列表
     *
     * @param areaId 区ID
     * @return 代理商权限列表
     */
    public List<AgentAuditAuth> findByAreaId(Long areaId) {
        return agentAuditAuthRepository.findByAreaId(areaId);
    }

    /**
     * 保存代理商权限信息
     *
     * @param agentAuditAuth 代理商权限信息
     */
    @Transactional
    public void save(AgentAuditAuth agentAuditAuth) {
        agentAuditAuthRepository.save(agentAuditAuth);
    }

    /**
     * 更新代理商权限信息
     *
     * @param agentAuditAuth 代理商权限信息
     * @return 更新结果
     */
    @Transactional
    public int update(AgentAuditAuth agentAuditAuth) {
        AgentAuditAuth existing = agentAuditAuthRepository.findById(agentAuditAuth.getId()).orElse(null);
        if (Objects.isNull(existing)) {
            return 0;
        }

        existing.setContactPhone(agentAuditAuth.getContactPhone());
        existing.setAreaId(agentAuditAuth.getAreaId());

        agentAuditAuthRepository.save(existing);
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
        AgentAuditAuth agentAuditAuth = agentAuditAuthRepository.findById(id).orElse(null);
        if (Objects.isNull(agentAuditAuth)) {
            return 0;
        }
        agentAuditAuthRepository.delete(agentAuditAuth);
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
        agentAuditAuthRepository.deleteAllById(ids);
        return ids.size();
    }

    /**
     * 分页查询代理商权限信息
     *
     * @param specification 查询条件
     * @param pageRequest 分页信息
     * @return 代理商权限分页列表
     */
    public Page<AgentAuditAuth> findPage(Specification<AgentAuditAuth> specification, PageRequest pageRequest) {
        return agentAuditAuthRepository.findAll(specification, pageRequest);
    }
}
