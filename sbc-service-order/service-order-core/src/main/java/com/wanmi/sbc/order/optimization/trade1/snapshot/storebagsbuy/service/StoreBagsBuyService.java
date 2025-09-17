package com.wanmi.sbc.order.optimization.trade1.snapshot.storebagsbuy.service;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.ParamsDataVO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author edz
 * @className StoreBagsBuyService
 * @description TODO
 * @date 2022/3/28 16:05
 */
@Service("storeBagsBuy")
public class StoreBagsBuyService extends TradeBuyService {

    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Autowired private TradeItemSnapshotService tradeItemSnapshotService;

    @Override
    public void queryData(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        // 查询会员信息
        CustomerVO customerVO = queryDataInterface.getCustomerInfo(request.getCustomerId());
        paramsDataVO.setCustomerVO(customerVO);

        // 查询商品信息
        List<String> skuIds =
                request.getTradeItemRequests().stream()
                        .map(TradeItemRequest::getSkuId)
                        .collect(Collectors.toList());
        TradeConfirmGoodsRequest goodsRequest =
                TradeConfirmGoodsRequest.builder()
                        .skuIds(skuIds)
                        .isHavSpecText(Boolean.TRUE)
                        .isHavIntervalPrice(Boolean.FALSE)
                        .showLabelFlag(Boolean.FALSE)
                        .showSiteLabelFlag(Boolean.FALSE)
                        .isHavRedisStock(Boolean.FALSE)
                        .build();
        GoodsInfoResponse goodsInfoResponse = queryDataInterface.getGoodsInfo(goodsRequest);
        paramsDataVO.setGoodsInfoResponseSourceData(goodsInfoResponse);
        GoodsInfoResponse infoResponse =
                KsBeanUtil.convert(goodsInfoResponse, GoodsInfoResponse.class);
        infoResponse
                .getGoodses()
                .forEach(
                        goodsVO -> {
                            goodsVO.setPriceType(GoodsPriceType.MARKET.toIntegerValue());
                        });
        infoResponse
                .getGoodsInfos()
                .forEach(
                        goodsInfoVO -> {
                            // 普通商品
                            goodsInfoVO.setDistributionGoodsAudit(
                                    DistributionGoodsAudit.COMMON_GOODS);
                            // 开店礼包不限制起订量、限定量
                            goodsInfoVO.setCount(null);
                            goodsInfoVO.setMaxCount(null);
                            goodsInfoVO.setBuyPoint(NumberUtils.LONG_ZERO);
                        });
        paramsDataVO.setGoodsInfoResponse(infoResponse);

        // 是否都是实体商品
        boolean isRealGoods = goodsInfoResponse.getGoodsInfos().stream().allMatch(g -> g.getGoodsType() == 0);
        // 第三方商家商品库存需要省市区信息
        if (isRealGoods && StringUtils.isBlank(paramsDataVO.getRequest().getAddressId())) {
            CustomerDeliveryAddressResponse customerDeliveryAddressResponse =
                    queryDataInterface.getDefaultAddress(paramsDataVO);
            String provinceId = customerDeliveryAddressResponse.getProvinceId().toString();
            String cityId = customerDeliveryAddressResponse.getCityId().toString();
            String areaId = customerDeliveryAddressResponse.getAreaId().toString();
            String streetId = "";
            if (customerDeliveryAddressResponse.getStreetId() != null) {
                streetId = customerDeliveryAddressResponse.getStreetId().toString();
            }
            paramsDataVO
                    .getRequest()
                    .setAddressId(provinceId + "|" + cityId + "|" + areaId + "|" + streetId);
        }

        // 查询店铺
        StoreVO storeVO =
                queryDataInterface.getStoreInfo(
                        goodsInfoResponse.getGoodsInfos().get(0).getStoreId(),
                        request.getCustomerId());
        paramsDataVO.setStoreVO(storeVO);
    }

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        // 商品校验
        tradeBuyCheckInterface.validateGoodsStock(paramsDataVO);

        // 商品渠道检验
        tradeBuyCheckInterface.verifyChannelGoods(paramsDataVO);

        // 开店礼包设置校验
        tradeBuyCheckInterface.validateStoreBag(paramsDataVO);
    }

    @Override
    public void getMarketing(ParamsDataVO paramsDataVO) {}

    @Override
    public void saveTrade(ParamsDataVO paramsDataVO) {
        List<TradeItemDTO> tradeItems = paramsDataVO.getTradeItemDTOS();
        TradeItemGroup tradeItemGroup = new TradeItemGroup();
        tradeItemGroup.setTradeItems(KsBeanUtil.convert(tradeItems, TradeItem.class));
        StoreVO store = paramsDataVO.getStoreVO();
        Supplier supplier =
                Supplier.builder()
                        .storeId(store.getStoreId())
                        .storeName(store.getStoreName())
                        .isSelf(store.getCompanyType() == BoolFlag.NO)
                        .supplierCode(store.getCompanyInfo().getCompanyCode())
                        .supplierId(store.getCompanyInfo().getCompanyInfoId())
                        .supplierName(store.getCompanyInfo().getSupplierName())
                        .freightTemplateType(store.getFreightTemplateType())
                        .storeType(store.getStoreType())
                        .build();
        tradeItemGroup.setSupplier(supplier);
        tradeItemGroup.setTradeMarketingList(
                paramsDataVO.getTradeMarketingList() == null
                        ? new ArrayList<>()
                        : paramsDataVO.getTradeMarketingList());
        tradeItemGroup.setStoreBagsFlag(DefaultFlag.YES);
        tradeItemGroup.setSuitMarketingFlag(Boolean.FALSE);

        TradeItemSnapshot snapshot =
                TradeItemSnapshot.builder()
                        .id(UUIDUtil.getUUID())
                        .buyerId(paramsDataVO.getCustomerVO().getCustomerId())
                        .itemGroups(Collections.singletonList(tradeItemGroup))
                        .terminalToken(paramsDataVO.getTerminalToken())
                        .purchaseBuy(Boolean.FALSE)
                        .build();
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }
}
