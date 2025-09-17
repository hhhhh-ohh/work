package com.wanmi.sbc.account.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class AccountBaseRequest extends BaseRequest {

    private static final long serialVersionUID = -5238396196345416352L;

    /**
     * 统一参数校验入口
     */
    public void checkParam(){}
}
