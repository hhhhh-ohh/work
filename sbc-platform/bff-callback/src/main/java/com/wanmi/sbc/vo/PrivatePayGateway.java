package com.wanmi.sbc.vo;

import com.wanmi.sbc.empower.bean.vo.PayGatewayConfigVO;
import com.wanmi.sbc.empower.bean.vo.PayGatewayVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author edz
 * @className PayConfig
 * @description TODO
 * @date 2022/7/8 16:27
 **/
@Data
@Schema
public class PrivatePayGateway extends PayGatewayVO {
    @Schema(description = "支付网关配置")
    private PrivatePayGatewayConfig config;

}
