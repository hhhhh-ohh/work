package com.wanmi.sbc.elastic.provider.impl.distributionrecord;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributionrecord.EsDistributionRecordQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributionrecord.EsDistributionRecordPageRequest;
import com.wanmi.sbc.elastic.api.response.distributionrecord.EsDistributionRecordPageResponse;
import com.wanmi.sbc.elastic.distributionrecord.service.EsDistributionRecordQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/4 16:04
 * @description <p> 分销记录查询 </p>
 */
@RestController
public class EsDistributionRecordQueryController implements EsDistributionRecordQueryProvider {

    @Autowired
    private EsDistributionRecordQueryService esDistributionRecordQueryService;

    /**
     * 分销记录分页查询
     *
     * @param request 分页请求参数和筛选对象 {@link EsDistributionRecordPageRequest}
     * @return BaseResponse
     */
    @Override
    public BaseResponse<EsDistributionRecordPageResponse> page(@RequestBody @Valid EsDistributionRecordPageRequest request) {
        return esDistributionRecordQueryService.page(request);
    }
}
