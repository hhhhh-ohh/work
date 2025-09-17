package com.wanmi.sbc.goods.service.list.imp;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsPcListService
 * @description
 * @date 2021/10/13 11:34 上午
 */
@Service
public class GoodsPcListService extends GoodsListService {
    // 单个spu展示sku的数量限制
    private static final int COUNT = 4;
    @Autowired EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;
    @Autowired MarketingQueryProvider marketingQueryProvider;
    @Autowired DistributionCacheService distributionCacheService;

    @Autowired GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired CommonUtil commonUtil;

    @Override
    public EsGoodsInfoSimpleResponse getEsDataPage(EsGoodsInfoQueryRequest request) {

        EsGoodsSimpleResponse goodsResponse =
                esGoodsInfoElasticQueryProvider.spuPage(request).getContext();
        EsGoodsInfoSimpleResponse response = new EsGoodsInfoSimpleResponse();
        if (goodsResponse != null && goodsResponse.getEsGoods() != null) {
            List<EsGoodsInfoVO> goodsInfoVOS = new ArrayList<>();

            goodsResponse
                    .getEsGoods()
                    .getContent()
                    .forEach(
                            esGoodsVO -> {
                                List<GoodsInfoNestVO> goodsInfos =
                                        esGoodsVO.getGoodsInfos().stream()
                                                .filter(
                                                        goodsInfoNestVO ->
                                                                goodsInfoNestVO
                                                                        .getVendibilityStatus()
                                                                        .equals(1)
                                                                        && Objects.nonNull(goodsInfoNestVO.getStock())
                                                                        && Objects.nonNull(goodsInfoNestVO.getMarketPrice()))
                                                .sorted(Comparator.comparing(GoodsInfoNestVO::getStock, (s1, s2) -> {
                                                    // 这里使用多重排序，先按是否有库存排序（有库存的在前），再按市场价升序排序
                                                    // 目的是为了多规格商品，优先展示[有库存货中价格最低的sku]
                                                    boolean s1HasStock = s1 > 0;
                                                    boolean s2HasStock = s2 > 0;
                                                    if (s1HasStock == s2HasStock) {
                                                        // 都有或都没有库存，返回0
                                                        return 0;
                                                    }
                                                    // s1有库存，返回-1，否则1，表示有库存的靠前，无库存的靠后
                                                    return s1HasStock ? -1 : 1;
                                                }).thenComparing(GoodsInfoNestVO::getMarketPrice))
                                                .collect(Collectors.toList());
                                int size = Math.min(goodsInfos.size(), COUNT);
                                for (int i = 0; i < size; i++) {
                                    EsGoodsInfoVO esGoodsInfoVO =
                                            goodsInfoConvertMapper.esGoodsVOToEsGoodsInfoVO(
                                                    esGoodsVO);
                                    if (Objects.nonNull(esGoodsVO.getGoodsSalesNum()) && esGoodsVO.getGoodsSalesNum() > 0) {
                                        goodsInfos.get(i).setGoodsSalesNum(esGoodsVO.getGoodsSalesNum());
                                    }
                                    esGoodsInfoVO.setGoodsInfo(goodsInfos.get(i));
                                    goodsInfoVOS.add(esGoodsInfoVO);
                                }
                            });
            MicroServicePage<EsGoodsInfoVO> page = new MicroServicePage(goodsInfoVOS);
            page.setTotal(goodsResponse.getEsGoods().getTotal());
            page.setSize(goodsResponse.getEsGoods().getSize());
            page.setNumber(goodsResponse.getEsGoods().getNumber());
            response.setEsGoodsInfoPage(page);
        }
        return response;
    }
}
