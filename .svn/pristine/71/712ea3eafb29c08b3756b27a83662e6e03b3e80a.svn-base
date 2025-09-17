package com.wanmi.sbc.marketing.api.provider.giftcard;


import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lvzhenwei
 * @className GiftCardQueryProvider
 * @description 会员礼品卡业务处理
 * @date 2022/12/8 16:29
 **/
@FeignClient(value = "${application.marketing.name}", contextId = "UserGiftCardProvider")
public interface UserGiftCardProvider {

    /**
     * @description 会员兑换礼品卡
     * @author  lvzhenwei
     * @date 2022/12/10 2:05 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/check-exchange-gift-card")
    BaseResponse<UserGiftCardInfoResponse> checkExchangeGiftCard(@RequestBody @Valid ExchangeGiftCardRequest request);

    /**
     * @description 会员兑换礼品卡
     * @author  lvzhenwei
     * @date 2022/12/10 2:05 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/exchange-gift-card")
    BaseResponse<UserGiftCardInfoResponse>  exchangeGiftCard(@RequestBody @Valid ExchangeGiftCardRequest request);

    /**
     * @description 查询会员礼品卡详情
     * @author  lvzhenwei
     * @date 2022/12/10 2:05 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/get-User-gift-card-detail")
    BaseResponse<UserGiftCardInfoResponse> getUserGiftCardDetail(@RequestBody @Valid UserGiftCardDetailQueryRequest request);

    /**
     * @description 会员激活礼品卡
     * @author  lvzhenwei
     * @date 2022/12/10 2:05 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/activate-gift-card")
    BaseResponse activateGiftCard(@RequestBody @Valid ActivateGiftCardRequest request);

    /**
     * @description 查询会员礼品卡余额
     * @author  lvzhenwei
     * @date 2022/12/10 2:05 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/get-gift-card-balance")
    BaseResponse<UserGiftCardBalanceQueryResponse> getGiftCardBalance(@RequestBody @Valid UserGiftCardBalanceQueryRequest request);

    /**
     * @description   C端用户礼品卡分页查询
     * @author  wur
     * @date: 2022/12/12 15:06
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/get-page")
    BaseResponse<UserGiftCardInfoPageResponse> getUserGiftCardInfoPage(@RequestBody @Valid UserGiftCardQueryRequest request);

    /**
     * @description 查询礼品卡的数量
     * @author  wur
     * @date: 2022/12/12 16:54
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/get-num")
    BaseResponse<UserGiftCardInfoNumResponse> getUserGiftCardInfoNum(@RequestBody @Valid UserGiftCardQueryRequest request);

    /**
     * @description  订单获取用户礼品卡信息
     * @author  wur
     * @date: 2022/12/13 14:10
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/trade-get")
    BaseResponse<UserGiftCardTradeResponse> tradeUserGiftCard(@RequestBody @Valid UserGiftCardTradeRequest request);

    /**
     * @description  礼品卡使用验证
     * @author  wur
     * @date: 2022/12/13 14:13
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/use-check")
    BaseResponse checkUserGiftCard(@RequestBody @Valid UserGiftCardUseCheckRequest request);

    /**
     * @description   查询列表
     * @author  wur
     * @date: 2022/12/14 10:30
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/get-List")
    BaseResponse<UserGiftCardListResponse> getUserGiftCardList(@RequestBody @Valid UserGiftCardQueryRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/12/14 15:31
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/use")
    BaseResponse<UserGiftCardUseResponse> useUserGiftCard(@RequestBody @Valid UserGiftCardTransRequest request);

    /**
     * @description
     * @author  wur
     * @date: 2022/12/14 15:31
     * @param request
     * @return
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/return")
    BaseResponse returnUserGiftCard(@RequestBody @Valid UserGiftCardTransRequest request);

    /**
     * {tableDesc}导出数量查询API
     *
     * @author 马连峰
     * @param request {tableDesc}导出数量查询请求 {@link GiftCardDetailExportRequest}
     * @return 礼品卡详情数量 {@link Long}
     */
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/export/count")
    BaseResponse<Long> countForExport(@RequestBody @Valid UserGiftCardQueryRequest request);

    /**
     * {tableDesc}导出列表查询API
     *
     * @author 马连峰
     * @param request {tableDesc}导出列表查询请求 {@link GiftCardDetailExportRequest}
     * @return 礼品卡详情列表 {@link GiftCardDetailExportResponse}
     */
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/export/page")
    BaseResponse<UserGiftCardInfoPageResponse> exportGiftCardDetailRecord(@RequestBody @Valid UserGiftCardQueryRequest request);

    /**
     * @description 提货卡适用男
     * @author  edz
     * @date: 2023/7/3 16:45
     * @param userPickupCardRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/pickup-card")
    BaseResponse userPickupCard(@RequestBody @Valid UserPickupCardRequest userPickupCardRequest);

    /**
     * @param userReturnPickupCardRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @description 订单取消返还提货卡
     * @author 刘方鑫
     * @date: 2025/8/12 16:45
     */
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/pickup-returnPickupCard")
    BaseResponse returnPickupCard(@RequestBody @Valid UserReturnPickupCardRequest userReturnPickupCardRequest);

    /**
     * @description 校服小助手旧订单过售后七天自动激活礼品卡
     * @author  刘方鑫
     * @date 2025/8/14 2:05 下午
     * @param orderSn
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @PostMapping("/marketing/${application.marketing.version}/user-gift-card/old_send_new_auto_activate-gift-card")
    BaseResponse oldSendNewAutoActivateGiftCard(@RequestBody OldSendNewAutoActivateGiftCardRequest  request);
}
