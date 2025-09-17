package com.wanmi.sbc.empower.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.empower.bean.enums.IsOpen;
import com.wanmi.sbc.empower.bean.enums.PayGatewayEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description  微信配置
 * @author  wur
 * @date: 2022/12/3 13:40
 **/
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WechatConfigVO implements Serializable{

    private static final long serialVersionUID = 1725819478142926858L;

    @Schema(description = "支付网关id")
    private Long id;

    /**
     * 商户id-boss端取默认值
     */
    @Schema(description = "商户id")
    private Long storeId;

    /**
     * 微信AppId
     */
    @Schema(description = "微信AppId")
    private String appId;

    /**
     * 微信 - secret
     */
    @Schema(description = "secret")
    private String secret;

    /**
     * 场景；H5微信设置（0：扫码 ，H5，微信网页-JSAPI），1：小程序（miniJSAPI），2：APP
     */
    @Schema(description = "场景")
    private Integer sceneType;
}
