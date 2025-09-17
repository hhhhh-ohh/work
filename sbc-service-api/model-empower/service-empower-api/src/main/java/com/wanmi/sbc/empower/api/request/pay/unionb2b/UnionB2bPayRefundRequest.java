package com.wanmi.sbc.empower.api.request.pay.unionb2b;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 银联B2B退款接口参数
 *
 * @author xufeng
 */
@Schema
@Data
public class UnionB2bPayRefundRequest {

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

    @Schema(description = "原始交易时间")
    private LocalDateTime createTime;

    @Schema(description = "原始交易订单号")
    private String oriBusinessId;

}
