package com.wanmi.sbc.dbreplay.service.canal;

import com.wanmi.sbc.dbreplay.bean.canal.CanalData;
import com.wanmi.sbc.dbreplay.utils.SyncUtil;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-16
 * \* Time: 15:51
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class DdlDataProcessService {
    /**
     * ddl语句的处理逻辑，因为目标库固定，所以需要过滤下database，
     * 也可以维护mapping来做
     * @param data
     * @return
     */
    public String getDdlSql(CanalData data){
        String ddlSql = data.getSql().replaceAll("`","");
        if(ddlSql.indexOf(data.getDatabase()+"."+data.getTable())>0){

            return SyncUtil.removeDatabaseDDLSQL(data.getDatabase(),ddlSql)
                    .replaceFirst(data.getTable(),SyncUtil.getMappingTableName(data.getTable()));
        }else{
            return ddlSql.replaceFirst(data.getTable(),SyncUtil.getMappingTableName(data.getTable()));
        }
    }
}
