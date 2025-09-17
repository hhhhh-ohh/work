package com.wanmi.sbc.dbreplay.service.capture;

import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.service.capture.mapping.MappingReplayProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * \* Author: zgl
 * \* Date: 2020-2-22
 * \* Time: 15:35
 * \* Description:
 * \
 */
@Service
public class MongoMappingReplayService {

    @Autowired
    private MappingReplayProcessService mappingReplayService;

    @Transactional(rollbackFor = Exception.class)
    public void replay(OplogData oplogData) {
        mappingReplayService.process(oplogData);
    }
}
