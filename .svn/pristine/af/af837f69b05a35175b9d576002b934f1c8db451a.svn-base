package com.wanmi.sbc.goods.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.StoreByIdRequest;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.request.freight.CollectPageInfoRequest;
import com.wanmi.sbc.goods.api.request.freight.GetFreightInGoodsInfoRequest;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.enums.*;
import com.wanmi.sbc.goods.bean.vo.*;
import com.wanmi.sbc.goods.freight.service.FreightTemplateGoodsService;
import com.wanmi.sbc.goods.freight.service.FreightTemplateStoreService;
import com.wanmi.sbc.goods.goodscommissionconfig.model.root.GoodsCommissionConfig;
import com.wanmi.sbc.goods.goodscommissionconfig.service.GoodsCommissionConfigService;
import com.wanmi.sbc.goods.info.model.root.GoodsInfo;
import com.wanmi.sbc.goods.info.repository.GoodsInfoRepository;
import com.wanmi.sbc.order.bean.enums.OrderErrorCodeEnum;
import com.wanmi.sbc.vas.api.provider.channel.order.ChannelTradeProvider;
import com.wanmi.sbc.vas.api.request.channel.ChannelOrderFreightRequest;
import com.wanmi.sbc.vas.api.response.channel.ChannelOrderFreightResponse;
import com.wanmi.sbc.vas.bean.dto.channel.ChannelGoodsInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className FreightService
 * @description TODO
 * @date 2022/7/6 11:13
 */
@Slf4j
@Service
public class FreightService {

    @Autowired private GoodsInfoRepository goodsInfoRepository;

    @Autowired private ChannelTradeProvider channelTradeProvider;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Autowired private GoodsCommissionConfigService goodsCommissionConfigService;

    @Autowired private FreightTemplateStoreService freightTemplateStoreService;

    @Autowired private FreightTemplateGoodsService freightTemplateGoodsService;

