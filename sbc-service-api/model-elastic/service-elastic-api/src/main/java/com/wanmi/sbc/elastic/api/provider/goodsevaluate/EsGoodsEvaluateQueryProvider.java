package com.wanmi.sbc.elastic.api.provider.goodsevaluate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import com.wanmi.sbc.elastic.api.response.goodsevaluate.EsGoodsEvaluatePageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/28 14:18
 * @description <p> 商品评价查询 </p>
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsGoodsEvaluateQueryProvider")
public interface EsGoodsEvaluateQueryProvider {

    /**
     * 分页查询商品评价API
     *
     * @author liutao
     * @param request 分页请求参数和筛选对象 {@link EsGoodsEvaluatePageRequest}
     * @return 商品评价分页列表信息 {@link EsGoodsEvaluatePageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/goodsevaluate/page")
    BaseResponse<EsGoodsEvaluatePageResponse> page(@RequestBody @Valid EsGoodsEvaluatePageRequest request);

}
