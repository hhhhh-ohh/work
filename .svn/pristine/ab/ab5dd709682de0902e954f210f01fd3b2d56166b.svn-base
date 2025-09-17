package com.wanmi.sbc.empower.api.response.apppush;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description: 友盟查询接口返回对象
 * @create: 2020-01-08 20:09
 * 接口说明及错误码：https://developer.umeng.com/docs/66632/detail/68343#h2--i-15
 **/
@Data
public class AppPushCancelResponse implements Serializable {
    //取消是否成功
    private Boolean success;
    // 接口状态 SUCCESS:成功 FAIL:失败
    private String ret;
    // 任务ID
    private String taskId;
    // 错误码详见附录I
    private String errorCode;
    // 错误码详见附录I
    private String errorMsg;
}