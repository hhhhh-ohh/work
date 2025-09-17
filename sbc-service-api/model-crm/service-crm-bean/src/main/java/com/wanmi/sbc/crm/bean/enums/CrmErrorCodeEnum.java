package com.wanmi.sbc.crm.bean.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

public enum CrmErrorCodeEnum implements ErrorCode {

    //原错误码：k0200401
    K160001("会员不存在"),

    //原错误码：K-200101
    K160002("标签名称已经存在"),

    //原错误码：K-200102
    K160003("该标签已被人群引用，不可删除"),

    //原错误码：K-200103
    K160004("非系统标签最多新增200个"),

    //原错误码：K-200301
    K160005("分群条件不能为空"),

    //原错误码：K-200302
    K160006("分群名称不能为空"),

    //原错误码：K-200303
    K160007("会员等级不存在或者已被删除，请重新选择"),

    //原错误码：K-200304
    K160008("会员标签不存在或者已被删除，请重新选择"),

    //原错误码：K-200305
    K160009("该人群已被运营计划引用，不可删除"),

    //原错误码：K-200306
    K160010("分群数量超过最大值"),

    //原错误码：K-200201
    K160011("{0}模型阶梯少于5个"),

    //原错误码：K-200202
    K160012("{0}模型条件重复"),

    //原错误码：K-200203
    K160013("{0}模型得分重复"),

    //原错误码：K-200204
    K160014("{0}分超过限定最大值");


    private String msg;
    CrmErrorCodeEnum(String msg){
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
