package com.wanmi.sbc.empower.api.provider.pay.unionb2b;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.unionb2b.UnionDirectRefundRequest;
import com.wanmi.sbc.empower.api.request.pay.unioncloud.UnionPayRequest;
import com.wanmi.sbc.empower.api.response.pay.RefundResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.Map;

/**
 * @program: shiyuan-service
 * @description: 银联支付相关接口
 * @author: zhangyong
 * @create: 2021-03-09 10:18
 **/
@FeignClient(value = "${application.empower.name}", contextId = "UnionB2BProvider")
public interface UnionB2BProvider {

    /**
     * 交易状态查询交易
     *
     * @param unionPay
     * @return 结果码
     */
    @PostMapping("/empower/${application.empower.version}/getUnionPayResult")
    BaseResponse<Map<String, String>> getUnionPayResult(@RequestBody UnionPayRequest unionPay);

    /**
     * 银联企业支付 同步回调添加交易数据
     *
     * @param resMap
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/unionCallBack")
    BaseResponse unionCallBack(@RequestBody Map<String, String> resMap);

    @PostMapping("/empower/${application.empower.version}/unionCallBackByTimeSeries")
    BaseResponse unionCallBackByTimeSeries(@RequestBody Map<String, String> resMap);

    /**
     * 银联企业支付 签名校验
     *
     * @param validateData 验签参数
     * @return 验签是否成功
     */
    @PostMapping("/empower/${application.empower.version}/unionCheckSign")
    BaseResponse<Boolean> unionCheckSign(@RequestBody Map<String, String> validateData);

    /**
     * 授信银联直接退款
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/unionDirectRefund")
    BaseResponse<RefundResponse> unionDirectRefund(@RequestBody @Valid UnionDirectRefundRequest request);

}
