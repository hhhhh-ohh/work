package com.wanmi.sbc.marketing.api.request.coupon;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCacheListForGoodsGoodInfoListRequest extends BaseRequest {

    @Schema(description = "商品信息列表")
    private List<GoodsInfoVO> goodsInfoList;

    /**
     * 会员id
     */
    @Schema(description = "客户信息")
    private CustomerVO customer;

    @Schema(description = "门店ID")
    private Long storeId;

    @Schema(description = "营销类型")
    private PluginType pluginType;
}
