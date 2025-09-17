package com.wanmi.sbc.trade.request;

import static com.wanmi.sbc.common.util.ValidateUtil.NULL_EX_MESSAGE;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/**
 * 支付请求参数
 * Created by wc on 2021/3/8.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "支付请求对象")
public class PayCommonRequest extends BaseRequest {

    private static final long serialVersionUID = -9011690769518722994L;
    /**
     * 订单id
     */
    @Schema(description = "订单id，用于单笔订单支付场景，单笔支付时不能为空", required = true)
    private String tid;

    /**
     * 父订单id
     */
    @Schema(description = "父订单id，用于多笔订单合并支付场景，合并支付时不能为空", required = true)
    private String parentTid;

    /**
     * 支付渠道id
     */
    @NotNull
    @Schema(description = "支付渠道id", required = true)
    private Long channelItemId;

    /**
     * 支付密码
     */
    @Schema(description = "支付密码", required = true)
    @NotNull
    private String payPassword;

    /**
     * 终端类型
     */
    @Enumerated
    @Schema(description = "终端类型",required = true, example="0:pc 1:h5 2:app")
    @NotNull
    private TerminalType terminal = TerminalType.PC;


    @Override
    public void checkParam() {
        Validate.isTrue(StringUtils.isNotEmpty(tid) || StringUtils.isNotEmpty(parentTid), NULL_EX_MESSAGE,
                "tid | parentTid");
    }
}
