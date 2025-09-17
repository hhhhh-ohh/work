package com.wanmi.sbc.trade.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

/**
 * 微信扫码支付请求参数
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema
public class WeiXinPayRequest extends BaseRequest {

    private static final long serialVersionUID = -2855598681583948807L;

    /**
     * 订单id
     */
    @Schema(description = "订单id，用于单笔订单支付场景，单笔支付时不能为空", contentSchema = String.class, required = true)
    private String tid;

    /**
     * 父订单id
     */
    @Schema(description = "父订单id，用于多笔订单合并支付场景，合并支付时不能为空", contentSchema = String.class, required = true)
    private String parentTid;

    /**
     * 授信还款id
     */
    @Schema(description = "授信还款单，用于授信还款支付场景，授信还款时不能为空", contentSchema = String.class, required = true)
    private String repayOrderCode;

    @Override
    public void checkParam() {
        Validate.isTrue(StringUtils.isNotEmpty(tid) || StringUtils.isNotEmpty(parentTid) || StringUtils.isNotEmpty(repayOrderCode), NULL_EX_MESSAGE,
                "tid | parentTid | repayOrderCode");
    }
}
