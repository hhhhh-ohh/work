package com.wanmi.sbc.customercredit;

import com.wanmi.sbc.account.api.provider.credit.CreditAuditProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyRequest;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDelFlagGetRequest;
import com.wanmi.sbc.customer.bean.vo.CustomerVO;
import com.wanmi.sbc.message.StoreMessageBizService;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;
import java.util.Objects;

/***
 * 授信账户审核Controller
 * @author zhengyang
 * @since 2021-03-02
 */
@RestController
@Validated
@RequestMapping("/credit/audit")
@Tag(name = "CustomerCreditAuditBaseController", description = "S2B web公用-授信账户审核API")
public class CustomerCreditAuditBaseController {
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private CreditAuditProvider creditAuditProvider;
    @Resource
    private CustomerQueryProvider customerQueryProvider;
    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /***
     * 申请审批
     * @param request
     * @return
     */
    @MultiSubmit
    @RequestMapping(value = "/applyAudit", method = RequestMethod.POST)
    public BaseResponse applyAudit(@RequestBody CreditApplyRequest request) {
        if (StringUtils.isBlank(request.getApplyNotes())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        BaseResponse<CustomerVO> customerRes = customerQueryProvider
                .getCustomerByCustomerId(CustomerDelFlagGetRequest.builder().customerId(commonUtil.getOperatorId()).build());

        if (Objects.nonNull(customerRes)
                && Objects.nonNull(customerRes.getContext())) {
            CustomerVO customerVO = customerRes.getContext();
            BaseResponse response = creditAuditProvider.applyAudit(CreditApplyRequest.builder()
                    .isChangeFlag(request.getIsChangeFlag())
                    .applyNotes(request.getApplyNotes())
                    .customerId(customerVO.getCustomerId())
                    .customerAccount(customerVO.getCustomerAccount())
                    .customerName(customerVO.getCustomerName()).build());

            // ============= 处理平台的消息发送：客户提交授信申请，平台待审核 START =============
            storeMessageBizService.handleForCreditAccountAudit(customerVO);
            // ============= 处理平台的消息发送：客户提交授信申请，平台待审核 END =============

            return response;
        }

        return BaseResponse.SUCCESSFUL();
    }
}
