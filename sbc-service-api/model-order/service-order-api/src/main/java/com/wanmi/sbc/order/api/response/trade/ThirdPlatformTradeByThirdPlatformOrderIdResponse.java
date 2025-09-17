package com.wanmi.sbc.order.api.response.trade;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.order.bean.vo.ThirdPlatformTradeVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author kkq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ThirdPlatformTradeByThirdPlatformOrderIdResponse extends BasicResponse {

    /**
     * 第三方订单
     */
    @Schema(description = "第三方订单")
    private ThirdPlatformTradeVO thirdPlatformTradeVO;
}
