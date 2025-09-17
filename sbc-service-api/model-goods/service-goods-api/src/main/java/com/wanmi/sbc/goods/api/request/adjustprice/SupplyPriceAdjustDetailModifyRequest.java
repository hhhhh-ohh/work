package com.wanmi.sbc.goods.api.request.adjustprice;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.ValidateUtil;
import com.wanmi.sbc.goods.api.request.GoodsBaseRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SupplyPriceAdjustDetailModifyRequest extends GoodsBaseRequest {

    private static final long serialVersionUID = -2987733268661928410L;
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
    @NonNull
    private Long adjustDetailId;

    /**
     * 更新后的供货价
     */
    @Schema(description = "更新后的供货价")
    private BigDecimal supplyPrice;

    @Override
    public void checkParam() {
        if (Objects.isNull(this.supplyPrice)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030117, new Object[]{"供货价！"});
        }
        if (!ValidateUtil.isNum(supplyPrice+"") && !ValidateUtil.isFloatNum(supplyPrice+"")) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{"供货价", "0和正数，允许两位小数，不超过9999999.99！"});
        }
        if (supplyPrice.compareTo(new BigDecimal("9999999.99")) > 0) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030118, new Object[]{"供货价", "0和正数，允许两位小数，不超过9999999.99！"});
        }
    }
}
