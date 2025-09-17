package com.wanmi.sbc.empower.api.request.Ledger.lakala;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 拉卡拉电子合同下载
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Schema
public class LakalaEcDownloadRequest extends LakalaBaseRequest{

    /**
     * 机构号
     * 签约方所属拉卡拉机构
     */
    @Schema(description = "机构号")
    @CanEmpty
    private Integer orgId;

    /**
     * 电子合同申请受理号
     * 申请接口反馈编号
     */
    @Schema(description = "电子合同申请受理号")
    @NotNull
    private Long ecApplyId;
}
