package com.wanmi.sbc.dbreplay.bean.canal;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-6
 * \* Time: 19:10
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Data
public class CanalData {

    private Long id;                     //canal数据id
    private String destination;         // 对应canal的实例或者MQ的topic
    private String groupId;             // 对应mq的group id
    private String database;            // 数据库或schema
    private String table;               // 表名
    private List<String> pkNames;       //主键
    private Boolean isDdl;              //是否为ddl语句
    private String type;                // 类型: INSERT UPDATE DELETE
    private Long   es;                  // 执行耗时
    private Long   ts;                  // 同步时间
    private String sql;                 // 执行的sql, dml sql为空
    private List<Map<String, Object>> data;                // 数据列表
    private List<Map<String, Object>> old;                 // 旧数据列表, 用于update, size和data的size一一对应
    private Map<String,Integer> sqlType;                    //字段对应的数据类型，int类型

    @Override
    public String toString() {
        return "Dml{" + "destination='" + destination + '\'' + ", database='" + database + '\'' + ", table='" + table
                + '\'' + ", type='" + type + '\'' + ", es=" + es + ", ts=" + ts + ", sql='" + sql + '\'' + ", data="
                + data + ", old=" + old + '}';
    }
}
