package com.wanmi.sbc.marketing.api.request.newcomerpurchasecoupon;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * @author xuyunpeng
 * @className NewcomerPurchaseXsiteRequest
 * @description
 * @date 2022/8/24 1:47 PM
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewcomerPurchaseXsiteRequest extends BaseRequest {
    private static final long serialVersionUID = -1240614354496152829L;

    /**
     * 优惠券ids
     */
    @Schema(description = "优惠券ids")
    private List<String> couponIds;

    /**
     * 商品ids
     */
    @Schema(description = "商品ids")
    private List<String> goodsInfoIds;
}
