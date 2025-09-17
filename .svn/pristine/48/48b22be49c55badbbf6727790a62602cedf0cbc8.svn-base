package com.wanmi.sbc.empower.api.request.logisticssetting;

import com.wanmi.sbc.empower.api.request.EmpowerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 查询快递详情入参
 * Created by CHENLI on 2017/5/25.
 */
@Schema
@Data
public class DeliveryQueryRequest extends EmpowerBaseRequest {
    private static final long serialVersionUID = 6609359011848476436L;
    /**
     * 快递公司代码
     */
    @Schema(description = "快递公司代码")
    private String companyCode;

    /**
     * 快递单号
     */
    @Schema(description = "快递单号")
    private String deliveryNo;

    /**
     * 收件人手机号 必传，不然部分快递查不到
     */
    @Schema(description = "收件人手机号")
    private String phone;

}
