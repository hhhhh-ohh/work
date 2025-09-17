package com.wanmi.sbc.credit;

import com.wanmi.sbc.account.api.provider.credit.CreditAuditProvider;
import com.wanmi.sbc.account.api.provider.credit.CustomerApplyRecordProvider;
import com.wanmi.sbc.account.api.request.credit.CreditApplyDetailRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditQueryRequest;
import com.wanmi.sbc.account.api.request.credit.CreditAuditRequest;
import com.wanmi.sbc.account.api.response.credit.CreditApplyDetailResponse;
import com.wanmi.sbc.account.api.response.credit.account.CreditAccountDetailResponse;
import com.wanmi.sbc.account.bean.vo.CustomerApplyRecordVo;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.NodeType;
import com.wanmi.sbc.common.enums.node.AccoutAssetsType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.message.notice.NoticeService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/***
 * 授信审核API
 * @author zhengyang
 */
@RestController
@Validated
@Tag(name = "CreditAuditController", description = "S2B 授信审核API")
@RequestMapping("/credit/account")
public class CreditAuditController {

    @Resource
    private CreditAuditProvider creditAuditProvider;
    @Resource
    private CustomerApplyRecordProvider applyRecordProvider;
    @Resource
    private OperateLogMQUtil operateLogMQUtil;
    @Resource
    private CommonUtil commonUtil;
    @Resource
    private NoticeService noticeService;
    @Resource
    private CustomerCacheService customerCacheService;

    @Operation(summary = "查询授信审核列表")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @PostMapping("/queryApplyRecord")
    @ReturnSensitiveWords(functionName = "f_boss_query_apply_record_sign_word")
    public BaseResponse<MicroServicePage<CustomerApplyRecordVo>> queryApplyRecord(@RequestBody CreditAuditQueryRequest request) {
        BaseResponse<MicroServicePage<CustomerApplyRecordVo>> microServicePageBaseResponse = applyRecordProvider.queryApplyRecord(request);
        List<String> customerIds = microServicePageBaseResponse.getContext().getContent()
                .stream()
                .map(CustomerApplyRecordVo::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        microServicePageBaseResponse.getContext().getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return microServicePageBaseResponse;
    }

    @Operation(summary = "根据用户申请记录ID查询用户授信申请详情")
    @PostMapping("/findCreditAccountApplyDetailById")
    public BaseResponse<CreditApplyDetailResponse> findCreditAccountApplyDetailById(@RequestBody @Validated CreditApplyDetailRequest request) {
        return applyRecordProvider.findCreditAccountApplyDetailById(request);
    }

    @MultiSubmit
    @Operation(summary = "授信通过审核")
    @PostMapping("/applyAgree")
    public BaseResponse applyAgree(@RequestBody CreditAuditRequest request) {
        // 参数验证
        if (Strings.isBlank(request.getId())
                || Strings.isBlank(request.getCustomerId())
                || Objects.isNull(request.getCreditAmount()) || request.getCreditAmount().compareTo(BigDecimal.ZERO) <=0 || request.getCreditAmount().compareTo(new BigDecimal("9999999")) > 0
                || Objects.isNull(request.getEffectiveDays()) || request.getEffectiveDays().compareTo(0L) <1 || request.getEffectiveDays().compareTo(365L) > 0
        ) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        request.setCustomerId(commonUtil.getOperatorId());
        BaseResponse<CreditAccountDetailResponse> response = creditAuditProvider.applyAgree(request);
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("财务", "申请同意", "授信申请通过：" + response.getContext());
            // 如果变更额度ID为空，就是授信审核通过
            if (StringUtils.isEmpty(response.getContext().getChangeRecordId())) {
                noticeService.sendMessage(
                        NodeType.ACCOUNT_ASSETS.toValue(),
                        AccoutAssetsType.CREDIT_AUDIT_PASS_NOTICE.toValue(),
                        AccoutAssetsType.CREDIT_AUDIT_PASS_NOTICE.getType(),
                        "",
                        response.getContext().getCustomerId(),
                        response.getContext().getCustomerAccount(),
                        Constants.no);
            } else {
                noticeService.sendMessage(
                        NodeType.ACCOUNT_ASSETS.toValue(),
                        AccoutAssetsType.CREDIT_CHANGE_PASS_NOTICE.toValue(),
                        AccoutAssetsType.CREDIT_CHANGE_PASS_NOTICE.getType(),
                        "",
                        response.getContext().getCustomerId(),
                        response.getContext().getCustomerAccount(),
                        Constants.no);
            }
        }
        return response;
    }

    @MultiSubmit
    @Operation(summary = "授信驳回审核")
    @PostMapping("/applyReject")
    public BaseResponse applyReject(@RequestBody CreditAuditRequest request) {
        // 参数验证
        if (Strings.isBlank(request.getId())
                || Strings.isBlank(request.getCustomerId())
                || Strings.isBlank(request.getRejectReason()) || request.getRejectReason().length() < 1 || request.getRejectReason().length() > 100
        ) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCustomerId(commonUtil.getOperatorId());
        BaseResponse<CreditAccountDetailResponse> response = creditAuditProvider.applyReject(request);
        if (CommonErrorCodeEnum.K000000.getCode().equals(response.getCode())) {
            operateLogMQUtil.convertAndSend("财务", "申请驳回", "授信申请驳回：" + response.getContext());
            // 如果变更额度ID为空，就是授信审核驳回
            if (StringUtils.isEmpty(response.getContext().getChangeRecordId())) {
                noticeService.sendMessage(
                        NodeType.ACCOUNT_ASSETS.toValue(),
                        AccoutAssetsType.CREDIT_AUDIT_REJECTED_NOTICE.toValue(),
                        AccoutAssetsType.CREDIT_AUDIT_REJECTED_NOTICE.getType(),
                        "",
                        response.getContext().getCustomerId(),
                        response.getContext().getCustomerAccount(),
                        Constants.no);
            } else {
                noticeService.sendMessage(
                        NodeType.ACCOUNT_ASSETS.toValue(),
                        AccoutAssetsType.CREDIT_CHANGE_REJECTED_NOTICE.toValue(),
                        AccoutAssetsType.CREDIT_CHANGE_REJECTED_NOTICE.getType(),
                        "",
                        response.getContext().getCustomerId(),
                        response.getContext().getCustomerAccount(),
                        Constants.no);
            }
        }
        return response;
    }
}
