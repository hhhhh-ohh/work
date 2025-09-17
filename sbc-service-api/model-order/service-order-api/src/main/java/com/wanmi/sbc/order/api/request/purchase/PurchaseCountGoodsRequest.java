package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-07
 */
@Data
@Schema
public class PurchaseCountGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "客户id")
    @NotBlank
    private String customerId;

    /**
     * 邀请人id-会员id
     */
    @Schema(description = "邀请人id")
    String inviteeId;

    /**
     * 公司信息ID
     */
    @Schema(description = "公司信息ID")
    private Long companyInfoId;


}
