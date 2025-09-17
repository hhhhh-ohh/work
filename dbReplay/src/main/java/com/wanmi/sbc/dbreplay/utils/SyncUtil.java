package com.wanmi.sbc.dbreplay.utils;


import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.dbreplay.bean.canal.CanalData;
import com.wanmi.sbc.dbreplay.bean.capture.mapping.ColumnData;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-6
 * \* Time: 19:32
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class SyncUtil {

    private final static String MONGO_DAT_FORMAT ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" ;
    public static String removeDatabaseDDLSQL(String database,String sql){
        return sql.replaceFirst(database+".","");
    }

    public static List<String> getKeys(Map<String,Object> map){
        Set<String> keySet = map.keySet();
        return new ArrayList<>(keySet);
    }

    public static List<List<Object>> getValues(List<Map<String,Object>> datas,List<String> keys){
        List<List<Object>> values = new ArrayList<>();
        datas.stream().forEach(map -> {
            values.add(keys.stream().map(key -> map.get(key)).collect(Collectors.toList()));
        });
        return values;
    }

    public static List<List<Object>> getValues(CanalData canalData, List<String> keys){
        List<List<Object>> values = new ArrayList<>();

        canalData.getData().stream().forEach(map -> {
            values.add(keys.stream().map(key -> paresData(map.get(key),canalData.getSqlType().get(key))).collect(Collectors.toList()));
        });
        return values;
    }

    public static List<List<Object>> getValues(List<Map<String,Object>> datas,List<String> setKeys,List<String> pks){
        List<List<Object>> values = new ArrayList<>();
        datas.forEach(map -> {
            List<Object> setValues = getValues(map,setKeys);
            List<Object> pkValues = getValues(map,pks);
            List<Object> rowValues = new ArrayList<>();
            rowValues.addAll(setValues);
            rowValues.addAll(pkValues);
            values.add(rowValues);
        });
        return values;
    }

    public static List<Object> getValues(Map<String,Object> data,List<String> keys){
        List<Object> values = new ArrayList<>();
        for(String key: keys){
            values.add(data.get(key));
        }
        return values;
    }

    public static String getArgsString(int length){
        StringBuilder sb = new StringBuilder();

        sb.append("(");
        for(int lengthIndex=0;lengthIndex<length;lengthIndex++ ){
            sb.append("?");
            if(lengthIndex<length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static String getWhereString(List<String> list){
        StringBuilder str = new StringBuilder();
        for(String key : list){
            str.append(" and ")
                    .append(key)
                    .append("=? ");

        }
        return str.toString();
    }

    public static String removeLastChart(String str){
        return str.substring(0,str.length()-1);
    }

    public static String getMappingTableName(String tableName){
        return "replay_"+tableName;
    }

    public static Object paresData(Object value,int sqlType){
        switch (sqlType){
            case Types.DECIMAL:
                if(value!=null) {
                    return new BigDecimal(String.valueOf(value));
                }else{
                    return value;
                }
            default:
                return value;
        }
    }
    public static Object paresMongoData(Object value,int sqlType){
        if(value==null){
            return null;
        }
        switch (sqlType){
            case Types.BIGINT:
                if(value instanceof JSONObject){
                    value = ((JSONObject) value).get("$numberLong");
                }
                return value;
            case Types.TINYINT:
                if(value instanceof Boolean){
                    value = (Boolean)value?1:0;
                }
                return value;
            case Types.DECIMAL:
                if(value instanceof JSONObject){
                    value = ((JSONObject) value).get("$numberDecimal");
                }
                return new BigDecimal(String.valueOf(value));
            case Types.TIMESTAMP:
                if(value instanceof JSONObject){
                    value = ((JSONObject) value).get("$date");
                }
                return dateParse(value);
            default:
                return value;
        }
    }

    private static LocalDateTime dateParse(Object value){
        if(value!=null) {
            if (value instanceof String) {
                //过滤无效数据
                if (((String) value).equals("Invalid date") || StringUtils.isEmpty((String)value)) {
                    return null;
                }
                if (DateUtil.isValidFormat(String.valueOf(value), MONGO_DAT_FORMAT)) {
                    return DateUtil.parseZone(String.valueOf(value), MONGO_DAT_FORMAT);
                } else if (DateUtil.isValidFormat(String.valueOf(value), DateUtil.FMT_TIME_4)) {
                    return DateUtil.parseZone(String.valueOf(value), DateUtil.FMT_TIME_4);
                }else {
                    return DateUtil.parseZone(String.valueOf(value), DateUtil.FMT_TIME_1);
                }
            }
            if (value instanceof Long) {
                return LocalDateTime.ofEpochSecond(Long.parseLong(String.valueOf(value)) / 1000, 0, ZoneOffset.ofHours(8));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String,ColumnData> map = new HashMap<>();
        ColumnData columnData = new ColumnData();
        columnData.setColumn("123");
        columnData.setValue("test");
        map.put("t",columnData);
        ColumnData c1 = map.get("t");
        c1.setValue("test2");

        System.out.println(map.get("t").getValue());
    }
}