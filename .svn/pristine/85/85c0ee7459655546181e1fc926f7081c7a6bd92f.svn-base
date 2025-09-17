package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 开票项目开关
 * Created by chenli on 2017/12/12.
 */
@Schema
@Data
public class InvoiceProjectSwitchVO extends BasicResponse {

    private static final long serialVersionUID = -3217051307624861916L;
    /**
     * 主键
     */
    @Schema(description = "开票项目开关id")
    private String invoiceProjectSwitchId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息id")
    private Long companyInfoId;

    /**
     * 是否支持开票 0 不支持 1 支持
     */
    @Schema(description = "是否支持开票")
    private DefaultFlag isSupportInvoice = DefaultFlag.NO;

    /**
     * 纸质发票 0 不支持 1 支持
     */
    @Schema(description = "纸质发票")
    private DefaultFlag isPaperInvoice = DefaultFlag.NO;

    /**
     * 增值税发票 0 不支持 1 支持
     */
    @Schema(description = "增值税发票")
    private DefaultFlag isValueAddedTaxInvoice = DefaultFlag.NO;
}
