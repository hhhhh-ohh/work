package com.wanmi.sbc.customer;

import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailQueryProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailPageRequest;
import com.wanmi.sbc.elastic.api.response.customer.EsCustomerDetailPageResponse;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

/**
 * 企业会员
 */
@Tag(name = "EnterpriseCustomerBaseController", description = "企业会员 Api")
@RestController
@Validated
@RequestMapping("/enterpriseCustomer")
public class EnterpriseCustomerBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private EsCustomerDetailQueryProvider esCustomerDetailQueryProvider;

    /**
     * 分页查询企业会员
     * @return 企业会员信息
     */
    @Operation(summary = "分页查询企业会员")
    @EmployeeCheck
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_page_for_enterprise_customer_sign_word")
    public ResponseEntity<BaseResponse<EsCustomerDetailPageResponse>> pageForEnterpriseCustomer(@RequestBody EsCustomerDetailPageRequest enterpriseCustomerPageRequest) {
        if(!commonUtil.findVASBuyOrNot(VASConstants.VAS_IEP_SETTING)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        enterpriseCustomerPageRequest.setEnterpriseCustomer(Boolean.TRUE);
        enterpriseCustomerPageRequest.setFillArea(Boolean.FALSE);
        return ResponseEntity.ok(esCustomerDetailQueryProvider.page(enterpriseCustomerPageRequest));
    }
}
