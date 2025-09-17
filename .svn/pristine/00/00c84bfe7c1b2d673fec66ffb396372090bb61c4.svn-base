package com.wanmi.sbc.account.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.enums.DefaultFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 开票项目开关返回结果
 * Created by chenli on 2017/12/12.
 */
@Schema
@Data
public class InvoiceProjectSwitchSupportVO extends BasicResponse {

    /**
     * 公司信息id
     */
    @Schema(description = "公司信息id")
    private Long companyInfoId;

    /**
     * 是否支持开票 0 不支持 1 支持
     */
    @Schema(description = "是否支持开票")
    private DefaultFlag supportInvoice = DefaultFlag.NO;
}
