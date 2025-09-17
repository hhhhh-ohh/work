package com.wanmi.sbc.dbreplay.service.capture.mapping;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.bean.capture.RowBehavior;
import com.wanmi.sbc.dbreplay.bean.capture.constants.ReplayType;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.ColumnData;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingBean;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingData;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.MappingField;
import com.wanmi.sbc.dbreplay.utils.JsonUtil;
import com.wanmi.sbc.dbreplay.utils.SyncUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * \* Author: zgl
 * \* Date: 2020-2-24
 * \* Time: 15:05
 * \* Description:
 * \
 */
@Slf4j
public class InsertDataProcess extends DataProcess {
    private static final String MONGO_ID = "_id";
    private Map<String,Integer> dataType = new HashMap<>();
    private String id = null;
    private RowBehavior type = RowBehavior.INSERT;
    @Override
    public List<SqlData> getSqlData(String jsonData, MappingBean mappingBean, String condition){
        List<SqlData> retList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        List<List<ColumnData>> dataList = getMappingData(jsonObject,mappingBean.getMappingData());
        if(CollectionUtils.isEmpty(dataList)){
            return null;
        }


        for(List<ColumnData> list : dataList){
            List<String> keys = list.stream().map(ColumnData::getColumn).collect(Collectors.toList());
            //判断是不是主键都不存在
            if(mappingBean.getPk()!=null && !keys.containsAll(mappingBean.getPk())){
                log.info("{} Invalid update data1 ！" ,mappingBean.getReplayTable());
                continue;
            }
            List<String> pk = new ArrayList<>();
            if(StringUtils.isNotEmpty(id)) {
                pk.add(id);
            }
            if(CollectionUtils.isNotEmpty(mappingBean.getPk())){
                pk.addAll(mappingBean.getPk());
            }
            //判断是不是除了主键没有其他的数据，则认为是无效数据
            if(pk.containsAll(keys)){
                log.info("{} Invalid update data2 ！",mappingBean.getReplayTable());
                continue;
            }
            List<List<Object>> values = new ArrayList<>();
            values.add(list.stream().map(ColumnData::getValue).collect(Collectors.toList()));
            //拼装sql
            StringBuilder sql = new StringBuilder("insert into ")
                    .append(mappingBean.getReplayTable())
                    .append(" (")
                    .append(String.join(",",keys))
                    .append(") values ")
                    .append(SyncUtil.getArgsString(keys.size()));

            List<Integer> sqlType = keys.stream().map(key-> dataType.get(key)).collect(Collectors.toList());
            if(CollectionUtils.isNotEmpty(mappingBean.getReplayTypes()) && mappingBean.getReplayTypes().contains(ReplayType.INSERT_REPLAY)){
                StringBuilder deleteSql = new StringBuilder("delete from ")
                        .append(mappingBean.getReplayTable())
                        .append(" where 1=1 ")
                        .append(SyncUtil.getWhereString(pk));
                Map<String,Object> dataMap = list.stream().collect(Collectors.toMap(ColumnData::getColumn,ColumnData::getValue));
                List<Object> pkValues = pk.stream().map(key-> dataMap.get(key)).collect(Collectors.toList());
                List<Integer> pkSqlTypes = pk.stream().map(key->dataType.get(key)).collect(Collectors.toList());
                retList.add(SqlData.builder().sql(deleteSql.toString())
                        .sqlType(pkSqlTypes)
                        .values(Arrays.asList(pkValues))
                        .build());
            }

            retList.add(SqlData.builder().sql(sql.toString()).sqlType(sqlType).values(values).build());

        }

        return retList;
    }

    /**
     * 获取mapping配置对应的回放字段的名称何值
     * @param jsonObject
     * @param mappingData
     * @return
     */
    private List<List<ColumnData>> getMappingData(JSONObject jsonObject, MappingData mappingData){
        List<List<ColumnData>> retList = new ArrayList<>();
        List<ColumnData> singleList = new ArrayList<>();
        List<List<ColumnData>> arrayList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mappingData.getSingleData())){
            for(MappingField field : mappingData.getSingleData()){
                Object value = getValue(jsonObject,field,type);
                if(isExist[0]) {
                    ColumnData columnData = new ColumnData();
                    columnData.setColumn(field.getReplayField());
                    columnData.setValue(value);
                    putDataType(field);
                    singleList.add(columnData);
                    //将主键放提取出来
                    if (field.getField().equals(MONGO_ID) && StringUtils.isEmpty(id)) {
                        id = field.getReplayField();
                    }
                }
            }
        }
        if(mappingData.getArrayData()!=null && mappingData.getArrayData().getArrayRoot() != null){
            JSONArray jsonArray = (JSONArray) JsonUtil.getValue(jsonObject,mappingData.getArrayData().getArrayRoot());
            if(jsonArray != null && mappingData.getArrayData()!=null) {
                if(mappingData.getArrayData().getIsBaseType() && mappingData.getArrayData().getBaseTypeMapping()!=null){
                    List<List<ColumnData>> tempList = new ArrayList<>();
                    for(Object object : jsonArray){

                        List<ColumnData> _columnDatas = new ArrayList<>();
                        for(ColumnData columnData :singleList){
                            _columnDatas.add(columnData);
                        }
                        ColumnData _columnData = new ColumnData();
                        _columnData.setValue(getValue(object,mappingData.getArrayData().getBaseTypeMapping()));
                        _columnData.setColumn(mappingData.getArrayData().getBaseTypeMapping().getReplayField());
                        putDataType(mappingData.getArrayData().getBaseTypeMapping());
                        _columnDatas.add(_columnData);
                        tempList.add(_columnDatas);
                    }

                    return tempList;
                }
                if(mappingData.getArrayData().getMappingData()!=null) {
                    for (Object object : jsonArray) {
                        JSONObject jsonField = (JSONObject) object;
                        //递归获取字段
                        if (mappingData.getArrayData().getMappingData() != null) {
                            List<List<ColumnData>> resultList = getMappingData(jsonField, mappingData.getArrayData().getMappingData());
                            if (CollectionUtils.isNotEmpty(resultList)) {
                                arrayList.addAll(resultList);
                            }
                        }
                    }
                }
            }
        }
        if(CollectionUtils.isNotEmpty(singleList) && CollectionUtils.isNotEmpty(arrayList)){
            for(List<ColumnData> list : arrayList){
                list.addAll(singleList);
                retList.add(list);
            }
        }else if(CollectionUtils.isNotEmpty(singleList) && CollectionUtils.isEmpty(arrayList)){
            retList.add(singleList);
        }else if(CollectionUtils.isEmpty(singleList) && CollectionUtils.isNotEmpty(arrayList)){
            retList.addAll(arrayList);
        }
        return retList;
    }

    private void putDataType(MappingField field){
        if (!dataType.containsKey(field.getReplayField())) {
            dataType.put(field.getReplayField(), field.getDataType());
        }
    }
}
