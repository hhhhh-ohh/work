package com.wanmi.sbc.elastic.provider.impl.goodsevaluate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goodsevaluate.EsGoodsEvaluateProvider;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateInitRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluatePageRequest;
import com.wanmi.sbc.elastic.goodsevaluate.service.EsGoodsEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/28 18:21
 * @description <p> 商品评价 </p>
 */
@RestController
public class EsGoodsEvaluateController implements EsGoodsEvaluateProvider {

    @Autowired
    private EsGoodsEvaluateService esGoodsEvaluateService;

    /**
     * 新增评价信息
     * @param request  {@link EsGoodsEvaluateAnswerRequest}
     * @return
     */

    @Override
    public BaseResponse add(@RequestBody @Valid EsGoodsEvaluateAnswerRequest request) {
        return esGoodsEvaluateService.add(request);
    }

    /**
     * 初始化评价管理
     * @param request  {@link EsGoodsEvaluatePageRequest}
     * @return
     */
    @Override
    public BaseResponse init(@RequestBody @Valid EsGoodsEvaluateInitRequest request) {
        esGoodsEvaluateService.init(request);
        return BaseResponse.SUCCESSFUL();
    }
}
