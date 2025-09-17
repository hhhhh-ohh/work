package com.wanmi.sbc.elastic.provider.impl.distributioninvitenew;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributioninvitenew.EsDistributionInviteNewQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.response.distributioninvitenew.EsDistributionInviteNewPageResponse;
import com.wanmi.sbc.elastic.distributioninvitenew.service.EsDistributionInviteNewQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/6 14:26
 * @description <p>邀请记录查询</p>
 */
@RestController
public class EsDistributionInviteNewQueryController implements EsDistributionInviteNewQueryProvider {

    @Autowired
    private EsDistributionInviteNewQueryService esDistributionInviteNewQueryService;

    /**
     * 分页查询邀请记录
     * @param request
     * @return
     */
    @Override
    public BaseResponse<EsDistributionInviteNewPageResponse> findDistributionInviteNewRecord(@RequestBody @Valid EsDistributionInviteNewPageRequest request) {
        return esDistributionInviteNewQueryService.findDistributionInviteNewRecord(request);
    }
}
