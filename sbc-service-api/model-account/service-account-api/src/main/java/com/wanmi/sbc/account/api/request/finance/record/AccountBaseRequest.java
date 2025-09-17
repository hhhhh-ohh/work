package com.wanmi.sbc.account.api.request.finance.record;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>账务请求参数基类</p>
 * Created by of628-wenzhi on 2018-10-13-下午1:52.
 */
@Schema
@Data
public class AccountBaseRequest extends BaseRequest {
    /**
     * 统一参数校验入口
     */
    public void checkParam(){}
}
