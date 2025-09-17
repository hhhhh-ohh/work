package com.wanmi.sbc.empower.api.provider.mqconsumer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.mqconsumer.EmpowerMqConsumerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;


/**
 * @author lvzhenwei
 * @className VasMqConsumerProvider
 * @description vas消费接口
 * @date 2021/8/18 11:16 上午
 **/
@FeignClient(value = "${application.empower.name}",contextId = "EmpowerMqConsumerProvider")
public interface EmpowerMqConsumerProvider {

    @PostMapping("/empower/${application.empower.version}/mq/consumer/goods-temp-sync-over")
    BaseResponse goodsTempSyncOver(@RequestBody @Valid EmpowerMqConsumerRequest request);

    /**
     * VOP初始化同步SPU
     */
    @PostMapping("/empower/${application.empower.version}/vop/spu/init")
    BaseResponse initSyncSpu();
}
