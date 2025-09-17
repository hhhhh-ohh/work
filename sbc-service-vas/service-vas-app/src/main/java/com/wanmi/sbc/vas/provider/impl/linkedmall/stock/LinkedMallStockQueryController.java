package com.wanmi.sbc.vas.provider.impl.linkedmall.stock;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.empower.api.provider.channel.linkedmall.goods.LinkedMallGoodsProvider;
import com.wanmi.sbc.empower.api.request.channel.linkedmall.LinkedMallGoodsStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class LinkedMallStockQueryController implements LinkedMallStockQueryProvider {
    @Autowired
    private LinkedMallGoodsProvider linkedMallGoodsProvider;
    @Override
    public BaseResponse<List<LinkedMallStockVO>> batchGoodsStockByDivisionCode(LinkedMallStockGetRequest request) {
        LinkedMallGoodsStockGetRequest linkedMallGoodsStockGetRequest = KsBeanUtil.copyPropertiesThird(request, LinkedMallGoodsStockGetRequest.class);
        if(StringUtils.isBlank(linkedMallGoodsStockGetRequest.getDivisionCode())){
            linkedMallGoodsStockGetRequest.setDivisionCode("0");
        }
        List<LinkedMallStockVO> items = Collections.emptyList();
        try {
            items = linkedMallGoodsProvider.getGoodsStock(linkedMallGoodsStockGetRequest).getContext().stream()
                    .map(linkedMallGoodsStockVO -> KsBeanUtil.copyPropertiesThird(linkedMallGoodsStockVO, LinkedMallStockVO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询linkedmall商品库存报错", e);
        }
        return BaseResponse.success(items);
    }
}
