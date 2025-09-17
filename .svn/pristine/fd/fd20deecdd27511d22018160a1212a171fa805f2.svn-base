package com.wanmi.sbc.customer.payingmemberlevel.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.payingmemberlevel.model.root.PayingMemberLevel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>付费会员等级表DAO</p>
 * @author zhanghao
 * @date 2022-05-13 11:42:42
 */
@Repository
public interface PayingMemberLevelRepository extends JpaRepository<PayingMemberLevel, Integer>,
        JpaSpecificationExecutor<PayingMemberLevel> {

    /**
     * 单个删除付费会员等级表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberLevel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where levelId = ?1")
    void deleteById(Integer levelId);

    /**
     * 批量删除付费会员等级表
     * @author zhanghao
     */
    @Modifying
    @Query("update PayingMemberLevel set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where levelId in ?1")
    void deleteByIdList(List<Integer> levelIdList);

    /**
     * 查询单个付费会员等级表
     * @author zhanghao
     */
    Optional<PayingMemberLevel> findByLevelIdAndDelFlag(Integer id, DeleteFlag delFlag);

    /**
     * 批量调整会员等级状态
     * @param levelState
     */
    @Modifying
    @Query("update PayingMemberLevel set levelState = ?1 where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO")
    void modifyLevelStatus(Integer levelState);

    /**
     * 根据等级查询
     * @param delFlag
     * @param levelIds
     * @return
     */
    List<PayingMemberLevel> findAllByDelFlagAndLevelIdIn(DeleteFlag delFlag, List<Integer> levelIds);

    /**
     * 查询有效的等级
     * @return
     */
    @Query("from PayingMemberLevel where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and levelState = 0")
    List<PayingMemberLevel> findActiveLevel();

}
