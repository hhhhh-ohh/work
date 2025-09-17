package com.wanmi.sbc.setting.api.request.openapisetting;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.*;

import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

/**
 * @author lvzhenwei
 * @className OpenApiSettingCheckAuditStateRequest
 * @description 开放平台权限审核 request
 * @date 2021/4/14 3:52 下午
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingCheckAuditStateRequest extends BaseRequest {
    private static final long serialVersionUID = -6478258095394536890L;

    /** 主键 */
    @Schema(description = "主键")
    @NotNull
    private Long id;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、审核未通过")
    @NotNull
    private AuditStatus auditState;

    /** 审核驳回原因 */
    @Schema(description = "审核驳回原因")
    private String auditReason;

    /** 限流值 */
    @Schema(description = "限流值")
    private Long limitingNum;

    /** 签约结束日期 */
    @Schema(description = "签约结束日期")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDate;

    /** app_key */
    @Schema(description = "app_key")
    @Length(max = 150)
    private String appKey;

    /** app_secret */
    @Schema(description = "app_secret")
    private String appSecret;

}
