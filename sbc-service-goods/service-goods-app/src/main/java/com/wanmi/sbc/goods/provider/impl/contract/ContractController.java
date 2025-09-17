package com.wanmi.sbc.goods.provider.impl.contract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.contract.ContractProvider;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditSaveRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractSaveRequest;
import com.wanmi.sbc.goods.api.response.contract.ContractSaveResponse;
import com.wanmi.sbc.goods.contract.request.ContractRequest;
import com.wanmi.sbc.goods.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * <p>对签约信息操作接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@RestController
@Validated
public class ContractController implements ContractProvider {

    @Autowired
    private ContractService contractService;

    /**
     * 更新签约信息
     *
     * @param request 保存签约信息数据结构 {@link ContractSaveRequest}
     * @return 保存签约信息响应结构 {@link ContractSaveResponse}
     */
    @Override

    public BaseResponse<ContractSaveResponse> save(@RequestBody @Valid ContractSaveRequest request){
        return BaseResponse.success(ContractSaveResponse.builder()
                .brandIds(contractService.renewal(KsBeanUtil.convert(request, ContractRequest.class)))
                .build());
    }

    /**
     * 更新二次签约信息
     *
     * @param request 保存签约信息数据结构 {@link ContractSaveRequest}
     * @return 保存签约信息响应结构 {@link ContractSaveResponse}
     */
    @Override
    public BaseResponse<ContractSaveResponse> saveAudit(@RequestBody @Valid ContractAuditSaveRequest request){
        contractService.renewalAudit(KsBeanUtil.convert(request, ContractRequest.class));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验并审核二次签约信息
     * @param request
     * @return
     */
    @Override
    public BaseResponse checkAudit(ContractAuditCheckRequest request) {
        contractService.checkAudit(request);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 校验二次签约信息是否有变更
     * @param request
     * @return
     */
    @Override
    public BaseResponse<Boolean> checkAuditData(ContractAuditSaveRequest request) {
        return BaseResponse.success(contractService.checkAuditData(request));
    }
}
