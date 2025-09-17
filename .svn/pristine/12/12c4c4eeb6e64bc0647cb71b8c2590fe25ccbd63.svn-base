package com.wanmi.sbc.elastic.activitycoupon.repository;


import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 优惠券活动范围ES持久层
 */
@Repository
public interface EsCouponScopeRepository extends ElasticsearchRepository<EsCouponScope,String> {


    void deleteByActivityId(String activityId);



}