package com.wanmi.sbc.goods.service.list.imp;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.ChannelType;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.distribution.DistributorLevelQueryProvider;
import com.wanmi.sbc.customer.api.provider.level.CustomerLevelQueryProvider;
import com.wanmi.sbc.distribute.DistributionCacheService;
import com.wanmi.sbc.distribute.DistributionService;
import com.wanmi.sbc.elastic.api.provider.goods.EsGoodsInfoElasticQueryProvider;
import com.wanmi.sbc.elastic.api.request.goods.EsGoodsInfoQueryRequest;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsInfoSimpleResponse;
import com.wanmi.sbc.elastic.api.response.goods.EsGoodsSimpleResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.EsGoodsInfoVO;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.api.provider.price.GoodsIntervalPriceProvider;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.marketing.api.provider.market.MarketingQueryProvider;
import com.wanmi.sbc.marketing.api.provider.newplugin.NewMarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.market.MarketingGetByIdRequest;
import com.wanmi.sbc.marketing.bean.vo.MarketingForEndVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingScopeVO;
import com.wanmi.sbc.order.api.provider.purchase.PurchaseQueryProvider;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.GoodsInfoConvertMapper;
import com.wanmi.sbc.vas.api.provider.linkedmall.stock.LinkedMallStockQueryProvider;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className GoodsInfoListService
 * @description TODO
 * @date 2021/8/11 5:14 下午
 */
@Service
public class GoodsListService extends GoodsInfoListService {

    @Autowired MarketingQueryProvider marketingQueryProvider;

    @Autowired CommonUtil commonUtil;

    @Autowired DistributionCacheService distributionCacheService;

    @Autowired EsGoodsInfoElasticQueryProvider esGoodsInfoElasticQueryProvider;

    @Autowired LinkedMallStockQueryProvider linkedMallStockQueryProvider;

    @Autowired GoodsIntervalPriceProvider goodsIntervalPriceProvider;

    @Autowired PurchaseQueryProvider purchaseQueryProvider;

    @Autowired NewMarketingPluginProvider newMarketingPluginProvider;

    @Autowired GoodsInfoConvertMapper goodsInfoConvertMapper;

    @Autowired DistributionService distributionService;

    @Autowired DistributorLevelQueryProvider distributorLevelQueryProvider;

    @Autowired CustomerLevelQueryProvider customerLevelQueryProvider;


    @Override
    public EsGoodsInfoQueryRequest setRequest(EsGoodsInfoQueryRequest request) {


        if (Objects.nonNull(request.getMarketingId())) {
            MarketingGetByIdRequest marketingGetByIdRequest = new MarketingGetByIdRequest();
            marketingGetByIdRequest.setMarketingId(request.getMarketingId());
            MarketingForEndVO marketingForEndVO =
                    marketingQueryProvider
                            .getMarketingSimpleByIdForCustomer(marketingGetByIdRequest)
                            .getContext()
                            .getMarketingForEndVO();
            List<String> scopeIds =
                    marketingForEndVO.getMarketingScopeList().stream()
                            .map(MarketingScopeVO::getScopeId)
                            .collect(Collectors.toList());
            request.dealMarketingGoods(
                    marketingForEndVO.getScopeType(), scopeIds, marketingForEndVO.getStoreId());
            // 非PC端分销商品状态
            if (Objects.equals(
                            ChannelType.PC_MALL, commonUtil.getDistributeChannel().getChannelType())
                    && DefaultFlag.YES.equals(distributionCacheService.queryOpenFlag())) {
                request.setExcludeDistributionGoods(true);
            }
        }

        // 只看分享赚商品信息
        if (Objects.nonNull(request.getDistributionGoodsAudit())
                && DistributionGoodsAudit.CHECKED.toValue()
                        == request.getDistributionGoodsAudit()) {
            request.setDistributionGoodsStatus(NumberUtils.INTEGER_ZERO);
        }
        request = wrapEsGoodsInfoQueryRequest(request);

        // 获取会员和等级,
        if(!Objects.equals(request.getIsGoods(),Boolean.FALSE)) {

            request.setQueryGoods(true);
        }
        if (TerminalSource.PC.equals(commonUtil.getTerminal())) {
            request.setIsBuyCycle(Constants.no);
        }
        threadRrequest.set(request);
        return request;
    }

    @Override
    public EsGoodsInfoSimpleResponse getEsDataPage(EsGoodsInfoQueryRequest request) {

        EsGoodsSimpleResponse goodsResponse =
                esGoodsInfoElasticQueryProvider.spuPage(request).getContext();
        EsGoodsInfoSimpleResponse response = new EsGoodsInfoSimpleResponse();
        if (goodsResponse != null && goodsResponse.getEsGoods() != null) {
            List<EsGoodsInfoVO> goodsInfoVOS =
                    goodsResponse.getEsGoods().getContent().stream()
                            .map(
                                    esGoodsVO -> {
                                        EsGoodsInfoVO esGoodsInfoVO =
                                                goodsInfoConvertMapper.esGoodsVOToEsGoodsInfoVO(
                                                        esGoodsVO);
                                        esGoodsInfoVO.setIsBuyCycle(esGoodsVO.getIsBuyCycle());
                                        List<GoodsInfoNestVO> goodsInfos =
                                                esGoodsVO.getGoodsInfos().stream()
                                                        .filter(
                                                                goodsInfoNestVO ->
                                                                        goodsInfoNestVO
                                                                                .getVendibilityStatus()
                                                                                .equals(1) &&
                                                                                !GoodsStatus.INVALID.equals(
                                                                                        goodsInfoNestVO.getGoodsStatus()))
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
                                        if (CollectionUtils.isNotEmpty(goodsInfos)) {
                                            if (Objects.nonNull(esGoodsVO.getGoodsSalesNum()) && esGoodsVO.getGoodsSalesNum() > 0) {
                                                goodsInfos.get(0).setGoodsSalesNum(esGoodsVO.getGoodsSalesNum());
                                            }
                                            esGoodsInfoVO.setGoodsInfo(goodsInfos.get(0));
                                            return esGoodsInfoVO;
                                        }else {
                                            return null;
                                        }
                                    })
                            .filter(esGoodsInfoVO -> esGoodsInfoVO!=null)
                            .collect(Collectors.toList());
            MicroServicePage<EsGoodsInfoVO> page = new MicroServicePage(goodsInfoVOS);
            page.setTotal(goodsResponse.getEsGoods().getTotal());
            page.setSize(goodsResponse.getEsGoods().getSize());
            page.setNumber(goodsResponse.getEsGoods().getNumber());
            response.setEsGoodsInfoPage(page);
        }
        return response;
    }
}
