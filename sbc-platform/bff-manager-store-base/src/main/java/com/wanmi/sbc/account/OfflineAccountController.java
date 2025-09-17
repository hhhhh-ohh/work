package com.wanmi.sbc.account;

import com.google.common.collect.Maps;
import com.wanmi.sbc.account.api.provider.company.CompanyAccountProvider;
import com.wanmi.sbc.account.api.provider.company.CompanyAccountQueryProvider;
import com.wanmi.sbc.account.api.request.company.CompanyAccountAffirmRemitRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountBatchRenewalRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountByCompanyInfoIdAndDefaultFlagRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountFindByAccountIdRequest;
import com.wanmi.sbc.account.api.request.company.CompanyAccountModifyPrimaryRequest;
import com.wanmi.sbc.account.api.response.storeInformation.StoreAuditStateResponse;
import com.wanmi.sbc.account.bean.vo.CompanyAccountVO;
import com.wanmi.sbc.account.request.OfflineAccountRequest;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.BoolFlag;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.elastic.api.provider.storeInformation.EsStoreInformationProvider;
import com.wanmi.sbc.elastic.api.request.storeInformation.StoreInfoStateModifyRequest;
import com.wanmi.sbc.message.service.StoreMessageBizService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.seata.spring.annotation.GlobalTransactional;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * 商家结算银行账户
 * Created by sunkun on 2017/11/2.
 */
@RestController("supplierOfflineAccountController")
@Validated
@RequestMapping("/account")
@Tag(name = "OfflineAccountController", description = "S2B 商家端-商家结算银行账户API")
public class OfflineAccountController {

    @Autowired
    private CompanyAccountQueryProvider companyAccountQueryProvider;

    @Autowired
    private CompanyAccountProvider companyAccountProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsStoreInformationProvider esStoreInformationProvider;

    @Autowired
    private StoreMessageBizService storeMessageBizService;

    /**
     * 获取商家结算银行账户
     *
     * @return
     */
    @Operation(summary = "获取商家的结算银行账户")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_store_company_account_sign_word")
    public BaseResponse<List<CompanyAccountVO>> list() {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        return BaseResponse.success(companyAccountQueryProvider.listByCompanyInfoIdAndDefaultFlag(
                CompanyAccountByCompanyInfoIdAndDefaultFlagRequest.builder()
                        .companyInfoId(companyInfoId).defaultFlag(DefaultFlag.NO).build()
        ).getContext().getCompanyAccountVOList());
    }

