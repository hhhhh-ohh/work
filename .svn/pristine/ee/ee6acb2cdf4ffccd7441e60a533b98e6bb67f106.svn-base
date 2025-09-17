package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.customer.agent.model.root.AgentAuditAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代理商权限数据访问接口
 */
@Repository
public interface AgentAuditAuthRepository extends JpaRepository<AgentAuditAuth, Long>, JpaSpecificationExecutor<AgentAuditAuth> {

    /**
     * 根据区ID查询代理商权限列表
     *
     * @param areaId 区ID
     * @return 代理商权限列表
     */
    List<AgentAuditAuth> findByAreaId(Long areaId);

    /**
     * 根据联系电话查询代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息
     */
    AgentAuditAuth findByContactPhone(String contactPhone);

    /**
     * 根据联系电话查询所有匹配的代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息列表
     */
    List<AgentAuditAuth> findAllByContactPhone(String contactPhone);
}
