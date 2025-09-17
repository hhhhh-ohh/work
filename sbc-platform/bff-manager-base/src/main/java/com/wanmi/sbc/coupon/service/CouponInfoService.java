package com.wanmi.sbc.coupon.service;

import com.wanmi.sbc.elastic.api.request.coupon.EsCouponInfoPageRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CouponInfoService
 * @Description TODO
 * @Author qiyuanzhao
 * @Date 2022/3/25 12:50
 * @Version 1.0
 **/
@Slf4j
@Service
public class CouponInfoService {

    public void populateRequest(EsCouponInfoPageRequest couponInfoQueryRequest) {
        List<CouponType> couponTypeList = new ArrayList<>();
        if (couponInfoQueryRequest.getCouponType() == null) {
            couponTypeList.add(CouponType.GENERAL_VOUCHERS);
        } else {
            couponTypeList.add(couponInfoQueryRequest.getCouponType());
        }
        couponInfoQueryRequest.setCouponTypes(couponTypeList);
    }



}
