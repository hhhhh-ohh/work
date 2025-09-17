package com.wanmi.sbc.vas.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Schema
@Data
public class VasBaseRequest extends BaseRequest {
    private static final long serialVersionUID = 9035647807787101793L;

    /**
     * 统一参数校验入口
     */
    public void checkParam(){}
}
