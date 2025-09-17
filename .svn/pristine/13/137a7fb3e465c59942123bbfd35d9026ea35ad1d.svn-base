package com.wanmi.ares.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

/**
 * @author zhanggaolei
 * @type AresErrorCodeEnum.java
 * @desc
 * @date 2023/2/21 19:50
 */
public enum  AresErrorCodeEnum implements ErrorCode {
    //原错误码：R-000102
    K130001("阿里云上传文件失败，请联系管理员"),

    //原错误码：R-000101
    K130002("阿里云OSS连接不可用"),

    //原错误码：R-000103
    K130003("阿里云删除文件失败，请联系管理员"),

    //原错误码：R-000104
    K130004("阿里云未配置");


    private String msg;
    AresErrorCodeEnum(String msg){
        this.msg = msg;
    }


    @Override
    public String getCode() {
        return this.name();
    }

    public String getMsg(){
        return this.msg;
    }
}