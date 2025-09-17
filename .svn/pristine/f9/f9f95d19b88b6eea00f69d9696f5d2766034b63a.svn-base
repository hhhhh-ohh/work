package com.wanmi.sbc.order.optimization.trade1.snapshot;

import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.common.util.UUIDUtil;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.info.TradeConfirmGoodsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.marketing.api.provider.bargain.BargainQueryProvider;
import com.wanmi.sbc.marketing.api.request.bargain.BargainByIdRequest;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.marketing.bean.vo.BargainGoodsVO;
import com.wanmi.sbc.marketing.bean.vo.BargainVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeBuyRequest;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.QueryDataInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyAssembleInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyCheckInterface;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl.TradeBuyService;
import com.wanmi.sbc.order.trade.model.entity.TradeItem;
import com.wanmi.sbc.order.trade.model.entity.value.Supplier;
import com.wanmi.sbc.order.trade.model.root.OrderTag;
import com.wanmi.sbc.order.trade.model.root.TradeItemGroup;
import com.wanmi.sbc.order.trade.model.root.TradeItemSnapshot;
import com.wanmi.sbc.order.trade.service.TradeItemSnapshotService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("bargainBuy")
public class BargainBuyService extends TradeBuyService {

    @Autowired
    @Qualifier("tradeBuyCheckService")
    private TradeBuyCheckInterface tradeBuyCheckInterface;

    @Autowired
    @Qualifier("queryDataService")
    private QueryDataInterface queryDataInterface;

    @Autowired
    @Qualifier("tradeBuyAssembleService")
    private TradeBuyAssembleInterface tradeBuyAssembleInterface;

    @Autowired
    private BargainQueryProvider bargainQueryProvider;

    @Autowired private TradeItemSnapshotService tradeItemSnapshotService;


