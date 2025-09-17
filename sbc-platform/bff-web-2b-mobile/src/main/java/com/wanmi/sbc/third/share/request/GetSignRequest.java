package com.wanmi.sbc.third.share.request;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GetSignRequest extends BaseRequest {

    @NotBlank
    private String url;

    private String terminalType;
}
