package com.wanmi.sbc.common.base;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求基类
 * Created by aqlu on 15/11/30.
 */
@Schema
@Data
@Slf4j
public class BasicResponse implements Serializable {

    /**
     * 扩展属性
     */
    private Map<String, String> extendMap = new HashMap<>();
}
