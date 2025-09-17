package com.wanmi.sbc.goods.api.request.goodscommission;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.CommissionPriceTargetVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;

import java.util.List;

/**
 * @description   代销智能设价加价比例设置
 * @author  wur
 * @date: 2021/9/10 15:02
 **/
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsCommissionPriceConfigUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "目标类型")
    @NotEmpty
    @Valid
    private List<CommissionPriceTargetVO> commissionPriceTargetVOList;

}