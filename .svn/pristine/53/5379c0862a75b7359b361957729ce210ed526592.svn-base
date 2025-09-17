package com.wanmi.sbc.vas.api.provider.linkedmall.stock;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "${application.vas.name}",contextId = "LinkedMallStockQueryProvider")
public interface LinkedMallStockQueryProvider {
    /**
     * 根据四级配送区域编码批量查询spu库存属性
     */
    @PostMapping("/linkedmall/${application.vas.version}/stock/by-DivisionCode")
    BaseResponse<List<LinkedMallStockVO>> batchGoodsStockByDivisionCode(@RequestBody LinkedMallStockGetRequest request);

}
