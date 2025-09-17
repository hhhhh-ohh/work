package com.wanmi.sbc.elastic.provider.impl.customer;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.customer.EsStoreEvaluateSumProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsStoreEvaluateSumAnswerRequest;
import com.wanmi.sbc.elastic.api.request.goodsevaluate.EsGoodsEvaluateAnswerRequest;
import com.wanmi.sbc.elastic.customer.service.EsStoreEvaluateSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xufeng
 * @date 2021/02/19 18:21
 * @description <p> 店铺评价 </p>
 */
@RestController
public class EsStoreEvaluateSumController implements EsStoreEvaluateSumProvider {

    @Autowired
    private EsStoreEvaluateSumService esStoreEvaluateSumService;

    /**
     * 新增评价信息
     * @param request  {@link EsGoodsEvaluateAnswerRequest}
     * @return
     */

    @Override
    public BaseResponse add(@RequestBody @Valid EsStoreEvaluateSumAnswerRequest request) {
        return esStoreEvaluateSumService.add(request);
    }
}
