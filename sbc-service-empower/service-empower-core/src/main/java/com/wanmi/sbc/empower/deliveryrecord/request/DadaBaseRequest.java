package com.wanmi.sbc.empower.deliveryrecord.request;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;

/**
 * <p>达达配送基础请求类</p>
 * @author dyt
 * @date 2019-07-30 14:08:26
 */
public class DadaBaseRequest {

    /**
     * 配送商户编号
     */
    @JSONField(serialize = false)
    private String sourceId;

    public Object toJson() {
        return JSON.toJSONString(this);
    }


    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }
}