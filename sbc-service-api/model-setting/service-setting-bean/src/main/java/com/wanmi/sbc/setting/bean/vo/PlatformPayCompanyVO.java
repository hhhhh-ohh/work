package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 支付企业VO
 *
 * @author liutao
 * @date 2019-05-14 16:29:21
 */
@Schema
@Data
public class PlatformPayCompanyVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /** 支付企业id */
    @Schema(description = "支付企业id")
    private String payCompanyId;

    /** 支付网关id */
    @Schema(description = "支付网关id")
    private Long gatewayId;

    /** 支付企业备案名称 */
    @Schema(description = "支付企业备案名称")
    private String payCompanyName;

    /** 支付企业备案编号 */
    @Schema(description = "支付企业备案编号")
    private String payCompanyCode;

    /** 报关请求地址 */
    @Schema(description = "报关请求地址")
    private String payCompanyAddress;

    /** 支付宝App ID */
    @Schema(description = "支付宝App ID")
    private String appId;

    /** 支付宝公钥 */
    @Schema(description = "支付宝公钥")
    private String publicKey;

    /** 支付宝私钥 */
    @Schema(description = "支付宝私钥")
    private String privateKey;

    /** 微信公众账号 */
    @Schema(description = "微信公众账号")
    private String publicAccount;

    /** 微信商户号 */
    @Schema(description = "微信商户号")
    private String merchantAccount;

    /** 是否启用 */
    @Schema(description = "是否启用")
    private Integer payCompanyEnable;

    /** 创建时间 */
    @Schema(description = "创建时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /** 创建人 */
    @Schema(description = "创建人")
    private String createPerson;

    /** 修改时间 */
    @Schema(description = "修改时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;

    /** 修改人 */
    @Schema(description = "修改人")
    private String updatePerson;

    /** 删除时间 */
    @Schema(description = "删除时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime deleteTime;

    /** 删除人 */
    @Schema(description = "删除人")
    private String deletePerson;

    /** 删除标识,0:未删除1:已删除 */
    @Schema(description = "删除标识,0:未删除1:已删除")
    private DeleteFlag delFlag;

    @Schema(description = "支付网关信息 - 较老版本有变化")
    private Object payGatewayVO;
}
