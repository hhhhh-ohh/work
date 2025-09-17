package com.wanmi.sbc.dbreplay.service.canal;


import com.alibaba.fastjson2.JSONObject;

import com.wanmi.sbc.dbreplay.bean.canal.CanalData;
import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.utils.SyncUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-16
 * \* Time: 15:49
 * \* To change this template use File | Settings | File Templates.
 * \* Description:dml数据处理类
 * \
 */
@Service
public class DmlDataProcessService {
    /**
     * 拼接动态的insert语句
     * @param data
     * @return
     */
    public SqlData getInsertData(CanalData data){
        SqlData sqlData = null;
        if(data.getData()!=null){
            sqlData = new SqlData();
            StringBuilder sql = new StringBuilder("insert into ")
                    .append(data.getTable())
                    .append(" (");

            List<String> keys = SyncUtil.getKeys(data.getData().get(0));
            sql.append(StringUtils.join(keys,","))
                    .append(") values ")
                    .append(SyncUtil.getArgsString(keys.size()));

//            List<List<Object>> values = SyncUtil.getValues(data.getData(),keys);
            List<List<Object>> values = SyncUtil.getValues(data,keys);
            List<Integer> sqlType = keys.stream().map(key-> data.getSqlType().get(key)).collect(Collectors.toList());
            sqlData.setSql(sql.toString());
            sqlData.setSqlType(sqlType);
            sqlData.setValues(values);
        }
        return sqlData;
    }

