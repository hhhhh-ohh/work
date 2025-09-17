package com.wanmi.sbc.vas.vop.goods;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.GoodsStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.empower.api.provider.channel.base.ChannelGoodsQueryProvider;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsSaleabilityQueryRequest;
import com.wanmi.sbc.empower.api.request.channel.base.ChannelGoodsStockQueryRequest;
import com.wanmi.sbc.empower.api.response.channel.base.ChannelGoodsSaleabilityQueryResponse;
import com.wanmi.sbc.empower.bean.dto.channel.base.ChannelGoodsBuyNumDTO;
import com.wanmi.sbc.empower.bean.vo.channel.base.ChannelGoodsStockVO;
import com.wanmi.sbc.goods.bean.enums.AddedFlag;
import com.wanmi.sbc.setting.bean.dto.ThirdAddress;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import com.wanmi.sbc.vas.channel.goods.service.ChannelGoodsService;
import com.wanmi.sbc.vas.vop.address.VopAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description VOP订单服务
 * @author daiyitian
 * @date 2021/5/12 18:09
 */
@Service
@Slf4j
public class VopGoodsServiceImpl implements ChannelGoodsService {

    @Autowired private VopAddressService vopAddressService;

    @Autowired private RedisUtil redisService;

    @Autowired private ChannelGoodsQueryProvider channelGoodsQueryProvider;

    /**
     * @description 填充购物车
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param goodsInfoList 商品列表
     * @param platformAddress 平台地区
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelCartStatusGetResponse
     */
    @Override
    public ChannelGoodsStatusGetResponse fillGoodsStatus(
            List<ChannelGoodsInfoVO> goodsInfoList, PlatformAddress platformAddress) {
        ChannelGoodsStatusGetResponse response = new ChannelGoodsStatusGetResponse();
        response.setOffAddedSkuId(new ArrayList<>());
        List<ChannelGoodsInfoVO> skuList =
                goodsInfoList.stream()
                        .filter(
                                i ->
                                        ThirdPlatformType.VOP.equals(i.getThirdPlatformType())
                                                && (!GoodsStatus.INVALID.equals(i.getGoodsStatus()))
                                                && StringUtils.isNotBlank(
                                                        i.getThirdPlatformSkuId()))
                        .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(skuList)) {
            return response;
        }
        // 先标记有效，无效的商品无需查询库存
        skuList.forEach(sku -> sku.setVendibility(Constants.yes));
        // 处理第三方地址
        ThirdAddress address = vopAddressService.convertPlatformToThird(platformAddress);

        // 当地址未映射完整的情况，将VOP商品设为不支持销售
        if (Objects.nonNull(platformAddress) && (!platformAddress.hasNull()) && address.hasNull()) {
            skuList.forEach(
                    s -> {
                        s.setVendibility(Constants.no);
                        s.setGoodsStatus(GoodsStatus.NO_SALE);
                    });
            return response;
        }

        // 填充有效状态
        try {
            this.fillGoodsStatus(skuList, address);
        } catch (SbcRuntimeException e) {
            // 出错，直接失效
            skuList.forEach(sku -> sku.setGoodsStatus(GoodsStatus.INVALID));
            return response;
        }

