package com.wanmi.sbc.order.api.request.thirdplatformtrade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: daiyitian
 * @Description:
 * @Date: 2020-08-20 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ThirdPlatformTradeAddRequest extends BaseRequest {

    /**
     * 订单业务id
     */
    @Schema(description = "订单业务id")
    private String businessId;

    @Schema(description = "第三方平台类型")
    private ThirdPlatformType thirdPlatformType;
}
