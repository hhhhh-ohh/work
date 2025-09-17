package com.wanmi.sbc.customer.bean.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

/**
 * @author zhanggaolei
 * @type CustomerErrorCodeEnum.java
 * @desc
 * @date 2023/2/21 19:50
 */
public enum AgentErrorCodeEnum implements ErrorCode {
    //原错误码：K-100001
    K100001("未查询到代理商信息"),

    //原错误码：K-100002
    K100002("代理商信息已删除！"),

    //原错误码：K-100003
    K100003("唯一码未审核通过!"),

    //原错误码：K-100004
    K100004("唯一码未在有效期内!"),

    //原错误码：K-100005
    K100005("用户无审核一户一码角色!"),

    //原错误码：K-100006
    K100006("用户无审核一户一码权限!"),

    //原错误码：K-100007
    K100007("该用户已申请代理商"),

    //原错误码：K-100008
    K100008("该用户已是代理商"),

    //原错误码：K-100009
    K100009("代理商待审核记录为空"),



    //原错误码：K-100010
    K100010("用户无审核一户一码刷新海报角色!"),



    //原错误码：K-100011
    K100011("用户无审核一户一码刷新海报权限!"),


    ;



    private String msg;
    AgentErrorCodeEnum(String msg){
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