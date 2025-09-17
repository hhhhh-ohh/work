package com.wanmi.sbc.goods.api.provider.brand;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.brand.ContractBrandAuditListRequest;
import com.wanmi.sbc.goods.api.response.brand.ContractBrandAuditListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>对二次签约品牌查询接口</p>
 * @author wangchao
 */
@FeignClient(value = "${application.goods.name}", contextId = "ContractBrandAuditQueryProvider")
public interface ContractBrandAuditQueryProvider {

    /**
     * 条件查询签约品牌列表
     *
     * @param request 签约品牌查询数据结构 {@link ContractBrandAuditListRequest}
     * @return 签约品牌列表 {@link ContractBrandAuditListResponse}
     */
    @PostMapping("/goods/${application.goods.version}/brand/audit/contract/list")
    BaseResponse<ContractBrandAuditListResponse> list(@RequestBody @Valid ContractBrandAuditListRequest request);

}
