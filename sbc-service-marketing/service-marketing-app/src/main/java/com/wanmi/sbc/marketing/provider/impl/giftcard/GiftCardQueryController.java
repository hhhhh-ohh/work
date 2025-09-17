package com.wanmi.sbc.marketing.provider.impl.giftcard;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardInfoRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardInfoResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardPageResponse;
import com.wanmi.sbc.marketing.bean.vo.GiftCardVO;
import com.wanmi.sbc.marketing.giftcard.model.root.GiftCard;
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
public class GiftCardQueryController implements GiftCardQueryProvider {

    @Autowired
    private GiftCardService giftCardService;

    @Override
    public BaseResponse<GiftCardPageResponse> queryPage(@Valid GiftCardPageRequest request) {
        MicroServicePage<GiftCardVO> giftCardVOPage = giftCardService.queryPage(request);
        return BaseResponse.success(GiftCardPageResponse.builder().giftCardVOS(giftCardVOPage).build());
    }

    @Override
    public BaseResponse<GiftCardInfoResponse> queryInfo(@Valid GiftCardInfoRequest request) {
        GiftCardVO giftCardVO = giftCardService.queryGiftCardInfo(request);
        return BaseResponse.success(GiftCardInfoResponse.builder().giftCardVO(giftCardVO).build());
    }

    @Override
    public BaseResponse<GiftCardInfoResponse> queryDetail(@Valid GiftCardInfoRequest request) {
        GiftCardVO giftCardVO = giftCardService.queryGiftCardDetail(request);
        return BaseResponse.success(GiftCardInfoResponse.builder().giftCardVO(giftCardVO).build());
    }

    @Override
    public BaseResponse<GiftCardInfoResponse> checkAndGetForBatchSend(@RequestBody @Valid GiftCardInfoRequest request) {
        GiftCard giftCard = giftCardService.checkAndGetForBatchSend(request.getGiftCardId());
        return BaseResponse.success(GiftCardInfoResponse.builder().giftCardVO(giftCardService.wrapperVo(giftCard)).build());
    }
}