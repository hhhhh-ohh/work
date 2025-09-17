package com.wanmi.sbc.order.trade.service;


import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.goods.api.provider.storecate.StoreCateQueryProvider;
import com.wanmi.sbc.goods.api.request.storecate.StoreCateListByGoodsRequest;
import com.wanmi.sbc.goods.bean.vo.StoreCateGoodsRelaVO;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingCouponPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingCouponWrapperRequest;
import com.wanmi.sbc.marketing.api.response.plugin.MarketingCouponWrapperResponse;
import com.wanmi.sbc.marketing.bean.dto.TradeItemInfoDTO;
import com.wanmi.sbc.marketing.bean.vo.TradeCouponVO;
import com.wanmi.sbc.order.groupon.service.GrouponOrderService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 存放订单与营销服务相关的接口方法
 * @author wanggang
 */
@Slf4j
@Service
public class TradeMarketingService {

    @Autowired
    private StoreCateQueryProvider storeCateQueryProvider;

    @Autowired
    private MarketingCouponPluginProvider marketingCouponPluginProvider;

    @Autowired
    private GrouponOrderService grouponOrderService;

    /**
     * 调用营销插件，构造订单优惠券对象
     * @return
     */
    public TradeCouponVO buildTradeCouponInfo(List<TradeItem> tradeItems, String couponCodeId,
                                               boolean forceCommit, String customerId) {

        // 1.查询tradeItems的storeCateIds
        List<String> goodsIds = tradeItems.stream()
                .map(TradeItem::getSpuId).distinct().collect(Collectors.toList());
        List<StoreCateGoodsRelaVO> relas =
                storeCateQueryProvider.listByGoods(new StoreCateListByGoodsRequest(goodsIds)).getContext().getStoreCateGoodsRelaVOList();
        Map<String, List<StoreCateGoodsRelaVO>> relasMap = relas.stream()
                .collect(Collectors.groupingBy(StoreCateGoodsRelaVO::getGoodsId));

        // 2.构建营销插件请求对象
        List<TradeItemInfoDTO> tradeItemInfos = tradeItems.stream()
                .map(t -> TradeItemInfoDTO.builder()
                        .num(t.getNum())
                        .price(t.getPrice())
                        .skuId(t.getSkuId())
                        .cateId(t.getCateId())
                        .storeId(t.getStoreId())
                        .brandId(t.getBrand())
                        .storeCateIds(relasMap.get(t.getSpuId()).stream()
                                .map(rela -> rela.getStoreCateId()).collect(Collectors.toList()))
                        .distributionGoodsAudit(t.getDistributionGoodsAudit())
                        .build())
                .collect(Collectors.toList());
        MarketingCouponWrapperRequest request = new MarketingCouponWrapperRequest();
        request.setCustomerId(customerId);
        request.setCouponCodeId(couponCodeId);
        request.setForceCommit(forceCommit);
        request.setTradeItems(tradeItemInfos);

        // 3.调用营销插件，查询订单优惠券对象
        MarketingCouponWrapperResponse response = marketingCouponPluginProvider.wrapper(request).getContext();
        if (log.isInfoEnabled()){
            log.info("TradeMarketingService => buildTradeCouponInfo running, customerId is {}, " +
                    "request is{}, response is{}", customerId, JSON.toJSONString(request), JSON.toJSONString(response));
        }
        if (response != null) {
            return response.getTradeCoupon();
        }

        return null;
    }

}
