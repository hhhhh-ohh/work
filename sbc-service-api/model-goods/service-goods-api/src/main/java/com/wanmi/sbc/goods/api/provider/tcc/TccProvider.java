package com.wanmi.sbc.goods.api.provider.tcc;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zhanggaolei
 * @className TccProvider
 * @description
 * @date 2022/6/28 16:08
 **/
@FeignClient(value = "${application.goods.name}", contextId = "TccProvider")
public interface TccProvider {

    @PostMapping("/goods/${application.goods.version}/test/insert-tcc")
    BaseResponse insertTcc(@RequestBody GoodsInfoRequest goodsInfoRequest);
}
