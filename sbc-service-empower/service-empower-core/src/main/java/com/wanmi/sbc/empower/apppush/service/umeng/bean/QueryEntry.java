package com.wanmi.sbc.empower.apppush.service.umeng.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: sbc-micro-service
 * @description: 请求参数
 * @create: 2020-01-10 15:48
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryEntry implements Serializable {
    private static final long serialVersionUID = 5618086573074692995L;

    // 任务ID
    private String taskId;

    // 应用key
    private String key;

    // 密钥
    private String appMasterSecret;
}