        List<String> offAddedSku =
                skuList.stream()
                        .filter(s -> Constants.no.equals(s.getAddedFlag()))
                        .map(ChannelGoodsInfoVO::getProviderGoodsInfoId)
                        .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(offAddedSku)) {
            response.setOffAddedSkuId(offAddedSku);
        }

        // 无地址，默认是有库存的
        if (address != null
                && (!address.hasNull())
                && skuList.stream().anyMatch(s -> Constants.yes.equals(s.getVendibility()))) {
            try {
                this.fillStock(
                        skuList.stream()
                                .filter(s -> Constants.yes.equals(s.getVendibility()))
                                .collect(Collectors.toList()),
                        address);
            } catch (SbcRuntimeException e) {
                // 出错，直接失效
                skuList.forEach(sku -> sku.setGoodsStatus(GoodsStatus.INVALID));
                return response;
            }
        }
        return response;
    }

    /**
     * @description 验证商品
     * @author daiyitian
     * @date 2021/5/13 18:56
     * @param goodsInfoList 商品列表
     * @param platformAddress 平台地区
     * @return com.wanmi.sbc.vas.api.response.channel.ChannelCartGoodsVerifyResponse
     */
    @Override
    public ChannelGoodsVerifyResponse verifyGoods(
            List<ChannelGoodsInfoVO> goodsInfoList, PlatformAddress platformAddress) {
        ChannelGoodsVerifyResponse response = new ChannelGoodsVerifyResponse();
        ChannelGoodsStatusGetResponse getResponse =
                this.fillGoodsStatus(goodsInfoList, platformAddress);
        response.setOffAddedSkuId(getResponse.getOffAddedSkuId());
        List<ChannelGoodsInfoVO> skuList =
                goodsInfoList.stream()
                        .filter(
                                i ->
                                        ThirdPlatformType.VOP.equals(i.getThirdPlatformType())
                                                && StringUtils.isNotBlank(
                                                        i.getThirdPlatformSkuId()))
                        .collect(Collectors.toList());
        // 含有失效
        if (skuList.stream().anyMatch(i -> GoodsStatus.INVALID.equals(i.getGoodsStatus()))) {
            response.setInvalidGoods(Boolean.TRUE);
            return response;
        }

        // 含有不支持销售
        if (skuList.stream().anyMatch(i -> GoodsStatus.NO_SALE.equals(i.getGoodsStatus()))) {
            response.setNoAuthGoods(Boolean.TRUE);
            return response;
        }

        // 含有缺货
        if (skuList.stream().anyMatch(i -> GoodsStatus.OUT_STOCK.equals(i.getGoodsStatus()))) {
            response.setNoOutStockGoods(Boolean.TRUE);
            return response;
        }
        return response;
    }

    /**
     * @description 填充可售性
     * @author daiyitian
     * @date 2021/5/13 14:20
     * @param skuList vop商品列表
     * @param thirdAddress 第三方地址信息
     */
    private void fillGoodsStatus(List<ChannelGoodsInfoVO> skuList, ThirdAddress thirdAddress) {
        // 未开启增值服务
        String value =
                redisService.hget(
                        ConfigKey.VALUE_ADDED_SERVICES.toValue(),
                        VASConstants.THIRD_PLATFORM_VOP.toValue());

        if (StringUtils.isBlank(value) || VASStatus.DISABLE.toValue().equals(value)) {
            skuList.stream()
                    .filter(i -> Constants.yes.equals(i.getVendibility()))
                    .forEach(
                            i -> {
                                i.setVendibility(Constants.no);
                                i.setGoodsStatus(GoodsStatus.INVALID);
                            });
            return;
        }

        List<ChannelGoodsBuyNumDTO> thirdSkuIds =
                skuList.stream()
                        .map(
                                channelGoodsInfoVO ->
                                        ChannelGoodsBuyNumDTO.builder()
                                                .thirdSkuId(
                                                        channelGoodsInfoVO.getThirdPlatformSkuId())
                                                .thirdSpuId(
                                                        channelGoodsInfoVO.getThirdPlatformSpuId())
                                                .buyNum(channelGoodsInfoVO.getBuyCount().intValue())
                                                .build())
                        .collect(Collectors.toList());

        ChannelGoodsSaleabilityQueryResponse saleResponse =
                channelGoodsQueryProvider
                        .querySaleability(
                                ChannelGoodsSaleabilityQueryRequest.builder()
                                        .thirdProvinceId(thirdAddress.getProvinceId())
                                        .thirdCityId(thirdAddress.getCityId())
                                        .thirdAreaId(thirdAddress.getAreaId())
                                        .thirdStreetId(thirdAddress.getStreetId())
                                        .thirdPlatformType(ThirdPlatformType.VOP)
                                        .thirdSkuIdList(thirdSkuIds)
                                        .build())
                        .getContext();
        // 填充不可售状态
        if (CollectionUtils.isNotEmpty(saleResponse.getOffSaleSkuIdList())) {
            skuList.stream()
                    .filter(
                            s ->
                                    saleResponse
                                            .getOffSaleSkuIdList()
                                            .contains(s.getThirdPlatformSkuId()))
                    .forEach(
                            s -> {
                                s.setAddedFlag(AddedFlag.NO.toValue());
                                s.setVendibility(Constants.no);
                                s.setGoodsStatus(GoodsStatus.INVALID);
                            });
        }

        // 填充下架状态
        if (CollectionUtils.isNotEmpty(saleResponse.getOffAddedSkuIdList())) {
            skuList.stream()
                    .filter(
                            s ->
                                    saleResponse
                                            .getOffAddedSkuIdList()
                                            .contains(s.getThirdPlatformSkuId()))
                    .forEach(
                            s -> {
                                s.setAddedFlag(AddedFlag.NO.toValue());
                                s.setVendibility(Constants.no);
                                s.setGoodsStatus(GoodsStatus.INVALID);
                            });
        }
        // 购买受限
        if (CollectionUtils.isNotEmpty(saleResponse.getLimitSkuIdList())) {
            skuList.stream()
                    .filter(
                            s ->
                                    saleResponse
                                            .getLimitSkuIdList()
                                            .contains(s.getThirdPlatformSkuId()))
                    .forEach(
                            s -> {
                                s.setVendibility(Constants.no);
                                s.setGoodsStatus(GoodsStatus.NO_SALE);
                            });
        }
    }

    /**
     * @description 填充Vop商品sku的库存
     * @author daiyitian
     * @date 2021/5/13 14:20
     * @param skuList 商品列表
     * @param thirdAddress 地址
     */
    private void fillStock(List<ChannelGoodsInfoVO> skuList, ThirdAddress thirdAddress) {
        List<ChannelGoodsBuyNumDTO> goodsBuyNumList =
                skuList.stream()
                        .map(
                                i -> {
                                    ChannelGoodsBuyNumDTO num = new ChannelGoodsBuyNumDTO();
                                    num.setThirdSkuId(i.getThirdPlatformSkuId());
                                    Integer buyCount = 1;
                                    if (Objects.nonNull(i.getBuyCount()) && i.getBuyCount()!=0) {
                                        buyCount = i.getBuyCount().intValue();
                                    }
                                    num.setBuyNum(buyCount);
                                    return num;
                                })
                        .collect(Collectors.toList());

        ChannelGoodsStockQueryRequest request =
                ChannelGoodsStockQueryRequest.builder()
                        .goodsBuyNumList(goodsBuyNumList)
                        .thirdProvinceId(thirdAddress.getProvinceId())
                        .thirdCityId(thirdAddress.getCityId())
                        .thirdAreaId(thirdAddress.getAreaId())
                        .thirdStreetId(thirdAddress.getStreetId())
                        .thirdPlatformType(ThirdPlatformType.VOP)
                        .build();
        List<ChannelGoodsStockVO> stockList =
                channelGoodsQueryProvider
                        .queryStock(request)
                        .getContext()
                        .getChannelGoodsStockList();
        // 可能地区未配好
        if (CollectionUtils.isEmpty(stockList)) {
            skuList.forEach(
                    g -> {
                        g.setStock(0L);
                        if (!(GoodsStatus.INVALID.equals(g.getGoodsStatus()))) {
                            g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                        }
                    });
            return;
        }
        // 填充库存
        Map<String, Long> skuStockMap =
                stockList.stream()
                        .collect(
                                Collectors.toMap(
                                        ChannelGoodsStockVO::getThirdSkuId,
                                        ChannelGoodsStockVO::getStock));
        skuList.stream()
                .filter(g -> skuStockMap.containsKey(g.getThirdPlatformSkuId()))
                .forEach(
                        g -> {
                            g.setStock(skuStockMap.get(g.getThirdPlatformSkuId()));
                            if ((!(GoodsStatus.INVALID.equals(g.getGoodsStatus())))
                                    && g.getStock() < 1) {
                                g.setGoodsStatus(GoodsStatus.OUT_STOCK);
                            }else {

                            }
                            if (GoodsStatus.OUT_STOCK.equals(g.getGoodsStatus())
                            && g.getStock() >0){
                                g.setGoodsStatus(GoodsStatus.OK);
                            }
                        });
    }
}
