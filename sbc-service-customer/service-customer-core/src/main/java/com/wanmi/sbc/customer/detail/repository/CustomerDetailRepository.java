package com.wanmi.sbc.customer.detail.repository;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.enums.CustomerStatus;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetail;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetailBase;
import com.wanmi.sbc.customer.detail.model.root.CustomerDetailInitEs;
import com.wanmi.sbc.customer.detail.model.root.CustomerStatusBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 会员详情数据源
 * Created by CHENLI on 2017/4/18.
 */
@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, String>,
        JpaSpecificationExecutor<CustomerDetail> {

    /**
     * 根据会员详情ID查询会员详情信息
     *
     * @param customerDetailId
     * @return
     */
    @Query("from CustomerDetail c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerDetailId = :customerDetailId")
    CustomerDetail findByCustomerDetailId(@Param("customerDetailId") String customerDetailId);

    @EntityGraph(value = "customerDetail.all",type= EntityGraph.EntityGraphType.FETCH)
    Page<CustomerDetail> findAll(@Nullable Specification<CustomerDetail> var1, Pageable var2);

    /**
     * 根据会员详情ID查询会员详情信息
     *
     * @param customerId
     * @return
     */
    @Query("from CustomerDetail c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = :customerId")
    CustomerDetail findByCustomerId(@Param("customerId") String customerId);

    /**
     * 根据会员详情ID查询会员详情信息
     *
     * @param customerId
     * @return
     */
    @Query("from CustomerDetail c where  c.customerId = :customerId")
    CustomerDetail findAnyByCustomerId(@Param("customerId") String customerId);

    /**
     * 根据会员详情ID查询会员详情信息
     *
     * @param customerIds
     * @return
     */
    @Query("from CustomerDetail c where  c.customerId in :customerIds")
    List<CustomerDetail> findAnyByCustomerIds(@Param("customerIds") List<String> customerIds);

    /**
     * 批量启用/禁用会员
     *
     * @param customerStatus
     * @param customerIds
     * @return
     */
    @Modifying
    @Query("update CustomerDetail c set c.customerStatus = :customerStatus, c.forbidReason = :forbidReason where c" +
            ".delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId in :customerIds")
    int updateCustomerState(@Param("customerStatus") CustomerStatus customerStatus, @Param("customerIds")
            List<String> customerIds, @Param("forbidReason") String forbidReason);

    /**
     * 批量删除会员详情
     *
     * @param customerIds
     * @return
     */
    @Modifying
    @Query("update CustomerDetail c set c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId in :customerIds")
    int deleteByCustomerId(@Param("customerIds") List<String> customerIds);

    /**
     * 审核时修改rejectReason
     *
     * @param rejectReason
     * @param customerId
     * @return
     */
    @Modifying
    @Query("update CustomerDetail c set c.rejectReason = :rejectReason where c.customerId = :customerId")
    int updateRejectReason(@Param("rejectReason") String rejectReason, @Param("customerId") String customerId);

    /**
     * 查询业务员下所有客户id
     *
     * @param employeeId
     * @param deleteFlag
     * @return
     */
    @Query("select c.customerId from CustomerDetail c where c.employeeId = :employeeId and c.delFlag = :deleteFlag")
    List<String> queryAllCustomerIdByEmployeeId(@Param("employeeId") String employeeId, @Param("deleteFlag")
            DeleteFlag deleteFlag);

    /**
     * 查询所有客户id
     *
     * @return
     */
    @Query("select c.customerId from CustomerDetail c where c.delFlag = :deleteFlag")
    List<String> queryAllCustomerId(@Param("deleteFlag") DeleteFlag deleteFlag);

    @Query("select c.customerName from CustomerDetail c where  c.customerId = :customerId and c.delFlag = :delFlag ")
    String getCustomerNameByCustomerId(@Param("customerId") String customerId ,@Param("delFlag") DeleteFlag delFlag);

    /**
     * 根据会员id集合查询会员详情ID
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.detail.model.root.CustomerDetailBase(c.customerId,c.customerDetailId) from CustomerDetail c where c.customerId in :customerIds ")
    List<CustomerDetailBase>  listCustomerDetailBaseByCustomerIds(@Param("customerIds") List<String> customerIds);

    /**
     * 修改业务员
     * @param customerId
     * @param employeeId
     * @return
     */
    @Modifying
    @Query("update CustomerDetail set employeeId = ?2 where customerId in (?1)")
    int handoverEmployee(List<String> customerId, String employeeId);


    /**
     * 根据会员id集合查询会员状态和
     * @param customerIds
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.detail.model.root.CustomerStatusBase(c.customerId, c.customerStatus, c.forbidReason) from CustomerDetail c where c.customerId in :customerIds and c.delFlag = :delFlag ")
    List<CustomerStatusBase>  getCustomerStatus(@Param("customerIds") List<String> customerIds, @Param("delFlag") DeleteFlag delFlag);

    /**
     * 分页查询会员详情信息
     * @return
     */
    @Query("select new com.wanmi.sbc.customer.detail.model.root.CustomerDetailInitEs(c.customerId,c.customerName," +
            " c.provinceId, c.cityId, c.areaId, c.streetId,\n" +
            "  c.customerAddress, c.customerStatus, c.contactName, c.contactPhone, \n" +
            "                                c.employeeId, c.isDistributor, c.rejectReason, c.forbidReason, c.createTime) " +
            " from CustomerDetail c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO  ")
    List<CustomerDetailInitEs>  page(Pageable pageable);

    @Query("select new com.wanmi.sbc.customer.detail.model.root.CustomerDetailInitEs(c.customerId,c.customerName," +
            " c.provinceId, c.cityId, c.areaId, c.streetId,\n" +
            "  c.customerAddress, c.customerStatus, c.contactName, c.contactPhone, \n" +
            "                                c.employeeId, c.isDistributor, c.rejectReason, c.forbidReason, c.createTime) " +
            " from CustomerDetail c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId in :idList ")
    List<CustomerDetailInitEs> queryByIdList(@Param("idList") List<String> idList);

    /**
     * 查询会员是否是分销员
     * @param customerId
     * @return
     */
    @Query("select c.isDistributor from CustomerDetail c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerStatus=0 and c.customerId=:customerId")
    DefaultFlag findIsDistributorById(@Param("customerId") String customerId);

    /**
     * 根据会员详情ID更新分销员标识
     *
     * @param customerId 会员id
     */
    @Modifying
    @Query("update CustomerDetail c set c.isDistributor = :isDistributor where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerId = :customerId")
    void updateIsDistributorByCustomerId(@Param("isDistributor") DefaultFlag isDistributor, @Param("customerId") String customerId);

    /**
     * 更新会员添加孩子的标识
     *
     */
    @Modifying
    @Query("update CustomerDetail c set c.isHasChild = 1")
    void updateCustomerHasChildren();

    /**
     * 根据代理商唯一编码查询用户信息
     *
     * @param agentUniqueCode 代理商唯一编码
     */
    @Query("from CustomerDetail c where  c.agentUniqueCode = :agentUniqueCode")
    List<CustomerDetail> findByAgentUniqueCode(@Param("agentUniqueCode") String agentUniqueCode);
}
