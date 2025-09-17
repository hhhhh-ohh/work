package com.wanmi.sbc.elastic.api.provider.settlement;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.settlement.EsSettlementPageRequest;
import com.wanmi.sbc.elastic.api.response.settlement.EsSettlementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @Author yangzhen
 * @Description // 结算单
 * @Date 18:27 2020/12/11
 * @Param
 * @return
 **/
@FeignClient(value = "${application.elastic.name}", contextId = "EsSettlementQueryProvider")
public interface EsSettlementQueryProvider {

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * <com.wanmi.sbc.elastic.api.response.settlement.EsSettlementResponse>
     * @Author yangzhen
     * @Description //es 分页查询结算单
     * @Date 17:59 2021/1/15
     * @Param [request]
     **/
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/es-settlement-page")
    BaseResponse<EsSettlementResponse> esSettlementPage(@RequestBody @Valid EsSettlementPageRequest request);

    /***
     * @description 拉卡拉结算单
     * @author  edz
     * @date: 2022/7/26 16:30
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse<com.wanmi.sbc.elastic.api.response.settlement.EsSettlementResponse>
     */
    @PostMapping("/elastic/${application.elastic.version}/storeInformation/lakala-es-settlement-page")
    BaseResponse<EsSettlementResponse> lakalaEsSettlementPage(@RequestBody @Valid EsSettlementPageRequest request);

}
