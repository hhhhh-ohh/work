package com.wanmi.sbc.order.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 普通发票
 * Created by jinwei on 7/5/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema
public class GeneralInvoiceDTO {

    /**
     * 0:个人 1:单位，必传
     */
    @Schema(description = "发票类型", contentSchema = com.wanmi.sbc.account.bean.enums.InvoiceType.class)
    private Integer flag;

    /**
     * 抬头，单位发票必传
     */
    @Schema(description = "抬头")
    private String title;

    /**
     * 纸质发票单位纳税人识别码
     */
    @Schema(description = "纸质发票单位纳税人识别码")
    private String identification;

    /**
     * 发票id
     */
    private Long customerInvoiceId;
}
