package com.wanmi.sbc.elastic.provider.impl.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsStockProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsListSpuSyncRequest;
import com.wanmi.sbc.elastic.goods.service.EsGoodsStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 18:19 2020/11/30
 * @Description: TODO
 */
@RestController
@Validated
public class EsGoodsStockController implements EsGoodsStockProvider {

    @Autowired
    private EsGoodsStockService esGoodsStockService;

    @Override
    public BaseResponse sync(@RequestBody @Valid EsGoodsListSpuSyncRequest request) {
        esGoodsStockService.syncBySpuIdList(request);
        esGoodsStockService.syncStockBySkuId(request);
        return BaseResponse.SUCCESSFUL();
    }
}
