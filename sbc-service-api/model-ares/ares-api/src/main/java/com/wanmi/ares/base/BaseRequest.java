package com.wanmi.ares.base;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>请求参数基类</p>
 * Created by of628-wenzhi on 2017-09-22-下午3:32.
 */
@Data
public class BaseRequest implements Serializable{

    /**
     * 扩展数据
     */
    private Map<String, String> extendMap = new HashMap<>();

    /**
     * 统一参数校验入口
     */
    public void checkParam(){}
}
