package com.wanmi.sbc.setting.api.request.openapisetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.setting.api.request.SettingBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * @description 开放平台api设置新增参数
 * @author lvzhenwei
 * @date 2021/4/14 3:12 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingAddRequest extends SettingBaseRequest {
    private static final long serialVersionUID = 9115100274652919266L;

    /** 店铺id */
    @Schema(description = "店铺id")
    @NotNull
    @Max(9223372036854775807L)
    private Long storeId;

    /** 店铺名称 */
    @Schema(description = "店铺名称")
    @Length(max = 150)
    private String storeName;

    /** 商家名称 */
    @Schema(description = "商家名称")
    @Length(max = 50)
    private String supplierName;

    /** 商家类型：0:供应商；1：商家； */
    @Schema(description = "商家类型：0:供应商；1:商家；")
    @NotNull
    private StoreType storeType;

    /** 签约开始日期 */
    @Schema(description = "签约开始日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /** 签约结束日期 */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、审核未通过")
    private AuditStatus auditState;

    /** 审核未通过原因 */
    @Schema(description = "审核未通过原因")
    @Length(max = 255)
    private String auditReason;

    /** 禁用状态:0:禁用；1:启用 */
    @Schema(description = "禁用状态:0:禁用；1:启用")
    private EnableStatus disableState;

    /** app_key */
    @Schema(description = "app_key")
    @Length(max = 150)
    private String appKey;

    /** app_secret */
    @Schema(description = "app_secret")
    @Length(max = 150)
    private String appSecret;

    /** 限流值 */
    @Schema(description = "限流值")
    @Max(9223372036854775807L)
    private Long limitingNum;

    /** 是否删除 0 否 1 是 */
    @Schema(description = "是否删除 0 否  1 是", hidden = true)
    private DeleteFlag delFlag;

    /** 创建人 */
    @Schema(description = "创建人", hidden = true)
    private String createPerson;

    /** 修改人 */
    @Schema(description = "修改人", hidden = true)
    private String updatePerson;

    /** 平台类型：0:boss；1:商家； */
    @Schema(description = "平台类型")
    private PlatformType platformType;

    /**
     * 平台描述
     */
    @Schema(description = "平台描述")
    private String platformDesc;
}
