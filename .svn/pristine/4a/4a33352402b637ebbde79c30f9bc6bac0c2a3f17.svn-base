package com.wanmi.sbc.customer.agent.service;

import com.wanmi.sbc.customer.agent.model.root.AgentAuditLog;
import com.wanmi.sbc.customer.agent.repository.AgentAuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代理商审核记录服务类
 */
@Service
public class AgentAuditLogService {

    @Autowired
    private AgentAuditLogRepository agentAuditLogRepository;

    /**
     * 保存审核记录
     *
     * @param agentAuditLog 审核记录信息
     */
    @Transactional
    public void save(AgentAuditLog agentAuditLog) {
        if (agentAuditLog.getAuditTime() == null) {
            agentAuditLog.setAuditTime(LocalDateTime.now());
        }
        agentAuditLogRepository.save(agentAuditLog);
    }

    /**
     * 根据代理商ID查询审核记录列表
     *
     * @param agentId 代理商ID
     * @return 审核记录列表
     */
    public List<AgentAuditLog> findByAgentId(String agentId) {
        return agentAuditLogRepository.findByAgentId(agentId);
    }

    /**
     * 根据审核状态查询审核记录列表
     *
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    public List<AgentAuditLog> findByAuditStatus(Integer auditStatus) {
        return agentAuditLogRepository.findByAuditStatus(auditStatus);
    }

    /**
     * 分页查询审核记录
     *
     * @param specification 查询条件
     * @param pageRequest 分页信息
     * @return 审核记录分页列表
     */
    public Page<AgentAuditLog> findPage(Specification<AgentAuditLog> specification, PageRequest pageRequest) {
        return agentAuditLogRepository.findAll(specification, pageRequest);
    }

    /**
     * 根据代理商ID和审核状态查询审核记录列表
     *
     * @param agentId 代理商ID
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    public List<AgentAuditLog> findByAgentIdAndAuditStatus(String agentId, Integer auditStatus) {
        return agentAuditLogRepository.findByAgentIdAndAuditStatus(agentId, auditStatus);
    }

    /**
     * 根据代理商ID列表查询审核记录列表
     *
     * @param agentIdList 代理商ID列表
     * @return 审核记录列表
     */
    public List<AgentAuditLog> findByAgentIdIn(List<String> agentIdList) {
        return agentAuditLogRepository.findByAgentIdIn(agentIdList);
    }

    /**
     * 根据代理商ID列表和审核状态查询审核记录列表
     *
     * @param agentIdList 代理商ID列表
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    public List<AgentAuditLog> findByAgentIdInAndAuditStatus(List<String> agentIdList, Integer auditStatus) {
        return agentAuditLogRepository.findByAgentIdInAndAuditStatus(agentIdList, auditStatus);
    }


    /**
     * 更新审核记录
     *
     * @param agentAuditLog 待更新审核记录信息
     */
    @Transactional
    public void update(AgentAuditLog agentAuditLog) {
        AgentAuditLog existingLog = agentAuditLogRepository.findById(agentAuditLog.getLogId()).orElse(null);
        if (existingLog != null) {
            existingLog.setAgentId(agentAuditLog.getAgentId());
            existingLog.setAuditStatus(agentAuditLog.getAuditStatus());
            existingLog.setAuditOpinion(agentAuditLog.getAuditOpinion());
            existingLog.setRejectReason(agentAuditLog.getRejectReason());
            existingLog.setAuditorId(agentAuditLog.getAuditorId());
            existingLog.setAuditorName(agentAuditLog.getAuditorName());
            existingLog.setAuditTime(agentAuditLog.getAuditTime());
            existingLog.setDelFlag(agentAuditLog.getDelFlag());
            agentAuditLogRepository.save(existingLog);
        }
    }
}
