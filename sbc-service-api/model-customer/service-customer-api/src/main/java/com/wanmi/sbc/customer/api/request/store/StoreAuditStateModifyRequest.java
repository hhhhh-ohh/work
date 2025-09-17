package com.wanmi.sbc.customer.api.request.store;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;
import com.wanmi.sbc.common.util.CustomLocalDateTimeSerializer;
import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;
import com.wanmi.sbc.customer.bean.enums.CheckState;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

import java.time.LocalDateTime;

@Schema
@Data
public class StoreAuditStateModifyRequest extends CustomerBaseRequest {

    private static final long serialVersionUID = 1L;
    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    @NotNull
    private Long storeId;

    /**
     * 驳回状态 0：待审核 1：已审核 2：审核未通过
     */
    @Schema(description = "驳回状态")
    @NotNull
    private CheckState auditState;

    /**
     * 申请入驻时间
     */
    @Schema(description = "申请入驻时间")
    @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime applyEnterTime;

    /**
     * 审核未通过原因
     */
    @Schema(description = "审核未通过原因")
    private String auditReason;
}