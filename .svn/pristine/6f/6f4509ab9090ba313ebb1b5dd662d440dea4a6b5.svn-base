package com.wanmi.sbc.goods.provider.impl.cate;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.goods.api.provider.cate.ContractCateAuditQueryProvider;
import com.wanmi.sbc.goods.api.request.cate.*;
import com.wanmi.sbc.goods.api.response.cate.ContractCateAuditListResponse;
import com.wanmi.sbc.goods.bean.vo.ContractCateAuditVO;
import com.wanmi.sbc.goods.cate.request.ContractCateAuditQueryRequest;
import com.wanmi.sbc.goods.cate.response.ContractCateAuditResponse;
import com.wanmi.sbc.goods.cate.service.ContractCateAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

/**
 * @Author: ZhangLingKe
 * @Description:
 * @Date: 2018-11-07 19:34
 */
@Validated
@RestController
public class ContractCateAuditQueryController implements ContractCateAuditQueryProvider {

    @Autowired
    private ContractCateAuditService contractCateService;


    /**
     * @param request 查询签约分类 {@link ContractCateListRequest}
     * @return
     */
    @Override
    public BaseResponse<ContractCateAuditListResponse> list(@RequestBody @Valid ContractCateAuditListRequest request) {
        ContractCateAuditQueryRequest contractCateQueryRequest = KsBeanUtil.convert(request, ContractCateAuditQueryRequest.class);
        List<ContractCateAuditResponse> contractCateResponses = contractCateService.queryList(contractCateQueryRequest);
        List<ContractCateAuditVO> contractCateVOList = KsBeanUtil.convert(contractCateResponses, ContractCateAuditVO.class);
        ContractCateAuditListResponse contractCateListResponse = new ContractCateAuditListResponse();
        contractCateListResponse.setContractCateList(contractCateVOList);

        return BaseResponse.success(contractCateListResponse);
    }
}
