package com.wanmi.sbc.marketing.discount.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.discount.model.request.MarketingFullDiscountSaveRequest;

/**
 * 营销满折接口
 * @author EDZ
 */
public interface MarketingFullDiscountServiceInterface {

    /**
     * 新增满折活动
     * @param request
     * @throws SbcRuntimeException
     */
    Marketing addMarketingFullDiscount(MarketingFullDiscountSaveRequest request) throws SbcRuntimeException;

    /**
     * 修改满折活动
     */
    void modifyMarketingFullDiscount(MarketingFullDiscountSaveRequest request) throws SbcRuntimeException;
}
