package com.wanmi.sbc.dbreplay.config.capture.dispatch;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.bean.capture.RowBehavior;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Author: zgl
 * \* Date: 2020-2-24
 * \* Time: 11:25
 * \* Description:
 * \
 */
@Component("ReturnOrderDispatch")
public class ReturnOrderDispatch implements DispatchInterface {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String beforeProcess(OplogData source) {
        JSONObject jsonObject = JSONObject.parseObject(source.getData());
        String id = jsonObject.getString("tid");
        if(StringUtils.isNotEmpty(id)) {
            JSONObject value = mongoTemplate.findById(id,JSONObject.class,"trade");
            if(value!=null){
                JSONArray tradeArray = value.getJSONArray("tradeItems");
                Map<String, Map<String,Object>> itemMap = new HashMap<>();
                for(Object object : tradeArray) {
                    Map<String,Object> jsonField = (HashMap) object;
                    if(jsonField.get("skuId")!=null){
                        itemMap.put((String) jsonField.get("skuId"),jsonField);
                    }
                }

                JSONArray jsonArray = jsonObject.getJSONArray("returnItems");
                for(Object object : jsonArray) {
                    JSONObject jsonField = (JSONObject) object;
                    Map<String,Object> item = itemMap.get(jsonField.get("skuId"));
                    if(item != null){
                        jsonField.put("spuId",item.get("spuId"));
                        jsonField.put("spuName",item.get("spuName"));
                        jsonField.put("cateId",item.get("cateId"));
                        jsonField.put("cateName",item.get("cateName"));
                        jsonField.put("brand",item.get("brand"));
                    }
                }
            }

        }
        return jsonObject.toJSONString();
    }
}
