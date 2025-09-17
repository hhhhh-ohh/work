package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.customer.agent.model.root.AgentUpdatePosterAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代理商权限数据访问接口
 */
@Repository
public interface AgentUpdatePosterAuthRepository extends JpaRepository<AgentUpdatePosterAuth, Long>, JpaSpecificationExecutor<AgentUpdatePosterAuth> {

    /**
     * 根据城市ID查询代理商权限列表
     *
     * @param cityId 城市ID
     * @return 代理商权限列表
     */
    List<AgentUpdatePosterAuth> findByCityId(Long cityId);

    /**
     * 根据联系电话查询代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息
     */
    AgentUpdatePosterAuth findByContactPhone(String contactPhone);

    /**
     * 根据联系电话查询所有匹配的代理商权限信息
     *
     * @param contactPhone 联系电话
     * @return 代理商权限信息列表
     */
    List<AgentUpdatePosterAuth> findAllByContactPhone(String contactPhone);
}
