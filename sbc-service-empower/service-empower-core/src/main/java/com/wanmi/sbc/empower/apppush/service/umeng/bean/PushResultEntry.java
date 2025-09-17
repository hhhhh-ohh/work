package com.wanmi.sbc.empower.apppush.service.umeng.bean;

import com.wanmi.sbc.empower.bean.enums.AppPushAppType;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description: 友盟推送接口返回对象
 * @create: 2020-01-08 19:17
 **/
@Data
public class PushResultEntry implements Serializable {
    //发送是否成功
    private Boolean success;

    // 接口状态 SUCCESS:成功 FAIL：失败
    private String ret;
    // 任务ID
    private String taskId;

    // 失败原因
    private String errorMsg;

    // iOS、android
    private AppPushAppType appType;
}