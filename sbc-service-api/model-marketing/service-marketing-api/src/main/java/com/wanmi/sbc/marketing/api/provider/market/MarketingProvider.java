package com.wanmi.sbc.marketing.api.provider.market;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountModifyRequest;
import com.wanmi.sbc.marketing.api.request.market.*;
import com.wanmi.sbc.marketing.api.response.market.MarketingAddResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingDeleteResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingPauseResponse;
import com.wanmi.sbc.marketing.api.response.market.MarketingStartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author: ZhangLingKe
 * @Description: 营销更新接口Feign客户端
 * @Date: 2018-11-16 16:56
 */
@FeignClient(value = "${application.marketing.name}", contextId = "MarketingProvider")
public interface MarketingProvider {

    /**
     * 新增营销数据
     * @param addRequest 新增参数 {@link MarketingAddRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/add")
    BaseResponse<MarketingAddResponse> add(@RequestBody @Valid MarketingAddRequest addRequest);

    /**
     * 修改营销数据
     * @param modifyRequest 新增参数 {@link MarketingModifyRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/modify")
    BaseResponse modify(@RequestBody @Valid MarketingModifyRequest modifyRequest);

    /**
     * 删除营销数据
     * @param deleteByIdRequest 营销ID {@link MarketingDeleteByIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/delete-by-id")
    BaseResponse<MarketingDeleteResponse> deleteById(@RequestBody @Valid MarketingDeleteByIdRequest deleteByIdRequest);

    /**
     * 暂停营销
     * @param pauseByIdRequest 营销ID {@link MarketingPauseByIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/pause-by-id")
    BaseResponse<MarketingPauseResponse> pauseById(@RequestBody @Valid MarketingPauseByIdRequest pauseByIdRequest);

    /**
     * 启动营销
     * @param startByIdRequest 营销ID {@link MarketingStartByIdRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/start-by-id")
    BaseResponse<MarketingStartResponse> startById(@RequestBody @Valid MarketingStartByIdRequest startByIdRequest);

    /**
     * 结束营销
     * @param request 营销ID {@link MarketingCloseRequest}
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/close")
    BaseResponse close(@RequestBody @Valid MarketingCloseRequest request);

    /**
     * 修改暂停中满系活动
     * @param request
     */
    @PostMapping("/marketing/${application.marketing.version}/full/discount/pause-modify")
    BaseResponse pauseModify(@RequestBody @Valid PauseModifyRequest request);
}
