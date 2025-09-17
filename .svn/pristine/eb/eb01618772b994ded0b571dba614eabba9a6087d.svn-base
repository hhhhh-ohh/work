package com.wanmi.sbc.empower.bean.enums;

import com.wanmi.sbc.common.enums.ErrorCode;

/**
 * @author zhanggaolei
 * @type WxLiveErrorCodeEnum.java
 * @desc
 * @date 2023/2/21 19:50
 */
public enum WxLiveErrorCodeEnum implements ErrorCode {
    T040201("300001","禁止创建/更新商品 或 禁止编辑&更新房间"),
    T040202("300006","图片上传失败（如：无图片或mediaID过期）"),
    T040203("300022","此房间号不存在"),
    T040204("300023","房间状态 拦截（当前房间状态不允许此操作）"),
    T040205("300024","商品不存在"),
    T040206("300025","商品审核未通过"),
    T040207("300015","商品不存在无法删除"),
    T040208("300026","房间商品数量已经满额"),
    T040209("300027","导入商品失败"),
    T040210("300028","房间名称违规"),
    T040211("300029","主播昵称违规"),
    T040212("300030","主播微信号不合法"),
    T040213("300031","直播间封面图不合规"),
    T040214("300032","直播间分享图违规"),
    T040215("300033","添加商品超过直播间上限"),
    T040216("300034","主播微信昵称长度不符合要求"),
    T040217("300035","主播微信号不存在"),
    T040218("300036","主播微信号未实名认证"),
    T040219("300002","名称长度不符合规则"),
    T040220("300003","价格输入不合规（如：现价比原价大、传入价格非数字等）"),
    T040221("300004","商品名称存在违规违法内容"),
    T040222("300005","商品图片存在违规违法内容"),
    T040223("300008","添加商品失败"),
    T040224("300009","商品审核撤回失败"),
    T040225("300010","商品审核状态不对（如：商品审核中）"),
    T040226("300011","操作非法（API不允许操作非API创建的商品）"),
    T040227("300012","没有提审额度（每天500次提审额度）"),
    T040228("300013","提审失败"),
    T040229("300014","审核中，无法删除（非零代表失败）"),
    T040230("300017","商品未提审"),
    T040231("300021","商品添加成功，审核失败"),
    T040232("10001","小程序直播已关闭，如需使用相关功能请开启!"),
    T040233("-1","微信系统错误!"),
    T040234("200002","参数不正确");

    private String msg;
    private String oldCode;
    WxLiveErrorCodeEnum(String oldCode,String msg){
        this.oldCode = oldCode;
        this.msg = msg;
    }


    @Override
    public String getCode() {
        return this.name();
    }

    public String getMsg(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(oldCode);
        stringBuilder.append(":");
        stringBuilder.append(this.msg);
        return stringBuilder.toString();
    }

    public static WxLiveErrorCodeEnum parseOldCode(String oldCode){
        for (WxLiveErrorCodeEnum errorCodeEnum : WxLiveErrorCodeEnum.values()){
            if(errorCodeEnum.oldCode.equals(oldCode)){
                return errorCodeEnum;
            }
        }
        return null;
    }

}