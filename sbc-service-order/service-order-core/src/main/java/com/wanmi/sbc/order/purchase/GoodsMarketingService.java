package com.wanmi.sbc.order.purchase;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.PluginType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.provider.marketing.GoodsMarketingProvider;
import com.wanmi.sbc.goods.api.provider.marketing.GoodsMarketingQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.request.marketing.GoodsMarketingModifyRequest;
import com.wanmi.sbc.goods.api.request.marketing.ListByCustomerIdAndMarketingIdReq;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.marketing.ListByCustomerIdAndMarketingIdResp;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoResponseVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingLevelPluginProvider;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginQueryProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingLevelGoodsListFilterRequest;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginByGoodsInfoListAndCustomerRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingType;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;
import com.wanmi.sbc.order.api.response.purchase.PurchaseGetGoodsMarketingResponse;
import com.wanmi.sbc.order.purchase.mapper.CustmerMapper;
import com.wanmi.sbc.order.purchase.mapper.GoodsInfoMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * 商品营销Service
 * Fix Issue ID1072754
 * 将修改方法从PurchaseService中独立出来使AOP 事务生效
 *
 * @author zhengyang
 * @since 2021/4/2 13:46
 */
@Primary
@Service
public class GoodsMarketingService implements GoodsMarketingServiceInterface {

    @Resource
    protected GoodsInfoMapper goodsInfoMapper;
    @Resource
    protected CustmerMapper custmerMapper;
    @Resource
    protected PurchaseRepository purchaseRepository;

    @Resource
    protected GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Resource
    protected GoodsMarketingProvider goodsMarketingProvider;
    @Resource
    protected MarketingLevelPluginProvider marketingLevelPluginProvider;
    @Resource
    protected MarketingPluginQueryProvider marketingPluginQueryProvider;
    @Resource
    protected GoodsMarketingQueryProvider goodsMarketingQueryProvider;

    /***
     * 获取商品营销信息
     * @param goodsInfos        商品SKU集合
     * @param customer          登录用户
     * @param storeId           门店Id，O2O模式使用,SBC模式直接传NULL
     * @param pluginType        插件类型，用于路由,SBC模式直接传NULL
     * @return
     */
    @Override
    public PurchaseGetGoodsMarketingResponse getGoodsMarketing(List<GoodsInfoVO> goodsInfos,
                                                               CustomerVO customer, Long storeId, PluginType pluginType) {
        Map<String, List<MarketingViewVO>> resMap = new LinkedHashMap<>();
        PurchaseGetGoodsMarketingResponse marketingResponse = new PurchaseGetGoodsMarketingResponse();

        if (goodsInfos.isEmpty()) {
            marketingResponse.setMap(resMap);
            marketingResponse.setGoodsInfos(goodsInfos);
            return marketingResponse;
        }

        List<GoodsInfoDTO> goodsInfoList = goodsInfoMapper.goodsInfoVOsToGoodsInfoDTOs(goodsInfos);
//        CustomerDTO customerDTO = custmerMapper.customerVOToCustomerDTO(customer);

        // 设置级别价
        goodsInfos = marketingLevelPluginProvider.goodsListFilter(MarketingLevelGoodsListFilterRequest.builder()
                .goodsInfos(goodsInfoList).customerId(customer.getCustomerId()).build()).getContext().getGoodsInfoVOList();
        goodsInfoList = goodsInfoMapper.goodsInfoVOsToGoodsInfoDTOs(goodsInfos);

        //获取营销
        Map<String, List<MarketingViewVO>> marketingMap = marketingPluginQueryProvider.getByGoodsInfoListAndCustomer(
                MarketingPluginByGoodsInfoListAndCustomerRequest.builder().goodsInfoList(goodsInfoList)
                        .customerId(customer.getCustomerId()).build()).getContext().getMarketingMap();


        marketingMap.forEach((key, value) -> {
            // 商品营销排序 减>折>赠
            List<MarketingViewVO> marketingResponses = value.stream().filter(marketingViewVO -> !Objects.equals(marketingViewVO.getMarketingType(), MarketingType.SUITS))
                    .sorted(Comparator.comparing(MarketingViewVO::getMarketingType)).collect(Collectors.toList());

            if (!marketingResponses.isEmpty()) {
                // 获取该商品的满赠营销
                Optional<MarketingViewVO> fullGiftMarketingOptional = marketingResponses.stream()
                        .filter(marketing -> marketing.getMarketingType() == MarketingType.GIFT).findFirst();

                fullGiftMarketingOptional.ifPresent(fullGiftMarketingResponse -> {
                    List<String> skuIds = fullGiftMarketingResponse.getFullGiftLevelList().stream()
                            .flatMap(marketingFullGiftLevel ->
                                    marketingFullGiftLevel.getFullGiftDetailList().stream()
                                            .map(MarketingFullGiftDetailVO::getProductId))
                            .distinct().collect(Collectors.toList());
                    GoodsInfoViewByIdsRequest goodsInfoRequest = GoodsInfoViewByIdsRequest.builder()
                            .goodsInfoIds(skuIds)
                            .isHavSpecText(Constants.yes)
                            .build();
                    GoodsInfoViewByIdsResponse idsResponse =
                            goodsInfoQueryProvider.listViewByIds(goodsInfoRequest).getContext();
                    fullGiftMarketingResponse.setGoodsList(GoodsInfoResponseVO.builder()
                            .goodsInfos(idsResponse.getGoodsInfos()).goodses(idsResponse.getGoodses()).build());
                });

                resMap.put(key, marketingResponses);
            }
        });

        marketingResponse.setMap(resMap);
        marketingResponse.setGoodsInfos(goodsInfos);
        return marketingResponse;
    }

