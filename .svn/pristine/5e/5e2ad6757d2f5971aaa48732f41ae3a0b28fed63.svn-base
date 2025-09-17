package com.wanmi.sbc.order.purchase.service;

import com.wanmi.sbc.common.enums.TerminalSource;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.WmCollectionUtils;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import com.wanmi.sbc.order.api.request.purchase.CartGoodsInfoRequest;
import com.wanmi.sbc.order.api.request.purchase.PurchaseInfoRequest;
import com.wanmi.sbc.order.api.response.purchase.GoodsCartListResponse;
import com.wanmi.sbc.order.purchase.Purchase;
import com.wanmi.sbc.order.util.mapper.GoodsMapper;
import com.wanmi.sbc.order.util.mapper.StoreMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className CartAdaptor
 * @description TODO
 * @date 2021/12/27 3:42 下午
 */
@Service
public class CartAdaptor {

    @Autowired GoodsMapper goodsMapper;

    @Autowired StoreMapper storeMapper;

    private CartInterface cartInterface;

    /***
     * set方法注入，为了方便子类注入自己的实现类
     * 不可改为属性注入
     * @param cartInterface 购物车实现类
     */
    @Autowired
    public void setCartInterface(CartInterface cartInterface) {
        this.cartInterface = cartInterface;
    }

    public GoodsCartListResponse getCartList(PurchaseInfoRequest request) {
        CustomerVO customer = request.getCustomer();
        GoodsCartListResponse response = new GoodsCartListResponse();

        //设置请求参数
        request = cartInterface.setRequest(request);

        //获取购物车信息 商品信息，登录用户从购物车内获取 未登录用户从请求参数获取
        List<Purchase> purchaseList = null;
        List<String> goodsInfoIds =
                CollectionUtils.isEmpty(request.getGoodsInfoList())
                        ? null
                        : request.getGoodsInfoList().stream()
                                .map(CartGoodsInfoRequest::getGoodsInfoId)
                                .collect(Collectors.toList());
        String customerId = Objects.nonNull(customer) ? customer.getCustomerId() : null;
        if (Objects.nonNull(customer)) {
            purchaseList = cartInterface.getPurchase(request);
            goodsInfoIds = CollectionUtils.isEmpty(purchaseList) ? null : purchaseList.stream().map(Purchase::getGoodsInfoId).collect(Collectors.toList());
            response.setPointsAvailable(customer.getPointsAvailable());
        }

        //验证商品信息是否为空
        if(CollectionUtils.isEmpty(goodsInfoIds)) {
            return response;
        }

        // 获取店铺信息
        // 因为O2O模式时商品查询依赖门店信息，所以此处返回结果
        // SBC模式下此处返回空
        List<StoreVO> storeVOS = cartInterface.getStoreInfoBeforeGoodsInfo(request);

        // 获取商品信息
        List<GoodsInfoCartVO> goodsInfoCartVOList =
                cartInterface.getGoodsInfo(goodsInfoIds, customer, request.getStoreId());

        //PC不展示周期购商品
        if (TerminalSource.PC.equals(request.getTerminalSource())) {
            List<String> cycleIds = goodsInfoCartVOList.stream()
                    .filter(goodsInfoCartVO -> Constants.yes.equals(goodsInfoCartVO.getIsBuyCycle()))
                    .map(GoodsInfoCartVO::getGoodsInfoId).collect(Collectors.toList());
            goodsInfoCartVOList = goodsInfoCartVOList.stream()
                    .filter(goodsInfoCartVO -> !cycleIds.contains(goodsInfoCartVO.getGoodsInfoId()))
                    .collect(Collectors.toList());
            if (purchaseList != null) {
                purchaseList = purchaseList.stream()
                        .filter(purchase -> !cycleIds.contains(purchase.getGoodsInfoId()))
                        .collect(Collectors.toList());
            }
        }

        if (CollectionUtils.isEmpty(goodsInfoCartVOList)) {
            return response;
        }
        // 获取店铺信息，如果是SBC则上面方法获取不到店铺信息
        // 在此处查询店铺
        if(WmCollectionUtils.isEmpty(storeVOS)) {
            storeVOS = cartInterface.getStoreInfo(goodsInfoCartVOList, purchaseList);
        }

        //设置信息校验
        goodsInfoCartVOList = cartInterface.checkSetting(goodsInfoCartVOList, customer, storeVOS, request.getInviteeId());

        //设置库存
        goodsInfoCartVOList = cartInterface.setStock(goodsInfoCartVOList, request.getAddress(), request.getStoreId());

        List<CartGoodsInfoRequest> cartGoodsInfoRequests = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(purchaseList)){
            for(Purchase p: purchaseList){
                cartGoodsInfoRequests.add(CartGoodsInfoRequest.builder().goodsInfoId(p.getGoodsInfoId()).buyCount(p.getGoodsNum()).build());
            }
        }else if(CollectionUtils.isNotEmpty(request.getGoodsInfoList())){
            cartGoodsInfoRequests = request.getGoodsInfoList();
        }

