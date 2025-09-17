package com.wanmi.sbc.customer.payingmemberprice.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberprice.model.root.PayingMemberPrice;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>付费设置表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:40:30
 */
@Repository
public interface PayingMemberPriceRepository extends JpaRepository<PayingMemberPrice, Integer>,
        JpaSpecificationExecutor<PayingMemberPrice> {

    /**
     * 单个删除付费设置表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberPrice set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where priceId = ?1")
    void deleteById(Integer priceId);

    /**
     * 批量删除付费设置表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberPrice set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where priceId in ?1")
    void deleteByIdList(List<Integer> priceIdList);

    /**
     * 查询单个付费设置表
     * @author zhanghao
     */
    Optional<PayingMemberPrice> findByPriceIdAndDelFlag(Integer id, DeleteFlag delFlag);


    /**
     * 根据等级id删除付费设置表
     * @author zhanghao
     */
    @Modifying
    @Query(value = "delete from paying_member_price where level_id = ?1", nativeQuery = true)
    void deleteByLevelId(Integer levelId);

}
