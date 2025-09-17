package com.wanmi.sbc.customer.payingmemberdiscountrel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberdiscountrel.model.root.PayingMemberDiscountRel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>折扣商品与付费会员等级关联表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:36
 */
@Repository
public interface PayingMemberDiscountRelRepository extends JpaRepository<PayingMemberDiscountRel, Long>,
        JpaSpecificationExecutor<PayingMemberDiscountRel> {

    /**
     * 单个删除折扣商品与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberDiscountRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除折扣商品与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberDiscountRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个折扣商品与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberDiscountRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);


    /**
     * 根据等级id删除付费设置表
     * @author zhanghao
     */
    @Modifying
    @Query(value = "delete from paying_member_discount_rel where level_id = ?1", nativeQuery = true)
    void deleteByLevelId(Integer levelId);

}
