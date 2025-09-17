package com.wanmi.sbc.dbreplay.bean.capture.mapping;

import lombok.Data;

import java.util.List;

@Data
public class MappingData {
    /**
     * 固定的字段数据
     */
    private List<MappingField> singleData;

    /**
     * 数组的字段数据
     */
    private MappingArrayData arrayData;
}
