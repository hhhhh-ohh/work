package com.wanmi.sbc.account.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * 新增付款记录参数（Customer纬度）
 * Created by of628-wenzhi on 2017/4/27.
 */
@Schema
@Data
public class PaymentRecordRequest extends BaseRequest {

    /**
     * 关联的订单id
     */
    @Schema(description = "关联的订单id")
    @NotBlank
    private String tid;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @NotBlank
    private String createTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 收款账号id
     */
    @Schema(description = "收款账号id")
    @NotNull
    private Long accountId;

    /**
     * 附件
     */
    @Schema(description = "附件")
    @NotBlank
    private String encloses;
}
