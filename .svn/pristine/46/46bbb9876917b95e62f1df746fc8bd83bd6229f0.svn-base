package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 增值税/专用发票
 * Created by jinwei on 7/5/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class SpecialInvoiceVO extends BasicResponse {

    /**
     * 增值税发票id，新增与修改时必传
     */
    @Schema(description = "增值税发票id")
    private Long id;

    /**
     * 以下信息无需传入
     **/
    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "公司编号")
    private String companyNo;

    @Schema(description = "手机号")
    private String phoneNo;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "银行")
    private String bank;

    /**
     * 纳税人识别号
     */
    @Schema(description = "纳税人识别号")
    private String identification;
}
