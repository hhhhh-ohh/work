package com.wanmi.sbc.dbreplay.dao.canal;

import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-7
 * \* Time: 10:29
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Repository
public class CanalBaseDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void execute(String sql) {
        this.jdbcTemplate.execute(sql);
    }

    public void update(SqlData sqlData) {
        if(sqlData.getDaoValues().size()>1){
            this.jdbcTemplate.batchUpdate(sqlData.getSql(),sqlData.getDaoValues(),sqlData.getDaoArgsType());
        }else {
            this.jdbcTemplate.update(sqlData.getSql(), sqlData.getDaoValues().get(0), sqlData.getDaoArgsType());
        }
    }

    public void updateValues(SqlData sqlData) {
        if(sqlData.getDaoValues().size()>1){
            this.jdbcTemplate.batchUpdate(sqlData.getSql(),sqlData.getDaoValues());
        }else {
            this.jdbcTemplate.update(sqlData.getSql(), sqlData.getDaoValues().get(0));
        }
    }

    public void update(List<SqlData> list){
        for(SqlData sqlData : list){
            update(sqlData);
        }
    }
}
