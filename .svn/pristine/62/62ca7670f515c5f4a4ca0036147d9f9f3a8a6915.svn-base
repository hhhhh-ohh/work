package com.wanmi.sbc.goods.standard.service;

import com.wanmi.sbc.elastic.api.provider.standard.EsStandardProvider;
import com.wanmi.sbc.elastic.api.request.standard.EsStandardInitRequest;
import com.wanmi.sbc.goods.bean.enums.CheckStatus;
import com.wanmi.sbc.goods.bean.enums.GoodsSource;
import com.wanmi.sbc.goods.info.model.root.Goods;
import com.wanmi.sbc.goods.info.request.GoodsRequest;
import com.wanmi.sbc.goods.info.service.GoodsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 商品库异步服务
 * Created by xufeng on 2021/6/24.
 */
@Service
public class StandardImportAsyncService {

    @Autowired
    private StandardImportService standardImportService;

    @Autowired
    private EsStandardProvider esStandardProvider;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StandardGoodsService standardGoodsService;

    @Async
    public void asyncProviderGoods(String goodsId, String standardGoodsId) {
        standardImportService.synProviderGoods(goodsId, standardGoodsId);
        //刷新商品库
        if (CollectionUtils.isNotEmpty(Collections.singletonList(standardGoodsId))) {
            esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(Collections.singletonList(standardGoodsId)).build());
        }
    }

    @Async
    public void asyncImportStandard(String goodsId) {
        standardImportService.importStandard(GoodsRequest.builder().goodsIds(Collections.singletonList(goodsId)).build());
        //刷新商品库
        Goods goods = goodsService.getGoodsById(goodsId);
        if (goods.getAuditStatus() == CheckStatus.CHECKED && GoodsSource.PROVIDER.toValue() == goods.getGoodsSource()) {
            List<String> standardIds = standardGoodsService.getUsedStandardByGoodsIds(Collections.singletonList(goodsId));
            //初始化商品库ES
            if (CollectionUtils.isNotEmpty(standardIds)) {
                esStandardProvider.init(EsStandardInitRequest.builder().goodsIds(standardIds).build());
            }
        }
    }
}
