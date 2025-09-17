package com.wanmi.sbc.elastic.api.provider.distributionrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionrecord.EsDistributionRecordPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/4 15:28
 * @description <p> 分销记录查询 </p>
 */
@FeignClient(name = "${application.elastic.name}",contextId = "EsDistributionRecordQueryProvider")
public interface EsDistributionRecordQueryProvider {

    /**
     * 分页查询DistributionRecordAPI
     *
     * @param request 分页请求参数和筛选对象 {@link EsDistributionRecordPageRequest}
     * @return DistributionRecord分页列表信息 {@link EsDistributionRecordPageResponse}
     */
    @PostMapping("/elastic/${application.elastic.version}/distributionrecord/page")
    BaseResponse<EsDistributionRecordPageResponse> page(@RequestBody @Valid EsDistributionRecordPageRequest
                                                              request);
}
