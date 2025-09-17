package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>市场价调价详情编辑请求参数</p>
 * Created by of628-wenzhi on 2020-12-15-3:09 下午.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class MarketingPriceAdjustDetailModifyRequest extends GoodsBaseRequest {
    private static final long serialVersionUID = 868467459578972778L;

    /**
     * 调价单号
     */
    @Schema(description = "调价单号")
    @NotBlank
    private String adjustNo;

    /**
     * 调价明细ID
     */
    @Schema(description = "调价明细ID")
    @NotNull
    private Long adjustDetailId;

    /**
     * 更新后的市场价
     */
    @Schema(description = "更新后的市场价")
    private BigDecimal marketingPrice;

    /**
     * Sku ID
     */
    @Schema(description = "Sku ID")
    @NotBlank
    private String goodsInfoId;

    @Override
    public void checkParam() {
        BigDecimal marketingPrice = this.marketingPrice;
        if (Objects.isNull(marketingPrice)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030117, new Object[]{"市场价！"});
        }
        if (!ValidateUtil.isNum(marketingPrice + "") && !ValidateUtil.isFloatNum(marketingPrice + "")) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{"市场价", "0和正数，允许两位小数，不超过9999999.99！"});
        }
        if (marketingPrice.compareTo(new BigDecimal("9999999.99")) > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{"市场价", "0和正数，允许两位小数，不超过9999999.99！"});
        }
    }
}
