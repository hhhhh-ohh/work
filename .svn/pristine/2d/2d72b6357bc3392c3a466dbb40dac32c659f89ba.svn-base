package com.wanmi.sbc.empower.api.response.vop.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xufan
 * @Date: 2020/2/25 17:56
 * @Description: 京东响应参数公共类
 *
 */
@Data
public class VopBaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -2219251796926811901L;
    /**
     * 执行结果成功，还是失败
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String resultCode;

    /**
     * 错误描述
     */
    private String resultMessage;

    /**
     * 具体结果
     */
    private T result;

}
