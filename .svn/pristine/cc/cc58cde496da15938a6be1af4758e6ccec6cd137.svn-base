package com.wanmi.sbc.order.api.request.thirdplatformtrade;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: daiyitian
 * @Description: 重新同步订单
 * @Date: 2020-08-20 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ThirdPlatformTradeReAddRequest extends BaseRequest {

    /**
     * 批量处理-订单业务id
     */
    @Schema(description = "批量处理-订单业务id")
    private List<String> tradeIds;
}
