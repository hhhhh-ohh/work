package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 根据优惠券范围id查询优惠券商品作用范列表
 * @Author: daiyitian
 * @Date: Created In 上午9:27 2018/11/24
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheListRequest extends BaseRequest {

    private static final long serialVersionUID = -2825237958435093165L;

    /**
     * 优惠券活动id集合
     */
    private List<String> couponActivityIds;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime queryStartTime;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime queryEndTime;


    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime createTimeStart;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime createTimeEnd;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime updateTimeStart;

    /**
     * 查询该时间段内开始的活动
     */
    private LocalDateTime updateTimeEnd;

    /***
     * 是否O2O
     */
    private Boolean isO2o;

    /***
     * pageNum
     */
    private Integer pageNum;


    /***
     * pageSize
     */
    private Integer pageSize;

}
