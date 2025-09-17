package com.wanmi.sbc.elastic.activitycoupon.repository;


import com.wanmi.sbc.elastic.activitycoupon.root.EsActivityCoupon;
import com.wanmi.sbc.elastic.activitycoupon.root.EsCouponScope;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * 优惠券活动范围ES持久层
 */
@Repository
public interface EsActivityCouponRepository extends ElasticsearchRepository<EsActivityCoupon,String> {


    void deleteByActivityId(String activityId);


}