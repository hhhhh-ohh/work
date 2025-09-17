package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 根据优惠券活动ID集合新增ES数据
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsCouponActivityAddListByActivityIdRequest extends BaseRequest {

    @Schema(description = "活动ID集合")
    private List<String> activityIdList;
}
