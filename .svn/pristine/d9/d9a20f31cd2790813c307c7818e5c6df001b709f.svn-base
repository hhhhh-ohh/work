package com.wanmi.sbc.marketing.gift.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.request.gift.FullGiftReduceStockRequest;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.gift.request.MarketingFullGiftSaveRequest;

/**
 * 营销满赠业务接口
 */
public interface MarketingFullGiftServiceInterface {

    /**
     * 新增满赠
     * @param request
     * @return
     * @throws SbcRuntimeException
     */
    Marketing addMarketingFullGift(MarketingFullGiftSaveRequest request) throws SbcRuntimeException;

    /**
     * 修改满赠
     * @param request
     * @throws SbcRuntimeException
     */
    void modifyMarketingFullGift(MarketingFullGiftSaveRequest request) throws SbcRuntimeException;

    /**
     * 扣减赠品库存
     * @param request
     */
    void reduceStock(FullGiftReduceStockRequest request);
}
