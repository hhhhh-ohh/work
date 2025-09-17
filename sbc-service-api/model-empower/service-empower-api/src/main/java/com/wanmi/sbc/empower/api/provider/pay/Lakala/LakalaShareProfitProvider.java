package com.wanmi.sbc.empower.api.provider.pay.Lakala;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.pay.lakala.LakalaShareProfitRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayCompanyPaymentInfoRequest;
import com.wanmi.sbc.empower.api.request.settlement.SepTranSidRequest;
import com.wanmi.sbc.empower.api.request.settlement.SeporcancelForSupplierReq;
import com.wanmi.sbc.empower.api.request.settlement.SettlementRequest;
import com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.WxPayCompanyPaymentRsponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @description 拉卡拉分账
 * @author edz
 * @date 2022/7/20 11:27
 */
@FeignClient(value = "${application.empower.name}", contextId = "LakalaShareProfitProvider")
public interface LakalaShareProfitProvider {

    /**
     * @description 分账结算
     * @author  edz
     * @date: 2022/9/24 16:23
     * @param settlementRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/lakala/seporcancel")
    BaseResponse seporcancel(@RequestBody SettlementRequest settlementRequest);

    /**
     * @description 结算给商家
     * @author  edz
     * @date: 2022/9/24 16:23
     * @param settlementRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/empower/${application.empower.version}/lakala/seporcancel-supplier")
    BaseResponse seporcancelForSupplier(@RequestBody SeporcancelForSupplierReq settlementRequest);

    /**
     * @description 结算查询
     * @author  edz
     * @date: 2022/9/24 16:24
     * @param sepTranSidRequest
     * @return com.wanmi.sbc.common.base.BaseResponse<java.util.List<com.wanmi.sbc.empower.api.response.pay.lakala.LakalaShareProfitQueryResponse>>
     */
    @PostMapping("/empower/${application.empower.version}/lakala/seporcancel/query")
    BaseResponse<List<LakalaShareProfitQueryResponse>> seporcancelQuery(@RequestBody SepTranSidRequest sepTranSidRequest);
}
