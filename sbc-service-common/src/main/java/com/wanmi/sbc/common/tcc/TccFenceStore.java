package com.wanmi.sbc.common.tcc;

import java.sql.Connection;
import java.util.Date;

/**
 * @author zhanggaolei
 * @className TccFenceStore
 * @description
 * @date 2022/7/13 16:43
 **/
public interface TccFenceStore {
    /**
     * Query tcc fence do.
     * @param xid the global transaction id
     * @param branchId the branch transaction id
     * @return the tcc fence do
     */
    TccFenceDO queryTccFenceDO(Connection conn, String xid, Long branchId);

    /**
     * Insert tcc fence do boolean.
     * @param tccFenceDO the tcc fence do
     * @return the boolean
     */
    boolean insertTccFenceDO(Connection conn, TccFenceDO tccFenceDO);

    /**
     * Update tcc fence do boolean.
     * @param xid the global transaction id
     * @param branchId the branch transaction id
     * @param newStatus the new status
     * @return the boolean
     */
    boolean updateTccFenceDO(Connection conn, String xid, Long branchId, int newStatus, int oldStatus);

    /**
     * Delete tcc fence do boolean.
     * @param xid the global transaction id
     * @param branchId the branch transaction id
     * @return the boolean
     */
    boolean deleteTccFenceDO(Connection conn, String xid, Long branchId);

    /**
     * Delete tcc fence by datetime.
     * @param datetime datetime
     * @return the deleted row count
     */
    int deleteTccFenceDOByDate(Connection conn, Date datetime);

    /**
     * Set LogTable Name
     * @param logTableName logTableName
     */
    void setLogTableName(String logTableName);
}
