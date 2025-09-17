package com.wanmi.sbc.marketing.drawprize.repository;

import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.marketing.drawprize.model.root.DrawPrize;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>抽奖活动奖品表DAO</p>
 * @author wwc
 * @date 2021-04-12 16:54:59
 */
@Repository
public interface DrawPrizeRepository extends JpaRepository<DrawPrize, Long>,
        JpaSpecificationExecutor<DrawPrize> {

    /**
     * 单个删除抽奖活动奖品表
     * @author wwc
     */
    @Modifying
    @Query("update DrawPrize set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id = ?1")
    void deleteById(Long id);


    /**
     * 扣除奖品库存
     * @author wwc
     */
    @Modifying
    @Query("update DrawPrize set prizeNum = prizeNum -1  where id = ?1")
    void subPrizeStock(Long id);


    /**
     * 批量删除抽奖活动奖品表
     * @author wwc
     */
    @Modifying
    @Query("update DrawPrize set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where id in ?1")
    int deleteByIdList(List<Long> idList);

    /**
     * 查询奖品
     *
     * @author wwc
     */
    List<DrawPrize> findAllByActivityIdAndDelFlag(Long activityId, DeleteFlag deleteFlag);
}
