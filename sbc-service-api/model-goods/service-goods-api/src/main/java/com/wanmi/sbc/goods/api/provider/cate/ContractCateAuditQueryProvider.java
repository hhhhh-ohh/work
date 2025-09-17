package com.wanmi.sbc.goods.api.provider.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.goods.api.request.cate.*;
import com.wanmi.sbc.goods.api.response.cate.ContractCateAuditListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

/**
 * <p>签约分类查询服务</p>
 * author: wangchao
 * Date: 2018-11-05
 */
@FeignClient(value = "${application.goods.name}", contextId = "ContractCateAuditQueryProvider")
public interface ContractCateAuditQueryProvider {

    /**
     * 查询签约分类
     * @param request 查询签约分类 {@link ContractCateAuditListRequest}
     * @return {@link ContractCateAuditListResponse}
     */
    @PostMapping("/goods/${application.goods.version}/contract/cate/audit/list")
    BaseResponse<ContractCateAuditListResponse> list(@RequestBody @Valid ContractCateAuditListRequest request);

}
