package com.wanmi.sbc.elastic.bean.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

public enum ElasticErrorCodeEnum implements ErrorCode {

    //原错误码：K-210001
    K040001("初始化优惠券ES数据异常，异常页码{0}，请重新设置此页码继续初始化"),

    //原错误码：K-210101
    K040002("根据优惠券活动ID:{0},查询不到优惠券活动信息"),

    //原错误码：K-210102
    K040003("初始化优惠券活动ES数据异常，异常页码{0}，请重新设置此页码继续初始化"),

    //原错误码：K-220101
    K040004("初始化会员ES数据异常，异常页码{0}，请重新设置此页码继续初始化"),

    //原错误码：K-121016
    K040005("ES初始化商家评价列表异常，异常pageNum:{0}"),

    //原错误码：K-120017
    K040006("ES初始化敏感词库列表异常，异常pageNum:{0}"),

    //原错误码：K-121013
    K040007("ES初始化邀新记录列表异常，异常pageNum:{0}"),

    //原错误码：K-120011
    K040008("ES初始化分销员列表异常，异常pageNum:{0}"),

    //原错误码：K-120011
    K040009("ES初始化操作日志列表异常，异常pageNum:{0}"),

    //原错误码：K-121011
    K040010("ES初始化商品素材列表异常，异常pageNum:{0}"),

    //原错误码：K-120011
    K040011("ES初始化品牌列表异常，异常pageNum:{0}"),

    //原错误码：K-121017
    K040012("ES初始化订单开票数据异常，异常pageNum:{0}"),

    //原错误码：K-120011
    K040013("ES初始化拼团列表异常，异常pageNum:{0} "),

    //原错误码：K-120011
    K040014("ES初始化搜索词库列表异常，异常pageNum:{0}"),

    //原错误码：K-121010
    K040015("ES初始化分销记录列表异常，异常pageNum:{0}"),

    //原错误码：K-121014
    K040016("ES初始化评价管理列表异常，异常pageNum:{0}"),

    //原错误码：K-121018
    K040017("ES初始化资源素材列表异常，异常pageNum:{0}");

    private String msg;
    ElasticErrorCodeEnum(String msg){
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
