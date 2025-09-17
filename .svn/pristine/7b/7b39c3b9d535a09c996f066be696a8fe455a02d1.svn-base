package com.wanmi.sbc.setting.api.request.openapisetting;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.EnableStatus;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

/**
 * 开放平台配置信息缓存数据
 * @author  lvzhenwei
 * @date 2021/4/16 3:05 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingCacheRequest extends BaseRequest {
    private static final long serialVersionUID = 2647676343061160962L;

    /** 店铺id */
    @Schema(description = "店铺id")
    @NotNull
    @Max(9223372036854775807L)
    private Long storeId;

    /** 签约结束日期 */
    @Schema(description = "签约结束日期")
    private String contractEndDate;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、审核未通过")
    private AuditStatus auditState;

    /** 禁用状态:0:禁用；1:启用 */
    @Schema(description = "禁用状态:0:禁用；1:启用")
    private EnableStatus disableState;

    /** app_key */
    @Schema(description = "app_key")
    private String appKey;

    /** app_secret */
    @Schema(description = "app_secret")
    @Length(max = 150)
    private String appSecret;

    /** 限流值 */
    @Schema(description = "限流值")
    @NotNull
    @Max(9223372036854775807L)
    private Long limitingNum;

    /** 平台类型：0:boss；1:商家； */
    @Schema(description = "平台类型")
    private Integer platformType;

    /**
     * 平台描述
     */
    @Schema(description = "平台描述")
    private String platformDesc;
}
