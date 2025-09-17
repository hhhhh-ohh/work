package com.wanmi.sbc.elastic.provider.impl.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsBrandProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandInitRequest;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsBrandSaveRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsBrandAddResponse;
import com.wanmi.sbc.elastic.goods.service.EsGoodsBrandService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2020/12/10 15:11
 * @description <p> </p>
 */
@RestController
public class EsGoodsBrandController implements EsGoodsBrandProvider {


    @Autowired
    private EsGoodsBrandService esGoodsBrandService;

    @Override
    public BaseResponse<EsGoodsBrandAddResponse> addGoodsBrandList(@RequestBody @Valid EsGoodsBrandSaveRequest request) {

        return esGoodsBrandService.addGoodsBrandList(request);
    }

    @Override
    public BaseResponse init(@RequestBody @Valid EsGoodsBrandInitRequest request) {
        if(CollectionUtils.isNotEmpty(request.getIdList())){
            request.setBrandIds(request.getIdList());
            request.setPageNum(0);
            request.setPageSize(request.getIdList().size());
        }
        esGoodsBrandService.init(request);
        return BaseResponse.SUCCESSFUL();
    }
}
