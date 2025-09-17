package com.wanmi.sbc.elastic.api.provider.distributioninvitenew;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewInitRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewSaveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/6 16:13
 * @description <p> 邀新记录 </p>
 */
@FeignClient(name = "${application.elastic.name}",contextId = "EsDistributionInviteNewProvider")
public interface EsDistributionInviteNewProvider {

    /**
     * 初始化邀新记录信息
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution/invite-new-init")
    BaseResponse initDistributionInviteNewRecord(@RequestBody @Valid EsDistributionInviteNewInitRequest request);

    /**
     * 新增邀新记录
     *
     * @param request
     * @return
     */
    @PostMapping("/elastic/${application.elastic.version}/distribution/invite-new-add")
    BaseResponse addInviteNewRecord(@RequestBody EsDistributionInviteNewSaveRequest request);
}