    /**
     * 修改商品使用的营销
     *
     * @param goodsInfoId
     * @param marketingId
     * @param customer
     */
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void modifyGoodsMarketing(String goodsInfoId, Long marketingId, CustomerVO customer) {
        modifyGoodsMarketing(goodsInfoId, marketingId, customer, null);
    }

    /**
     * 修改商品使用的营销
     *
     * @param goodsInfoId
     * @param marketingId
     * @param customer
     * @param storeId           门店Id，O2O模式使用,SBC模式直接传NULL
     */
    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void modifyGoodsMarketing(String goodsInfoId, Long marketingId, CustomerVO customer, Long storeId) {
        GoodsInfoViewByIdResponse response = new GoodsInfoViewByIdResponse();
        try {
            response = goodsInfoQueryProvider.getViewById(
                    GoodsInfoViewByIdRequest.builder().goodsInfoId(goodsInfoId).build()
            ).getContext();
        } catch (SbcRuntimeException e) {
            if (GoodsErrorCodeEnum.K030035.getCode().equals(e.getErrorCode())) {
                purchaseRepository.deleteByGoodsInfoids(Lists.newArrayList(goodsInfoId), customer.getCustomerId());
            } else {
                throw e;
            }
        }
        if (Objects.nonNull(response.getGoodsInfo())) {
            PurchaseGetGoodsMarketingResponse goodsMarketingMap =
                    this.getGoodsMarketing(Collections.singletonList(response.getGoodsInfo()), customer, storeId, null);

            List<MarketingViewVO> marketingResponses = goodsMarketingMap.getMap().get(goodsInfoId);

            if (marketingResponses != null && marketingResponses.stream().anyMatch(marketingResponse -> marketingResponse.getMarketingId().equals(marketingId))) {
                // 更新商品选择的营销
                GoodsMarketingModifyRequest goodsMarketingModifyRequest = new GoodsMarketingModifyRequest();
                goodsMarketingModifyRequest.setCustomerId(customer.getCustomerId());
                goodsMarketingModifyRequest.setGoodsInfoId(goodsInfoId);
                goodsMarketingModifyRequest.setMarketingId(marketingId);
                goodsMarketingProvider.modify(goodsMarketingModifyRequest);
            }

            purchaseRepository.updateTime(LocalDateTime.now(), customer.getCustomerId(), goodsInfoId);
            ListByCustomerIdAndMarketingIdResp resp =
                    goodsMarketingQueryProvider.listByCustomerIdAndMarketingId(ListByCustomerIdAndMarketingIdReq.builder()
                            .customerId(customer.getCustomerId()).marketingId(marketingId).build()).getContext();
            List<String> goodsInfoIds =
                    resp.getGoodsMarketings().stream().map(GoodsMarketingVO::getGoodsInfoId).collect(Collectors.toList());
            List<Purchase> purchaseList = purchaseRepository.queryByGoodsInfoIdAndCustomerId(goodsInfoIds,
                    customer.getCustomerId());
            for (int i = 0; i < purchaseList.size(); i++) {
                Purchase item = purchaseList.get(i);
                item.setUpdateTime(LocalDateTime.now());
                item.setOrderSort(i);
            }
            purchaseRepository.saveAll(purchaseList);
        }else {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
    }


}
