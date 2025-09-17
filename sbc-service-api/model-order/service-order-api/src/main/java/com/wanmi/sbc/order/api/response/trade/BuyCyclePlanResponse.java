package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.CycleDeliveryPlanVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xuyunpeng
 * @className BuyCyclePlanResponse
 * @description
 * @date 2022/10/19 5:08 PM
 **/
@Data
@Builder
@Schema
@NoArgsConstructor
@AllArgsConstructor
public class BuyCyclePlanResponse extends BasicResponse {
    private static final long serialVersionUID = 3769989691231998956L;

    /**
     * 送达计划
     */
    @Schema(description = "送达计划")
    private List<CycleDeliveryPlanVO> plans;
}
