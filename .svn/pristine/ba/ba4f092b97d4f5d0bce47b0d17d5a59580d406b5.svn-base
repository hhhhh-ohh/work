package com.wanmi.sbc.common.tcc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author zhanggaolei
 * @className TccFenceHandler
 * @description
 * @date 2022/7/13 13:55
 **/
public class TccFenceHandler {
    private TccFenceHandler() {
        throw new IllegalStateException("Utility class");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TccFenceHandler.class);

    private static final TccFenceStore TCC_FENCE_DAO = TccFenceStoreDataBaseDAO.getInstance();

    private static DataSource dataSource;

    private static TransactionTemplate transactionTemplate;

    private static final int MAX_THREAD_CLEAN = 1;

    private static final int MAX_QUEUE_SIZE = 500;

    private static final LinkedBlockingQueue<FenceLogIdentity> LOG_QUEUE = new LinkedBlockingQueue<>(MAX_QUEUE_SIZE);

    private static FenceLogCleanRunnable fenceLogCleanRunnable;

    private static ExecutorService logCleanExecutor;

    public static void setDataSource(DataSource dataSource) {
        TccFenceHandler.dataSource = dataSource;
    }

    public static void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        TccFenceHandler.transactionTemplate = transactionTemplate;
    }


    /**
     * Insert TCC fence log
     *
     * @param conn     the db connection
     * @param xid      the xid
     * @param branchId the branchId
     * @param status   the status
     * @return the boolean
     */
    private static boolean insertTCCFenceLog(Connection conn, String xid, Long branchId, String actionName, Integer status) {
        TccFenceDO tccFenceDO = new TccFenceDO();
        tccFenceDO.setXid(xid);
        tccFenceDO.setBranchId(branchId);
        tccFenceDO.setActionName(actionName);
        tccFenceDO.setStatus(status);
        return TCC_FENCE_DAO.insertTccFenceDO(conn, tccFenceDO);
    }

    /**
     * Delete TCC Fence
     *
     * @param xid      the global transaction id
     * @param branchId the branch transaction id
     * @return the boolean
     */
    public static boolean deleteFence(String xid, Long branchId) {
        return transactionTemplate.execute(status -> {
            boolean ret = false;
            try {
                Connection conn = DataSourceUtils.getConnection(dataSource);
                ret = TCC_FENCE_DAO.deleteTccFenceDO(conn, xid, branchId);
            } catch (RuntimeException e) {
                status.setRollbackOnly();
                LOGGER.error("delete fence log failed, xid: {}, branchId: {}", xid, branchId, e);
            }
            return ret;
        });
    }

    /**
     * Delete TCC Fence By Datetime
     *
     * @param datetime datetime
     * @return the deleted row count
     */
    public static int deleteFenceByDate(Date datetime) {
        return transactionTemplate.execute(status -> {
            try {
                Connection conn = DataSourceUtils.getConnection(dataSource);
                return TCC_FENCE_DAO.deleteTccFenceDOByDate(conn, datetime);
            } catch (RuntimeException e) {
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    private static void addToLogCleanQueue(final String xid, final long branchId) {
        FenceLogIdentity logIdentity = new FenceLogIdentity();
        logIdentity.setXid(xid);
        logIdentity.setBranchId(branchId);
        try {
            LOG_QUEUE.add(logIdentity);
        } catch (Exception e) {
            LOGGER.warn("Insert tcc fence record into queue for async delete error,xid:{},branchId:{}", xid, branchId, e);
        }
    }

    /**
     * clean fence log that has the final status runnable.
     *
     * @see TccFenceConstant
     */
    private static class FenceLogCleanRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {

                try {
                    FenceLogIdentity logIdentity = LOG_QUEUE.take();
                    boolean ret = TccFenceHandler.deleteFence(logIdentity.getXid(), logIdentity.getBranchId());
                    if (!ret) {
                        LOGGER.error("delete fence log failed, xid: {}, branchId: {}", logIdentity.getXid(), logIdentity.getBranchId());
                    }
                } catch (InterruptedException e) {
                    LOGGER.error("take fence log from queue for clean be interrupted", e);
                } catch (Exception e) {
                    LOGGER.error("exception occur when clean fence log", e);
                }
            }
        }
    }

    private static class FenceLogIdentity {
        /**
         * the global transaction id
         */
        private String xid;

        /**
         * the branch transaction id
         */
        private Long branchId;

        public String getXid() {
            return xid;
        }

        public Long getBranchId() {
            return branchId;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }

        public void setBranchId(Long branchId) {
            this.branchId = branchId;
        }
    }
}