    /**
     * 拼接动态update语句
     * @param data
     * @return
     */
    public SqlData getUpdateData(CanalData data){
        SqlData sqlData = null;
        if(data.getData()!=null){
            sqlData = new SqlData();
            StringBuilder sql = new StringBuilder("update ")
                    .append(data.getTable())
                    .append(" set ");
            List<String> pks = data.getPkNames();
            List<String> keys = SyncUtil.getKeys(data.getData().get(0));
            List<String> setKeys = new ArrayList<>();

            if(pks==null) {
                pks = new ArrayList<>();
            }
            for(String key: keys){
                if(!pks.contains(key)) {
                    setKeys.add(key);
                    sql.append(key);
                    sql.append("=?, ");
                }
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(" where 1=1 ");
            sql.append(SyncUtil.getWhereString(pks));
            List<String> reSortKeys = new ArrayList<>();
            reSortKeys.addAll(setKeys);
            reSortKeys.addAll(pks);

//            sqlData.setValues(SyncUtil.getValues(data.getData(),reSortKeys));
            sqlData.setValues(SyncUtil.getValues(data,reSortKeys));
            sqlData.setSqlType(reSortKeys.stream().map(key-> data.getSqlType().get(key)).collect(Collectors.toList()));
            sqlData.setSql(sql.toString());
        }
        return sqlData;
    }

    /**
     * 动态拼接存在则更新，不存在则插入
     * @param data
     * @return
     */
    public SqlData getSaveOrUpdateData(CanalData data){
        SqlData sqlData = getInsertData(data);
        List<String> updateKeys = SyncUtil.getKeys(data.getOld().get(0));
        List<Integer> sqlTypeList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(sqlData.getSql());
        sql.append(" ON DUPLICATE KEY UPDATE ");
        for(String updateKey : updateKeys){
            sql.append(updateKey);
            sql.append("=?, ");
            sqlTypeList.add(data.getSqlType().get(updateKey));
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sqlData.setSql(sql.toString());
       // List<List<Object>> duplicateValues = SyncUtil.getValues(data.getData(),updateKeys);
        List<List<Object>> duplicateValues = SyncUtil.getValues(data,updateKeys);
        List<List<Object>> values = sqlData.getValues();
        for (int i = 0; i <values.size() ; i++) {
            sqlData.getValues().get(i).addAll(duplicateValues.get(i));
        }
        sqlData.getSqlType().addAll(sqlTypeList);
        return sqlData;
    }

    /**
     * delete语句
     * @param data
     * @return
     */
    public SqlData getDeleteDate(CanalData data){
        SqlData sqlData = null;
        if(data.getData()!=null){
            sqlData = new SqlData();
            StringBuilder sql = new StringBuilder("delete from  ")
                    .append(data.getTable())
                    .append(" where 1=1 ");
            List<String> pks = data.getPkNames();
            sql.append(SyncUtil.getWhereString(pks));
           // sqlData.setValues(SyncUtil.getValues(data.getData(),pks));
            sqlData.setValues(SyncUtil.getValues(data,pks));
            sqlData.setSqlType(pks.stream().map(key-> data.getSqlType().get(key)).collect(Collectors.toList()));
            sqlData.setSql(sql.toString());
        }
        return sqlData;
    }

    public static void main(String args[]){
        String str = "{\"data\":[{\"goods_info_id\":\"2c93a4886a445a27016a445f78010001\",\"goods_id\":\"2c93a4886a445a27016a445f779f0000\",\"goods_info_name\":\"稻花香珍品1号\",\"goods_info_no\":\"8925268340\",\"goods_info_img\":null,\"goods_info_barcode\":null,\"stock\":\"0\",\"market_price\":\"3500.0\",\"cost_price\":null,\"create_time\":\"2019-04-22 17:27:48\",\"update_time\":\"2019-07-18 17:59:11\",\"added_time\":\"2019-04-22 17:27:48\",\"del_flag\":\"0\",\"added_flag\":\"0\",\"customer_id\":null,\"company_info_id\":\"5\",\"custom_flag\":\"0\",\"level_discount_flag\":\"0\",\"store_id\":\"123456861\",\"audit_status\":\"1\",\"company_type\":\"1\",\"alone_flag\":\"0\",\"small_program_code\":null,\"distribution_commission\":null,\"distribution_sales_count\":null,\"distribution_goods_audit\":\"0\",\"distribution_goods_audit_reason\":null,\"cate_id\":\"798\",\"commission_rate\":null,\"brand_id\":null}],\"database\":\"sbc-goods\",\"es\":1566266259000,\"id\":5343,\"isDdl\":false,\"mysqlType\":{\"goods_info_id\":\"varchar(32)\",\"goods_id\":\"varchar(32)\",\"goods_info_name\":\"varchar(255)\",\"goods_info_no\":\"varchar(45)\",\"goods_info_img\":\"varchar(255)\",\"goods_info_barcode\":\"varchar(45)\",\"stock\":\"bigint(10)\",\"market_price\":\"decimal(20,2)\",\"cost_price\":\"decimal(20,2)\",\"create_time\":\"datetime\",\"update_time\":\"datetime\",\"added_time\":\"datetime\",\"del_flag\":\"tinyint(4)\",\"added_flag\":\"tinyint(4)\",\"customer_id\":\"varchar(32)\",\"company_info_id\":\"bigint(11)\",\"custom_flag\":\"tinyint(4)\",\"level_discount_flag\":\"tinyint(4)\",\"store_id\":\"bigint(20)\",\"audit_status\":\"tinyint(4)\",\"company_type\":\"tinyint(4)\",\"alone_flag\":\"tinyint(4)\",\"small_program_code\":\"varchar(250)\",\"distribution_commission\":\"decimal(11,2)\",\"distribution_sales_count\":\"int(11)\",\"distribution_goods_audit\":\"tinyint(4)\",\"distribution_goods_audit_reason\":\"varchar(100)\",\"cate_id\":\"bigint(20)\",\"commission_rate\":\"decimal(4,2)\",\"brand_id\":\"bigint(20)\"},\"old\":[{\"goods_info_name\":\"稻花香珍品1号1\"}],\"pkNames\":[\"goods_info_id\"],\"sql\":\"\",\"sqlType\":{\"goods_info_id\":12,\"goods_id\":12,\"goods_info_name\":12,\"goods_info_no\":12,\"goods_info_img\":12,\"goods_info_barcode\":12,\"stock\":-5,\"market_price\":3,\"cost_price\":3,\"create_time\":93,\"update_time\":93,\"added_time\":93,\"del_flag\":-6,\"added_flag\":-6,\"customer_id\":12,\"company_info_id\":-5,\"custom_flag\":-6,\"level_discount_flag\":-6,\"store_id\":-5,\"audit_status\":-6,\"company_type\":-6,\"alone_flag\":-6,\"small_program_code\":12,\"distribution_commission\":3,\"distribution_sales_count\":4,\"distribution_goods_audit\":-6,\"distribution_goods_audit_reason\":12,\"cate_id\":-5,\"commission_rate\":3,\"brand_id\":-5},\"table\":\"goods_info\",\"ts\":1566266260367,\"type\":\"UPDATE\"}";
        CanalData canalData = JSONObject.parseObject(str,CanalData.class);
        DmlDataProcessService bean = new DmlDataProcessService();
        SqlData updateSql = bean.getSaveOrUpdateData(canalData);
        System.out.println(updateSql.getSql());
        System.out.println(bean.getUpdateData(canalData).getSql());
    }
}
