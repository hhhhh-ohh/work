package com.wanmi.sbc.setting.api.request.openapisetting;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.enums.AuditStatus;
import com.wanmi.sbc.common.enums.EnableStatus;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import java.time.LocalDateTime;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.base.BaseQueryRequest;
import lombok.*;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 开放平台api设置分页查询请求参数
 *
 * @author lvzhenwei
 * @date 2021-04-12 17:00:26
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiSettingPageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = -8962612988889873801L;

    /** 批量查询-主键List */
    @Schema(description = "批量查询-主键List")
    private List<Long> idList;

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

    /** 搜索条件:签约开始日期开始 */
    @Schema(description = "搜索条件:签约开始日期开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDateBegin;
    /** 搜索条件:签约开始日期截止 */
    @Schema(description = "搜索条件:签约开始日期截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractStartDateEnd;

    /** 搜索条件:签约结束日期开始 */
    @Schema(description = "搜索条件:签约结束日期开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDateBegin;
    /** 搜索条件:签约结束日期截止 */
    @Schema(description = "搜索条件:签约结束日期截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime contractEndDateEnd;

    /** 审核状态 0、待审核 1、已审核 2、审核未通过 */
    @Schema(description = "审核状态 0、待审核 1、已审核 2、审核未通过")
    private AuditStatus auditState;

    /** 审核未通过原因 */
    @Schema(description = "审核未通过原因")
    private String auditReason;

    /** 禁用状态:0:禁用；1:启用 */
    @Schema(description = "禁用状态:0:禁用；1:启用")
    private EnableStatus disableState;

    /** app_key */
    @Schema(description = "app_key")
    private String appKey;

    /** app_secret */
    @Schema(description = "app_secret")
    private String appSecret;

    /** 限流值 */
    @Schema(description = "限流值")
    private Long limitingNum;

    /** 是否删除 0 否 1 是 */
    @Schema(description = "是否删除 0 否  1 是")
    private DeleteFlag delFlag;

    /** 搜索条件:创建时间开始 */
    @Schema(description = "搜索条件:创建时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeBegin;
    /** 搜索条件:创建时间截止 */
    @Schema(description = "搜索条件:创建时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime createTimeEnd;

    /** 创建人 */
    @Schema(description = "创建人")
    private String createPerson;

    /** 搜索条件:修改时间开始 */
    @Schema(description = "搜索条件:修改时间开始")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeBegin;
    /** 搜索条件:修改时间截止 */
    @Schema(description = "搜索条件:修改时间截止")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime updateTimeEnd;

    /** 修改人 */
    @Schema(description = "修改人")
    private String updatePerson;
}
