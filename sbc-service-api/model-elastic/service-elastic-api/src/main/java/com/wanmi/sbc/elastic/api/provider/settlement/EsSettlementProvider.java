package com.wanmi.sbc.elastic.api.provider.settlement;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.settlement.*;
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
@FeignClient(value = "${application.elastic.name}", contextId = "EsSettlementProvider")
public interface EsSettlementProvider {

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author yangzhen
     * @Description //初始化结算单
     * @Date 17:58 2021/1/15
     * @Param [request]
     **/
    @PostMapping("/elastic/${application.elastic.version}/settlement/init")
    BaseResponse initSettlement(@RequestBody @Valid EsSettlementInitRequest request);

    /**
     * @description 结算单ID批量初始化es数据
     * @author  edz
     * @date: 2022/8/16 16:28
     * @param request
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/elastic/${application.elastic.version}/lakala/settlement/init")
    BaseResponse initLakalaSettlement(@RequestBody @Valid EsSettlementInitRequest request);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author yangzhen
     * @Description //批量初始化结算单
     * @Date 17:58 2021/1/15
     * @Param [request]
     **/
    @PostMapping("/elastic/${application.elastic.version}/settlement/init-settlement-list")
    BaseResponse initSettlementList(@RequestBody @Valid EsSettlementListRequest request);

    /**
     * @return com.wanmi.sbc.common.base.BaseResponse
     * @Author yangzhen
     * @Description //修改结算单状态
     * @Date 17:58 2021/1/15
     * @Param [request]
     **/
    @PostMapping("/elastic/${application.elastic.version}/settlement/update-settlement-status")
    BaseResponse updateSettlementStatus(@RequestBody @Valid SettlementQueryRequest request);

    /**
     * @description 同步结算单分账状态
     * @author  edz
     * @date: 2022/8/16 16:28
     * @param esSettlementInitRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/elastic/${application.elastic.version}/settlement/sync-lakalasettlement-status")
    BaseResponse syncLakalaSettlementStutus(@RequestBody EsSettlementInitRequest esSettlementInitRequest);

    /**
     * @description 根据结算单ID删除es
     * @author  edz
     * @date: 2022/8/16 16:27
     * @param esLakalaSettlementDelRequest
     * @return com.wanmi.sbc.common.base.BaseResponse
     */
    @PostMapping("/elastic/${application.elastic.version}/settlement/del-settlement-id")
    BaseResponse delLakalaSettlement(@RequestBody EsLakalaSettlementDelRequest esLakalaSettlementDelRequest);

}
