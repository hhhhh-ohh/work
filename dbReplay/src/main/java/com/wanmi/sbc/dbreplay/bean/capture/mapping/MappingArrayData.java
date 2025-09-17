package com.wanmi.sbc.dbreplay.bean.capture.mapping;

import lombok.Data;

import java.util.List;

/**
 * \* Author: zgl
 * \* Date: 2020-2-20
 * \* Time: 11:18
 * \* Description:
 * \
 */
@Data
public class MappingArrayData {
    private String arrayRoot;
    /*private List<MappingField> arrayList;*/

    private MappingData mappingData;

    /**
     * 是否为基础数据类型的list，比如List<String> List<Long>
     */
    private Boolean isBaseType = false;

    /**
     * 与isBaseType相对应，如果isBaseType = true则需要配置该值
     */
    private MappingField baseTypeMapping;
}
