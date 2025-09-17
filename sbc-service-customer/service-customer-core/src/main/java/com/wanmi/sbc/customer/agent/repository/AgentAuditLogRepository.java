package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.agent.model.root.AgentAuditLog;
import com.wanmi.sbc.customer.company.model.root.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代理商审核记录数据访问接口
 */
@Repository
public interface AgentAuditLogRepository extends JpaRepository<AgentAuditLog, String>, JpaSpecificationExecutor<AgentAuditLog> {

    /**
     * 根据代理商ID查询审核记录列表
     *
     * @param agentId 代理商ID
     * @return 审核记录列表
     */
    @Query("SELECT a FROM AgentAuditLog a WHERE a.agentId = :agentId AND a.delFlag = 0")
    List<AgentAuditLog> findByAgentId(String agentId);

    /**
     * 根据审核状态查询审核记录列表
     *
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    @Query("SELECT a FROM AgentAuditLog a WHERE a.auditStatus = :auditStatus AND a.delFlag = 0")
    List<AgentAuditLog> findByAuditStatus(Integer auditStatus);

    /**
     * 根据代理商ID和审核状态查询审核记录列表
     *
     * @param agentId     代理商ID
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    @Query("SELECT a FROM AgentAuditLog a WHERE a.agentId = :agentId AND a.auditStatus = :auditStatus AND a.delFlag = 0")
    List<AgentAuditLog> findByAgentIdAndAuditStatus(String agentId, Integer auditStatus);

    /**
     * 根据代理商ID列表查询审核记录列表
     *
     * @param agentIdList 代理商ID列表
     * @return 审核记录列表
     */
    @Query("SELECT a FROM AgentAuditLog a WHERE a.agentId IN (:agentIdList) AND a.delFlag = 0")
    List<AgentAuditLog> findByAgentIdIn(@Param("agentIdList") List<String> agentIdList);

    /**
     * 根据代理商ID列表和审核状态查询审核记录列表
     *
     * @param agentIdList 代理商ID列表
     * @param auditStatus 审核状态
     * @return 审核记录列表
     */
    @Query("SELECT a FROM AgentAuditLog a WHERE a.agentId IN (:agentIdList) AND a.auditStatus = :auditStatus AND a.delFlag = 0")
    List<AgentAuditLog> findByAgentIdInAndAuditStatus(@Param("agentIdList") List<String> agentIdList, @Param("auditStatus") Integer auditStatus);
}

