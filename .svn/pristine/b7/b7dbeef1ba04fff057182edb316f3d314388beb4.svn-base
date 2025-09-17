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
import org.apache.commons.collections.MapUtils;
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
public class UpdateDataProcess extends DataProcess {

    JSONObject jsonObject = null;
    JSONObject conditionObject = null;
    /*Map<String, Object> singleMap = new HashMap<>();*/
    Map<String, Integer> dataType = new HashMap<>();
   /* List<Map<String, Object>> arrayList = new ArrayList<>();*/
    List<String> conditionFields = new ArrayList<>();
    Map<String, Object> conditionMap = new HashMap<>();
    List<String> keys = new ArrayList<>();
    MappingBean mappingBean = null;
    List<List<ColumnData>> dataList = null;
    RowBehavior type = RowBehavior.UPDATE;


    @Override
    public List<SqlData> getSqlData(String jsonData, MappingBean mappingBean, String condition){
        if(jsonData.contains("$unset")){            //移除字段的场景暂不考虑
            return null;
        }else {
            boolean setFlag = false;
            if(jsonData.contains("$set")) {        //更新部分值的场景
                jsonData = JsonUtil.delOutField(jsonData, "$set");
                setFlag = true;
            }
            jsonToMapping(jsonData,mappingBean,condition);
            if (CollectionUtils.isEmpty(dataList)) {
                return null;
            }
            if(setFlag){        //更新部分值的场景
                return setDataProcess();
            }else{                                      //replay全量替换的场景
                if(CollectionUtils.isNotEmpty(mappingBean.getReplayTypes()) && mappingBean.getReplayTypes().contains(ReplayType.UPDATE_REPLAY)){
                    MappingBean _mapingBean = new MappingBean();
                    _mapingBean.setMappingData(mappingBean.getMappingData());
                    _mapingBean.setPk(mappingBean.getPk());
                    _mapingBean.setReplayTable(mappingBean.getReplayTable());
                    _mapingBean.setReplayTypes(Arrays.asList(ReplayType.INSERT_REPLAY));
                    return new InsertDataProcess().getSqlData(jsonData,_mapingBean,condition);
                }
                return replayDataProcess();
            }
        }
    }

    /**
     * 全量替换的数据处理
     * @return
     */
    private List<SqlData> replayDataProcess() {
        List<SqlData> retList = new ArrayList<>();


        for(List<ColumnData> list : dataList){

            List<List<Object>> values = new ArrayList<>();
            List<Integer> sqlType = new ArrayList<>();
            keys = list.stream().map(ColumnData::getColumn).collect(Collectors.toList());
            if(mappingBean.getPk()!=null && !keys.containsAll(mappingBean.getPk())){
                log.info("{} Invalid update data3 ！" ,mappingBean.getReplayTable());
                continue;
            }
            if (conditionFields.containsAll(keys)) {
                log.info("{} Invalid update data4 ！",mappingBean.getReplayTable());
                continue;
            }
            StringBuilder sql = new StringBuilder("update ")
                    .append(mappingBean.getReplayTable())
                    .append(" set ")
                    .append(String.join("=?,",keys))
                    .append("=? ")
                    .append(" where 1=1 ")
                    .append(SyncUtil.getWhereString(conditionFields));
            List<Object> value = new ArrayList<>();
            Map<String,Object> cValueMap = new HashMap<>();
            for(ColumnData columnData : list){
                value.add(columnData.getValue());
                if(conditionFields.contains(columnData.getColumn())){
                    cValueMap.put(columnData.getColumn(),columnData.getValue());
                }
            }
            cValueMap.putAll(conditionMap);
            value.addAll(conditionFields.stream().map(str -> cValueMap.get(str)).collect(Collectors.toList()));;
            values.add(value);
            sqlType = keys.stream().map(conditionKey-> dataType.get(conditionKey)).collect(Collectors.toList());
            sqlType.addAll(conditionFields.stream().map(conditionKey-> dataType.get(conditionKey)).collect(Collectors.toList()));


             retList.add(SqlData.builder().sql(sql.toString()).sqlType(sqlType).values(values).build());
        }
        return retList;
    }

