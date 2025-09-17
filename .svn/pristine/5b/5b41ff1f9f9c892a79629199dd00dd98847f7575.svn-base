package com.wanmi.sbc.setting.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import lombok.Data;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 开放平台api设置VO
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@Schema
@Data
public class OpenApiSettingVO extends BasicResponse {
    private static final long serialVersionUID = -3785909742681642486L;

    /** 主键 */
    @Schema(description = "主键")
    private Long id;

    /** 店铺id */
    @Schema(description = "店铺id")
    private Long storeId;

    /** 店铺名称 */
    @Schema(description = "店铺名称")
    private String storeName;

    /** 商家名称 */
    @Schema(description = "商家名称")
    private String supplierName;

    /** 商家类型：0:供应商；1:商家； */
    @Schema(description = "商家类型：0:供应商；1:商家；")
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
    private String auditReason;

    /** 禁用状态:0:禁用；1:启用 */
    @Schema(description = "禁用状态:0:禁用；1:启用")
    private EnableStatus disableState;

    /** 禁用原因 */
    @Schema(description = "禁用原因")
    private String disableReason;

    /** app_key */
    @Schema(description = "app_key")
    private String appKey;

    /** 限流值 */
    @Schema(description = "限流值")
    private Long limitingNum;

    /**
     * 商家类型(0、平台自营 1、第三方商家)
     */
    @Schema(description = "商家类型(0、平台自营 1、第三方商家)")
    private BoolFlag companyType;

    /** 平台类型：0:boss；1:商家； */
    @Schema(description = "平台类型")
    private PlatformType platformType;

    /**
     * 平台描述
     */
    @Schema(description = "平台描述")
    private String platformDesc;
}
