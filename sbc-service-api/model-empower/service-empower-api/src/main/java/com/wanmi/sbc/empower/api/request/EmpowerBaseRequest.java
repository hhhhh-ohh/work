package com.wanmi.sbc.empower.api.request;

import com.wanmi.sbc.common.base.BaseRequest;

import java.io.Serializable;

/**
 * @Author: lvzhenwei
 * @Description:
 * @Date: 2019-10-14 10:16
 */
public class EmpowerBaseRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统一参数校验入口
     */
    public void checkParam(){}
}