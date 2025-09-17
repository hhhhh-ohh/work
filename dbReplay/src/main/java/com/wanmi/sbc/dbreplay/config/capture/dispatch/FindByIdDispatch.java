package com.wanmi.sbc.dbreplay.config.capture.dispatch;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.bean.capture.RowBehavior;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * \* Author: zgl
 * \* Date: 2020-2-24
 * \* Time: 11:25
 * \* Description:
 * \
 */
@Component("FindByIdDispatch")
public class FindByIdDispatch implements DispatchInterface {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String beforeProcess(OplogData source) {
        String id = null;
        if(source.getType().equals(RowBehavior.UPDATE)){
            id = JSONObject.parseObject(source.getCondition()).getString("_id");
        }else{
            id = JSONObject.parseObject(source.getData()).getString("_id");
        }
        if(StringUtils.isNotEmpty(id)) {
            Object value = mongoTemplate.findById(id,Object.class,source.getCollection());
            return JSON.toJSONString(value,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        }
        return null;
    }
}