        //获取营销
        goodsInfoCartVOList =
                cartInterface.getMarketing(goodsInfoCartVOList, customer, request.getStoreId(),cartGoodsInfoRequests);

        //设置购买数量 未登录用户无需设置购买量
        if(Objects.nonNull(purchaseList)) {
            goodsInfoCartVOList = cartInterface.setBuyCount(goodsInfoCartVOList, purchaseList);
        }

        //设置购物车营销选择
        List<GoodsMarketingVO> goodsMarketingVOS =
                cartInterface.setGoodsMarketing(goodsInfoCartVOList, customerId, 0);

        if (CollectionUtils.isNotEmpty(purchaseList)) {
            //设置降价金额
            cartInterface.populateReductionPrice(goodsInfoCartVOList, purchaseList, customer);
        }

        //后置处理逻辑
        goodsInfoCartVOList = cartInterface.afterProcess(goodsInfoCartVOList, customer);

        //组装返回参数
        response.setStores(cartInterface.perfectStore(goodsInfoCartVOList, storeMapper.storeVOsToMiniStoreVOs(storeVOS)));
        response.setGoodsMarketings(goodsMarketingVOS);
        response.setMarketingDetail(getMarketingDetail(goodsInfoCartVOList));
        response.setGoodsInfos(
                goodsMapper.goodsInfoCartVOsToGoodsInfoCartSimpleVOs(goodsInfoCartVOList));
        response.setFreightCartVOS(cartInterface.setFreight(goodsInfoCartVOList, storeVOS, request.getAddress()));

        return response;
    }

    public void getPrice() {}

    public Map<String, Object> getMarketingDetail(List<GoodsInfoCartVO> goodsInfoCartVOList) {

        // 此处垃圾写法，如果后续新增了营销活动需要在此处维护，如果做到可变可以存入redis缓存
        Set<Integer> pluginTypes = new HashSet<>();
        pluginTypes.add(MarketingPluginType.DISCOUNT.getId());
        pluginTypes.add(MarketingPluginType.GIFT.getId());
        pluginTypes.add(MarketingPluginType.REDUCTION.getId());
        pluginTypes.add(MarketingPluginType.HALF_PRICE_SECOND_PIECE.getId());
        pluginTypes.add(MarketingPluginType.BUYOUT_PRICE.getId());
        pluginTypes.add(MarketingPluginType.PREFERENTIAL.getId());

        Map<String, Object> marketingDetail = new HashMap<>();
        goodsInfoCartVOList.forEach(g -> {
            if (CollectionUtils.isNotEmpty(g.getMarketingPluginLabels())) {
                g.getMarketingPluginLabels().forEach(m -> {
                    if (pluginTypes.contains(m.getMarketingType())
                            && marketingDetail.get(m.getMarketingType()) == null) {
                        marketingDetail.put(m.getMarketingId().toString(), m.getDetail());
                    }
                });
            }
        });
        return marketingDetail;
    }
}
