package com.wanmi.sbc.vas.bean.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

/**
 * @author zhanggaolei
 * @type VasErrorCodeEnum.java
 * @desc
 * @date 2023/2/21 19:50
 */
public enum  VasErrorCodeEnum implements ErrorCode {
    //原错误码：K-160101
    K120001("未购买企业购服务"),

    //原错误码：K-200001
    K120002("linkedmall验签失败");


    private String msg;
    VasErrorCodeEnum(String msg){
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