package com.wanmi.sbc.empower.api.request.pay.gateway;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;
import com.wanmi.sbc.empower.bean.enums.TerminalType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.validator.constraints.Length;

/**
 * Created by sunkun on 2017/8/9.
 */
@EqualsAndHashCode(callSuper = true)
@Schema
@Data
public class PayGatewaySaveByTerminalTypeRequest extends BaseRequest {

    private static final long serialVersionUID = 4304124969782172682L;

    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    @Schema(description = "网关名称（英文名称，全大写）")
    @NotNull
    private PayGatewayEnum payGatewayEnum;

    @Schema(description = "终端类型")
    @NotNull
    private TerminalType terminalType;

    @Schema(description = "secret key")
    @Length(max = 60)
    private String secret;

    @Schema(description = "第三方应用标识")
    @Length(max = 40)
    private String appId;


}
