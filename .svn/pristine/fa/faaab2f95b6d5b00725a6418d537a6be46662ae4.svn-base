package com.wanmi.sbc.empower.api.request.pay.unioncloud;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 银联云闪付退款接口参数
 *
 * @author xufeng
 */
@Schema
@Data
public class UnionCloudPayRefundRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "业务id(订单或退单号)")
    private String businessId;

    @Schema(description = "申请价格")
    private BigDecimal applyPrice;

    @Schema(description = "交易流水号")
    private String tradeNo;

    @Schema(description = "身份标识")
    private String apiKey;

    @Schema(description = "boss后台接口地址")
    private String bossBackUrl;

    @Schema(description = "是否需要回调")
    private boolean needCallback = true;

}
