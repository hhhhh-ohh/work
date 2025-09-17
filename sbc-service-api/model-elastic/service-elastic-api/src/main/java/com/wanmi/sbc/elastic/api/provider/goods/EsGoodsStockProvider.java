package com.wanmi.sbc.elastic.api.provider.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsListSpuSyncRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: songhanlin
 * @Date: Created In 18:19 2020/11/30
 * @Description: TODO
 */
@FeignClient(value = "${application.elastic.name}", contextId = "EsGoodsStockProvider")
public interface EsGoodsStockProvider {

    /**
     * 局部同步库存ES
     * @param request 同步ES库存
     * @return 操作结果
     */
    @PostMapping("/elastic/${application.elastic.version}/collect/goods/sync")
    BaseResponse sync(@RequestBody @Valid EsGoodsListSpuSyncRequest request);

}
