package com.wanmi.sbc.trade.request;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 支付请求参数
 * Created by sunkun on 2017/8/10.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "支付请求对象")
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
    @Schema(description = "支付成功后的前端回调url", contentSchema = String.class)
    private String successUrl;

    /**
     * 支付渠道id
     */
    @NotNull
    @Schema(description = "支付渠道id", contentSchema = Long.class, required = true)
    private Long channelItemId;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码", contentSchema = String.class, required = true)
    private String payPassword;

    /**
     * 微信支付时必传，付款用户在商户 appid 下的唯一标识。
     */
    @Schema(description = "微信支付时必传，付款用户在商户 appid 下的唯一标识", contentSchema = String.class)
    private String openId;


    @Schema(description = "唤起支付宝时使用")
    private String origin;

    @Schema(description = "终端类型",required = true, example="0:pc 1:h5 2:app")
    private TerminalType terminal = TerminalType.H5;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id-boss端取默认值")
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