    /**
     * @description
     * @author wur
     * @date: 2022/7/6 11:21
     * @param request
     * @return
     */
    public GetFreightInGoodsInfoResponse getFreightTemplateInGoodsInfo(
            GetFreightInGoodsInfoRequest request) {
        // 查询商品信息
        Optional<GoodsInfo> optional = goodsInfoRepository.findById(request.getGoodsInfoId());
        if (!optional.isPresent()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsInfo goodsInfo = optional.get();
        if (Strings.isNotEmpty(goodsInfo.getProviderGoodsInfoId())) {
            return getProviderFreightTemplate(goodsInfo, request);
        }
        return getFreightTemplate(goodsInfo, request);
    }

    /**
     * @description 获取商家运费信息
     * @author wur
     * @date: 2022/7/6 19:11
     * @param goodsInfo
     * @param request
     * @return
     */
    public GetFreightInGoodsInfoResponse getFreightTemplate(
            GoodsInfo goodsInfo, GetFreightInGoodsInfoRequest request) {
        // 查询商家信息
        StoreVO storeVO =
                storeQueryProvider
                        .getById(StoreByIdRequest.builder().storeId(goodsInfo.getStoreId()).build())
                        .getContext()
                        .getStoreVO();
        if (Objects.isNull(storeVO)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GetFreightInGoodsInfoResponse response = null;
        if (storeVO.getFreightTemplateType().equals(DefaultFlag.NO)) {
            response = freightTemplateStoreService.inGoodsInfoPage(request, goodsInfo, storeVO);
        } else if (DefaultFlag.YES.equals(storeVO.getFreightTemplateType())) {
            response = freightTemplateGoodsService.inGoodsInfoPage(request, goodsInfo, storeVO);
        }
        if (Objects.isNull(response)) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        return response;
    }

    /**
     * @description 获取供应商运费信息
     * @author wur
     * @date: 2022/7/6 19:12
     * @param goodsInfo
     * @param request
     * @return
     */
    public GetFreightInGoodsInfoResponse getProviderFreightTemplate(
            GoodsInfo goodsInfo, GetFreightInGoodsInfoRequest request) {
        // 查询商家代销设置
        GoodsCommissionConfig commissionConfig =
                goodsCommissionConfigService.queryBytStoreId(goodsInfo.getStoreId());
        if (commissionConfig.getFreightBearFlag().equals(CommissionFreightBearFlag.SELLER_BEAR)) {
            return GetFreightInGoodsInfoResponse.builder()
                    .freightDescribe("免运费")
                    .collectFlag(Boolean.FALSE)
                    .build();
        }

        // 验证商品是不是VOP或LinkedMall
        if (Objects.nonNull(goodsInfo.getThirdPlatformType())
                        && (goodsInfo.getThirdPlatformType().equals(ThirdPlatformType.LINKED_MALL)
                || goodsInfo.getThirdPlatformType().equals(ThirdPlatformType.VOP))) {
            // 封装VOP运费请求参数
            List<ChannelGoodsInfoDTO> goodsInfoList =
                    KsBeanUtil.convert(Arrays.asList(goodsInfo), ChannelGoodsInfoDTO.class);
            for (ChannelGoodsInfoDTO channelGoodsInfoDTO : goodsInfoList) {
                channelGoodsInfoDTO.setNum(1L);
            }

            BaseResponse<ChannelOrderFreightResponse> baseResponse =
                    channelTradeProvider.queryFreight(
                            ChannelOrderFreightRequest.builder()
                                    .goodsInfoList(goodsInfoList)
                                    .address(
                                            PlatformAddress.builder()
                                                    .provinceId(request.getProvinceId())
                                                    .cityId(request.getCityId())
                                                    .areaId(request.getAreaId())
                                                    .streetId(request.getStreetId())
                                                    .build())
                                    .build());
            if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
                if (Objects.isNull(baseResponse.getContext().getFreight())
                        || baseResponse.getContext().getFreight().compareTo(BigDecimal.ZERO) <= 0) {
                    return GetFreightInGoodsInfoResponse.builder()
                            .freightDescribe("免运费")
                            .collectFlag(Boolean.FALSE)
                            .build();
                }
                return GetFreightInGoodsInfoResponse.builder()
                        .freightDescribe(
                                String.format("运费%s元", baseResponse.getContext().getFreight()))
                        .collectFlag(Boolean.FALSE)
                        .build();
            } else {
                throw new SbcRuntimeException(OrderErrorCodeEnum.K050049);
            }
        }

        // 查询商品信息
        Optional<GoodsInfo> optional =
                goodsInfoRepository.findById(goodsInfo.getProviderGoodsInfoId());
        if (!optional.isPresent()) {
            throw new SbcRuntimeException(GoodsErrorCodeEnum.K030035);
        }
        GoodsInfo providerGoodsInfo = optional.get();
        GetFreightInGoodsInfoResponse response =
                this.getFreightTemplate(providerGoodsInfo, request);
        response.setProviderStoreId(providerGoodsInfo.getStoreId());
        return response;
    }

    /**
     * @description
     * @author wur
     * @date: 2022/7/11 10:21
     * @param request
     * @return
     */
    public CollectPageInfoResponse collectPageInfo(CollectPageInfoRequest request) {
        // 查询运费模板
        if (DefaultFlag.NO.equals(request.getFreightTemplateType())) {
            return freightTemplateStoreService.collectPageInfo(request);
        } else {
            return freightTemplateGoodsService.collectPageInfo(request);
        }
    }

    /**
     * @description
     * @author wur
     * @date: 2022/7/13 9:45
     * @param goodsInfoList 购物车商品信息
     * @param storeVO 商家信息
     * @param address 收货地址
     * @param providerMap 供应商信息
     * @return
     */
    public FreightCartVO cartList(
            List<GoodsInfoCartVO> goodsInfoList,
            StoreVO storeVO,
            PlatformAddress address,
            Map<Long, StoreVO> providerMap) {
        FreightCartVO freightCateVO = new FreightCartVO();
        freightCateVO.setStoreId(storeVO.getStoreId());
        List<FreightTemplateCartVO> cateVOList = new ArrayList<>();
        // 1 自营商品
        List<GoodsInfoCartVO> skuList =
                goodsInfoList.stream()
                        .filter(sku -> Strings.isBlank(sku.getProviderGoodsInfoId()))
                        .collect(Collectors.toList());
        // 1.1 验证商家运费设置
        if (DefaultFlag.YES.equals(storeVO.getFreightTemplateType())) {
            // 匹配单品运费
            List<FreightTemplateCartVO> cartList =
                    freightTemplateGoodsService.cartFreightList(
                            skuList, storeVO, address, Boolean.FALSE);
            if (CollectionUtils.isNotEmpty(cartList)) {
                cateVOList.addAll(cartList);
            }
        } else {
            // 匹配店铺运费
            FreightTemplateCartVO cartVO =
                    freightTemplateStoreService.cartFreightList(skuList, storeVO, address);
            if (Objects.nonNull(cartVO)) {
                cateVOList.add(cartVO);
            }
        }

        // 2 供应商商品
        List<GoodsInfoCartVO> sellSkuList = goodsInfoList.stream()
                .filter(sku -> Strings.isNotBlank(sku.getProviderGoodsInfoId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(sellSkuList)) {
            // 2.1. 验证代销设置
            GoodsCommissionConfig commissionConfig =
                    goodsCommissionConfigService.queryBytStoreId(storeVO.getStoreId());
            if (commissionConfig.getFreightBearFlag().equals(CommissionFreightBearFlag.SELLER_BEAR)) {
                FreightTemplateCartVO cartVO = new FreightTemplateCartVO();
                cartVO.setGoodsInfoId(
                        sellSkuList.stream()
                                .map(GoodsInfoCartVO::getGoodsInfoId)
                                .collect(Collectors.toList()));
                cartVO.setFreight(BigDecimal.ZERO);
                cartVO.setDescribe("免运费");
                cartVO.setFreeFlag(DeleteFlag.YES);
                cateVOList.add(cartVO);
            } else {
                Map<Long, List<GoodsInfoCartVO>> sellSkuMap =
                        goodsInfoList.stream()
                                .filter(sku -> Strings.isNotBlank(sku.getProviderGoodsInfoId()))
                                .collect(Collectors.groupingBy(GoodsInfoCartVO::getProviderId));
                for (Map.Entry<Long, List<GoodsInfoCartVO>> entry : sellSkuMap.entrySet()) {
                    // 2.2 获取供应商商家
                    StoreVO providerStore = providerMap.get(entry.getKey());
                    if (Objects.isNull(providerStore)) {
                        continue;
                    }
                    // 2.3验证是不是VOP LinkedMall
                    if (CompanySourceType.JD_VOP.equals(providerStore.getCompanySourceType())
                            || CompanySourceType.LINKED_MALL.equals(
                            providerStore.getCompanySourceType())) {
                        FreightTemplateCartVO cateVO = cartChannelFreight(entry.getValue(), address);
                        if (Objects.nonNull(cateVO)) {
                            cateVOList.add(cateVO);
                        }
                    } else {
                        // 2.4 验证商家运费类型
                        if (DefaultFlag.YES.equals(providerStore.getFreightTemplateType())) {
                            // 匹配单品运费
                            List<FreightTemplateCartVO> cartList =
                                    freightTemplateGoodsService.cartFreightList(
                                            entry.getValue(), providerStore, address, Boolean.TRUE);
                            if (CollectionUtils.isNotEmpty(cartList)) {
                                cateVOList.addAll(cartList);
                            }
                        } else {
                            // 匹配店铺运费
                            FreightTemplateCartVO cateVO =
                                    freightTemplateStoreService.cartFreightList(
                                            entry.getValue(), providerStore, address);
                            cateVO.setProviderId(providerStore.getStoreId());
                            if (Objects.nonNull(cateVO)) {
                                cateVOList.add(cateVO);
                            }
                        }
                    }
                }
            }
        }
        freightCateVO.setCateVOList(cateVOList);
        return freightCateVO;
    }

    /**
     * @description 购物车 - 获取第三放平台运费信息
     * @author wur
     * @date: 2022/7/13 14:45
     * @param goodsInfoList
     * @param address
     * @return
     */
    private FreightTemplateCartVO cartChannelFreight(
            List<GoodsInfoCartVO> goodsInfoList, PlatformAddress address) {
        // 封装VOP运费请求参数
        List<ChannelGoodsInfoDTO> channelGoodsInfoDTOList =
                KsBeanUtil.convert(goodsInfoList, ChannelGoodsInfoDTO.class);

        channelGoodsInfoDTOList.forEach(
                channelGoodsInfoDTO -> {
                    channelGoodsInfoDTO.setNum(channelGoodsInfoDTO.getBuyCount());
                });

        try {
            BaseResponse<ChannelOrderFreightResponse> baseResponse =
                    channelTradeProvider.queryFreight(
                            ChannelOrderFreightRequest.builder()
                                    .goodsInfoList(channelGoodsInfoDTOList)
                                    .address(
                                            PlatformAddress.builder()
                                                    .provinceId(address.getProvinceId())
                                                    .cityId(address.getCityId())
                                                    .areaId(address.getAreaId())
                                                    .streetId(address.getStreetId())
                                                    .build())
                                    .build());
            if (CommonErrorCodeEnum.K000000.getCode().equals(baseResponse.getCode())) {
                FreightTemplateCartVO cartVO = new FreightTemplateCartVO();
                cartVO.setChannelFreight(DeleteFlag.YES);
                cartVO.setGoodsInfoId(
                        goodsInfoList.stream()
                                .map(GoodsInfoCartVO::getGoodsInfoId)
                                .collect(Collectors.toList()));
                if (Objects.isNull(baseResponse.getContext().getFreight())
                        || baseResponse.getContext().getFreight().compareTo(BigDecimal.ZERO) <= 0) {
                    cartVO.setFreight(BigDecimal.ZERO);
                    cartVO.setDescribe("免运费");
                    cartVO.setFreeFlag(DeleteFlag.YES);
                } else {
                    cartVO.setFreight(BigDecimal.ZERO);
                    cartVO.setDescribe(String.format("运费%s元", baseResponse.getContext().getFreight()));
                    cartVO.setFreeFlag(DeleteFlag.NO);
                }
                return cartVO;
            }
        } catch (Exception e) {
            log.error("购物车 - 获取第三放平台运费信息，失败: {}", e);
        }
        return null;
    }
}
