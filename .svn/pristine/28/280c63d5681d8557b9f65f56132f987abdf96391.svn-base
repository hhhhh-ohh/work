package com.wanmi.sbc.dbreplay.bean.capture.mapping;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * \* Author: zgl
 * \* Date: 2020-2-17
 * \* Time: 17:00
 * \* Description:
 * \
 */
@Data
public class MappingField {

    private String field;                                   //mongo字段
    private String replayField;                             //回放字段
    private int dataType;                                   //数据类型，参考SqlType
    private Object defaultValue;                            //默认值
    private Map<String, Integer> enumType;                  //枚举类型
    private String enumFile;                                //枚举类型文件，与enumType冲突，两只都存在以enumType为准


}
