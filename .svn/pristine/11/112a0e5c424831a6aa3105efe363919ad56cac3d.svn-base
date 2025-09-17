package com.wanmi.sbc.marketing.api.response.coupon;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class CouponCodeValidOrderCommitResponse extends BasicResponse {

    private static final long serialVersionUID = 7318856110920575849L;

    /**
     * 验证信息
     */
    @Schema(description = "验证信息")
    private String validInfo;

    /**
     * 过期的优惠券
     */
    private  List<String> invalidCodeIds;
}
