package com.wanmi.sbc.dbreplay.bean.capture;

import com.wanmi.sbc.dbreplay.service.capture.mapping.DataProcess;
import com.wanmi.sbc.dbreplay.service.capture.mapping.DeleteDataProcess;
import com.wanmi.sbc.dbreplay.service.capture.mapping.InsertDataProcess;
import com.wanmi.sbc.dbreplay.service.capture.mapping.UpdateDataProcess;

/**
 * 数据操作行为
 */
public enum RowBehavior {

    /**
     * 插入操作
     */
    INSERT{
        public DataProcess process() {
            return new InsertDataProcess();
        }
    },

    /**
     * 更新操作
     */
    UPDATE{
        public DataProcess process() {
            return new UpdateDataProcess();
        }
    },

    /**
     * 删除操作
     */
    DELETE{
        public DataProcess process() {
            return new DeleteDataProcess();
        }
    };

    public abstract DataProcess process();
}
