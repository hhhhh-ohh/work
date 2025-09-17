package com.wanmi.sbc.order.api.request.trade;

import com.wanmi.sbc.account.bean.enums.CreditRepayTypeEnum;
import com.wanmi.sbc.common.base.BaseQueryRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author wc
 * @date 2021/9/12 14:11
 * @description <p> 授信还款处理 </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema
public class CreditCallBackRequest extends BaseQueryRequest implements Serializable {
    private static final long serialVersionUID = 2203336020183346335L;

    /**
     * 还款单号
     */
    @Schema(description = "还款单号")
    private String repayOrderCode;

    /**
     * 订单ids
     */
    private List<String> ids;

    /**
     * 账户Id
     */
    @Schema(description = "账户Id")
    private String userId;

    /**
     * 还款方式 0银联，1微信，2支付宝
     */
    @Schema(description = "还款方式 0银联，1微信，2支付宝")
    private CreditRepayTypeEnum repayType;

}
