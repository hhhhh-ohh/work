package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDeleteRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardNewRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardSaveRequest;
import com.wanmi.sbc.marketing.giftcard.service.GiftCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author wur
 * @className GiftCardQueryController
 * @description 礼品卡查询处理
 * @date 2022/12/8 19:55
 **/
@Validated
@RestController
public class GiftCardController implements GiftCardProvider {

    @Autowired
    private GiftCardService giftCardService;

    @Override
    public BaseResponse add(@RequestBody  @Valid GiftCardNewRequest request) {
        giftCardService.addGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse save(@RequestBody @Valid GiftCardSaveRequest request) {
        giftCardService.saveGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }

    @Override
    public BaseResponse delete(@RequestBody @Valid GiftCardDeleteRequest request) {
        giftCardService.delGiftCard(request);
        return BaseResponse.SUCCESSFUL();
    }
}