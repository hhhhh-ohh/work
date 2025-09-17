package com.wanmi.sbc.sensitivewords.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 拒绝收货
 * Created by jinwei on 22/4/2017.
 */
@Schema
@Data
public class SensitiveWordsValidateRequest extends BaseRequest {

    /**
     * 文本内容
     */
    @NotBlank
    @Schema(description = "文本内容", required = true)
    private String text;
}
