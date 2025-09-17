package com.wanmi.sbc.customer.payingmemberrightsrel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberrightsrel.model.root.PayingMemberRightsRel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>权益与付费会员等级关联表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:21
 */
@Repository
public interface PayingMemberRightsRelRepository extends JpaRepository<PayingMemberRightsRel, Long>,
        JpaSpecificationExecutor<PayingMemberRightsRel> {

    /**
     * 单个删除权益与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRightsRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除权益与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberRightsRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个权益与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberRightsRel> findByIdAndDelFlag(Long id, DeleteFlag delFlag);


    /**
     * 根据等级id删除权益与付费会员等级关联
     * @author zhanghao
     */
    @Modifying
    @Query(value = "delete from paying_member_rights_rel where level_id = ?1", nativeQuery = true)
    void deleteByLevelId(Integer levelId);

    List<PayingMemberRightsRel> findAllByRightsIdAndDelFlag(Integer rightId, DeleteFlag deleteFlag);

}
