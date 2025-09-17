package com.wanmi.sbc.marketing.common.service;

import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.common.model.root.Marketing;
import com.wanmi.sbc.marketing.common.request.MarketingRequest;
import com.wanmi.sbc.marketing.common.request.MarketingSaveRequest;
import com.wanmi.sbc.marketing.common.response.MarketingResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/***
 * 营销基础服务类
 * @className MarketingServiceInterface
 * @author zhengyang
 * @date 2021/12/2 15:28
 **/
public interface MarketingServiceInterface {

    /**
     * 保存营销信息
     *
     * @param request
     * @return
     * @throws SbcRuntimeException
     */
    Marketing addMarketing(MarketingSaveRequest request) throws SbcRuntimeException;

    /**
     * 修改营销信息
     * @param request
     * @return
     * @throws SbcRuntimeException
     */
    Marketing modifyMarketing(MarketingSaveRequest request) throws SbcRuntimeException;

    /**
     * 将营销活动集合，map成 { goodsId - list<Marketing> } 结构
     *
     * @param marketingRequest
     * @return
     */
    @Transactional
    Map<String, List<MarketingResponse>> getMarketingMapByGoodsId(MarketingRequest marketingRequest);
}
