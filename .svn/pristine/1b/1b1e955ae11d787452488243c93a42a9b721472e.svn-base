package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wur
 * @className GoodsCommissionPriceConfigVO
 * @description  代销智能设价加价比例
 * @date 2021/9/10 16:05
 **/
@Schema
@Data
public class GoodsCommissionPriceConfigVO extends BasicResponse {

    private static final long serialVersionUID = -5340314199677396723L;

    @Schema(description = "唯一主键")
    private String id;

    @Schema(description = "目标类型 0.类目 1.SKU")
    private CommissionPriceTargetType targetType;

    @Schema(description = "目标唯一标识")
    private String targetId;

    @Schema(description = "加价比例")
    private BigDecimal addRate;

    @Schema(description = "是否启用：0：未启用；1：启用")
    private EnableStatus enableStatus;
}