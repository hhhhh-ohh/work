package com.wanmi.sbc.elastic.provider.impl.goodsevaluate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateQueryProvider;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import com.wanmi.sbc.elastic.api.response.goodsevaluate.EsGoodsEvaluatePageResponse;
import com.wanmi.sbc.elastic.goodsevaluate.service.EsGoodsEvaluateQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/28 14:31
 * @description <p> 商品评价查询 </p>
 */
@RestController
public class EsGoodsEvaluateQueryController implements EsGoodsEvaluateQueryProvider {

    @Autowired
    private EsGoodsEvaluateQueryService esGoodsEvaluateQueryService;

    /**
     * 分页查询商品评价信息
     * @param request 分页请求参数和筛选对象 {@link EsGoodsEvaluatePageRequest}
     * @return
     */
    @Override
    public BaseResponse<EsGoodsEvaluatePageResponse> page(@RequestBody @Valid EsGoodsEvaluatePageRequest request) {
        return esGoodsEvaluateQueryService.page(request);
    }
}
