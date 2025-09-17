package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wur
 * @className CommissionPriceTargetVO
 * @description 代销商品加价配置实体
 * @date 2021/9/10 15:25
 **/
@Schema
@Data
public class CommissionPriceTargetVO extends BasicResponse {

    private static final long serialVersionUID = -5340314199677396723L;

    @Schema(description = "目标类型 0：类目 1：SKU")
    @NotNull
    private CommissionPriceTargetType targetType;

    @Schema(description = "目标唯一标识")
    @NotNull
    private String targetId;

    @Schema(description = "加价比例")
    @NotNull
    @Min(value = 0, message = "参数错误")
    @Max(value = 300, message = "参数错误")
    private BigDecimal addRate;

    @Schema(description = "是否启用：0：未启用；1：启用")
    @NotNull
    private EnableStatus enableStatus;

}