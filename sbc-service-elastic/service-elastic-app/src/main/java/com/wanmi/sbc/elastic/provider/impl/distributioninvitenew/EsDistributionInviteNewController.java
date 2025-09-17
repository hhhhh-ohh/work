package com.wanmi.sbc.elastic.provider.impl.distributioninvitenew;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.elastic.api.provider.distributioninvitenew.EsDistributionInviteNewProvider;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewInitRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewSaveRequest;
import com.wanmi.sbc.elastic.distributioninvitenew.service.EsDistributionInviteNewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author houshuai
 * @date 2021/1/6 16:19
 * @description <p> 邀新记录 </p>
 */
@RestController
public class EsDistributionInviteNewController implements EsDistributionInviteNewProvider {

    @Autowired
    private EsDistributionInviteNewService esDistributionInviteNewService;

    /**
     * 初始化邀新记录信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse initDistributionInviteNewRecord(@RequestBody @Valid EsDistributionInviteNewInitRequest request) {
        esDistributionInviteNewService.initDistributionInviteNewRecord(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 新增邀新记录
     * @param request
     * @return
     */
    @Override
    public BaseResponse addInviteNewRecord(@RequestBody EsDistributionInviteNewSaveRequest request) {
        esDistributionInviteNewService.addInviteNewRecord(request);
        return BaseResponse.SUCCESSFUL();
    }
}
