package com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.impl;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.OsUtil;
import com.wanmi.sbc.goods.api.response.cate.ContractCateListResponse;
import com.wanmi.sbc.goods.bean.enums.DistributionGoodsAudit;
import com.wanmi.sbc.goods.bean.enums.GoodsPriceType;
import com.wanmi.sbc.goods.bean.vo.ContractCateVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.bean.vo.GoodsIntervalPriceVO;
import com.wanmi.sbc.goods.bean.vo.GoodsVO;
import com.wanmi.sbc.order.api.optimization.trade1.request.TradeItemRequest;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.order.optimization.trade1.snapshot.base.service.TradeBuyAssembleInterface;
import com.wanmi.sbc.order.trade.service.TradeCacheService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author edz
 * @className TradeBuyAssembleService
 * @description TODO
 * @date 2022/3/1 20:00
 */
@Service
public class TradeBuyAssembleService implements TradeBuyAssembleInterface {

    @Autowired private OsUtil osUtil;

    @Autowired private TradeCacheService tradeCacheService;

    @Override
    public void tradeItemBaseBuilder(
            TradeItemDTO tradeItem, GoodsInfoVO goodsInfo, GoodsVO goods, Long storeId) {
        tradeItem.setStoreId(storeId);
        tradeItem.setSpuId(goods.getGoodsId());
        tradeItem.setSpuName(goods.getGoodsName());
        tradeItem.setSkuId(goodsInfo.getGoodsInfoId());
        tradeItem.setSkuName(goodsInfo.getGoodsInfoName());
        tradeItem.setSkuNo(goodsInfo.getGoodsInfoNo());
        tradeItem.setThirdPlatformType(goodsInfo.getThirdPlatformType());
        tradeItem.setPluginType(goodsInfo.getPluginType());
        tradeItem.setGoodsSource(goodsInfo.getGoodsSource());
        tradeItem.setGoodsType(goodsInfo.getGoodsType());
        tradeItem.setGoodsWeight(goodsInfo.getGoodsWeight());
        tradeItem.setGoodsCubage(goodsInfo.getGoodsCubage());
        tradeItem.setCateTopId(goodsInfo.getCateTopId());
        tradeItem.setCateId(goods.getCateId());
        if (osUtil.isS2b() && storeId != null) {
            BaseResponse<ContractCateListResponse> baseResponse =
                    tradeCacheService.queryContractCateList(storeId, goods.getCateId());
            ContractCateListResponse contractCateListResponse = baseResponse.getContext();
            if (Objects.nonNull(contractCateListResponse)) {
                List<ContractCateVO> cates = contractCateListResponse.getContractCateList();
                if (CollectionUtils.isNotEmpty(cates)) {
                    ContractCateVO cateResponse = cates.get(0);
                    tradeItem.setCateName(cateResponse.getCateName());
                }
            }
        }
        tradeItem.setPic(goodsInfo.getGoodsInfoImg());
        tradeItem.setBrand(goods.getBrandId());
        tradeItem.setUnit(goods.getGoodsUnit());
        tradeItem.setSpecDetails(goodsInfo.getSpecText());
        tradeItem.setDistributionGoodsAudit(DistributionGoodsAudit.COMMON_GOODS);
        tradeItem.setDistributionCommission(BigDecimal.ZERO);
        tradeItem.setBuyPoint(goodsInfo.getBuyPoint());
        tradeItem.setFreightTempId(goods.getFreightTempId());
    }

    @Override
    public void tradeItemPriceBuilder(
            TradeItemRequest tradeItem,
            TradeItemDTO tradeItemDTO,
            GoodsInfoVO goodsInfo,
            GoodsVO goods,
            List<GoodsIntervalPriceVO> goodsIntervalPrices) {
        // 订货区间设价
        if (Integer.valueOf(GoodsPriceType.STOCK.toValue()).equals(goods.getPriceType())) {
            long buyNum = tradeItem.getNum();
            Optional<GoodsIntervalPriceVO> first =
                    goodsIntervalPrices.stream()
                            .filter(item -> item.getGoodsInfoId().equals(tradeItem.getSkuId()))
                            .filter(intervalPrice -> buyNum >= intervalPrice.getCount())
                            .max(Comparator.comparingLong(GoodsIntervalPriceVO::getCount));
            if (first.isPresent()) {
                GoodsIntervalPriceVO goodsIntervalPrice = first.get();
                tradeItemDTO.setLevelPrice(goodsIntervalPrice.getPrice());
                tradeItemDTO.setOriginalPrice(goodsInfo.getMarketPrice());
                tradeItemDTO.setPrice(goodsIntervalPrice.getPrice());
                tradeItemDTO.setSplitPrice(
                        tradeItemDTO
                                .getLevelPrice()
                                .multiply(new BigDecimal(tradeItemDTO.getNum()))
                                .setScale(2, RoundingMode.HALF_UP));
                return;
            }
        }
        BigDecimal price =
                goodsInfo.getMarketPrice() == null ? BigDecimal.ZERO : goodsInfo.getMarketPrice();
        tradeItemDTO.setLevelPrice(price);
        tradeItemDTO.setOriginalPrice(price);
        tradeItemDTO.setPrice(price);
        tradeItemDTO.setSplitPrice(
                tradeItemDTO
                        .getLevelPrice()
                        .multiply(new BigDecimal(tradeItemDTO.getNum()))
                        .setScale(2, RoundingMode.HALF_UP));
    }
}
