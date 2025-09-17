package com.wanmi.sbc.flashsaleactivity;

import com.alibaba.fastjson2.JSON;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.MarketingGoodsStatus;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.store.ListStoreByIdsRequest;
import com.wanmi.sbc.customer.api.response.store.ListStoreByIdsResponse;
import com.wanmi.sbc.goods.api.provider.flashsaleactivity.FlashSaleActivityQueryProvider;
import com.wanmi.sbc.goods.api.provider.flashsalegoods.FlashSaleGoodsQueryProvider;
import com.wanmi.sbc.goods.api.provider.info.GoodsInfoQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityPageRequest;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityQueryRequest;
import com.wanmi.sbc.goods.api.request.flashsalegoods.FlashSaleGoodsPageRequest;
import com.wanmi.sbc.goods.api.request.info.GoodsInfoListByIdsRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashSaleActivityListNewResponse;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashSaleActivityPageResponse;
import com.wanmi.sbc.goods.api.response.flashsalegoods.FlashSaleGoodsPageResponse;
import com.wanmi.sbc.goods.bean.vo.FlashSaleGoodsVO;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.goods.service.GoodsBaseService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name =  "秒杀活动列表管理API", description =  "FlashSaleActivityController")
@RestController
@Validated
@RequestMapping(value = "/flashsaleactivity")
public class FlashSaleActivityController {

    @Autowired
    private FlashSaleActivityQueryProvider flashSaleActivityQueryProvider;

    @Autowired
    private FlashSaleGoodsQueryProvider flashSaleGoodsQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private GoodsInfoQueryProvider goodsInfoQueryProvider;

    @Autowired
    private GoodsBaseService goodsBaseService;

    @Operation(summary = "列表查询即将开场活动列表")
    @PostMapping("/soonlist")
    public BaseResponse<FlashSaleActivityListNewResponse> getSoonList(@RequestBody @Valid FlashSaleActivityQueryRequest listReq) {
        //查询最近一个月秒杀活动列表
        if (listReq.getFullTimeBegin() == null) {
            listReq.setFullTimeBegin(LocalDateTime.now());
            listReq.setFullTimeEnd(LocalDateTime.now().plusDays(10));
        } else {
            // 如果是今天
            if(listReq.getFullTimeBegin().toLocalDate().isEqual(LocalDate.now())) {
                listReq.setFullTimeEnd(listReq.getFullTimeBegin().plusDays(1).minusHours(1));
                listReq.setFullTimeBegin(LocalDateTime.now());
            }else{
                listReq.setFullTimeEnd(listReq.getFullTimeBegin().plusDays(1).minusHours(1));
            }

        }
        return  flashSaleActivityQueryProvider.listNew(listReq);
    }

    @Operation(summary = "列表查询进行中活动列表")
    @PostMapping("/salelist")
    public BaseResponse<FlashSaleActivityPageResponse> getSaleList(@RequestBody @Valid FlashSaleActivityPageRequest pageRequest) {
        LocalDateTime startTime = LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR);
        LocalDateTime endTime = LocalDateTime.now();
        pageRequest.setFullTimeBegin(startTime);
        pageRequest.setFullTimeEnd(endTime);

        return flashSaleActivityQueryProvider.page(pageRequest);
    }

    @Operation(summary = "列表查询已结束活动列表")
    @PostMapping("/endlist")
    public BaseResponse<FlashSaleActivityPageResponse> getEndList(@RequestBody @Valid FlashSaleActivityPageRequest pageRequest) {
        if (pageRequest.getFullTimeBegin() != null) {
            // 如果是今天
            if(pageRequest.getFullTimeBegin().toLocalDate().isEqual(LocalDate.now())) {
                LocalDateTime endTime = LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR);
                pageRequest.setFullTimeEnd(endTime);
            }else {
                pageRequest.setFullTimeEnd(pageRequest.getFullTimeBegin().plusDays(1).minusHours(1));
            }
        } else {
            LocalDateTime endTime = LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR);
            pageRequest.setFullTimeEnd(endTime);
        }
        pageRequest.putSort("activity_full_time","desc");

        return flashSaleActivityQueryProvider.page(pageRequest);
    }

    @Operation(summary = "列表查询已结束活动列表")
    @PostMapping("/detail")
    public BaseResponse<FlashSaleGoodsPageResponse> getDetail(@RequestBody @Valid FlashSaleGoodsPageRequest req) {

        req.putSort("activityFullTime", "asc");
        req.putSort("id", "asc");
        FlashSaleGoodsPageResponse response = flashSaleGoodsQueryProvider.page(req).getContext();
        List<Long> storeIds = response.getFlashSaleGoodsVOPage().getContent().stream().map(FlashSaleGoodsVO::getStoreId)
                .collect(Collectors.toList());
        ListStoreByIdsResponse storeResponse =
                storeQueryProvider.listByIds(ListStoreByIdsRequest.builder().storeIds(storeIds).build()).getContext();

        response.getFlashSaleGoodsVOPage().getContent().forEach(flashSaleGoodsVO -> {
            storeResponse.getStoreVOList().forEach(storeVO -> {
                if (flashSaleGoodsVO.getStoreId().equals(storeVO.getStoreId())) {
                    flashSaleGoodsVO.setStoreName(storeVO.getStoreName());
                }
            });
        });

        //填充marketingGoodsStatus属性
        this.populateMarketingGoodsStatus(response.getFlashSaleGoodsVOPage().getContent());

        return BaseResponse.success(response);
    }

    /**
     * @description 填充marketingGoodsStatus属性
     * @Author qiyuanzhao
     * @Date 2022/10/10 14:15
     * @param
     * @return
     **/
    private void populateMarketingGoodsStatus(List<FlashSaleGoodsVO> flashSaleGoodsVOS) {
        if (CollectionUtils.isEmpty(flashSaleGoodsVOS)){
            return;
        }
        //获取goodsInfoVO的集合
        List<String> goodsInfoIds = flashSaleGoodsVOS.stream().map(FlashSaleGoodsVO::getGoodsInfoId).collect(Collectors.toList());
        Map<String, MarketingGoodsStatus> statusMap = goodsBaseService.getMarketingGoodsStatusListByGoodsInfoIds(goodsInfoIds);

        for (FlashSaleGoodsVO flashSaleGoodsVO : flashSaleGoodsVOS){
            String goodsInfoId = flashSaleGoodsVO.getGoodsInfoId();

            MarketingGoodsStatus marketingGoodsStatus = statusMap.get(goodsInfoId);
            flashSaleGoodsVO.getGoodsInfo().setMarketingGoodsStatus(marketingGoodsStatus);
        }
    }




}