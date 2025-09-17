package com.wanmi.sbc.trade.request;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 支付请求参数
 * Created by sunkun on 2017/8/10.
 */
@Schema
@Data
public class PayMobileRequest extends BaseRequest {

    private static final long serialVersionUID = -9011690769518722995L;

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
     * 支付成功后的前端回调url
     */
    @Schema(description = "支付成功后的前端回调url")
    @NotBlank
    private String successUrl;

    /**
     * 支付渠道id
     */
    @Schema(description = "支付渠道id")
    @NotNull
    private Long channelItemId;

    /**
     * 微信支付时必传，付款用户在商户 appid 下的唯一标识。
     */
    @Schema(description = "付款用户在商户 appid 下的唯一标识")
    private String openId;

    @Schema(description = "唤起支付宝时使用")
    private String origin;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
    @NotNull
    private Long storeId;

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
