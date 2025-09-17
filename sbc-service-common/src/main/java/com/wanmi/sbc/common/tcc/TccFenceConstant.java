package com.wanmi.sbc.common.tcc;

/**
 * @author zhanggaolei
 * @className TccFenceConstant
 * @description
 * @date 2022/7/13 15:23
 **/
public class TccFenceConstant {
    private TccFenceConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * PHASE 1: The Commit tried.
     */
    public static final int STATUS_TRIED = 1;

    /**
     * PHASE 2: The Committed.
     */
    public static final int STATUS_COMMITTED = 2;

    /**
     * PHASE 2: The Rollbacked.
     */
    public static final int STATUS_ROLLBACKED = 3;

    /**
     * Suspended status.
     */
    public static final int STATUS_SUSPENDED = 4;
}
