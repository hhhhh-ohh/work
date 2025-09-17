package com.wanmi.sbc.goods.api.provider.contract;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditCheckRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractAuditSaveRequest;
import com.wanmi.sbc.goods.api.request.contract.ContractSaveRequest;
import com.wanmi.sbc.goods.api.response.contract.ContractSaveResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对签约信息操作接口</p>
 * Created by daiyitian on 2018-10-31-下午6:23.
 */
@FeignClient(value = "${application.goods.name}", contextId = "ContractProvider")
public interface ContractProvider {

    /**
     * 更新签约信息
     *
     * @param request 保存签约信息数据结构 {@link ContractSaveRequest}
     * @return 保存签约信息响应结构 {@link ContractSaveResponse}
     */
    @PostMapping("/goods/${application.goods.version}/contract/save")
    BaseResponse<ContractSaveResponse> save(@RequestBody @Valid ContractSaveRequest request);

    /**
     * 更新二次签约信息
     *
     * @param request 保存签约信息数据结构 {@link ContractSaveRequest}
     * @return 保存签约信息响应结构 {@link ContractSaveResponse}
     */
    @PostMapping("/goods/${application.goods.version}/contract/audit/save")
    BaseResponse<ContractSaveResponse> saveAudit(@RequestBody @Valid ContractAuditSaveRequest request);


    /**
     * 校验并审核二次签约信息
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/contract/audit/check")
    BaseResponse checkAudit(@RequestBody @Valid ContractAuditCheckRequest request);

    /**
     * 校验二次签约信息是否有变更
     * @param request
     * @return
     */
    @PostMapping("/goods/${application.goods.version}/contract/audit/check/data")
    BaseResponse<Boolean> checkAuditData(@RequestBody @Valid ContractAuditSaveRequest request);
}
