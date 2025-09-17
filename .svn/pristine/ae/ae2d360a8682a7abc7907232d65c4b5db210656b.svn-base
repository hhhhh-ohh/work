package com.wanmi.sbc.customer.level.repository;

import com.wanmi.sbc.customer.level.model.root.CustomerLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 客户等级数据源
 * Created by CHENLI on 2017/4/14.
 */
@Repository
public interface CustomerLevelRepository extends JpaRepository<CustomerLevel, Long>,
        JpaSpecificationExecutor<CustomerLevel> {
    /**
     * 列表查询所有平台客户等级 按成长值降序排序
     *
     * @return
     */
    @Query("from CustomerLevel l where l.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO order by l.growthValue desc")
    List<CustomerLevel> listLevelOrderByValueDesc();

    /**
     * 列表查询所有平台客户等级 按id升序排序
     *
     * @return
     */
    @Query("from CustomerLevel l where l.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO order by l.customerLevelId asc ")
    List<CustomerLevel> listLevelOrderByIdAsc();

    /**
     * 通过ID查询客户级别信息
     *
     * @param customerLevelId
     * @return
     */
    @Query("from CustomerLevel l where l.customerLevelId = ?1 and l.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    CustomerLevel findBycustomerLevelId(Long customerLevelId);

    /**
     * 通过客户ID查询客户级别信息
     *
     * @param customerId
     * @return
     */
    @Query(value="select l.* from customer_level l left join customer c on l.customer_level_id = c.customer_level_id " +
            "where c.customer_id = ?1 and l.del_flag = 0", nativeQuery = true)
    CustomerLevel findByCustomerId(String customerId);

    /**
     * 修改会员等级
     *
     * @param customerLevel
     * @return
     */
    @Modifying
    @Query("update CustomerLevel l set l.customerLevelName = :#{#customerLevel.customerLevelName}," +
            "l.customerLevelDiscount = :#{#customerLevel.customerLevelDiscount}, " +
            "l.growthValue = :#{#customerLevel.growthValue}, " +
            "l.rankBadgeImg = :#{#customerLevel.rankBadgeImg}," +
            "l.updatePerson = :#{#customerLevel.updatePerson}," +
            "l.updateTime = :#{#customerLevel.updateTime} " +
            "where l.customerLevelId = :#{#customerLevel.customerLevelId}")
    int updateByCustomerLevelId(@Param("customerLevel") CustomerLevel customerLevel);

    /**
     * 删除会员等级
     */
    @Modifying
    @Query("update CustomerLevel l set l.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where l.customerLevelId = :customerLevelId")
    void deleteCustomerLevel(@Param("customerLevelId") Long customerLevelId);

    /**
     * 批量查询会员等级
     *
     * @param customerLevelIds
     * @return
     */
    @Query("from CustomerLevel c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and c.customerLevelId in (:customerLevelIds)")
    List<CustomerLevel> findByCustomerLevelIds(@Param("customerLevelIds") List<Long> customerLevelIds);

    /**
     * 查询会员等级列表，按照创建时间正序
     *
     * @return
     */
    @Query("from CustomerLevel c where c.delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO order by c.createTime asc")
    List<CustomerLevel> findByDelFlagOrderByCreateTimeAsc();
}
