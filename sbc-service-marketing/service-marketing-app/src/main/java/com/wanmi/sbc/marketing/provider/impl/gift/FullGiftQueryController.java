package com.wanmi.sbc.marketing.provider.impl.gift;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.api.provider.gift.FullGiftQueryProvider;
import com.wanmi.sbc.marketing.api.request.gift.*;
import com.wanmi.sbc.marketing.api.response.gift.*;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullGiftLevelVO;
import com.wanmi.sbc.marketing.gift.response.MarketingFullGiftLevelResponse;
import com.wanmi.sbc.marketing.gift.service.MarketingFullGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-22 9:37
 */
@Validated
@RestController
public class FullGiftQueryController implements FullGiftQueryProvider {

    @Autowired
    private MarketingFullGiftService marketingFullGiftService;

    /**
     * @param request 营销id {@link FullGiftLevelListByMarketingIdRequest}
     * @return
     */
    @Override
    public BaseResponse<FullGiftLevelListByMarketingIdResponse> listLevelByMarketingId(@RequestBody @Valid FullGiftLevelListByMarketingIdRequest request) {
        List<MarketingFullGiftLevelVO> fullGiftLevelVOList = marketingFullGiftService.getLevelsByMarketingId(request.getMarketingId());
        return BaseResponse.success(FullGiftLevelListByMarketingIdResponse.builder().fullGiftLevelVOList(fullGiftLevelVOList).build());
    }

    /**
     * @param request 营销id与客户信息 {@link FullGiftLevelListByMarketingIdAndCustomerRequest}
     * @return
     */
    @Override
    public BaseResponse<FullGiftLevelListByMarketingIdAndCustomerResponse> listGiftByMarketingIdAndCustomer(@RequestBody @Valid FullGiftLevelListByMarketingIdAndCustomerRequest request) {
        MarketingFullGiftLevelResponse giftListResponse = marketingFullGiftService.getGiftList(request.getMarketingId(),
                request.getCustomerId(), request.getIsMarketing());
        return BaseResponse.success(FullGiftLevelListByMarketingIdAndCustomerResponse.builder()
                .giftList(giftListResponse.getGiftList())
                .levelList(KsBeanUtil.convert(giftListResponse.getLevelList(), MarketingFullGiftLevelVO.class))
                .build());
    }

    /**
     * @param request 营销id与等级id {@link FullGiftDetailListByMarketingIdAndLevelIdRequest}
     * @return
     */
    @Override
    public BaseResponse<FullGiftDetailListByMarketingIdAndLevelIdResponse> listDetailByMarketingIdAndLevelId(@RequestBody @Valid FullGiftDetailListByMarketingIdAndLevelIdRequest request) {
        List<MarketingFullGiftDetailVO> fullGiftDetailVOList = KsBeanUtil.convert(marketingFullGiftService.getGiftList
                (request.getMarketingId(), request.getGiftLevelId()), MarketingFullGiftDetailVO.class);
        return BaseResponse.success(FullGiftDetailListByMarketingIdAndLevelIdResponse.builder().fullGiftDetailVOList(fullGiftDetailVOList).build());
    }

    @Override
    public BaseResponse<FullGiftDetailListByMarketingIdsAndLevelIdsResponse>
            listDetailByMarketingIdsAndLevelIds(
                    @RequestBody @Valid
                            FullGiftDetailListByMarketingIdsAndLevelIdsRequest request) {
        List<MarketingFullGiftDetailVO> fullGiftDetailVOList =
                KsBeanUtil.convert(
                        marketingFullGiftService.getGiftList(
                                request.getMarketingIds(), request.getGiftLevelIds()),
                        MarketingFullGiftDetailVO.class);
        return BaseResponse.success(
                FullGiftDetailListByMarketingIdsAndLevelIdsResponse.builder()
                        .fullGiftDetailVOList(fullGiftDetailVOList)
                        .build());
    }

    @Override
    public BaseResponse<FullGiftDetailListByMarketingIdResponse> listDetailByMarketingId
            (@RequestBody @Valid FullGiftDetailListByMarketingIdRequest request) {
        List<MarketingFullGiftDetailVO> fullGiftDetailVOList =
                KsBeanUtil.convert(
                        marketingFullGiftService.getGiftListByMarketingId(
                                request.getMarketingId()),
                        MarketingFullGiftDetailVO.class);
        return BaseResponse.success(
                FullGiftDetailListByMarketingIdResponse.builder()
                        .fullGiftDetailVOList(fullGiftDetailVOList)
                        .build());
    }
}
