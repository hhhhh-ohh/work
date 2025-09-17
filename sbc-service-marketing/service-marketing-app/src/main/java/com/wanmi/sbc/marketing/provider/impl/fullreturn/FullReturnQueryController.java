package com.wanmi.sbc.marketing.provider.impl.fullreturn;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.marketing.api.provider.fullreturn.FullReturnQueryProvider;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnDetailListByMarketingIdAndLevelIdRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnDetailListByMarketingIdsAndLevelIdsRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdAndCustomerRequest;
import com.wanmi.sbc.marketing.api.request.fullreturn.FullReturnLevelListByMarketingIdRequest;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnDetailListByMarketingIdAndLevelIdResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnDetailListByMarketingIdsAndLevelIdsResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnLevelListByMarketingIdAndCustomerResponse;
import com.wanmi.sbc.marketing.api.response.fullreturn.FullReturnLevelListByMarketingIdResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnDetailVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReturnLevelVO;
import com.wanmi.sbc.marketing.fullreturn.response.MarketingFullReturnLevelResponse;
import com.wanmi.sbc.marketing.fullreturn.service.MarketingFullReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @Author: xufeng
 * @Description:
 * @Date: 2022-04-11 9:37
 */
@Validated
@RestController
public class FullReturnQueryController implements FullReturnQueryProvider {

    @Autowired
    private MarketingFullReturnService marketingFullReturnService;

    /**
     * @param request 营销id {@link FullReturnLevelListByMarketingIdRequest}
     * @return
     */
    @Override
    public BaseResponse<FullReturnLevelListByMarketingIdResponse> listLevelByMarketingId(@RequestBody @Valid FullReturnLevelListByMarketingIdRequest request) {
        List<MarketingFullReturnLevelVO> fullReturnLevelVOList = KsBeanUtil.convert(marketingFullReturnService
                .getLevelsByMarketingId(request.getMarketingId()), MarketingFullReturnLevelVO.class);
        return BaseResponse.success(FullReturnLevelListByMarketingIdResponse.builder().fullReturnLevelVOList(fullReturnLevelVOList).build());
    }

    /**
     * @param request 营销id与客户信息 {@link FullReturnLevelListByMarketingIdAndCustomerRequest}
     * @return
     */
    @Override
    public BaseResponse<FullReturnLevelListByMarketingIdAndCustomerResponse> listReturnByMarketingIdAndCustomer(@RequestBody @Valid FullReturnLevelListByMarketingIdAndCustomerRequest request) {
        MarketingFullReturnLevelResponse returnListResponse = marketingFullReturnService.getReturnList(request.getMarketingId());
        return BaseResponse.success(FullReturnLevelListByMarketingIdAndCustomerResponse.builder()
                .returnList(returnListResponse.getReturnList())
                .levelList(KsBeanUtil.convert(returnListResponse.getLevelList(), MarketingFullReturnLevelVO.class))
                .build());
    }

    /**
     * @param request 营销id与等级id {@link FullReturnDetailListByMarketingIdAndLevelIdRequest}
     * @return
     */
    @Override
    public BaseResponse<FullReturnDetailListByMarketingIdAndLevelIdResponse> listDetailByMarketingIdAndLevelId(@RequestBody @Valid FullReturnDetailListByMarketingIdAndLevelIdRequest request) {
        List<MarketingFullReturnDetailVO> fullReturnDetailVOList = KsBeanUtil.convert(marketingFullReturnService.getReturnList
                (request.getMarketingId(), request.getReturnLevelId()), MarketingFullReturnDetailVO.class);
        return BaseResponse.success(FullReturnDetailListByMarketingIdAndLevelIdResponse.builder().fullReturnDetailVOList(fullReturnDetailVOList).build());
    }

    @Override
    public BaseResponse<FullReturnDetailListByMarketingIdsAndLevelIdsResponse>
            listDetailByMarketingIdsAndLevelIds(
                    @RequestBody @Valid
                            FullReturnDetailListByMarketingIdsAndLevelIdsRequest request) {
        List<MarketingFullReturnDetailVO> fullReturnDetailVOList =
                KsBeanUtil.convert(
                        marketingFullReturnService.getReturnList(
                                request.getMarketingIds(), request.getReturnLevelIds()),
                        MarketingFullReturnDetailVO.class);
        return BaseResponse.success(
                FullReturnDetailListByMarketingIdsAndLevelIdsResponse.builder()
                        .fullReturnDetailVOList(fullReturnDetailVOList)
                        .build());
    }
}
