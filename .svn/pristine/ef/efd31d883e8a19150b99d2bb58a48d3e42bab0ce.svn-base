package com.wanmi.sbc.elastic.api.provider.distributionrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordAddRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordByTradeIdRequest;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordInitRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/5 09:54
 * @description <p> 分销记录 </p>
 */
@FeignClient(name = "${application.elastic.name}", contextId = "EsDistributionRecordProvider")
public interface EsDistributionRecordProvider {

    /**
     * 添加分销记录
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distributionrecord/add")
    BaseResponse add(@RequestBody EsDistributionRecordAddRequest request);

    /**
     * 删除分销记录
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distributionrecord/delete-by-id")
    BaseResponse deleteByTradeId(@RequestBody @Valid EsDistributionRecordByTradeIdRequest request);

    /**
     * 初始化分销记录
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distributionrecord/init")
    BaseResponse init(@RequestBody EsDistributionRecordInitRequest request);

}
