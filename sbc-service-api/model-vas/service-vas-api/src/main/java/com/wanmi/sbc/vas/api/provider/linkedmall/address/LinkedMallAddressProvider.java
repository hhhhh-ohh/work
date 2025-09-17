package com.wanmi.sbc.vas.api.provider.linkedmall.address;

import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "${application.vas.name}", contextId = "LinkedMallAddressProvider")
public interface LinkedMallAddressProvider {

    /**
     * 初始化LinkedMall地址
     * @return
     */
    @PostMapping("/linkedmall/${application.vas.version}/address/init")
    BaseResponse init();

    /**
     *
     * LinkedMall地址映射
     * @return
     **/
    @PostMapping("/linkedmall/${application.vas.version}/address/mapping")
    BaseResponse mapping();
}
