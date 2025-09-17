package com.wanmi.sbc.vas.api.response.sellplatform.order;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * @description SellPlatformOrderResponse
 * @author  wur
 * @date: 2022/4/18 20:00
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellPlatformOrderResponse implements Serializable {
    private static final long serialVersionUID = 9035647807787101793L;

    /**
     *  代销平台订单Id
     */
    @Schema(description = "第三方")
    public String sellPlatformOrderId;

    @Schema(description = "视频号计算金额 需要和订单比对")
    private Integer finalPrice;
}
