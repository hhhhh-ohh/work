package com.wanmi.sbc.common.tcc;

import lombok.Data;

import java.util.Date;

/**
 * @author zhanggaolei
 * @className TccFenceDO
 * @description
 * @date 2022/7/13 15:12
 **/
@Data
public class TccFenceDO {
    /**
     * the global transaction id
     */
    private String xid;

    /**
     * the branch transaction id
     */
    private Long branchId;

    /**
     * the action name
     */
    private String actionName;

    /**
     * the tcc fence status
     * tried: 1; committed: 2; rollbacked: 3; suspended: 4
     */
    private Integer status;

    /**
     * create time
     */
    private Date gmtCreate;

    /**
     * update time
     */
    private Date gmtModified;

}
