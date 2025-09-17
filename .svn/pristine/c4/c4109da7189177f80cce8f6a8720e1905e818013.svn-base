package com.wanmi.sbc.vas.api.provider.vop.address;

import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * VOP地址增值服务
 *
 * @author wur
 * @date: 2021/5/12 16:45
 */
@FeignClient(value = "${application.vas.name}", contextId = "VopAddressProvider")
public interface VopAddressProvider {

    /**
     * VOP地址数据同步
     *
     * @author wur
     * @date: 2021/5/12 16:35
     * @return 操作结果
     */
    @PostMapping("/vop/${application.vas.version}/address/init")
    BaseResponse init();

    /**
    *
     * VOP地址映射
     * @author  wur
     * @date: 2021/5/14 10:53
     * @return
     **/
    @PostMapping("/vop/${application.vas.version}/address/mapping")
    BaseResponse mapping();

    /**
     * @description VOP地址映射[修正]
     * @author malianfeng
     * @date 2023/2/22 15:29
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/vop/${application.vas.version}/address/mapping-revise")
    BaseResponse mappingRevise();
}
