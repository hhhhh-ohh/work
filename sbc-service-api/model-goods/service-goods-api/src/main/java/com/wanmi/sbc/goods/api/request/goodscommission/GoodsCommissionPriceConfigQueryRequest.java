package com.wanmi.sbc.goods.api.request.goodscommission;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.enums.CommissionPriceTargetType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

/**
 * @description   代销配置查询
 * @author  wur
 * @date: 2021/9/10 15:02
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommissionPriceConfigQueryRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 目标类型 ：0.类目 1.SKU
     */
    @Schema(description = "目标类型 ：0.类目 1.SKU")
    @NotNull
    private CommissionPriceTargetType targetType;

    /**
     * 目标主键
     */
    @Schema(description = "目标主键")
    private String targetId;

    /**
     * 目标主键
     */
    @Schema(description = "目标主键集合")
    private List<String> targetIdList;

    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private EnableStatus enableStatus;

}