    /**
     * 批量更新结算银行账户
     *
     * @return
     */
    @MultiSubmit
    @Operation(summary = "批量操作结算银行账户，批量操作结算银行账户，此方法可以新增、修改、删除结算银行账号")
    @RequestMapping(value = "/renewal", method = RequestMethod.POST)
    @GlobalTransactional
    public BaseResponse renewal(@RequestBody OfflineAccountRequest accountRequest) {
        Long companyInfoId = commonUtil.getCompanyInfoId();
        Long storeId= commonUtil.getStoreId();
        Map<Long, CompanyAccountVO> companyAccountMap = Maps.newHashMap();

        if (CollectionUtils.isNotEmpty(accountRequest.getOfflineAccounts())) {
            accountRequest.getOfflineAccounts().stream()
                    .filter(request -> nonNull(request.getAccountId()))
                    .forEach(request -> this.getCompanyAccount(request.getAccountId())
                            .ifPresent(companyAccount -> {
                                //越权校验
                                commonUtil.checkCompanyInfoId(companyAccount.getCompanyInfoId());
                                companyAccountMap.put(request.getAccountId(), companyAccount);
                            }));
        }
        //操作日志：删除
        if (CollectionUtils.isNotEmpty(accountRequest.getDeleteIds())) {
            accountRequest.getDeleteIds().forEach(id -> this.getCompanyAccount(id).ifPresent(companyAccount -> {
                //越权校验
                commonUtil.checkCompanyInfoId(companyAccount.getCompanyInfoId());
                operateLogMQUtil.convertAndSend("财务", "删除账号",
                        "删除账号：账号" + companyAccount.getBankNo());
            }));
        }

        BaseResponse<StoreAuditStateResponse> response = companyAccountProvider.batchRenewalReturnState(
                CompanyAccountBatchRenewalRequest.builder()
                        .companyAccountSaveDTOList(accountRequest.getOfflineAccounts())
                        .accountIds(accountRequest.getDeleteIds())
                        .companyInfoId(companyInfoId).build()
        );
        //操作日志：新增、修改
        if (CollectionUtils.isNotEmpty(accountRequest.getOfflineAccounts())) {
            accountRequest.getOfflineAccounts().forEach(account -> {
                //新增
                if (isNull(account.getAccountId())) {
                    operateLogMQUtil.convertAndSend("财务", "新增账号", "新增账号：账号" + account.getBankNo());
                } else {//修改
                    if (companyAccountMap.containsKey(account.getAccountId())) {
                        if(companyAccountMap.get(account.getAccountId()).getBankNo().equals(account.getBankNo())){
                            operateLogMQUtil.convertAndSend("财务", "变更当前收款账户",
                                    "变更当前收款账户：账号" + account.getBankNo());
                        }else {
                            operateLogMQUtil.convertAndSend("财务", "变更当前收款账户",
                                    "变更当前收款账户：账号" + companyAccountMap.get(account.getAccountId()).getBankNo() + "变更为" + account.getBankNo());
                        }
                    }
                }
            });
        }
        if(Objects.nonNull(response.getContext())){
            if(Objects.nonNull(response.getContext())){
                if(Objects.nonNull(response.getContext().getAuditState())){
                    //更新es店铺审核状态
                    esStoreInformationProvider.modifyStoreState(StoreInfoStateModifyRequest
                            .builder()
                            .storeId(storeId)
                            .auditState(response.getContext().getAuditState().toValue())
                            .remitAffirm(response.getContext().getRemitAffirm()).build());

                    // ============= 处理平台的消息发送：新商家/供应商入驻待审核 START =============
                    storeMessageBizService.handleForStoreAudit(response, storeId);
                    // ============= 处理平台的消息发送：新商家/供应商入驻待审核 END =============
                }
            }
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 确认收到打款
     *
     * @param request
     * @return
     */
    @Operation(summary = "确认收到打款")
    @RequestMapping(value = "/affirm/remit", method = RequestMethod.PUT)
    @GlobalTransactional
    public BaseResponse affirmRemit(@RequestBody CompanyAccountAffirmRemitRequest request) {
        if (isNull(request.getAccountId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        this.getCompanyAccount(request.getAccountId()).ifPresent(companyAccount -> operateLogMQUtil.convertAndSend(
                "财务", "收到打款", "收到打款：账号" + companyAccount.getBankNo()));

        request.setCompanyInfoId(commonUtil.getCompanyInfoId());
        BaseResponse<BoolFlag>  result = companyAccountProvider.affirmRemitAndReturn(request);
        //更新es确认打款状态
        if(Objects.nonNull(result.getContext())){
            esStoreInformationProvider.modifyStoreState(StoreInfoStateModifyRequest
                    .builder()
                    .storeId(commonUtil.getStoreId())
                    .remitAffirm(result.getContext().toValue()).build());
        }
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 设为主账户
     *
     * @param request
     * @return
     */
    @Operation(summary = "设为主账户")
    @RequestMapping(value = "/set/primary", method = RequestMethod.PUT)
    public BaseResponse setPrimary(@RequestBody CompanyAccountModifyPrimaryRequest request) {
        if (isNull(request.getAccountId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.setCompanyInfoId(commonUtil.getCompanyInfoId());

        this.getCompanyAccount(request.getAccountId()).ifPresent(companyAccount -> operateLogMQUtil.convertAndSend("财务",
                "设置主账号", "设置主账号：账号" + companyAccount.getBankNo()));
        return companyAccountProvider.modifyPrimary(request);
    }

    /**
     * 公共方法获取账号信息
     *
     * @param accountId
     * @return
     */
    private Optional<CompanyAccountVO> getCompanyAccount(Long accountId) {
        return Optional.ofNullable(companyAccountQueryProvider.getByAccountId(new CompanyAccountFindByAccountIdRequest(accountId)).getContext());
    }
}
