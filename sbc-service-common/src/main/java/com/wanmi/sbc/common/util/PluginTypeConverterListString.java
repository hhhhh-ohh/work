package com.wanmi.sbc.common.util;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.enums.PluginType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.AttributeConverter;
import java.util.List;

/**
 * @author zhanggaolei
 * @className JpaConverterListString
 * @description
 * @date 2022/10/18 15:38
 **/
public class PluginTypeConverterListString implements AttributeConverter<List<PluginType>,String> {
    @Override
    public String convertToDatabaseColumn(List<PluginType> o) {
        if (CollectionUtils.isNotEmpty(o)) {
            return JSON.toJSONString(o);
        }else{
            return null;
        }
    }

    @Override
    public List<PluginType> convertToEntityAttribute(String s) {
        if (StringUtils.isNotBlank(s)) {
            return JSON.parseArray(s, PluginType.class);
        }else {
            return null;
        }
    }
}
