package com.wanmi.sbc.customer.agent.repository;

import com.wanmi.sbc.customer.agent.model.root.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 代理商数据访问接口
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, String>, JpaSpecificationExecutor<Agent> {

    /**
     * 根据代理商ID查询代理商信息
     *
     * @param agentId 代理商ID
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.agentId = :agentId AND a.delFlag = 0")
    Agent findByAgentId(@Param("agentId") String agentId);

    /**
     * 根据系统唯一码查询代理商信息
     *
     * @param uniqueCode 系统唯一码
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.agentUniqueCode = :uniqueCode AND a.delFlag = 0")
    Agent findByAgentUniqueCode(@Param("uniqueCode") String uniqueCode);

    /**
     * 根据审核状态查询代理商列表
     *
     * @param auditStatus 审核状态
     * @return 代理商列表
     */
    @Query("SELECT a FROM Agent a WHERE a.auditStatus = :auditStatus AND a.delFlag = 0")
    List<Agent> findByAuditStatus(@Param("auditStatus") Integer auditStatus);

    /**
     * 根据手机号查询代理商信息
     *
     * @param contactPhone 手机号
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.contactPhone = :contactPhone AND a.delFlag = 0")
    Agent findByContactPhone(@Param("contactPhone") String contactPhone);

    /**
     * 根据手机号查询最新的代理商信息
     *
     * @param contactPhone 手机号
     * @return 代理商信息，按创建时间倒序排列后的第一条
     */
    @Query("SELECT a FROM Agent a WHERE a.contactPhone = :contactPhone AND a.delFlag = 0 ORDER BY a.createTime DESC")
    List<Agent> findByContactPhoneOrderByCreateTimeDesc(@Param("contactPhone") String contactPhone);

    /**
     * 根据手机号查询代理商信息
     *
     * @param contactPhone 手机号
     * @param auditStatus 审核状态 0已创建 1待审核 2通过 3驳回
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.contactPhone = :contactPhone AND a.auditStatus = :auditStatus AND a.delFlag = 0")
    Agent findByContactPhoneAndAuditStatus(@Param("contactPhone") String contactPhone, @Param("auditStatus") Integer auditStatus);

    /**
     * 根据会员ID查询代理商信息
     *
     * @param customerId 会员ID
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.customerId = :customerId AND a.delFlag = 0 ORDER BY a.createTime DESC")
    List<Agent> findByCustomerIdOrderByCreateTimeDesc(@Param("customerId") String customerId);

    /**
     * 根据会员ID查询代理商信息
     *
     * @param customerId 会员ID
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.customerId = :customerId AND a.delFlag = 0")
    Agent findByCustomerId(@Param("customerId") String customerId);

    /**
     * 根据会员ID和审核状态查询代理商信息
     *
     * @param customerId 会员ID
     * @param auditStatus 审核状态 0已创建 1待审核 2通过 3驳回
     * @return 代理商信息
     */
    @Query("SELECT a FROM Agent a WHERE a.customerId = :customerId AND a.auditStatus = :auditStatus AND a.delFlag = 0")
    Agent findByCustomerIdAndAuditStatus(@Param("customerId") String customerId, @Param("auditStatus") Integer auditStatus);

    /**
     * 根据区域ID查询代理商列表
     *
     * @param areaId 区域ID
     * @return 代理商列表
     */
    @Query("SELECT a FROM Agent a WHERE a.areaId = :areaId AND a.delFlag = 0")
    List<Agent> findByAreaId(@Param("areaId") Long areaId);

    /**
     * 根据区域ID列表查询代理商列表
     * @param areaIdList
     * @return
     */
    @Query("SELECT a FROM Agent a WHERE a.areaId IN :areaIdList AND a.delFlag = 0")
    List<Agent> findByAreaIdIn(@Param("areaIdList") List<Long> areaIdList);

    /**
     * 根据城市ID查询代理商列表
     * @param cityId
     * @return
     */
    @Query("SELECT a FROM Agent a WHERE a.cityId = :cityId AND a.delFlag = 0")
    List<Agent> findByCityId(Long cityId);

    /**
     * 根据城市ID和审核状态查询代理商列表
     * @param cityId
     * @param auditStatus
     * @return
     */
    @Query("SELECT a FROM Agent a WHERE a.cityId = :cityId AND a.auditStatus = :auditStatus AND a.delFlag = 0")
    List<Agent> findByCityIdAndAuditStatus(Long cityId, Integer auditStatus);

    /**
     * 根据城市ID、审核状态和更新时间筛选代理商列表
     * @param cityId 城市ID
     * @param auditStatus 审核状态
     * @param updateTime 更新时间
     * @return 代理商列表
     */
    List<Agent> findByCityIdAndAuditStatusAndUpdateTimeBefore(Long cityId, Integer auditStatus, LocalDateTime updateTime);

    /**
     * 根据代理商ID更新代理商信息
     *
     * @param agent 包含更新信息的代理商对象
     */
    @Modifying
    @Query(value = "UPDATE agent SET agent_name = :#{#agent.agentName}, contact_phone = :#{#agent.contactPhone}, " +
            "area_id = :#{#agent.areaId}, city_id = :#{#agent.cityId}, audit_status = :#{#agent.auditStatus}, " +
            "update_time = NOW() " +
            "WHERE agent_id = :#{#agent.agentId} AND del_flag = 0", nativeQuery = true)
    void updateByIdNative(@Param("agent") Agent agent);

    @Query("SELECT a FROM Agent a WHERE a.delFlag = 0 " +
            "AND a.auditStatus = 2 " +
            "AND (:areaId IS NULL OR a.areaId = :areaId) " +
            "ORDER BY a.createTime DESC")
    List<Agent> getAgentListByAreaId(Long areaId);


    /**
     * 根据上级唯一码查询下属二级代理商（type=3，未删除）
     * @param parentUniqueCode 上级代理商唯一码（一级代理商的agentUniqueCode）
     * @return 下属二级代理商列表
     */
    @Query("SELECT a FROM Agent a WHERE a.agentParentUniqueCode = :parentUniqueCode AND a.type = 3 AND a.delFlag = 0")
    List<Agent> findByAgentParentUniqueCodeAndTypeAndDelFlag(@Param("parentUniqueCode") String parentUniqueCode);
}
