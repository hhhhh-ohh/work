package com.wanmi.sbc.elastic.api.provider.goodsevaluate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateInitRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/28 18:14
 * @description <p> 商品评价 </p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsGoodsEvaluateProvider")
public interface EsGoodsEvaluateProvider {

    /**
     * 添加商品评价信息API
     *
     * @author liutao
     * @param request  {@link EsGoodsEvaluateAnswerRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/goodsevaluate/add")
    BaseResponse add(@RequestBody @Valid EsGoodsEvaluateAnswerRequest request);


    /**
     * 添加商品评价信息API
     *
     * @author liutao
     * @param request  {@link EsGoodsEvaluatePageRequest}
     */
    @PostMapping("/elastic/${application.elastic.version}/goodsevaluate/init")
    BaseResponse init(@RequestBody @Valid EsGoodsEvaluateInitRequest request);

}
