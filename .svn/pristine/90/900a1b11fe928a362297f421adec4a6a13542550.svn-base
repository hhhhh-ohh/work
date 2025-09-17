package com.wanmi.sbc.empower.api.provider.pay.unioncloud;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.BasePayRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @program: shiyuan-service
 * @description: 银联支付相关接口
 * @author: zhangyong
 * @create: 2021-03-09 10:18
 **/
@FeignClient(value = "${application.empower.name}", contextId = "UnionCloudPayProvider")
public interface UnionCloudPayProvider {

    /***
     * 银联加密公钥更新查询接口
     *
     * @date 14:46 2021/3/15
     * @author zhangyong
      * @param
     * @return   {@link BaseResponse < Map< String, String>>}
     */
    @PostMapping("/empower/${application.empower.version}/get-update-EncryptCer")
    BaseResponse<Map<String, String>> getUpdateEncryptCer(@RequestBody UnionPayRequest unionPay);

    /**
     * 银联app对接获取tn
     *
     * @param basePayRequest
     * @return 请求HTML
     */
    @PostMapping("/empower/${application.empower.version}/get-tn")
    BaseResponse<String> getTn(@RequestBody BasePayRequest basePayRequest);
}
