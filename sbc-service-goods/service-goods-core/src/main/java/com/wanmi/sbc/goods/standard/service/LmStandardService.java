package com.wanmi.sbc.goods.standard.service;

import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.bean.vo.StandardSkuVO;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import com.wanmi.sbc.vas.api.request.linkedmall.stock.LinkedMallStockGetRequest;
import com.wanmi.sbc.vas.bean.vo.linkedmall.LinkedMallStockVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LM商品库服务
 * Created by daiyitian on 2017/4/11.
 */
@Service
public class LmStandardService {

    @Autowired
    private LinkedMallStockQueryProvider linkedMallStockQueryProvider;


    /**
     * 填充LM商品sku的库存
     *
     * @param standardSkuList 商品库SKu列表
     */
    public void fillLmStock(List<StandardSkuVO> standardSkuList) {
        //如果是linkedmall商品，实时查库存
        List<Long> itemIds = standardSkuList.stream()
                .filter(v -> Integer.valueOf(GoodsSource.LINKED_MALL.toValue()).equals(v.getGoodsSource()) && !StringUtils.isNotBlank(v.getThirdPlatformSpuId()))
                .map(v -> Long.valueOf(v.getThirdPlatformSpuId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(itemIds)) {
            return;
        }
        List<LinkedMallStockVO> stocks = linkedMallStockQueryProvider.batchGoodsStockByDivisionCode(LinkedMallStockGetRequest.builder().providerGoodsIds(itemIds).build()).getContext();
        if (CollectionUtils.isNotEmpty(stocks)) {
            for (StandardSkuVO standardSku : standardSkuList) {
                for (LinkedMallStockVO spuStock : stocks) {
                    Optional<LinkedMallStockVO.SkuStock> stock = spuStock.getSkuList().stream()
                            .filter(v -> String.valueOf(spuStock.getItemId()).equals(standardSku.getThirdPlatformSpuId())
                                    && String.valueOf(v.getSkuId()).equals(standardSku.getThirdPlatformSkuId()))
                            .findFirst();
                    stock.ifPresent(sku -> standardSku.setStock(sku.getStock()));
                }
            }
        }
    }
}
