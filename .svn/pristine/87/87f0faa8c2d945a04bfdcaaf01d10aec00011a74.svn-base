package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 张文昌
 * @description 拉卡拉可用支付项
 * @date 2024/3/26 18:47
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LakalaCasherPayItemVO implements Serializable {

    private static final long serialVersionUID = 8256591345170228032L;

    @Schema(description = "支付宝可用状态")
    @JsonProperty("alipayStatus")
    private DefaultFlag alipayStatus = DefaultFlag.NO;

    @Schema(description = "微信可用状态")
    @JsonProperty("wechatStatus")
    private DefaultFlag wechatStatus = DefaultFlag.NO;

    @Schema(description = "银联云闪付可用状态")
    @JsonProperty("unionStatus")
    private DefaultFlag unionStatus = DefaultFlag.NO;

    @Schema(description = "网银支付可用状态")
    @JsonProperty("eBankStatus")
    private DefaultFlag eBankStatus = DefaultFlag.NO;

    @Schema(description = "银联支付可用状态")
    @JsonProperty("unionCCStatus")
    private DefaultFlag unionCCStatus = DefaultFlag.NO;

    @Schema(description = "线下转账可用状态")
    @JsonProperty("lklAtStatus")
    private DefaultFlag lklAtStatus = DefaultFlag.NO;

    @Schema(description = "pos刷卡可用状态")
    @JsonProperty("cardStatus")
    private DefaultFlag cardStatus = DefaultFlag.NO;

    @Schema(description = "快捷支付可用状态")
    @JsonProperty("quickPayStatus")
    private DefaultFlag quickPayStatus = DefaultFlag.NO;
}
