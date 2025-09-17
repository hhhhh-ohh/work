package com.wanmi.sbc.goods.wechatvideosku.model.root;

import com.wanmi.sbc.common.enums.EditStatus;

import jakarta.persistence.AttributeConverter;

public class EditStatusAttributeConverter implements AttributeConverter<EditStatus,Integer> {
    @Override
    public Integer convertToDatabaseColumn(EditStatus editStatus) {
        return editStatus.getValue();
    }

    @Override
    public EditStatus convertToEntityAttribute(Integer integer) {
        for (EditStatus editStatus : EditStatus.values()) {
            if (editStatus.getValue().equals(integer)) {
                return editStatus;
            }
        }
        return null;
    }
}
