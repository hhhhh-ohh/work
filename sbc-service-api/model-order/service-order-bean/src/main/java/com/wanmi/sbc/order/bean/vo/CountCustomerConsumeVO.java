package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema
@Data
public class CountCustomerConsumeVO extends BasicResponse {

    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "客户姓名")
    private String customerName;

    @Schema(description = "客户头像")
    private String headImg;

    @Schema(description = "总计消费金额")
    private BigDecimal amount;

    @Schema(description = "订单数量")
    private Long orderNum;

    @Schema(description = "订单id")
    private String orderId ;

    @Schema(description = "注册时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime registerTime;

    @Schema(description = "首单时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime firstOrderTime;

}
