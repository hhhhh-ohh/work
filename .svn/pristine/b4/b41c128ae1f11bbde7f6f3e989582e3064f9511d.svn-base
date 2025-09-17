package com.wanmi.sbc.goods.wechatvideosku.model.root;

import com.wanmi.sbc.goods.bean.enums.WechatShelveStatus;

import jakarta.persistence.AttributeConverter;

public class WechatShelveStatusAttributeConverter implements AttributeConverter<WechatShelveStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(WechatShelveStatus wechatShelveStatus) {
        return wechatShelveStatus.getValue();
    }

    @Override
    public WechatShelveStatus convertToEntityAttribute(Integer integer) {
        for (WechatShelveStatus wechatShelveStatus : WechatShelveStatus.values()) {
            if (wechatShelveStatus.getValue().equals(integer)) {
                return wechatShelveStatus;
            }
        }
        return null;
    }
}
