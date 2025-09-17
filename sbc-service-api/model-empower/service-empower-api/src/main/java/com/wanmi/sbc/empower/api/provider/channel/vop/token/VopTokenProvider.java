package com.wanmi.sbc.empower.api.provider.channel.vop.token;

import com.wanmi.sbc.common.base.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @className VopTokenProvider
 * @description token更新provider
 * @author    张文昌
 * @date      2021/5/10 下午2:42
 */
@FeignClient(value = "${application.empower.name}", contextId = "VopTokenProvider")
public interface VopTokenProvider {
    /**
     * 获取tokenAPI
     *
     * @author zhangwenchang
     * @return 结果 {@link BaseResponse}
     */
    @PostMapping("/vop/${application.empower.version}/token/access")
    BaseResponse accessToken();

    /**
     * 刷新tokenAPI
     *
     * @author zhangwenchang
     * @return 结果 {@link BaseResponse}
     */
    @PostMapping("/vop/${application.empower.version}/token/refresh")
    BaseResponse refreshToken();
}
