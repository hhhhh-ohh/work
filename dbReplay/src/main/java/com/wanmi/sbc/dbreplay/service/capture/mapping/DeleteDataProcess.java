package com.wanmi.sbc.dbreplay.service.capture.mapping;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.bean.capture.RowBehavior;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingBean;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingField;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * \* Author: zgl
 * \* Date: 2020-2-24
 * \* Time: 15:05
 * \* Description:
 * \
 */
@Slf4j
public class DeleteDataProcess extends DataProcess {

    RowBehavior type = RowBehavior.DELETE;

    @Override
    public List<SqlData> getSqlData(String jsonData, MappingBean mappingBean, String condition){
        List<SqlData> retList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        MappingField idField = null;
        for(MappingField field : mappingBean.getMappingData().getSingleData()){
            if(field.getField().equals("_id")){         //因删除只有_id属性所以只取_id作为where条
                idField = field;
            }
        }
        if(idField !=null ){

            Object fieldValue = getValue(jsonObject, idField,type);
            StringBuilder sql = new StringBuilder(" delete from ")
                    .append(mappingBean.getReplayTable())
                    .append( " where ")
                    .append( idField.getReplayField() )
                    .append(" = ? ");
            List<Object> value = new ArrayList<>();
            List<List<Object>> values = new ArrayList<>();
            List<Integer> sqlType = new ArrayList<>();
            value.add(fieldValue);
            values.add(value);
            sqlType.add(idField.getDataType());
            retList.add(SqlData.builder().sql(sql.toString()).values(values).sqlType(sqlType).build());
        }
        return retList;
    }
}
