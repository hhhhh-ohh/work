package com.wanmi.sbc.flashsalegoods;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.goods.api.provider.flashsaleactivity.FlashSaleActivityQueryProvider;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityPageRequest;
import com.wanmi.sbc.goods.api.request.flashsaleactivity.FlashSaleActivityQueryRequest;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashSaleActivityListNewResponse;
import com.wanmi.sbc.goods.api.response.flashsaleactivity.FlashSaleActivityPageResponse;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Tag(name =  "秒杀活动列表管理API", description =  "FlashSaleActivityController")
@RestController
@Validated
@RequestMapping(value = "/flashsaleactivity")
public class FlashSaleActivityController {

    @Autowired
    private FlashSaleActivityQueryProvider flashSaleActivityQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "列表查询即将开场活动列表")
    @PostMapping("/soonlist")
    public BaseResponse<FlashSaleActivityListNewResponse> getSoonList(@RequestBody @Valid FlashSaleActivityQueryRequest listReq) {
        listReq.setStoreId(commonUtil.getStoreId());
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

        return flashSaleActivityQueryProvider.listNew(listReq);
    }

    @Operation(summary = "列表查询进行中活动列表")
    @PostMapping("/salelist")
    public BaseResponse<FlashSaleActivityPageResponse> getSaleList(@RequestBody @Valid FlashSaleActivityPageRequest pageRequest) {
        pageRequest.setStoreId(commonUtil.getStoreId());
        LocalDateTime startTime = LocalDateTime.now().minusHours(Constants.FLASH_SALE_LAST_HOUR);
        LocalDateTime endTime = LocalDateTime.now();
        pageRequest.setFullTimeBegin(startTime);
        pageRequest.setFullTimeEnd(endTime);

        return flashSaleActivityQueryProvider.page(pageRequest);
    }

    @Operation(summary = "列表查询已结束活动列表")
    @PostMapping("/endlist")
    public BaseResponse<FlashSaleActivityPageResponse> getEndList(@RequestBody @Valid FlashSaleActivityPageRequest pageRequest) {
        pageRequest.setStoreId(commonUtil.getStoreId());
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
}