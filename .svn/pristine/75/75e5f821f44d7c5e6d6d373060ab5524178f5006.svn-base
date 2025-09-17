package com.wanmi.sbc.mongo.oplog.position;

import com.wanmi.sbc.mongo.oplog.comm.MongoCaptureLifeCycle;
import com.wanmi.sbc.mongo.oplog.data.Meta;

/**
 * @author zhanggaolei
 */
public interface PositionManager extends MongoCaptureLifeCycle {

    /**
     * 获取 cursor 游标
     */
    Meta getCursor() ;

    /**
     * 更新 cursor 游标
     */
    void updateCursor(Meta meta);


    /**
     * 获得该client最新的一个位置
     */
    void removeCursor();

}
