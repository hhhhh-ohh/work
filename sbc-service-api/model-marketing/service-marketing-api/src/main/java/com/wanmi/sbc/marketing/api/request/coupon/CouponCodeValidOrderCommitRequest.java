package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: gaomuwei
 * @Date: Created In 1:23 PM 2018/9/28
 * @Description: 查询优惠券码列表请求
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeValidOrderCommitRequest extends BaseQueryRequest  {

    private static final long serialVersionUID = -1924077031934211666L;

    /**
     * 领取人id
     */
    @Schema(description = "领取人id")
    private String customerId;

    /**
     * 用户选取的优惠券列表
     */
    private List<String> couponCodeIds;
}
