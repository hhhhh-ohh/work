package com.wanmi.sbc.customer.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * @author minchen
 */
@Schema
@Data
public class CustomerImportExcelRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Schema(description = "文件后缀")
    private String ext;

    /**
     * 是否发送短信
     */
    @Schema(description = "是否发送短信")
    private Boolean sendMsgFlag;

    /**
     * 操作员id
     */
    @Schema(description = "操作员id")
    private String userId;
}
