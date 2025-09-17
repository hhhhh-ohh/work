package com.wanmi.sbc.goods.provider.impl.freight;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.PlatformAddress;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.goods.api.provider.freight.FreightProvider;
import com.wanmi.sbc.goods.api.provider.freight.FreightTemplateGoodsProvider;
import com.wanmi.sbc.goods.api.request.freight.*;
import com.wanmi.sbc.goods.api.response.freight.CartListResponse;
import com.wanmi.sbc.goods.api.response.freight.CollectPageInfoResponse;
import com.wanmi.sbc.goods.api.response.freight.GetFreightInGoodsInfoResponse;
import com.wanmi.sbc.goods.bean.vo.FreightCartVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoCartVO;
import com.wanmi.sbc.goods.freight.FreightService;
import com.wanmi.sbc.goods.freight.service.FreightTemplateGoodsServiceInterface;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wur
 * @className FreightController
 * @description TODO
 * @date 2022/7/6 11:13
 */
@RestController
@Validated
public class FreightController implements FreightProvider {

    @Autowired private FreightService freightService;

    @Autowired private StoreQueryProvider storeQueryProvider;

    @Override
    public BaseResponse inGoodsInfo(@Valid GetFreightInGoodsInfoRequest request) {
        GetFreightInGoodsInfoResponse response =
                freightService.getFreightTemplateInGoodsInfo(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CollectPageInfoResponse> collectPageInfo(
            @Valid CollectPageInfoRequest request) {
        CollectPageInfoResponse response = freightService.collectPageInfo(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<CartListResponse> cartList(@Valid CartListRequest request) {
        List<FreightCartVO> freightCateVOList = new ArrayList<>();
        List<GoodsInfoCartVO> goodsInfoCartVOList = request.getGoodsInfoCartVOList();
        List<StoreVO> storeVOS = request.getStoreVOS();
        // 批量获取供应商信息
        List<GoodsInfoCartVO> providerGoodsInfo =
                goodsInfoCartVOList.stream()
                        .filter(
                                goodsInfoCartVO ->
                                        StringUtils.isNotBlank(
                                                goodsInfoCartVO.getProviderGoodsInfoId()))
                        .collect(Collectors.toList());
        Map<Long, StoreVO> providerMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(providerGoodsInfo)) {
            List<Long> providerStoreId =
                    providerGoodsInfo.stream()
                            .map(GoodsInfoCartVO::getProviderId)
                            .collect(Collectors.toList());
            ListStoreByIdsResponse storeResponse =
                    storeQueryProvider
                            .listByIds(
                                    ListStoreByIdsRequest.builder()
                                            .storeIds(providerStoreId)
                                            .build())
                            .getContext();
            if (Objects.nonNull(storeResponse)
                    && CollectionUtils.isNotEmpty(storeResponse.getStoreVOList())) {
                providerMap =
                        storeResponse.getStoreVOList().stream()
                                .collect(
                                        Collectors.toMap(StoreVO::getStoreId, Function.identity()));
            }
        }
        // 商品按照商家分组
        Map<Long, List<GoodsInfoCartVO>> goodsInfoMap =
                goodsInfoCartVOList.stream()
                        .collect(Collectors.groupingBy(GoodsInfoCartVO::getStoreId));
        for (StoreVO storeVO : storeVOS) {
            List<GoodsInfoCartVO> goodsInfoList = goodsInfoMap.get(storeVO.getStoreId());
            if (CollectionUtils.isEmpty(goodsInfoList)) {
                break;
            }
            FreightCartVO cartVO =
                    freightService.cartList(
                            goodsInfoList, storeVO, request.getAddress(), providerMap);
            if (Objects.nonNull(cartVO)) {
                freightCateVOList.add(cartVO);
            }
        }
        return BaseResponse.success(
                CartListResponse.builder().freightCateVOList(freightCateVOList).build());
    }
}
