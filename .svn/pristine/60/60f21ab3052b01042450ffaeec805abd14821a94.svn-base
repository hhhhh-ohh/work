package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.*;
import com.wanmi.sbc.marketing.api.response.giftcard.*;
import com.wanmi.sbc.marketing.bean.enums.GiftCardStatus;
import com.wanmi.sbc.marketing.bean.enums.GiftCardUseStatus;
import com.wanmi.sbc.marketing.bean.vo.UserGiftCardInfoVO;
import com.wanmi.sbc.marketing.giftcard.service.UserGiftCardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author lvzhenwei
 * @className UserGiftCardController
 * @description 会员礼品卡
 * @date 2022/12/10 2:02 下午
 **/
@Validated
@RestController
public class UserGiftCardController implements UserGiftCardProvider {

    @Autowired private UserGiftCardService userGiftCardService;

    /**
     * @description 校验会员礼品卡兑换业务处理
     * @author  lvzhenwei
     * @date 2022/12/10 2:14 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Override
    public BaseResponse<UserGiftCardInfoResponse> checkExchangeGiftCard(@Valid ExchangeGiftCardRequest request) {
        return BaseResponse.success(UserGiftCardInfoResponse.builder().
                userGiftCardInfoVO(userGiftCardService.checkExchangeGiftCard(request)).build());
    }

    /**
     * @description 会员礼品卡兑换业务处理
     * @author  lvzhenwei
     * @date 2022/12/10 2:14 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Override
    public BaseResponse<UserGiftCardInfoResponse>  exchangeGiftCard(@Valid ExchangeGiftCardRequest request) {
        return BaseResponse.success(UserGiftCardInfoResponse.builder().
                userGiftCardInfoVO(userGiftCardService.exchangeGiftCard(request)).build());
    }

    /**
     * @description 查询会员礼品卡详情
     * @author  lvzhenwei
     * @date 2022/12/10 3:57 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Override
    public BaseResponse<UserGiftCardInfoResponse> getUserGiftCardDetail(@Valid UserGiftCardDetailQueryRequest request) {
        return BaseResponse.success(UserGiftCardInfoResponse.builder().
                userGiftCardInfoVO(userGiftCardService.getUserGiftCardDetail(request)).build());
    }

    /**
     * @description 会员礼品卡激活业务处理
     * @author  lvzhenwei
     * @date 2022/12/10 2:14 下午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     **/
    @Override
    public BaseResponse activateGiftCard(@Valid ActivateGiftCardRequest request) {
        userGiftCardService.activateGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @description 查询会员可用礼品卡余额
     * @author  lvzhenwei
     * @date 2022/12/12 10:34 上午
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardBalanceQueryResponse>
     **/
    @Override
    public BaseResponse<UserGiftCardBalanceQueryResponse> getGiftCardBalance(@Valid UserGiftCardBalanceQueryRequest request) {
        return BaseResponse.success(userGiftCardService.getGiftCardBalanceByCustomerId(request));
    }

    @Override
    public BaseResponse<UserGiftCardInfoPageResponse> getUserGiftCardInfoPage(@Valid UserGiftCardQueryRequest request) {
        UserGiftCardInfoPageResponse response = new UserGiftCardInfoPageResponse();
        //查询分页信息
        MicroServicePage<UserGiftCardInfoVO> page = userGiftCardService.queryPage(request);
        response.setUserGiftCardInfoVOS(page);
        //查询可用数量
        if ( Objects.equals(GiftCardUseStatus.USE, request.getStatus())) {
            response.setUseNum(page.getTotal());
            BigDecimal cardBalance =
                    userGiftCardService
                            .getGiftCardBalanceByCustomerId(
                                    UserGiftCardBalanceQueryRequest.builder()
                                            .customerId(request.getCustomerId())
                                            .giftCardStatus(GiftCardStatus.ACTIVATED)
                                            .build())
                            .getGiftCardBalance();
            response.setCardBalance(cardBalance);
        } else {
            response.setUseNum(userGiftCardService.getUseNumCustomerId(request.getCustomerId(), request.getGiftCardType()));
        }
        //查询不可用数量
        if ( Objects.equals(GiftCardUseStatus.INVALID, request.getStatus())
                && Objects.isNull(request.getInvalidStatus())) {
            response.setInvalidNum(page.getTotal());
        } else {
            response.setInvalidNum(userGiftCardService.getInvalidCustomerId(request.getCustomerId(), request.getGiftCardType()));
        }
        //查询待激活数量
        if ( Objects.equals(GiftCardUseStatus.NOT_ACTIVE, request.getStatus())) {
            response.setNotActiveNum(page.getTotal());
            Long cardBalance =
                    userGiftCardService
                            .getGiftCardParValueByCustomerId(
                                    UserGiftCardBalanceQueryRequest.builder()
                                            .customerId(request.getCustomerId())
                                            .giftCardStatus(GiftCardStatus.NOT_ACTIVE)
                                            .build());
            response.setCardBalance(Objects.isNull(cardBalance) ? BigDecimal.ZERO : new BigDecimal(cardBalance));
        } else {
            response.setNotActiveNum(userGiftCardService.getNotActiveNumCustomerId(request.getCustomerId(), request.getGiftCardType()));
        }
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<UserGiftCardInfoNumResponse> getUserGiftCardInfoNum(@Valid UserGiftCardQueryRequest request) {
        Long useNum = userGiftCardService.getUseNumCustomerId(request.getCustomerId(), null);
        return BaseResponse.success(UserGiftCardInfoNumResponse.builder().useNum(useNum).build());
    }

    @Override
    public BaseResponse<UserGiftCardTradeResponse> tradeUserGiftCard(@Valid UserGiftCardTradeRequest request) {
        UserGiftCardTradeResponse response = userGiftCardService.tradeUserGiftCard(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse checkUserGiftCard(@Valid UserGiftCardUseCheckRequest request) {
        userGiftCardService.checkUserGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<UserGiftCardListResponse> getUserGiftCardList(@Valid UserGiftCardQueryRequest request) {
        UserGiftCardListResponse response = userGiftCardService.getUserGiftCardList(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse<UserGiftCardUseResponse> useUserGiftCard(@Valid UserGiftCardTransRequest request) {
        UserGiftCardUseResponse response = userGiftCardService.useUserGiftCard(request);
        return BaseResponse.success(response);
    }

    @Override
    public BaseResponse returnUserGiftCard(@Valid UserGiftCardTransRequest request) {
        userGiftCardService.returnUserGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse<Long> countForExport(@Valid UserGiftCardQueryRequest request) {
        Long total = userGiftCardService.count(request);
        return BaseResponse.success(total);
    }

    @Override
    public BaseResponse<UserGiftCardInfoPageResponse> exportGiftCardDetailRecord(@Valid UserGiftCardQueryRequest request) {
        UserGiftCardInfoPageResponse response = new UserGiftCardInfoPageResponse();
        MicroServicePage<UserGiftCardInfoVO> page = userGiftCardService.queryPage(request);
        response.setUserGiftCardInfoVOS(page);
        return BaseResponse.success(response);
    }

    /**
     * @param userPickupCardRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @description 提货卡适用男
     * @author edz
     * @date: 2023/7/3 16:45
     */
    @Override
    public BaseResponse userPickupCard(UserPickupCardRequest userPickupCardRequest) {
        userGiftCardService.userPickupCard(userPickupCardRequest);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param userReturnPickupCardRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @description 订单取消返还提货卡
     * @author 刘方鑫
     * @date: 2025/8/12 16:45
     */
    @Override
    public BaseResponse returnPickupCard(@RequestBody @Valid UserReturnPickupCardRequest userReturnPickupCardRequest) {
        userGiftCardService.returnPickupCard(userReturnPickupCardRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse oldSendNewAutoActivateGiftCard(@RequestBody OldSendNewAutoActivateGiftCardRequest  request){
        List<String> orderSnList = request.getOrderSnList();
        userGiftCardService.oldSendNewAutoActivateGiftCard(orderSnList);
        return BaseResponse.SUCCESSFUL();
    }

}
