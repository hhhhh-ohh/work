package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheListForGoodsDetailRequest extends BaseRequest {

    /**
     * 商品信息ID
     */
    @Schema(description = "商品信息ID")
    private String goodsInfoId;

    /**
     * 会员id
     */
    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "店铺Id")
    private Long storeId;

    @Schema(description = "营销类型")
    private PluginType pluginType;

}
