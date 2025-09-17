package com.wanmi.sbc.goods.service;

import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.CustomerErrorCodeEnum;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoViewByIdsRequest;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewByIdsResponse;
import com.wanmi.sbc.goods.api.response.info.GoodsInfoViewListResponse;
import com.wanmi.sbc.goods.api.response.price.GoodsIntervalPriceByCustomerIdResponse;
import com.wanmi.sbc.goods.bean.dto.GoodsInfoDTO;
import com.wanmi.sbc.intervalprice.GoodsIntervalPriceService;
import com.wanmi.sbc.marketing.api.provider.plugin.MarketingPluginProvider;
import com.wanmi.sbc.marketing.api.request.plugin.MarketingPluginGoodsListFilterRequest;
import com.wanmi.sbc.util.CommonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Objects;

/**
 * @author zhengyang
 * @className StoreGoodsInfoService
 * @description 订单商品服务Service
 * @date 2022/2/25 10:25 上午
 **/
@Service
public class StoreGoodsInfoService {

    @Resource
    private CustomerQueryProvider customerQueryProvider;
    @Resource
    private GoodsInfoQueryProvider goodsInfoQueryProvider;
    @Resource
    private GoodsIntervalPriceService goodsIntervalPriceService;
    @Resource
    private MarketingPluginProvider marketingPluginProvider;
    @Resource
    private CommonUtil commonUtil;

    /***
     * 批量获取商品信息
     * @param request  查询参数
     * @return
     */
    public GoodsInfoViewListResponse findSkus(GoodsInfoRequest request) {
        if (CollectionUtils.isEmpty(request.getGoodsInfoIds()) || StringUtils.isEmpty(request.getCustomerId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        // 获取会员
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest(request
                .getCustomerId())).getContext();
        if (Objects.isNull(customer)) {
            throw new SbcRuntimeException(CustomerErrorCodeEnum.K010001);
        }
        request.setStoreId(commonUtil.getStoreId());
        GoodsInfoViewByIdsResponse idsResponse = goodsInfoQueryProvider.listViewByIds(
                GoodsInfoViewByIdsRequest.builder().goodsInfoIds(request.getGoodsInfoIds())
                        .storeId(commonUtil.getStoreId()).build()).getContext();

        GoodsInfoViewListResponse response = new GoodsInfoViewListResponse();
        response.setGoodsInfos(idsResponse.getGoodsInfos());
        response.setGoodses(idsResponse.getGoodses());

        // 计算区间价
        GoodsIntervalPriceByCustomerIdResponse priceResponse =
                goodsIntervalPriceService.getGoodsIntervalPriceVOList(response.getGoodsInfos(),
                        customer.getCustomerId());
        response.setGoodsIntervalPrices(priceResponse.getGoodsIntervalPriceVOList());
        response.setGoodsInfos(priceResponse.getGoodsInfoVOList());
        // 计算营销价格
        MarketingPluginGoodsListFilterRequest filterRequest = new MarketingPluginGoodsListFilterRequest();
        filterRequest.setGoodsInfos(KsBeanUtil.convert(response.getGoodsInfos(), GoodsInfoDTO.class));
        filterRequest.setCustomerId(customer.getCustomerId());
        filterRequest.setStoreId(request.getStoreId());
        response.setGoodsInfos(marketingPluginProvider.goodsListFilter(filterRequest).getContext().getGoodsInfoVOList());
        return response;
    }
}
