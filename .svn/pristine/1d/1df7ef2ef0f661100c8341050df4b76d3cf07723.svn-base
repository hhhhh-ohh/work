package com.wanmi.sbc.setting.pagemanage.repository;


import com.wanmi.sbc.setting.pagemanage.model.root.PageInfoExtend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * 页面信息扩展记录
 * Created by dyt on 2020/4/17.
 */
public interface PageInfoExtendRepository extends MongoRepository<PageInfoExtend, String> {

    /**
     * 根据券ID和活动ID查询推广详情
     * @param couponId 券ID
     * @param activityId 活动ID
     * @return
     */
    Optional<List<PageInfoExtend>> findByCouponIdAndActivityId(String couponId, String activityId);

    /**
     * 根据活动id和活动类型查询推广详情
     * @param activityId
     * @param marketingType
     * @return
     */
    Optional<PageInfoExtend> findByActivityIdAndMarketingType(String activityId ,Integer marketingType);


}

