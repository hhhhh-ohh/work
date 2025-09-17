package com.wanmi.sbc.marketing.provider.impl.gift;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.gift.FullGiftProvider;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftAddRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftModifyRequest;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftReduceStockRequest;
import com.wanmi.sbc.marketing.api.response.gift.FullGiftAddResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingVO;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.gift.request.MarketingFullGiftSaveRequest;
import com.wanmi.sbc.marketing.gift.service.MarketingFullGiftService;
import com.wanmi.sbc.marketing.gift.service.MarketingFullGiftServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-22 9:37
 */
@Validated
@RestController
public class FullGiftController implements FullGiftProvider {

    @Autowired
    private MarketingFullGiftServiceInterface marketingFullGiftServiceInterface;


    /**
     * @param addRequest 新增参数 {@link FullGiftAddRequest}
     * @return
     */
    @Override
    public BaseResponse<FullGiftAddResponse> add(@RequestBody @Valid FullGiftAddRequest addRequest) {
        Marketing marketing = marketingFullGiftServiceInterface.addMarketingFullGift(KsBeanUtil.convert(addRequest, MarketingFullGiftSaveRequest.class));
        return BaseResponse.success(FullGiftAddResponse.builder().marketingVO(KsBeanUtil.convert(marketing, MarketingVO.class)).build());
    }

    /**
     * @param modifyRequest 修改参数 {@link FullGiftModifyRequest}
     * @return
     */
    @Override
    public BaseResponse modify(@RequestBody @Valid FullGiftModifyRequest modifyRequest) {
        marketingFullGiftServiceInterface.modifyMarketingFullGift(KsBeanUtil.convert(modifyRequest, MarketingFullGiftSaveRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    @Transactional
    @Override
    public BaseResponse reduceStock(FullGiftReduceStockRequest request) {
        marketingFullGiftServiceInterface.reduceStock(request);
        return BaseResponse.SUCCESSFUL();
    }
}
