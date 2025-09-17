package com.wanmi.sbc.customer.payingmemberrecommendrel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberrecommendrel.model.root.PayingMemberRecommendRel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>推荐商品与付费会员等级关联表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:51
 */
@Repository
public interface PayingMemberRecommendRelRepository extends JpaRepository<PayingMemberRecommendRel, Long>,
        JpaSpecificationExecutor<PayingMemberRecommendRel> {

    /**
     * 单个删除推荐商品与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecommendRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除推荐商品与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRecommendRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个推荐商品与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberRecommendRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);


    /**
     * 根据等级id删除付费设置表
     * @author zhanghao
     */
    @Modifying
    @Query(value = "delete from paying_member_recommend_rel where level_id = ?1", nativeQuery = true)
    void deleteByLevelId(Integer levelId);
}
