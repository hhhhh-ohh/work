package com.wanmi.sbc.dbreplay.bean.capture.mapping;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * \* Author: zgl
 * \* Date: 2020-2-25
 * \* Time: 13:49
 * \* Description:
 * \
 */
@Data
public class MappingDispatch {

    private String dispatch;                                      //调度器，当typeDispatch存在该类型的调度器时，该配置无效
    private List<String> typeFilter;                              // 类型: INSERT UPDATE DELETE,不填默认三个都执行，
    private Map<String,String> typeDispatch;                      //该类型的调度器

}
