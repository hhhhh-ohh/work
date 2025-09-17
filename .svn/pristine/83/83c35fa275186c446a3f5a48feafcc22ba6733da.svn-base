package com.wanmi.sbc.vop;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.mqconsumer.EmpowerMqConsumerProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huangzhao
 * VOP初始化同步SPU
 */
@Tag(name =  "VOP初始化同步SPU", description =  "VopInitController")
@RestController
@Validated
@RequestMapping(value = "/vop")
public class VopInitController {

    @Autowired
    private EmpowerMqConsumerProvider empowerMqConsumerProvider;

    @Operation(summary = "概况数据查询")
    @GetMapping("/spu/init")
    public BaseResponse initSyncSpu() {
        empowerMqConsumerProvider.initSyncSpu();
        return BaseResponse.SUCCESSFUL();
    }
}
