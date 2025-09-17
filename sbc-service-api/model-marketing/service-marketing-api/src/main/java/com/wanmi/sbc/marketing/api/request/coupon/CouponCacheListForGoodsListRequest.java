package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-23 15:31
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponCacheListForGoodsListRequest extends BaseRequest {

    /**
     * 商品信息id 列表
     */
    @Schema(description = "商品信息id列表")
    private List<String> goodsInfoIds;

    /**
     * 会员id
     */
    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 优惠券类型 0 普通优惠券  2 O2O运费券
     */
    @Schema(description = "优惠券类型")
    private PluginType pluginType;

}
