package com.wanmi.sbc.marketing.api.request.electroniccoupon;

import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author xuyunpeng
 * @className ElectronicGoodsPageRequest
 * @description
 * @date 2022/1/27 3:31 下午
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectronicGoodsPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String skuName;

    /**
     * 卡券名称
     */
    @Schema(description = "卡券名称")
    private String couponName;

    /**
     * 销售状态
     */
    @Schema(description = "销售状态 0、未过销售期 1、已过销售期")
    private Integer saleType;
}