    @Override
    public void queryData(ParamsDataVO paramsDataVO) {
        TradeBuyRequest request = paramsDataVO.getRequest();
        paramsDataVO.setCustomerVO(queryDataInterface.getCustomerInfo(request.getCustomerId()));
        // 砍价记录商品信息
        BargainVO bargainVO = bargainQueryProvider.getByIdWithBargainGoods(BargainByIdRequest.builder().bargainId(request.getBargainId()).build()).getContext();
        if (Objects.isNull(bargainVO) ||
                !Objects.equals(request.getCustomerId(), bargainVO.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 验证活动是否帮砍完成
        if(StringUtils.isNotBlank(bargainVO.getOrderId())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080164);
        }

        if(bargainVO.getTargetJoinNum().compareTo(bargainVO.getJoinNum()) > 0) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080167);
        }
        // 验证活动时间
        if (Objects.isNull(bargainVO.getBargainGoodsVO())
                || bargainVO.getBargainGoodsVO().getEndTime().isBefore(LocalDateTime.now())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080158);
        }

        paramsDataVO.setBargainVO(bargainVO);

        String goodsInfoId = bargainVO.getGoodsInfoId();
        //封装商品信息
        GoodsInfoResponse goodsInfoResponse = queryDataInterface.getGoodsInfo(TradeConfirmGoodsRequest.builder()
                .skuIds(Collections.singletonList(goodsInfoId))
                .isHavSpecText(Boolean.TRUE)
                .isHavIntervalPrice(Boolean.FALSE)
                .showLabelFlag(Boolean.FALSE)
                .showSiteLabelFlag(Boolean.FALSE)
                .isHavRedisStock(Boolean.FALSE)
                .build());
        goodsInfoResponse.getGoodsInfos().get(0).setMarketPrice(bargainVO.getBargainGoodsVO().getMarketPrice());
        TradeItemRequest tradeItemRequest = new TradeItemRequest();
        tradeItemRequest.setSkuId(goodsInfoId);
        tradeItemRequest.setNum(1L);
        tradeItemRequest.setIsAppointmentSaleGoods(false);
        tradeItemRequest.setIsBookingSaleGoods(false);
        request.setTradeItemRequests(Collections.singletonList(tradeItemRequest));
        paramsDataVO.setGoodsInfoResponseSourceData(goodsInfoResponse);
        paramsDataVO.setGoodsInfoResponse(KsBeanUtil.convert(goodsInfoResponse, GoodsInfoResponse.class));

        //封装店铺信息
        paramsDataVO.setStoreVO(queryDataInterface.getStoreInfo(
                goodsInfoResponse.getGoodsInfos().get(0).getStoreId(),
                request.getCustomerId()));

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
    }

    @Override
    public void check(ParamsDataVO paramsDataVO) {
        //查询库存
        GoodsInfoVO goodsInfoVO = paramsDataVO.getGoodsInfoResponse().getGoodsInfos().get(0);
        BargainGoodsVO bargainGoodsVO = paramsDataVO.getBargainVO().getBargainGoodsVO();
        goodsInfoVO.setStock(bargainGoodsVO.getLeaveStock());
        if (goodsInfoVO.getStock() < 1) {
            goodsInfoVO.setGoodsStatus(GoodsStatus.OUT_STOCK);
        }
        // 商品渠道检验
        tradeBuyCheckInterface.verifyChannelGoods(paramsDataVO);
    }

    @Override
    public void assembleTrade(ParamsDataVO paramsDataVO) {
        Map<String, GoodsInfoVO> skuGoodsInfoMap =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .collect(
                                Collectors.toMap(GoodsInfoVO::getGoodsInfoId, Function.identity()));

        Map<String, GoodsVO> spuGoodsMap =
                paramsDataVO.getGoodsInfoResponse().getGoodses().stream()
                        .collect(Collectors.toMap(GoodsVO::getGoodsId, Function.identity()));
        // 前端的请求参数
        List<TradeItemRequest> tradeItemRequests = paramsDataVO.getRequest().getTradeItemRequests();
        // 开始构建订单快照的TradeItemDTO
        List<TradeItemDTO> tradeItems = new ArrayList<>();
        tradeItemRequests.forEach(
                item -> {
                    TradeItemDTO tradeItemDTO = new TradeItemDTO();
                    // 前端传过来的购买数量
                    tradeItemDTO.setNum(item.getNum());
                    GoodsInfoVO goodsInfoVO = skuGoodsInfoMap.get(item.getSkuId());
                    // 构建tradeItem基础数据
                    tradeBuyAssembleInterface.tradeItemBaseBuilder(
                            tradeItemDTO,
                            goodsInfoVO,
                            spuGoodsMap.get(goodsInfoVO.getGoodsId()),
                            paramsDataVO.getStoreVO().getStoreId());
                    // 构建tradeItem价格信息
                    BigDecimal price = goodsInfoVO.getMarketPrice();
                    tradeItemDTO.setLevelPrice(price);
                    tradeItemDTO.setOriginalPrice(price);
                    BargainGoodsVO bargainGoodsVO = paramsDataVO.getBargainVO().getBargainGoodsVO();
                    tradeItemDTO.setBargainPrice(bargainGoodsVO.getBargainPrice());
                    tradeItemDTO.setBargainGoodsId(bargainGoodsVO.getBargainGoodsId());
                    tradeItemDTO.setPrice(bargainGoodsVO.getMarketPrice());
                    tradeItemDTO.setSplitPrice(bargainGoodsVO.getMarketPrice().subtract(bargainGoodsVO.getBargainPrice()));
                    tradeItems.add(tradeItemDTO);
                });
        OrderTag orderTag = new OrderTag();
        boolean virtualFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        NumberUtils.INTEGER_ONE.equals(goodsInfoVO.getGoodsType()));
        orderTag.setVirtualFlag(virtualFlag);
        boolean electronicCouponFlag =
                paramsDataVO.getGoodsInfoResponse().getGoodsInfos().stream()
                        .anyMatch(
                                goodsInfoVO ->
                                        Constants.TWO == (goodsInfoVO.getGoodsType()));
        orderTag.setElectronicCouponFlag(electronicCouponFlag);
        paramsDataVO.setTradeItemDTOS(tradeItems);
        paramsDataVO.setOrderTag(orderTag);
    }

    @Override
    public void getMarketing(ParamsDataVO paramsDataVO) {

    }

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
        tradeItemGroup.setStoreBagsFlag(DefaultFlag.NO);
        tradeItemGroup.setSuitMarketingFlag(Boolean.FALSE);
        tradeItemGroup.setOrderTag(paramsDataVO.getOrderTag());

        TradeItemSnapshot snapshot =
                TradeItemSnapshot.builder()
                        .id(UUIDUtil.getUUID())
                        .bargain(Boolean.TRUE)
                        .bargainId(paramsDataVO.getBargainVO().getBargainId())
                        .buyerId(paramsDataVO.getCustomerVO().getCustomerId())
                        .itemGroups(Collections.singletonList(tradeItemGroup))
                        .terminalToken(paramsDataVO.getTerminalToken())
                        .purchaseBuy(Boolean.FALSE)
                        .build();
        tradeItemSnapshotService.addTradeItemSnapshot(snapshot);
    }
}
