package com.wanmi.sbc.setting.api.request.openapisetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.PlatformType;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotBlank;

import lombok.*;

import org.hibernate.validator.constraints.*;

import java.time.LocalDateTime;

/**
 * @description 开放平台api设置修改参数
 * @author lvzhenwei
 * @date 2021/4/14 3:19 下午
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingModifyRequest extends BaseRequest {
    private static final long serialVersionUID = -9070107290769220549L;

    /** 主键 */
    @Schema(description = "主键")
    private Long id;

    /** 店铺id */
    @Schema(description = "店铺id")
    @NotNull
    private Long storeId;

    /** 店铺名称 */
    @Schema(description = "店铺名称")
    private String storeName;

    /** 商家名称 */
    @Schema(description = "商家名称")
    private String supplierName;

    /** 商家类型：0:供应商；1:商家； */
    @Schema(description = "商家类型：0:供应商；1:商家；")
    @NotNull
    private StoreType storeType;

    /** 签约开始日期 */
    @Schema(description = "签约开始日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDate;

    /** 签约结束日期 */
    @Schema(description = "签约结束日期")
    @NotNull
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、审核未通过")
    @NotNull
    private AuditStatus auditState;

    /** 审核未通过原因 */
    @Schema(description = "审核未通过原因")
    private String auditReason;

    /** 禁用状态:0:禁用；1:启用 */
    @Schema(description = "禁用状态:0:禁用；1:启用")
    @NotNull
    private EnableStatus disableState;

    /** app_key */
    @Schema(description = "app_key")
    @NotBlank
    private String appKey;

    /** 限流值 */
    @Schema(description = "限流值")
    @NotNull
    private Long limitingNum;

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
