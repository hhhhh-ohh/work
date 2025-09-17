package com.wanmi.sbc.elastic.api.request.coupon;

import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.elastic.api.request.base.EsBaseQueryRequest;
import com.wanmi.sbc.marketing.bean.enums.CouponActivityType;
import com.wanmi.sbc.marketing.bean.enums.CouponMarketingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author EDZ
 * @className EsMagicCouponActivityPageRequest
 * @description 魔方优惠券活动列表查询
 * @date 2021/6/2 16:23
 */
@Schema
@Data
public class EsMagicCouponActivityPageRequest extends EsBaseQueryRequest {

    @Schema(description = "店铺id")
    private Long storeId;

    @Schema(description = "是否平台 0店铺 1平台")
    private DefaultFlag platformFlag;

    @Schema(description = "优惠券活动名称")
    private String activityName;

    /**
     * 活动所属平台（目前只有门店用到）
     */
    @Schema(description = "活动所属平台")
    private PluginType pluginType;

    /**
     * 活动类型
     */
    @Schema(description = "活动类型")
    private CouponActivityType couponActivityType;
}
