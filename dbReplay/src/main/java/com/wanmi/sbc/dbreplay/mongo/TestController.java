package com.wanmi.sbc.dbreplay.mongo;

import com.wanmi.sbc.dbreplay.bean.capture.OplogData;
import com.wanmi.sbc.dbreplay.service.capture.MongoMappingReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * \* Author: zgl
 * \* Date: 2020-2-21
 * \* Time: 13:47
 * \* Description:
 * \
 */
@RestController
public class TestController {
    @Autowired
    private MongoMappingReplayService mongoMappingReplayService;

    @PostMapping("/test/mapping")
    public String  testMapping(@RequestBody OplogData oplogData){
        this.mongoMappingReplayService.replay(oplogData);
        return "SUCCESS";
    }
}
