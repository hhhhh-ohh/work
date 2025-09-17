package com.wanmi.sbc.elastic.api.provider.distributioninvitenew;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.response.distributioninvitenew.EsDistributionInviteNewPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/6 14:19
 * @description <p> 邀新记录查询 </p>
 */
@FeignClient(name = "${application.elastic.name}",contextId = "EsDistributionInviteNewQueryProvider")
public interface EsDistributionInviteNewQueryProvider {

    /**
     * 根据条件分页查询邀新记录
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution/invite-new-page")
    BaseResponse<EsDistributionInviteNewPageResponse> findDistributionInviteNewRecord(@RequestBody @Valid
                                                                                              EsDistributionInviteNewPageRequest request);

}
