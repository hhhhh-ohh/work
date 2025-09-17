package com.wanmi.ares.marketing.coupon.dao;

import com.wanmi.ares.request.coupon.CouponEffectRequest;
import java.util.List;

/**
 * DAO公共基类，由MybatisGenerator自动生成请勿修改
 * @param <Model> The Model Class 这里是泛型不是Model类
 */
public interface EffectMapper<Model> {


    List<Model> selectList(CouponEffectRequest request);

}