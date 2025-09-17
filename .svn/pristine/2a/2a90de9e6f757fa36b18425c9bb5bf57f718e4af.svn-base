package com.wanmi.sbc.common.tcc;

import java.sql.Connection;
import java.util.Date;

/**
 * @author zhanggaolei
 * @className TccFenceStoreDataBaseDAO
 * @description
 * @date 2022/7/13 16:45
 **/
public class TccFenceStoreDataBaseDAO implements TccFenceStore{

    private static volatile TccFenceStoreDataBaseDAO instance = null;
    public static TccFenceStore getInstance() {
        if (instance == null) {
            synchronized (TccFenceStore.class) {
                if (instance == null) {
                    instance = new TccFenceStoreDataBaseDAO();
                }
            }
        }
        return instance;
    }
    @Override
    public TccFenceDO queryTccFenceDO(Connection conn, String xid, Long branchId) {
        return null;
    }

    @Override
    public boolean insertTccFenceDO(Connection conn, TccFenceDO tccFenceDO) {
        return false;
    }

    @Override
    public boolean updateTccFenceDO(Connection conn, String xid, Long branchId, int newStatus, int oldStatus) {
        return false;
    }

    @Override
    public boolean deleteTccFenceDO(Connection conn, String xid, Long branchId) {
        return false;
    }

    @Override
    public int deleteTccFenceDOByDate(Connection conn, Date datetime) {
        return 0;
    }

    /**
     *
     * @param logTableName logTableName
     */
    @Override
    public void setLogTableName(String logTableName) {
        //empty method
    }
}
