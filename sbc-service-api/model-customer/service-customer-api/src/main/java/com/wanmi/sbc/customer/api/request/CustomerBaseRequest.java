package com.wanmi.sbc.customer.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;



@Schema
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerBaseRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统一参数校验入口
     */
    @Schema(description = "统一参数校验入口", hidden = true)
    public void checkParam(){}
}
