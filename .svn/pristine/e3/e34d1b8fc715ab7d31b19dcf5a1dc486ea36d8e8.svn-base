package com.wanmi.sbc.third.goods;

import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.constant.VASStatus;
import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.util.IteratorUtils;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.address.CustomerDeliveryAddressQueryProvider;
import com.wanmi.sbc.customer.api.request.address.CustomerDeliveryAddressRequest;
import com.wanmi.sbc.customer.api.response.address.CustomerDeliveryAddressResponse;
import com.wanmi.sbc.elastic.bean.vo.goods.GoodsInfoNestVO;
import com.wanmi.sbc.goods.bean.enums.GoodsErrorCodeEnum;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoSimpleVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.mq.producer.WebBaseProducerService;
import com.wanmi.sbc.order.bean.dto.TradeItemDTO;
import com.wanmi.sbc.setting.bean.enums.ConfigKey;
import com.wanmi.sbc.vas.api.provider.channel.goods.ChannelGoodsProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsStatusGetRequest;
import com.wanmi.sbc.vas.api.request.channel.ChannelGoodsVerifyRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsStatusGetResponse;
import com.wanmi.sbc.vas.api.response.channel.ChannelGoodsVerifyResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import com.wanmi.sbc.vas.bean.vo.channel.ChannelGoodsInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @description 第三方商品服务
 * @author daiyitian
 * @date 2021/5/29 18:18
 */
@Slf4j
@Service
public class ThirdPlatformGoodsService {

    @Autowired private RedisUtil redisService;

    @Autowired private ChannelGoodsProvider channelGoodsProvider;

    @Autowired WebBaseProducerService webBaseProducerService;

    @Autowired private CustomerDeliveryAddressQueryProvider customerDeliveryAddressQueryProvider;

