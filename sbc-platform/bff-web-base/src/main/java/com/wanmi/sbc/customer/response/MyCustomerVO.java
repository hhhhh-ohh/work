package com.wanmi.sbc.customer.response;

import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Schema
@Data
public class MyCustomerVO extends DistributionInviteNewForPageVO {

    @Schema(description = "客户id")
    private String customerId;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "总计消费金额")
    private BigDecimal amount;

    @Schema(description = "订单数量")
    private Long orderNum;
}
