package com.wanmi.sbc.elastic.provider.impl.spu;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.spu.EsSpuQueryProvider;
import com.wanmi.sbc.elastic.api.request.spu.EsSpuPageRequest;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuIdListReponse;
import com.wanmi.sbc.elastic.api.response.spu.EsSpuPageResponse;
import com.wanmi.sbc.elastic.spu.serivce.EsSpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: dyt
 * @Date: Created In 18:19 2020/11/30
 * @Description: ES商品SPU服务实现
 */
@RestController
@Validated
public class EsSpuQueryController implements EsSpuQueryProvider {

    @Autowired
    private EsSpuService esSpuService;

    @Override
    public BaseResponse<EsSpuPageResponse> page(@RequestBody EsSpuPageRequest request) {
        return BaseResponse.success(esSpuService.page(request));
    }

    @Override
    public BaseResponse<Long> count(@RequestBody EsSpuPageRequest request) {
        return BaseResponse.success(esSpuService.count(request));
    }

    @Override
    public BaseResponse<EsSpuIdListReponse> spuIdList(@RequestBody EsSpuPageRequest request) {
        return BaseResponse.success(esSpuService.getSpuIdList(request));
    }
}
