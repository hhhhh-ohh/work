package com.wanmi.sbc.empower.api.provider.channel.linkedmall.goods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallGoodsStockGetRequest;
import com.wanmi.sbc.empower.bean.vo.channel.linkedmall.LinkedMallGoodsStockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${application.empower.name}", contextId = "LinkedMallGoodsProvider")
public interface LinkedMallGoodsProvider {

    /**
     * 批量查询spu库存
     *
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/linkedmall/goods/get-goods-stock")
    BaseResponse<List<LinkedMallGoodsStockVO>> getGoodsStock(
            @RequestBody LinkedMallGoodsStockGetRequest request);
}
