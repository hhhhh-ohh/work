package com.wanmi.sbc.marketing.api.provider.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardInfoRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className GiftCardQueryProvider
 * @description 礼品卡查询业务
 * @date 2022/12/8 16:29
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "GiftCardQueryProvider")
public interface GiftCardQueryProvider {

    /**
     * 分页查询
     * @param request
     * @return
     */
    @PostMapping("/marketing/${application.marketing.version}/gift-card/query/page")
    BaseResponse<GiftCardPageResponse> queryPage(@RequestBody @Valid GiftCardPageRequest request);

    /**
     * @description  查询礼品卡详情
     * @author  wur
     * @date: 2022/12/9 15:22
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card/query/info")
    BaseResponse<GiftCardInfoResponse> queryInfo(@RequestBody @Valid GiftCardInfoRequest request);

    /**
     * @description  查询礼品卡详情包含未删除的
     * @author  wur
     * @date: 2022/12/9 15:22
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/gift-card/query/detail")
    BaseResponse<GiftCardInfoResponse> queryDetail(@RequestBody @Valid GiftCardInfoRequest request);

    /**
     * @description 针对批量发卡，校验并获取礼品卡信息（有异常直接抛出，无异常返回礼品卡信息）
     * @author malianfeng
     * @date 2022/12/19 10:16
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse>
     */
    @PostMapping("/marketing/${application.marketing.version}/gift-card/check-and-get-for-batch-send")
    BaseResponse<GiftCardInfoResponse> checkAndGetForBatchSend(@RequestBody @Valid GiftCardInfoRequest request);
}
