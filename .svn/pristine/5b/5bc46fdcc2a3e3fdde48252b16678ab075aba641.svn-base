package com.wanmi.sbc.marketing.provider.impl.discount;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.plugin.annotation.RoutingResource;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.discount.MarketingFullDiscountProvider;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountAddRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountModifyRequest;
import com.wanmi.sbc.marketing.api.request.discount.MarketingFullDiscountSaveLevelListRequest;
import com.wanmi.sbc.marketing.discount.model.request.MarketingFullDiscountSaveRequest;
import com.wanmi.sbc.marketing.discount.service.MarketingFullDiscountServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-22 10:41
 */
@Validated
@RestController
public class MarketingFullDiscountController implements MarketingFullDiscountProvider {

    @Autowired
    private MarketingFullDiscountServiceInterface marketingFullDiscountServiceInterface;

    /**
     * @param request 新增满折请求结构 {@link MarketingFullDiscountAddRequest}
     * @return
     */
    @Override
    public BaseResponse add(@RequestBody @Valid MarketingFullDiscountAddRequest request) {
        marketingFullDiscountServiceInterface.addMarketingFullDiscount(KsBeanUtil.convert(request, MarketingFullDiscountSaveRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 修改满折请求结构 {@link MarketingFullDiscountModifyRequest}
     * @return
     */
    @Override
    public BaseResponse modify(@RequestBody @Valid MarketingFullDiscountModifyRequest request) {
        marketingFullDiscountServiceInterface.modifyMarketingFullDiscount(KsBeanUtil.convert(request, MarketingFullDiscountSaveRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * @param request 保存多级优惠信息请求结构 {@link MarketingFullDiscountSaveLevelListRequest}
     * @return
     */
    @Override
    public BaseResponse saveLevelList(@RequestBody @Valid MarketingFullDiscountSaveLevelListRequest request) {
        return BaseResponse.SUCCESSFUL();
    }

}