    /**
     * @description 渠道验证
     * @author daiyitian
     * @date 2021/5/29 17:50
     * @param skuList 商品列表
     */
    public void verifyChannelGoods(List<GoodsInfoVO> skuList) {
        if (CollectionUtils.isNotEmpty(skuList)
                && skuList.stream().anyMatch(sku -> Objects.nonNull(sku.getThirdPlatformType()))) {
            if (this.isClose()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(skuList, ChannelGoodsInfoDTO.class);
            goodsInfoList.forEach(s -> s.setBuyCount(1L));
            ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
            verifyRequest.setGoodsInfoList(goodsInfoList);
            ChannelGoodsVerifyResponse verifyResponse =
                    channelGoodsProvider.verifyGoods(verifyRequest).getContext();
            // 下架商品
            if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                webBaseProducerService.sendThirdPlatformSkuOffAddedSync(
                        verifyResponse.getOffAddedSkuId());
            }

            Map<String, Integer> skuStatusMap =
                    verifyResponse.getGoodsInfoList().stream()
                            .collect(
                                    Collectors.toMap(
                                            ChannelGoodsInfoVO::getGoodsInfoId,
                                            sku -> sku.getGoodsStatus().toValue()));
            skuList.stream()
                    .filter(s -> skuStatusMap.containsKey(s.getGoodsInfoId()))
                    .forEach(
                            s -> {
                                if (!GoodsStatus.INVALID.equals(s.getGoodsStatus())) {
                                    s.setGoodsStatus(
                                            GoodsStatus.fromValue(
                                                    skuStatusMap.get(s.getGoodsInfoId())));
                                }
                            });
            //所有SKU均都失效，才不存在
            if (skuList.stream().filter(s -> GoodsStatus.INVALID.equals(s.getGoodsStatus())).count()
                    == skuList.size()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
        }
    }

    /**
     * @description 渠道验证
     * @author zgl
     * @date 2021/5/29 17:50
     * @param sku 商品列表
     */
    public void verifyChannelGoodsInfoSimple(GoodsInfoSimpleVO sku, PlatformAddress address) {
        if (sku != null && Objects.nonNull(sku.getThirdPlatformType())) {
            if (this.isClose()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(Collections.singletonList(sku), ChannelGoodsInfoDTO.class);
            goodsInfoList.forEach(s -> s.setBuyCount(1L));
            ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
            verifyRequest.setGoodsInfoList(goodsInfoList);
            verifyRequest.setAddress(address);
            ChannelGoodsVerifyResponse verifyResponse =
                    channelGoodsProvider.verifyGoods(verifyRequest).getContext();
            // 下架商品
            if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                webBaseProducerService.sendThirdPlatformSkuOffAddedSync(
                        verifyResponse.getOffAddedSkuId());
            }
            if (verifyResponse != null
                    && CollectionUtils.isNotEmpty(verifyResponse.getGoodsInfoList())) {
                Map<String, Integer> skuStatusMap =
                        verifyResponse.getGoodsInfoList().stream()
                                .collect(
                                        Collectors.toMap(
                                                ChannelGoodsInfoVO::getGoodsInfoId,
                                                s -> s.getGoodsStatus().toValue()));

                if (!GoodsStatus.INVALID.equals(sku.getGoodsStatus())
                        && skuStatusMap.get(sku.getGoodsInfoId()) != null) {
                    sku.setGoodsStatus(
                            GoodsStatus.fromValue(skuStatusMap.get(sku.getGoodsInfoId())));
                }
            }
        }
    }

    /**
     * @description 渠道验证
     * @author daiyitian
     * @date 2021/5/29 17:50
     * @param skuList 商品列表
     * @param tradeItems 商品列表
     */
    public void verifyChannelGoods(List<GoodsInfoVO> skuList, List<TradeItemDTO> tradeItems) {
        if (CollectionUtils.isNotEmpty(skuList)
                && skuList.stream().anyMatch(sku -> Objects.nonNull(sku.getThirdPlatformType()))) {
            if (this.isClose()) {
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(skuList, ChannelGoodsInfoDTO.class);
            goodsInfoList.forEach(s -> s.setBuyCount(1L));
            if (CollectionUtils.isNotEmpty(tradeItems)) {
                goodsInfoList =
                        IteratorUtils.zip(
                                goodsInfoList,
                                tradeItems,
                                (a, b) -> a.getGoodsInfoId().equals(b.getSkuId()),
                                (c, d) -> {
                                    c.setBuyCount(d.getNum());
                                });
            }
            ChannelGoodsVerifyRequest verifyRequest = new ChannelGoodsVerifyRequest();
            verifyRequest.setGoodsInfoList(goodsInfoList);
            ChannelGoodsVerifyResponse verifyResponse =
                    channelGoodsProvider.verifyGoods(verifyRequest).getContext();
            if (verifyResponse.getInvalidGoods()) {
                // 下架商品
                if (CollectionUtils.isNotEmpty(verifyResponse.getOffAddedSkuId())) {
                    webBaseProducerService.sendThirdPlatformSkuOffAddedSync(
                            verifyResponse.getOffAddedSkuId());
                }
                throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
            }
        }
    }
    /**
     * @description 渠道验证
     * @author daiyitian
     * @date 2021/5/29 17:50
     * @param skuList 商品列表
     */
    public Map<String, ChannelGoodsInfoVO> verifyChannelGoodsEs(List<GoodsInfoNestVO> skuList, String customerId) {
        if(CollectionUtils.isEmpty(skuList)){
            return Collections.emptyMap();
        }
        List<GoodsInfoNestVO> nestVOList = skuList.stream().filter(sku -> Objects.nonNull(sku.getThirdPlatformType())
                && Objects.equals(ThirdPlatformType.VOP, sku.getThirdPlatformType()))
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(nestVOList)){
            return Collections.emptyMap();
        }
        if (this.isClose()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        List<ChannelGoodsInfoDTO> goodsInfoList =
                KsBeanUtil.convert(nestVOList, ChannelGoodsInfoDTO.class);
        goodsInfoList.forEach(s -> s.setBuyCount(1L));

        PlatformAddress address = null;
        if (StringUtils.isNotBlank(customerId)) {
            CustomerDeliveryAddressRequest queryRequest = new CustomerDeliveryAddressRequest();
            queryRequest.setCustomerId(customerId);
            CustomerDeliveryAddressResponse addressResponse = customerDeliveryAddressQueryProvider.getDefaultOrAnyOneByCustomerId(queryRequest).getContext();
            // 有地址的情况，填充地区
            if (Objects.nonNull(addressResponse) && Boolean.FALSE.equals(addressResponse.getNeedComplete())) {
                address = new PlatformAddress();
                address.setProvinceId(Objects.toString(addressResponse.getProvinceId(), StringUtils.EMPTY));
                address.setCityId(Objects.toString(addressResponse.getCityId(), StringUtils.EMPTY));
                address.setAreaId(Objects.toString(addressResponse.getAreaId(), StringUtils.EMPTY));
                address.setStreetId(Objects.toString(addressResponse.getStreetId(), StringUtils.EMPTY));
            }
        }

        ChannelGoodsStatusGetRequest statusGetRequest = new ChannelGoodsStatusGetRequest();
        statusGetRequest.setGoodsInfoList(goodsInfoList);
        statusGetRequest.setAddress(address);
        ChannelGoodsStatusGetResponse statusGetResponse = channelGoodsProvider.getGoodsStatus(statusGetRequest).getContext();
        // 下架商品
        if (CollectionUtils.isNotEmpty(statusGetResponse.getOffAddedSkuId())) {
            webBaseProducerService.sendThirdPlatformSkuOffAddedSync(
                    statusGetResponse.getOffAddedSkuId());
        }
        Map<String, ChannelGoodsInfoVO> skuMap =
                statusGetResponse.getGoodsInfoList().stream()
                        .collect(
                                Collectors.toMap(
                                        ChannelGoodsInfoVO::getGoodsInfoId, Function.identity()));
        return skuMap;


    }

    /**
     * @description 判断是否关闭vas服务
     * @author daiyitian
     * @date 2021/5/29 14:48
     * @return boolean true:关闭 false:开启
     */
    private boolean isClose() {
        boolean vasFlag = true;
        // 是否开启LM
        if (VASStatus.ENABLE
                .toValue()
                .equals(
                        redisService.hget(
                                ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                VASConstants.THIRD_PLATFORM_LINKED_MALL.toValue()))) {
            vasFlag = false;
        }
        // 是否开启VOP
        if (VASStatus.ENABLE
                .toValue()
                .equals(
                        redisService.hget(
                                ConfigKey.VALUE_ADDED_SERVICES.toString(),
                                VASConstants.THIRD_PLATFORM_VOP.toValue()))) {
            vasFlag = false;
        }
        return vasFlag;
    }
}
