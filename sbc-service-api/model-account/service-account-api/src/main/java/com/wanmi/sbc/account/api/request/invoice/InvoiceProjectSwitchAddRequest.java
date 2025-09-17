package com.wanmi.sbc.account.api.request.invoice;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 开票项目保存实体
 * Created by yuanlinling on 2017/4/25.
 */
@Schema
@Data
public class InvoiceProjectSwitchAddRequest extends BaseRequest {

    private static final long serialVersionUID = -6871323537231479109L;
    /**
     * 主键
     */
    @Schema(description = "主键")
    private String invoiceProjectSwitchId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;

    /**
     * 是否支持开票 0 不支持 1 支持
     */
    @Schema(description = "是否支持开票")
    private DefaultFlag isSupportInvoice;

    /**
     * 是否支持纸质发票 0 不支持 1 支持
     */
    @Schema(description = "是否支持纸质发票")
    private DefaultFlag isPaperInvoice;

    /**
     * 是否支持增值税发票 0 不支持 1 支持
     */
    @Schema(description = "是否支持增值税发票")
    private DefaultFlag isValueAddedTaxInvoice;

}
