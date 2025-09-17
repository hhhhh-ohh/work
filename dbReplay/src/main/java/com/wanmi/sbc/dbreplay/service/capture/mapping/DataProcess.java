package com.wanmi.sbc.dbreplay.service.capture.mapping;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.bean.capture.RowBehavior;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingBean;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingField;
import com.wanmi.sbc.dbreplay.utils.JsonUtil;
import com.wanmi.sbc.dbreplay.utils.SyncUtil;

import java.util.List;

import static com.wanmi.sbc.dbreplay.common.MappingCache.ENUM;

public abstract class DataProcess {
    Boolean[] isExist = {true};         //该值用于判断字段存不存在
    public <T> T dataProcess(T source){

        return null;
    }

    /**
     * 根据配置获取json（oplog）中的值
     * @param jsonObject
     * @param field
     * @return
     */
    public Object getValue(JSONObject jsonObject, MappingField field, RowBehavior type){
        Object value = null;
        if(JsonUtil.containsKey(jsonObject,field.getField())){
            value = JsonUtil.getValue(jsonObject,field.getField());
            if(field.getEnumType()!=null){
                value = field.getEnumType().get(value);
            }else if(field.getEnumFile() != null){
                value = ENUM.get(field.getEnumFile()).get(value);
            }
            isExist[0] = true;
        }else if (field.getDefaultValue()!=null && type.equals(RowBehavior.INSERT)){
            value = field.getDefaultValue();
            isExist[0] = true;
        }else{
            isExist[0] = false;
        }
        return SyncUtil.paresMongoData(value,field.getDataType());
    }

    /**
     * 根据配置获取object中的值
     * @param object
     * @param field
     * @return
     */
    public Object getValue(Object object, MappingField field){
        Object value = object;
        if(field.getEnumType()!=null){
            value = field.getEnumType().get(value);
        }else if(field.getEnumFile() != null){
            value = ENUM.get(field.getEnumFile()).get(value);
        }
        return SyncUtil.paresMongoData(value,field.getDataType());
    }

    abstract List<SqlData> getSqlData(String jsonData, MappingBean mappingBean, String condition);
}
