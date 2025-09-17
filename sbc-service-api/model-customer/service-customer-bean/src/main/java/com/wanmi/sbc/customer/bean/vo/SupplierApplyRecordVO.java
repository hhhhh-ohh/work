package com.wanmi.sbc.customer.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.wanmi.sbc.common.util.CustomLocalDateTimeDeserializer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author edz
 * @className SupplierApplyRecord
 * @description TODO
 * @date 2022/9/6 17:49
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SupplierApplyRecordVO implements Serializable {

    @Schema(description = "商家编码")
    private String companyCode;

    @Schema(description = "商家名称")
    private String supplierName;

    @Schema(description = "商家账号")
    private String accountName;

    @Schema(description = "进件状态")
    private Integer state;

    @Schema(description = "进件时间")
    private String passTime;

    @Schema(description = "商家id")
    private Long supplierId;

    @Schema(description = "进件失败原因")
    private String accountRejectReason;

    @Schema(description = "分账开通状态")
    private Integer ledgerState;

    @Schema(description = "分账开通失败原因")
    private String ledgerRejectReason;

    @Schema(description = "分账绑定状态")
    private Integer receiverState;

    @Schema(description = "分账绑定失败原因")
    private String receiverRejectReason;

    @Schema(description = "商户号")
    private String merCupNo;
}
