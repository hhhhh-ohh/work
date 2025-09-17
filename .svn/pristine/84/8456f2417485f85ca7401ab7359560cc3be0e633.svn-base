package com.wanmi.sbc.goods.flashpromotionactivity.repository;

import com.wanmi.sbc.goods.flashpromotionactivity.model.root.FlashPromotionActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>抢购商品表DAO</p>
 *
 * @author xufeng
 * @date 2022-02-14 14:54:31
 */
@Repository
public interface FlashPromotionActivityRepository extends JpaRepository<FlashPromotionActivity, String>,
        JpaSpecificationExecutor<FlashPromotionActivity> {

    /**
     * 查询限时购活动
     * @author xufeng
     */
    @Query("from FlashPromotionActivity where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    FlashPromotionActivity findByActivityId(@Param("activityId") String activityId);

    /**
     * 限时购启动暂停活动
     *
     * @author xufeng
     */
    @Modifying
    @Query("update FlashPromotionActivity set status = :status where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    int modifyByActivityId(@Param("status") int status, @Param("activityId") String activityId);

    /**
     * 删除限时抢购活动
     *
     * @author xufeng
     */
    @Modifying
    @Query("update FlashPromotionActivity set delFlag = com.wanmi.sbc.common.enums.DeleteFlag.YES where delFlag = com.wanmi.sbc.common.enums.DeleteFlag.NO and activityId = :activityId")
    int deleteByActivityId(@Param("activityId") String activityId);
}
