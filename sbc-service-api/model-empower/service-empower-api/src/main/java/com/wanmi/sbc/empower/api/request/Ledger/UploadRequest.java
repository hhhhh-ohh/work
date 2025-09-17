package com.wanmi.sbc.empower.api.request.Ledger;

import com.wanmi.sbc.empower.api.request.Ledger.lakala.LakalaUploadRequest;
import com.wanmi.sbc.empower.bean.enums.LedgerType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 附件上传request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class UploadRequest {

    /**
     * 拉卡拉附件上传参数
     */
    @Schema(description = "拉卡拉附件上传参数")
    private LakalaUploadRequest lakalaUploadRequest;


    /**
     * 分账平台类型
     */
    @Schema(description = "分账平台类型")
    @Builder.Default
    private LedgerType ledgerType = LedgerType.LAKALA_LEDGER;
}
