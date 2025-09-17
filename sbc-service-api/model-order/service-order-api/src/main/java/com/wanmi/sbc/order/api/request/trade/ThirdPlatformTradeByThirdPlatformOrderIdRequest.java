package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.ThirdPlatformType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;

import lombok.*;

/**
 * <p>根据第三方订单号获取订单集合参数</p>
 *
 * @author kkq
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class ThirdPlatformTradeByThirdPlatformOrderIdRequest extends BaseRequest {
    private static final long serialVersionUID = -9143745241530996245L;

    /**
     * 第三方平台订单id
     */
    @NotEmpty
    @Schema(description = "第三方平台订单id")
    private String thirdPlatformOrderId;

    /**
     * 第三方平台订单类型
     */
    @Schema(description = "第三方平台订单类型")
    private ThirdPlatformType thirdPlatformType;
}