    /**
     * mongo更新某个字段的数据处理
     * @return
     */
    private List<SqlData> setDataProcess(){
        List<SqlData> retList = new ArrayList<>();

        for(List<ColumnData> list : dataList){
            keys = list.stream().map(ColumnData::getColumn).collect(Collectors.toList());
            if (conditionFields.containsAll(keys)) {
                log.info(" Invalid update data5 ！");
                return null;
            }
            StringBuilder sql = new StringBuilder("update ")
                    .append(mappingBean.getReplayTable())
                    .append(" set ")
                    .append(String.join("=?,",keys))
                    .append("=? ")
                    .append(" where 1=1 ");

            //移除无用的字段
            Iterator<String> iterator = conditionFields.iterator();
            while(iterator.hasNext()){
                String str = iterator.next();
                if(!keys.contains(str) && !conditionMap.containsKey(str)){
                    iterator.remove();
                }
            }
            sql.append(SyncUtil.getWhereString(conditionFields));
            List<Integer> sqlType = new ArrayList<>();
            List<List<Object>> values = new ArrayList<>();
            List<Object> value = new ArrayList<>();
            Map<String,Object> cValueMap = new HashMap<>();
            for(ColumnData columnData : list){
                value.add(columnData.getValue());
                if(conditionFields.contains(columnData.getColumn())){
                    cValueMap.put(columnData.getColumn(),columnData.getValue());
                }
            }
            cValueMap.putAll(conditionMap);
            value.addAll(conditionFields.stream().map(str -> cValueMap.get(str)).collect(Collectors.toList()));;
            values.add(value);
            sqlType = keys.stream().map(key-> dataType.get(key)).collect(Collectors.toList());
            sqlType.addAll(conditionFields.stream().map(conditionKey-> dataType.get(conditionKey)).collect(Collectors.toList()));


            retList.add(SqlData.builder().sql(sql.toString()).sqlType(sqlType).values(values).build());
        }

        return retList;
    }


    /**
     * 数据处理
     * @param jsonData
     * @param mBean
     * @param condition
     */
    private void jsonToMapping(String jsonData,MappingBean mBean,String condition){
        this.mappingBean = mBean;
        jsonObject = JSONObject.parseObject(jsonData);
        conditionObject = JSONObject.parseObject(condition);
        if (CollectionUtils.isNotEmpty(mappingBean.getPk())) {
            conditionFields.addAll(mappingBean.getPk());
        }
        dataList = getMappingData(jsonObject,mappingBean.getMappingData(),true);
        if(CollectionUtils.isEmpty(dataList)){
            return;
        }


    }

    /**
     * 获取mapping配置对应的回放字段的名称何值
     * @param jsonObject
     * @param mappingData
     * @return
     */
    private List<List<ColumnData>> getMappingData(JSONObject jsonObject, MappingData mappingData,boolean first){

        List<List<ColumnData>> retList = new ArrayList<>();
        List<ColumnData> singleList = new ArrayList<>();
        List<List<ColumnData>> arrayList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(mappingData.getSingleData())){
            for(MappingField field : mappingData.getSingleData()){
                if (JsonUtil.containsKey(jsonObject, field.getField())) {
                    Object value = getValue(jsonObject, field,type);
                    if(isExist[0]) {
                        ColumnData columnData = new ColumnData();
                        columnData.setColumn(field.getReplayField());
                        columnData.setValue(value);
                        putDataType(field);
                        singleList.add(columnData);
                    }
                }
                setConditionFields(field, first);
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
                            List<List<ColumnData>> resultList = getMappingData(jsonField, mappingData.getArrayData().getMappingData(), false);
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

    private void setConditionFields(MappingField field,boolean isFirst){
        if(isFirst) {
            if (JsonUtil.containsKey(conditionObject, field.getField())) {
                if (!conditionFields.contains(field.getReplayField())) {
                    conditionFields.add(field.getReplayField());
                    putDataType(field);
                    conditionMap.put(field.getReplayField(),getValue(conditionObject,field,type));
                }
            }
        }
    }
}
