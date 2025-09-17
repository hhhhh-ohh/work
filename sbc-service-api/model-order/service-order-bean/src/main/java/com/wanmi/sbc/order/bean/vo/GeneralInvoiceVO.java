package com.wanmi.sbc.order.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

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
public class GeneralInvoiceVO extends BasicResponse {

    /**
     * 0:个人 1:单位，必传
     */
    @Schema(description = "发票类型")
    private Integer flag;

    /**
     * 抬头，单位发票必传
     */
    @Schema(description = "抬头，单位发票必传")
    private String title;

    /**
     * 纸质发票单位纳税人识别码
     */
    @Schema(description = "纸质发票单位纳税人识别码")
    private String identification;
}
