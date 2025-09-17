package com.wanmi.sbc.dbreplay.service.canal;

import com.wanmi.sbc.dbreplay.bean.canal.CanalData;
import com.wanmi.sbc.dbreplay.bean.canal.SqlData;
import com.wanmi.sbc.dbreplay.common.constants.DmlType;
import com.wanmi.sbc.dbreplay.dao.canal.CanalBaseDao;
import com.wanmi.sbc.dbreplay.utils.SyncUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * \* Created with IntelliJ IDEA.
 * \* User: zgl
 * \* Date: 2019-8-6
 * \* Time: 20:05
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class ReplayService {


    @Autowired
    private CanalBaseDao baseDao;
    @Autowired
    private DmlDataProcessService dmlDataProcessService;

    @Autowired
    private DdlDataProcessService ddlDataProcessService;

    @Autowired
    private CanalProcess canalProcess;

    /**
     * 回放程序如果不考虑事物的情况，或者批量插入的情况下，可以启用
     * 线程池，异步处理
     * @param data
     * @return
     */
    //@Async("etlExecutor")  /**线程池**/
    public boolean replay(CanalData data){

        if(data.getIsDdl()){
            ddl(data);
            return true;
        }else{
            //允许将多个表回放到一个表中，适用于分库分表的数据，需要保证字段一致
            String tableName = canalProcess.getReplayTableName(data.getTable());
            data.setTable(SyncUtil.getMappingTableName(tableName));
            switch (data.getType()){
                case DmlType.INSERT:
                    insert(data);
                    break;
                case DmlType.UPDATE:
                    update(data);
                    break;
                case DmlType.DELETE:
                    delete(data);
                    break;
                default:
                    break;
            }
        }

        return true;
    }

    /**
     * ddl
     */
    public void ddl(CanalData data){

        this.baseDao.execute(ddlDataProcessService.getDdlSql(data));
    }


    /**
     * 插入逻辑
     * @param data
     */
    public void insert(CanalData data){
        SqlData sqlData = dmlDataProcessService.getInsertData(data);
        if(sqlData!=null){
            this.baseDao.update(sqlData);
        }
    }

    /**
     * 更新逻辑
     * @param data
     */
    public void update(CanalData data){
        //更新
    /*    SqlData sqlData = dmlDataProcessService.getUpdateData(data);*/
        //存在更新，不存在则插入
        SqlData sqlData = dmlDataProcessService.getSaveOrUpdateData(data);
        if(sqlData!=null){
            this.baseDao.update(sqlData);
        }
    }

    /**
     * 删除逻辑
     * @param data
     */
    public void delete(CanalData data){
        SqlData sqlData = dmlDataProcessService.getDeleteDate(data);
        if(sqlData!=null){
            this.baseDao.update(sqlData);
        }
    }
}
