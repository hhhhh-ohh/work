package com.wanmi.sbc.elastic.api.provider.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>店铺评价查询服务Provider</p>
 *
 * @author xufeng
 * @date 2021-02-19 10:59:09
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsStoreEvaluateSumProvider")
public interface EsStoreEvaluateSumProvider {
    /**
     * 添加店铺评价信息API
     *
     * @author xufeng
     * @param request  {@link EsGoodsEvaluateAnswerRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/storeevaluatesum/add")
    BaseResponse add(@RequestBody @Valid EsStoreEvaluateSumAnswerRequest request);
}