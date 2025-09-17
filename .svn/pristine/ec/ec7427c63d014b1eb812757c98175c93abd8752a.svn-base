package com.wanmi.sbc.customer.payingmemberstorerel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberstorerel.model.root.PayingMemberStoreRel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>商家与付费会员等级关联表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 13:41:04
 */
@Repository
public interface PayingMemberStoreRelRepository extends JpaRepository<PayingMemberStoreRel, Long>,
        JpaSpecificationExecutor<PayingMemberStoreRel> {

    /**
     * 单个删除商家与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberStoreRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);

    /**
     * 批量删除商家与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberStoreRel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    void deleteByIdList(List<Long> idList);

    /**
     * 查询单个商家与付费会员等级关联表
     * @author zhanghao
     */
    Optional<PayingMemberStoreRel> findByIdAndStoreIdAndDelFlag(Long id, Long storeId, DeleteFlag delFlag);


    /**
     * 根据等级id删除商家与付费会员等级关联表
     * @author zhanghao
     */
    @Modifying
    @Query(value = "delete from paying_member_store_rel where level_id = ?1", nativeQuery = true)
    void deleteByLevelId(Integer levelId);

}
