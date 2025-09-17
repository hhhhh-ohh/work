package com.wanmi.sbc.goods.api.request.goodscommission;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.goods.bean.vo.CommissionPriceTargetVO;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class GoodsCommissionPriceConfigStatusUpdateRequest extends BaseRequest {
    private static final long serialVersionUID = 1L;

    @Schema(description = "目标类型")
    private String id;

    @Schema(description = "是否启用：0：未启用；1：启用")
    private EnableStatus enableStatus;

}