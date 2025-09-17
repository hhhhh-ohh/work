package com.wanmi.sbc.dbreplay.bean.capture.mapping;

import com.wanmi.sbc.dbreplay.bean.capture.constants.ReplayType;
import lombok.Data;

import java.util.List;

/**
 * \* Author: zgl
 * \* Date: 2020-2-17
 * \* Time: 16:58
 * \* Description:
 * \
 */
@Data
public class MappingBean {
    /**
     * 回放表
     */
    private String replayTable;

    /**
     * dispatch类
     */
    private MappingDispatch dispatch;

//    /**
//     * 固定的字段数据
//     */
//    private List<MappingField> singleData;
//
//    /**
//     * 数组的字段数据
//     */
//    private MappingArrayData arrayData;

    private MappingData mappingData;


    private List<String> pk;                                //主键

    private List<ReplayType> replayTypes;
}
