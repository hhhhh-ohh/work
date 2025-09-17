package com.wanmi.sbc.finance;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashProvider;
import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashQueryProvider;
import com.wanmi.sbc.account.api.provider.customerdrawcash.CustomerDrawCashSaveProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsDetailQueryProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsProvider;
import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.customerdrawcash.*;
import com.wanmi.sbc.account.api.request.funds.*;
import com.wanmi.sbc.account.api.response.customerdrawcash.CustomerDrawCashModifyResponse;
import com.wanmi.sbc.account.api.response.customerdrawcash.CustomerDrawCashStatusResponse;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.account.bean.enums.*;
import com.wanmi.sbc.account.bean.vo.CustomerDrawCashVO;
import com.wanmi.sbc.account.bean.vo.CustomerFundsForEsVO;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.HttpUtil;
import com.wanmi.sbc.customer.api.provider.detail.CustomerDetailQueryProvider;
import com.wanmi.sbc.customer.api.request.detail.CustomerDetailStatusByCustomerIdsRequest;
import com.wanmi.sbc.customer.api.response.detail.CustomerDetailStatusResponse;
import com.wanmi.sbc.customer.bean.vo.CustomerStatusBaseVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.empower.api.provider.miniprogramset.MiniProgramSetQueryProvider;
import com.wanmi.sbc.empower.api.provider.pay.weixin.WxPayProvider;
import com.wanmi.sbc.empower.api.request.miniprogramset.MiniProgramSetByTypeRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPayCompanyPaymentInfoRequest;
import com.wanmi.sbc.empower.api.request.pay.weixin.WxPaySupplierTransferQueryRequest;
import com.wanmi.sbc.empower.api.response.miniprogramset.MiniProgramSetByTypeResponse;
import com.wanmi.sbc.empower.api.response.pay.weixin.*;
import com.wanmi.sbc.empower.bean.enums.WxPayTradeType;
import com.wanmi.sbc.empower.bean.vo.MiniProgramSetVO;
import com.wanmi.sbc.marketing.bean.enums.MarketingErrorCodeEnum;
import com.wanmi.sbc.pay.weixinpaysdk.WXPayConstants;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 会员提现管理
 *
 * @author chenyufei
 */
@Slf4j
@RestController(value = "CustomerDrawCashController")
@Validated
@RequestMapping("/draw/cash")
@Tag(name = "CustomerDrawCashController", description = "S2B 平台端-会员提现API")
public class CustomerDrawCashController {

    @Autowired
    private CustomerDrawCashQueryProvider customerDrawCashQueryProvider;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired
    private CustomerDetailQueryProvider customerDetailQueryProvider;

    @Autowired
    private CustomerDrawCashProvider customerDrawCashProvider;

    @Autowired
    private CustomerDrawCashSaveProvider customerDrawCashSaveProvider;

    @Autowired
    private WxPayProvider wxPayProvider;

    @Autowired
    private CustomerFundsProvider customerFundsProvider;

    @Autowired
    private CustomerFundsDetailQueryProvider customerFundsDetailQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private MiniProgramSetQueryProvider miniProgramSetQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Resource
    private CommonUtil commonUtil;

    /**
     * S2B 平台端-获取会员提现管理列表数据统计(待审核、已完成、提现失败、审核未通过、已取消)
     *
     * @return
     */
    @Operation(summary = "S2B 平台端-获取会员提现管理列表数据统计(待审核、已完成、提现失败、审核未通过、已取消)")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/gather", method = RequestMethod.POST)
    public BaseResponse<CustomerDrawCashStatusResponse> gather(CustomerDrawCashStatusQueryRequest request) {
        return this.customerDrawCashQueryProvider.countDrawCashTabNum(request);
    }

    /**
     * S2B 平台端-获取会员提现管理分页列表
     *
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-获取会员提现管理分页列表")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_draw_cash_page_sign_word")
    public BaseResponse<MicroServicePage<CustomerDrawCashVO>> page(@RequestBody CustomerDrawCashPageRequest request) {

        //刷新转账中的数据
        this.handelSupplierTransferProcessingList();

        request.setDelFlag(DeleteFlag.NO);
        request.setSourceFromPlatForm(Boolean.TRUE);
        MicroServicePage<CustomerDrawCashVO> cashVOS = this.customerDrawCashQueryProvider.page(request).getContext()
                .getCustomerDrawCashVOPage();
        // 设置账户信息
        if (CollectionUtils.isNotEmpty(cashVOS.getContent())) {
            this.setAccountInfoByCustomers(cashVOS.getContent());

            //获取用户注销标识
            List<String> customerIds = cashVOS.getContent().stream().map(CustomerDrawCashVO::getCustomerId).collect(Collectors.toList());
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
            cashVOS.getContent().forEach(vo -> vo.setLogOutStatus(map.get(vo.getCustomerId())));
        }
        return BaseResponse.success(cashVOS);
    }

    /**
     * 刷新转账状态
     **/
    private void handelSupplierTransferProcessingList() {
        //1.查询转账中的记录
        CustomerDrawCashPageRequest request = CustomerDrawCashPageRequest.builder()
                .delFlag(DeleteFlag.NO)
                .sourceFromPlatForm(Boolean.TRUE)
                .drawCashStatus(DrawCashStatus.PROCESSING)
                .finishStatus(FinishStatus.PROCESSING)
                .build();
        List<CustomerDrawCashVO> cashVOS = this.customerDrawCashQueryProvider.page(request).getContext()
                .getCustomerDrawCashVOPage().getContent();
        if (CollectionUtils.isEmpty(cashVOS)) {
            return;
        }

        for (CustomerDrawCashVO customerDrawCashVO : cashVOS) {
            WxPaySupplierTransferQueryRequest queryRequest = WxPaySupplierTransferQueryRequest.builder()
                    .batchId(customerDrawCashVO.getBatchId())
                    .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                    .need_query_detail(Boolean.TRUE)
                    .detail_status("ALL")
                    .build();
            //2.遍历记录，查询转账状态
            WxPaySupplierTransferQueryResponse response = wxPayProvider.wxPaySupplierTransferQuery(queryRequest).getContext();
            //如果请求失败则不处理
            if (StringUtils.isNotBlank(response.getMessage())){
                continue;
            }

            List<WXPaySUpplierTransferDetailListResponse> transfer_detail_list = response.getTransfer_detail_list();
            if (CollectionUtils.isEmpty(transfer_detail_list)) {
                continue;
            }
            WXPaySUpplierTransferDetailListResponse wxPaySUpplierTransferDetailListResponse = transfer_detail_list.get(0);

            //明细状态
            String detail_status = wxPaySUpplierTransferDetailListResponse.getDetail_status();

            //储存微信明细单号
            customerDrawCashVO.setDetailId(wxPaySUpplierTransferDetailListResponse.getDetail_id());


            //3.根据转账状态更新记录
            BigDecimal accountBalance = customerDrawCashVO.getAccountBalance();
            //3.1 如果是转账中则不处理
            if (DrawCashStatus.PROCESSING.name().equalsIgnoreCase(detail_status)) {
                continue;
            }

            //3.2 已完成的更新账户余额，更新提现状态
            if (DrawCashStatus.SUCCESS.name().equalsIgnoreCase(detail_status)) {
                //处理账户余额
                accountBalance = this.increaseAlreadyDrawCashAmount(customerDrawCashVO);

                withdrawAmount(customerDrawCashVO, WithdrawAmountStatus.AGREE);
                //同时更新账户明细表
                agreeAmountPaidAndExpenditure(customerDrawCashVO);
                //更新会员账户明细表数据
                CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest =
                        new CustomerFundsDetailModifyRequest();
                customerFundsDetailModifyRequest.setBusinessId(customerDrawCashVO.getDrawCashNo());
                customerFundsDetailModifyRequest.setCustomerId(customerDrawCashVO.getCustomerId());
                customerFundsDetailModifyRequest.setDrawCashId(customerDrawCashVO.getDrawCashId());
                customerFundsDetailModifyRequest.setFundsStatus(FundsStatus.YES);
                customerFundsDetailModifyRequest.setTabType(2);
                modifyCustomerFundsDetail(customerFundsDetailModifyRequest);

                //设置提现状态
                customerDrawCashVO.setDrawCashStatus(DrawCashStatus.SUCCESS);
                customerDrawCashVO.setFinishStatus(FinishStatus.SUCCESS);
            }

            //3.3 如果失败，再查询明细，获取失败原因
            if (DrawCashStatus.FAIL.name().equalsIgnoreCase(detail_status)) {
                //设置提现状态
                customerDrawCashVO.setDrawCashStatus(DrawCashStatus.FAIL);
                customerDrawCashVO.setFinishStatus(FinishStatus.SUCCESS);
                //获取失败原因
                String detailId = customerDrawCashVO.getDetailId();
                WxPaySupplierTransferQueryRequest transferQueryRequest = WxPaySupplierTransferQueryRequest.builder()
                        .detailId(detailId)
                        .batchId(customerDrawCashVO.getBatchId())
                        .storeId(Constants.BOSS_DEFAULT_STORE_ID)
                        .build();
                WxPaySupplierTransferQueryDetailResponse queryDetailResponse = wxPayProvider.wxPaySupplierTransferDetailQuery(transferQueryRequest).getContext();
                //失败原因详情
                String failReason = queryDetailResponse.getFail_reason();
                if (StringUtils.isNotBlank(failReason)){
                    FailReasonEnum reasonEnum = FailReasonEnum.valueOf(failReason);
                    //失败原因
                    String desc = reasonEnum.getDesc();
                    customerDrawCashVO.setDrawCashFailedReason(desc);
                }

            }

            //修改 返回
            CustomerDrawCashBatchModifyAuditStatusRequest customerDrawCashBatchModifyAuditStatusRequest =
                    CustomerDrawCashBatchModifyAuditStatusRequest.builder()
                            .drawCashIdList(Collections.singletonList(customerDrawCashVO.getDrawCashId()))
                            .drawCashFailedReason(customerDrawCashVO.getDrawCashFailedReason())
                            .drawCashStatus(customerDrawCashVO.getDrawCashStatus())
                            .finishStatus(customerDrawCashVO.getFinishStatus())
                            .auditStatus(customerDrawCashVO.getAuditStatus())
                            .rejectReason(customerDrawCashVO.getRejectReason())
                            .accountBalance(accountBalance)
                            .batchId(customerDrawCashVO.getBatchId())
                            .detailId(customerDrawCashVO.getDetailId())
                            .build();
            //更新数据
            this.customerDrawCashProvider.batchModifyAuditStatus(customerDrawCashBatchModifyAuditStatusRequest);
        }


    }


    /**
     * S2B 平台端-审核会员提现请求(0 待审核  1审核不通过 2审核通过)
     *
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-审核会员提现请求(0 待审核  1审核不通过 2审核通过)")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @Transactional
    @MultiSubmit
    public BaseResponse audit(@RequestBody CustomerDrawCashModifyAuditStatusRequest request) {

        //商家转账到零钱（新版本）
        return this.newAudit(request);

        //微信付款到零钱接口 (旧版本，微信已废弃)
//        return this.oldAudit(request);
    }

    /**
     * @description 商家转账到零钱（新版本）
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:26
     **/
    private BaseResponse newAudit(CustomerDrawCashModifyAuditStatusRequest request) {

        CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest
                .builder()
                .drawCashId(request.getDrawCashId())
                .build()
        ).getContext().getCustomerDrawCashVO();

        // 提现前校验提现状态
        validCustomerDrawCash(cash);

        if (cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080195, new Object[]{cash.getDrawCashNo()});
        }

        BigDecimal accountBalance = cash.getAccountBalance();
        //如果审核通过调用微信提现接口  //这边是页面操作 点击审核成功的
        if (request.getAuditStatus() == AuditStatus.PASS) {
            CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                    this.wxPaySupplierTransfer(cash);

            request.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
            request.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
            request.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());
            request.setBatchId(customerDrawCashModifyAuditStatusRequest.getBatchId());

        } else if (request.getAuditStatus() == AuditStatus.REJECT) {
            //审核拒绝操作
            request.setFinishStatus(FinishStatus.UNSUCCESS);
            request.setDrawCashStatus(DrawCashStatus.WAIT);
            withdrawAmount(cash, WithdrawAmountStatus.REJECT);
        }
        //修改 返回
        CustomerDrawCashBatchModifyAuditStatusRequest customerDrawCashBatchModifyAuditStatusRequest =
                CustomerDrawCashBatchModifyAuditStatusRequest.builder()
                        .drawCashIdList(Collections.singletonList(request.getDrawCashId()))
                        .drawCashFailedReason(request.getDrawCashFailedReason())
                        .drawCashStatus(request.getDrawCashStatus())
                        .finishStatus(request.getFinishStatus())
                        .auditStatus(request.getAuditStatus())
                        .rejectReason(request.getRejectReason())
                        .accountBalance(accountBalance)
                        .batchId(request.getBatchId())
                        .build();

        BaseResponse response =
                this.customerDrawCashProvider.batchModifyAuditStatus(customerDrawCashBatchModifyAuditStatusRequest);

        String statusTxt = "待审核";
        if (Objects.equals(customerDrawCashBatchModifyAuditStatusRequest.getAuditStatus(), AuditStatus.PASS)) {
            statusTxt = "通过";
        } else if (Objects.equals(customerDrawCashBatchModifyAuditStatusRequest.getAuditStatus(), AuditStatus.REJECT)) {
            statusTxt = "不通过";
        }
        // 操作日志
        operateLogMQUtil.convertAndSend("财务", "审核会员提现", "提现单号: " + cash.getDrawCashNo() + " 审核状态：" + statusTxt);
        return response;
    }

    /**
     * @description 微信付款到零钱接口 (旧版本，微信已废弃)
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:27
     **/
    private BaseResponse oldAudit(CustomerDrawCashModifyAuditStatusRequest request) {
        CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest
                .builder()
                .drawCashId(request.getDrawCashId())
                .build()
        ).getContext().getCustomerDrawCashVO();

        if (cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080195, new Object[]{cash.getDrawCashNo()});
        }

        BigDecimal accountBalance = cash.getAccountBalance();
        //如果审核通过调用微信提现接口  //这边是页面操作 点击审核成功的
        if (request.getAuditStatus() == AuditStatus.PASS) {
            CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                    this.wxPayCompanyPayment(cash);
            request.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
            request.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
            request.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());
            //这边如果审核通过  同时微信提现成功的要入已提现字段
            if (request.getDrawCashStatus() == DrawCashStatus.SUCCESS) {
                accountBalance = this.increaseAlreadyDrawCashAmount(cash);
                //this.descendAccountBalance(cash);
                //this.descendBlockedBalance(cash);
                withdrawAmount(cash, WithdrawAmountStatus.AGREE);
                //同时更新账户明细表
                agreeAmountPaidAndExpenditure(cash);
                //更新会员账户明细表数据
                CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest =
                        new CustomerFundsDetailModifyRequest();
                customerFundsDetailModifyRequest.setBusinessId(customerDrawCashModifyAuditStatusRequest.getBusinessId());
                customerFundsDetailModifyRequest.setCustomerId(cash.getCustomerId());
                customerFundsDetailModifyRequest.setDrawCashId(cash.getDrawCashId());
                customerFundsDetailModifyRequest.setFundsStatus(FundsStatus.YES);
                customerFundsDetailModifyRequest.setTabType(2);
                modifyCustomerFundsDetail(customerFundsDetailModifyRequest);
            }
        } else if (request.getAuditStatus() == AuditStatus.REJECT) {
            //审核拒绝操作
            request.setFinishStatus(FinishStatus.UNSUCCESS);
            request.setDrawCashStatus(DrawCashStatus.WAIT);
            withdrawAmount(cash, WithdrawAmountStatus.REJECT);
        }
        //修改 返回
        CustomerDrawCashBatchModifyAuditStatusRequest customerDrawCashBatchModifyAuditStatusRequest =
                CustomerDrawCashBatchModifyAuditStatusRequest.builder()
                        .drawCashIdList(Collections.singletonList(request.getDrawCashId()))
                        .drawCashFailedReason(request.getDrawCashFailedReason())
                        .drawCashStatus(request.getDrawCashStatus())
                        .finishStatus(request.getFinishStatus())
                        .auditStatus(request.getAuditStatus())
                        .rejectReason(request.getRejectReason())
                        .accountBalance(accountBalance)
                        .build();

        BaseResponse response =
                this.customerDrawCashProvider.batchModifyAuditStatus(customerDrawCashBatchModifyAuditStatusRequest);

        String statusTxt = "待审核";
        if (Objects.equals(customerDrawCashBatchModifyAuditStatusRequest.getAuditStatus(), AuditStatus.PASS)) {
            statusTxt = "通过";
        } else if (Objects.equals(customerDrawCashBatchModifyAuditStatusRequest.getAuditStatus(), AuditStatus.REJECT)) {
            statusTxt = "不通过";
        }
        // 操作日志
        operateLogMQUtil.convertAndSend("财务", "审核会员提现", "提现单号: " + cash.getDrawCashNo() + " 审核状态：" + statusTxt);
        return response;
    }

    /**
     * S2B 平台端-审核会员提现请求重新请求支付接口
     *
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-审核会员提现请求重新请求支付接口")
    @RequestMapping(value = "/try/again", method = RequestMethod.POST)
    @Transactional
    @MultiSubmit
    public BaseResponse<CustomerDrawCashModifyResponse> tryAgain(@RequestBody CustomerDrawCashPageRequest request) {

        return this.newTryAgain(request);

//        return this.oldTryAgain(request);
    }

    /**
     * @description 商家转账到零钱重试（新版本）
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:32
     **/
    private BaseResponse<CustomerDrawCashModifyResponse> newTryAgain(CustomerDrawCashPageRequest request) {

        CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest.builder()
                        .drawCashId(request.getDrawCashId())
                        .build())
                .getContext().getCustomerDrawCashVO();

        // 提现前校验提现状态
        validCustomerDrawCash(cash);

        //判断提现申请是否已经取消
        if (cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080195, new Object[]{cash.getDrawCashNo()});
        }

        BigDecimal accountBalance = cash.getAccountBalance();

        //如果审核通过调用微信提现接口  //这边是取数据库中查出的数据  审核成功的
        if (cash.getAuditStatus() == AuditStatus.PASS) {
            CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                    this.wxPaySupplierTransfer(cash);

            request.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
            request.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
            request.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());
            request.setBatchId(customerDrawCashModifyAuditStatusRequest.getBatchId());
            request.setAuditStatus(cash.getAuditStatus());

        } else if (request.getAuditStatus() == AuditStatus.REJECT) {
            //审核拒绝操作
            request.setFinishStatus(FinishStatus.UNSUCCESS);
            request.setDrawCashStatus(DrawCashStatus.WAIT);
            withdrawAmount(cash, WithdrawAmountStatus.REJECT);
        }

        //处理 返回
        return this.customerDrawCashSaveProvider.modify(
                CustomerDrawCashModifyRequest.builder()
                        .drawCashId(request.getDrawCashId())
                        .drawCashNo(cash.getDrawCashNo())
                        .applyTime(cash.getApplyTime())
                        .customerId(cash.getCustomerId())
                        .customerName(cash.getCustomerName())
                        .customerAccount(cash.getCustomerAccount())
                        .drawCashChannel(cash.getDrawCashChannel())
                        .drawCashAccountName(cash.getDrawCashAccountName())
                        .drawCashAccount(cash.getDrawCashAccount())
                        .drawCashSum(cash.getDrawCashSum())
                        .drawCashRemark(cash.getDrawCashRemark())
                        .auditStatus(request.getAuditStatus())
                        .rejectReason(cash.getRejectReason())
                        .drawCashStatus(request.getDrawCashStatus())
                        .drawCashFailedReason(request.getDrawCashFailedReason())
                        .customerOperateStatus(cash.getCustomerOperateStatus())
                        .finishStatus(request.getFinishStatus())
                        .finishTime(LocalDateTime.now())
                        .supplierOperateId(cash.getSupplierOperateId())
                        .updateTime(cash.getUpdateTime())
                        .delFlag(cash.getDelFlag())
                        .drawCashSource(cash.getDrawCashSource())
                        .openId(cash.getOpenId())
                        .accountBalance(accountBalance)
                        .batchId(request.getBatchId())
                        .build()
        );
    }

    /**
     * @description 商家转账到零钱重试（旧版本）
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:32
     **/
    private BaseResponse<CustomerDrawCashModifyResponse> oldTryAgain(CustomerDrawCashPageRequest request) {

        CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest.builder()
                        .drawCashId(request.getDrawCashId())
                        .build())
                .getContext().getCustomerDrawCashVO();

        //判断提现申请是否已经取消
        if (cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080195, new Object[]{cash.getDrawCashNo()});
        }

        BigDecimal accountBalance = cash.getAccountBalance();

        //如果审核通过调用微信提现接口  //这边是取数据库中查出的数据  审核成功的
        if (cash.getAuditStatus() == AuditStatus.PASS) {
            CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                    this.wxPayCompanyPayment(cash);
            request.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
            request.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
            request.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());
            request.setAuditStatus(cash.getAuditStatus());

            //这边如果审核通过  同时微信提现成功的要入已提现字段
            if (request.getDrawCashStatus() == DrawCashStatus.SUCCESS) {
                accountBalance = this.increaseAlreadyDrawCashAmount(cash);
//                this.descendAccountBalance(cash);
//                this.descendBlockedBalance(cash);
                withdrawAmount(cash, WithdrawAmountStatus.AGREE);
                //同时更新账户明细表
                agreeAmountPaidAndExpenditure(cash);
                //更新会员账户明细表数据
                CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest =
                        new CustomerFundsDetailModifyRequest();
                customerFundsDetailModifyRequest.setBusinessId(customerDrawCashModifyAuditStatusRequest.getBusinessId());
                customerFundsDetailModifyRequest.setCustomerId(cash.getCustomerId());
                customerFundsDetailModifyRequest.setDrawCashId(cash.getDrawCashId());
                customerFundsDetailModifyRequest.setFundsStatus(FundsStatus.YES);
                customerFundsDetailModifyRequest.setTabType(2);
                modifyCustomerFundsDetail(customerFundsDetailModifyRequest);
            }
        } else if (request.getAuditStatus() == AuditStatus.REJECT) {
            //审核拒绝操作
            request.setFinishStatus(FinishStatus.UNSUCCESS);
            request.setDrawCashStatus(DrawCashStatus.WAIT);
            withdrawAmount(cash, WithdrawAmountStatus.REJECT);
        }

        //处理 返回
        return this.customerDrawCashSaveProvider.modify(
                CustomerDrawCashModifyRequest.builder()
                        .drawCashId(request.getDrawCashId())
                        .drawCashNo(cash.getDrawCashNo())
                        .applyTime(cash.getApplyTime())
                        .customerId(cash.getCustomerId())
                        .customerName(cash.getCustomerName())
                        .customerAccount(cash.getCustomerAccount())
                        .drawCashChannel(cash.getDrawCashChannel())
                        .drawCashAccountName(cash.getDrawCashAccountName())
                        .drawCashAccount(cash.getDrawCashAccount())
                        .drawCashSum(cash.getDrawCashSum())
                        .drawCashRemark(cash.getDrawCashRemark())
                        .auditStatus(request.getAuditStatus())
                        .rejectReason(cash.getRejectReason())
                        .drawCashStatus(request.getDrawCashStatus())
                        .drawCashFailedReason(request.getDrawCashFailedReason())
                        .customerOperateStatus(cash.getCustomerOperateStatus())
                        .finishStatus(request.getFinishStatus())
                        .finishTime(LocalDateTime.now())
                        .supplierOperateId(cash.getSupplierOperateId())
                        .updateTime(cash.getUpdateTime())
                        .delFlag(cash.getDelFlag())
                        .drawCashSource(cash.getDrawCashSource())
                        .openId(cash.getOpenId())
                        .accountBalance(accountBalance)
                        .build()
        );
    }

    /**
     * S2B 平台端-批量审核会员提现单
     *
     * @param request
     * @return
     */
    @Operation(summary = "S2B 平台端-批量审核会员提现单")
    @RequestMapping(value = "/batch/audit", method = RequestMethod.POST)
    @Transactional
    @MultiSubmit
    public BaseResponse batchAudit(@RequestBody CustomerDrawCashBatchModifyAuditStatusRequest request) {

        return this.newBatchAudit(request);

//        return this.oldBatchAudit(request);

    }


    /**
     * @description 测试商家转账到零钱重试
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:34
     **/
    @Operation(summary = "测试转账到零钱")
    @RequestMapping(value = "/test/wxPayBossTransfer", method = RequestMethod.POST)
    @Transactional
    @MultiSubmit
    public BaseResponse testWxPayBossTransfer() {

        // 封装微信企业付款到零钱请求参数
        WxPayCompanyPaymentInfoRequest wxPayCompanyPaymentInfoRequest = new WxPayCompanyPaymentInfoRequest();

//        String partner_trade_no = "176090101 572587520";
        String partner_trade_no = "176176176123123123";
        String openid = "o7MEC7rArxw7kDVHVIRb-8yTberw";
        BigDecimal drawCashSum = new BigDecimal(1);

        wxPayCompanyPaymentInfoRequest.setPartner_trade_no(partner_trade_no);
        wxPayCompanyPaymentInfoRequest.setOpenid(openid);
        wxPayCompanyPaymentInfoRequest.setAmount(drawCashSum.toString());

        // 标准版余额提现
        wxPayCompanyPaymentInfoRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        wxPayCompanyPaymentInfoRequest.setCheck_name("NO_CHECK");
        wxPayCompanyPaymentInfoRequest.setDesc("余额提现");
        // 小程序
        BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                .type(Constants.ZERO)
                .build());
        if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(), miniProgramSetByTypeResponseBaseResponse.getCode())) {
            MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
            if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
            }
            String appId = miniProgramSetVO.getAppId();
            wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.JSAPI);
            wxPayCompanyPaymentInfoRequest.setMiniAppId(appId);
        }

        wxPayCompanyPaymentInfoRequest.setSpbill_create_ip(HttpUtil.getIpAddr());

        // 微信支付--微信企业付款到零钱
        WxPayBossTransferRsponse response =
                wxPayProvider.wxPayBossTransfer(wxPayCompanyPaymentInfoRequest).getContext();

        return BaseResponse.success(response);
    }


    /**
     * @description 商家转账到零钱重试（新版本）
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:34
     **/
    private BaseResponse newBatchAudit(CustomerDrawCashBatchModifyAuditStatusRequest request) {

        List<String> drawCashNos = new ArrayList<>();

        request.getDrawCashIdList().forEach(drawCashId -> {

            CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest
                    .builder()
                    .drawCashId(drawCashId)
                    .build()
            ).getContext().getCustomerDrawCashVO();

            // 提现前校验提现状态
            validCustomerDrawCash(cash);

            if (cash.getCustomerOperateStatus() == CustomerOperateStatus.CANCEL) {
                throw new SbcRuntimeException(MarketingErrorCodeEnum.K080195, new Object[]{cash.getDrawCashNo()});
            }

            BigDecimal accountBalance = cash.getAccountBalance();
            //如果审核通过调用微信提现接口  //这边是页面操作 点击审核成功的
            if (request.getAuditStatus() == AuditStatus.PASS) {
                CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                        this.wxPaySupplierTransfer(cash);

                request.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
                request.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
                request.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());
                request.setBatchId(customerDrawCashModifyAuditStatusRequest.getBatchId());

            } else if (request.getAuditStatus() == AuditStatus.REJECT) {
                //审核拒绝操作
                request.setFinishStatus(FinishStatus.UNSUCCESS);
                request.setDrawCashStatus(DrawCashStatus.WAIT);
                withdrawAmount(cash, WithdrawAmountStatus.REJECT);
            }
            //修改 返回
            CustomerDrawCashBatchModifyAuditStatusRequest customerDrawCashBatchModifyAuditStatusRequest =
                    CustomerDrawCashBatchModifyAuditStatusRequest.builder()
                            .drawCashIdList(Collections.singletonList(drawCashId))
                            .drawCashFailedReason(request.getDrawCashFailedReason())
                            .drawCashStatus(request.getDrawCashStatus())
                            .finishStatus(request.getFinishStatus())
                            .auditStatus(request.getAuditStatus())
                            .rejectReason(request.getRejectReason())
                            .accountBalance(accountBalance)
                            .batchId(request.getBatchId())
                            .build();

            this.customerDrawCashProvider.batchModifyAuditStatus(customerDrawCashBatchModifyAuditStatusRequest);

        });
        if (CollectionUtils.isNotEmpty(drawCashNos)) {
            // 操作日志
            operateLogMQUtil.convertAndSend("财务", "批量审核会员提现", "提现单号: " + drawCashNos);
        }
        return BaseResponse.success("批量审核成功");
    }

    /**
     * @description 商家转账到零钱重试（旧版本）
     * @Author qiyuanzhao
     * @Date 2022/8/19 14:34
     **/
    private BaseResponse oldBatchAudit(CustomerDrawCashBatchModifyAuditStatusRequest request) {
        List<String> drawCashNos = new ArrayList<>();

        request.getDrawCashIdList().forEach(drawCashId -> {

            //单个对象
            CustomerDrawCashVO cash = this.customerDrawCashQueryProvider.getById(CustomerDrawCashByIdRequest
                    .builder()
                    .drawCashId(drawCashId)
                    .build()
            ).getContext().getCustomerDrawCashVO();
            drawCashNos.add(cash.getDrawCashNo());
            BigDecimal accountBalance = cash.getAccountBalance();

            //批量处理时，判断提现申请是否已经取消，未取消才进行审核操作
            if (cash.getCustomerOperateStatus() != CustomerOperateStatus.CANCEL) {
                //调用微信
                CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                        this.wxPayCompanyPayment(cash);
                cash.setDrawCashFailedReason(customerDrawCashModifyAuditStatusRequest.getDrawCashFailedReason());
                cash.setFinishStatus(customerDrawCashModifyAuditStatusRequest.getFinishStatus());
                cash.setDrawCashStatus(customerDrawCashModifyAuditStatusRequest.getDrawCashStatus());

                //这边如果审核通过  同时微信提现成功的要入已提现字段  账户余额   冻结资金金额
                if (cash.getDrawCashStatus() == DrawCashStatus.SUCCESS) {
                    accountBalance = this.increaseAlreadyDrawCashAmount(cash);
                    //this.descendAccountBalance(cash);
                    // this.descendBlockedBalance(cash);
                    withdrawAmount(cash, WithdrawAmountStatus.AGREE);
                    //同时更新账户明细表
                    agreeAmountPaidAndExpenditure(cash);
                    //更新会员账户明细表数据
                    CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest =
                            new CustomerFundsDetailModifyRequest();
                    customerFundsDetailModifyRequest.setBusinessId(customerDrawCashModifyAuditStatusRequest.getBusinessId());
                    customerFundsDetailModifyRequest.setCustomerId(cash.getCustomerId());
                    customerFundsDetailModifyRequest.setDrawCashId(cash.getDrawCashId());
                    customerFundsDetailModifyRequest.setFundsStatus(FundsStatus.YES);
                    customerFundsDetailModifyRequest.setTabType(2);
                    modifyCustomerFundsDetail(customerFundsDetailModifyRequest);
                }

                //循环塞值
                this.customerDrawCashProvider.batchModifyAuditStatus(CustomerDrawCashBatchModifyAuditStatusRequest.builder()
                        .drawCashIdList(Collections.singletonList(drawCashId))
                        .drawCashFailedReason(cash.getDrawCashFailedReason())
                        .drawCashStatus(cash.getDrawCashStatus())
                        .finishStatus(cash.getFinishStatus())
                        .auditStatus(request.getAuditStatus())
                        .rejectReason(cash.getRejectReason())
                        .accountBalance(accountBalance)
                        .build()
                );
            }
        });
        if (CollectionUtils.isNotEmpty(drawCashNos)) {
            // 操作日志
            operateLogMQUtil.convertAndSend("财务", "批量审核会员提现", "提现单号: " + drawCashNos);
        }
        return BaseResponse.success("批量审核成功");
    }


    /**
     * 更新提现字段
     *
     * @param customerDrawCashVO
     */
    private BigDecimal increaseAlreadyDrawCashAmount(CustomerDrawCashVO customerDrawCashVO) {

        CustomerFundsByCustomerIdRequest customerIdRequest = new CustomerFundsByCustomerIdRequest();
        customerIdRequest.setCustomerId(customerDrawCashVO.getCustomerId());

        BaseResponse<CustomerFundsByCustomerIdResponse> customerIdResponseBaseResponse =
                this.customerFundsQueryProvider.getByCustomerId(customerIdRequest);

        if (Objects.isNull(customerIdResponseBaseResponse.getContext())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"当前会员没有会员资金账户,已提现入账失败");
        }

        BigDecimal nextAlreadyDrawCashAmount =
                customerIdResponseBaseResponse.getContext().getAlreadyDrawAmount().add(customerDrawCashVO.getDrawCashSum());

        this.customerFundsProvider.modifyAlreadyDrawCashAmountByCustomerId(
                CustomerFundsModifyAlreadyDrawCashAmountByCustomerIdRequest
                        .builder()
                        .customerId(customerDrawCashVO.getCustomerId())
                        .alreadyDrawCashAmount(nextAlreadyDrawCashAmount)
                        .build());

        return customerIdResponseBaseResponse.getContext().getAccountBalance().subtract(customerDrawCashVO.getDrawCashSum());
    }

    /**
     * 提现成功更新
     *
     * @param customerDrawCashVO
     */
    private void agreeAmountPaidAndExpenditure(CustomerDrawCashVO customerDrawCashVO) {
        CustomerFundsByCustomerIdRequest customerIdRequest = new CustomerFundsByCustomerIdRequest();
        customerIdRequest.setCustomerId(customerDrawCashVO.getCustomerId());
        BaseResponse<CustomerFundsByCustomerIdResponse> customerIdResponseBaseResponse =
                this.customerFundsQueryProvider.getByCustomerId(customerIdRequest);
        if (Objects.isNull(customerIdResponseBaseResponse.getContext())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"当前会员没有会员资金账户,已提现入账失败");
        }
        CustomerFundsModifyRequest customerFundsModifyRequest = new CustomerFundsModifyRequest();
        customerFundsModifyRequest.setCustomerFundsId(customerIdResponseBaseResponse.getContext().getCustomerFundsId());
        customerFundsModifyRequest.setWithdrawAmount(customerDrawCashVO.getDrawCashSum());
        customerFundsProvider.agreeAmountPaidAndExpenditure(customerFundsModifyRequest);
    }

    /**
     * 更新账户明细表对应字段
     *
     * @param customerFundsDetailModifyRequest
     */
    private void modifyCustomerFundsDetail(CustomerFundsDetailModifyRequest customerFundsDetailModifyRequest) {
        customerFundsDetailQueryProvider.modifyCustomerFundsDetail(customerFundsDetailModifyRequest);
    }

    /**
     * 更新可提现字段
     *
     * @param customerDrawCashVO
     */
    private void withdrawAmount(CustomerDrawCashVO customerDrawCashVO, WithdrawAmountStatus withdrawAmountStatus) {
        CustomerFundsByCustomerIdRequest customerIdRequest = new CustomerFundsByCustomerIdRequest();
        customerIdRequest.setCustomerId(customerDrawCashVO.getCustomerId());
        BaseResponse<CustomerFundsByCustomerIdResponse> customerIdResponseBaseResponse =
                this.customerFundsQueryProvider.getByCustomerId(customerIdRequest);
        if (Objects.isNull(customerIdResponseBaseResponse.getContext())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999,"当前会员没有会员资金账户,已提现入账失败");
        }
        CustomerFundsModifyRequest customerFundsModifyRequest = new CustomerFundsModifyRequest();
        customerFundsModifyRequest.setCustomerFundsId(customerIdResponseBaseResponse.getContext().getCustomerFundsId());
        customerFundsModifyRequest.setWithdrawAmount(customerDrawCashVO.getDrawCashSum());
        customerFundsModifyRequest.setWithdrawAmountStatus(withdrawAmountStatus);
        customerFundsProvider.modifyCustomerFundsByIdAndWithdrawAmountStatus(customerFundsModifyRequest);
    }


    /**
     * 余额提现--商家转账到零钱接口
     *
     * @Author qiyuanzhao
     **/
    private CustomerDrawCashModifyAuditStatusRequest wxPaySupplierTransfer(CustomerDrawCashVO cash) {
        log.info("go into wxPaySupplierTransfer method......");
        log.info("charge withdraw start......");

        CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                new CustomerDrawCashModifyAuditStatusRequest();

        // 封装微信企业付款到零钱请求参数
        WxPayCompanyPaymentInfoRequest wxPayCompanyPaymentInfoRequest = new WxPayCompanyPaymentInfoRequest();
        // 标准版余额提现
        wxPayCompanyPaymentInfoRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        wxPayCompanyPaymentInfoRequest.setPartner_trade_no(cash.getDrawCashNo());
        wxPayCompanyPaymentInfoRequest.setOpenid(cash.getOpenId());
        wxPayCompanyPaymentInfoRequest.setCheck_name("NO_CHECK");
        wxPayCompanyPaymentInfoRequest.setAmount(cash.getDrawCashSum().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        wxPayCompanyPaymentInfoRequest.setDesc("余额提现");
        // 微信支付交易类型
        if (Objects.equals(DrawCashSource.APP, cash.getDrawCashSource())) {
            // app支付
            wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.APP);
        } else if (Objects.equals(DrawCashSource.MOBILE, cash.getDrawCashSource())) {
            // H5支付
            wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.MWEB);
        } else if (Objects.equals(DrawCashSource.MINI, cash.getDrawCashSource())) {
            // 小程序
            BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                    .type(Constants.ZERO)
                    .build());
            if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(), miniProgramSetByTypeResponseBaseResponse.getCode())) {
                MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
                if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                String appId = miniProgramSetVO.getAppId();
                wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.JSAPI);
                wxPayCompanyPaymentInfoRequest.setMiniAppId(appId);
            }
        }
        wxPayCompanyPaymentInfoRequest.setSpbill_create_ip(HttpUtil.getIpAddr());

        // 微信支付--微信企业付款到零钱
        WxPaySupplierTransferRsponse response =
                wxPayProvider.wxPaySupplierTransfer(wxPayCompanyPaymentInfoRequest).getContext();

        if (response.getSuccess()){
            customerDrawCashModifyAuditStatusRequest.setBatchId(response.getBatch_id());
            customerDrawCashModifyAuditStatusRequest.setDrawCashStatus(DrawCashStatus.PROCESSING);
            customerDrawCashModifyAuditStatusRequest.setFinishStatus(FinishStatus.PROCESSING);
        }else {
            customerDrawCashModifyAuditStatusRequest.setDrawCashStatus(DrawCashStatus.FAIL);
            customerDrawCashModifyAuditStatusRequest.setFinishStatus(FinishStatus.UNSUCCESS);
            customerDrawCashModifyAuditStatusRequest.setDrawCashFailedReason(response.getMessage());
        }


        log.info("charge withdraw end......");
        return customerDrawCashModifyAuditStatusRequest;
    }


    /**
     * 余额提现--微信付款到零钱接口
     *
     * @author lvzhengwei
     */
    private CustomerDrawCashModifyAuditStatusRequest wxPayCompanyPayment(CustomerDrawCashVO cash) {
        log.info("go into wxPayCompanyPayment method......");
        log.info("charge withdraw start......");
        CustomerDrawCashModifyAuditStatusRequest customerDrawCashModifyAuditStatusRequest =
                new CustomerDrawCashModifyAuditStatusRequest();

//        ThirdLoginRelationByCustomerRequest thirdLoginRelationByCustomerRequest =
//                new ThirdLoginRelationByCustomerRequest();
//        thirdLoginRelationByCustomerRequest.setCustomerId(cash.getCustomerId());
//        thirdLoginRelationByCustomerRequest.setThirdLoginType(ThirdLoginType.WECHAT);
//        ThirdLoginRelationResponse thirdLoginRelationResponse = thirdLoginRelationQueryProvider.
//                listThirdLoginRelationByCustomer(thirdLoginRelationByCustomerRequest).getContext();
        // 封装微信企业付款到零钱请求参数
        WxPayCompanyPaymentInfoRequest wxPayCompanyPaymentInfoRequest = new WxPayCompanyPaymentInfoRequest();
        // 标准版余额提现
        wxPayCompanyPaymentInfoRequest.setStoreId(Constants.BOSS_DEFAULT_STORE_ID);
        wxPayCompanyPaymentInfoRequest.setPartner_trade_no(cash.getDrawCashNo());
        wxPayCompanyPaymentInfoRequest.setOpenid(cash.getOpenId());
        wxPayCompanyPaymentInfoRequest.setCheck_name("NO_CHECK");
        wxPayCompanyPaymentInfoRequest.setAmount(cash.getDrawCashSum().multiply(new BigDecimal(100)).
                setScale(0, RoundingMode.DOWN).toString());
        wxPayCompanyPaymentInfoRequest.setDesc("余额提现");
        // 微信支付交易类型
        if (Objects.equals(DrawCashSource.APP, cash.getDrawCashSource())) {
            // app支付
            wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.APP);
        } else if (Objects.equals(DrawCashSource.MOBILE, cash.getDrawCashSource())) {
            // H5支付
            wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.MWEB);
        } else if (Objects.equals(DrawCashSource.MINI, cash.getDrawCashSource())) {
            // 小程序
            BaseResponse<MiniProgramSetByTypeResponse> miniProgramSetByTypeResponseBaseResponse = miniProgramSetQueryProvider.getByType(MiniProgramSetByTypeRequest.builder()
                    .type(Constants.ZERO)
                    .build());
            if (StringUtils.equals(CommonErrorCodeEnum.K000000.getCode(), miniProgramSetByTypeResponseBaseResponse.getCode())) {
                MiniProgramSetVO miniProgramSetVO = miniProgramSetByTypeResponseBaseResponse.getContext().getMiniProgramSetVO();
                if (Constants.no.equals(miniProgramSetVO.getStatus())) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K000019);
                }
                String appId = miniProgramSetVO.getAppId();
                wxPayCompanyPaymentInfoRequest.setPayType(WxPayTradeType.JSAPI);
                wxPayCompanyPaymentInfoRequest.setMiniAppId(appId);
            }
        }
        wxPayCompanyPaymentInfoRequest.setSpbill_create_ip(HttpUtil.getIpAddr());

        // 微信支付--微信企业付款到零钱
        BaseResponse<WxPayCompanyPaymentRsponse> response =
                wxPayProvider.wxPayCompanyPayment(wxPayCompanyPaymentInfoRequest);
        // 付款请求成功
        if (response.getCode().equals(BaseResponse.SUCCESSFUL().getCode())) {
            WxPayCompanyPaymentRsponse wxPayCompanyPaymentRsponse = response.getContext();
            DrawCashStatus drawCashStatus = cash.getDrawCashStatus();
            FinishStatus finishStatus = cash.getFinishStatus();
            String drawCashFailedReason = "";
            //返回状态码：SUCCESS/FAIL；此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
            if (wxPayCompanyPaymentRsponse.getReturn_code().equals(WXPayConstants.SUCCESS)) {
                // 业务结果
                if (wxPayCompanyPaymentRsponse.getResult_code().equals(WXPayConstants.SUCCESS)) {
                    // 提现成功
                    drawCashStatus = DrawCashStatus.SUCCESS;
                    // 提现已完成
                    finishStatus = FinishStatus.SUCCESS;
                    customerDrawCashModifyAuditStatusRequest.setBusinessId(cash.getDrawCashNo());
                } else {
                    // 错误代码描述
                    drawCashFailedReason = wxPayCompanyPaymentRsponse.getErr_code_des();
                    // 提现失败
                    drawCashStatus = DrawCashStatus.FAIL;
                }
            } else {
                // 为错误原因
                drawCashFailedReason = wxPayCompanyPaymentRsponse.getReturn_msg();
                // 提现失败
                drawCashStatus = DrawCashStatus.FAIL;
            }
            customerDrawCashModifyAuditStatusRequest.setDrawCashFailedReason(drawCashFailedReason);
            customerDrawCashModifyAuditStatusRequest.setDrawCashStatus(drawCashStatus);
            customerDrawCashModifyAuditStatusRequest.setFinishStatus(finishStatus);
            log.info("drawCashFailedReason============={}", drawCashFailedReason);
        }
        log.info("charge withdraw end......");
        return customerDrawCashModifyAuditStatusRequest;
    }

    /**
     * 导出会员提现记录
     *
     * @param encrypted
     */
    @Operation(summary = "导出会员提现记录")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted, CustomerDrawCashExportRequest request) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        CustomerDrawCashExportRequest queryReq = JSON.parseObject(decrypted, CustomerDrawCashExportRequest.class);
        queryReq.setDelFlag(DeleteFlag.NO);
        queryReq.setEmployeeCustomerIds(request.getEmployeeCustomerIds());

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(queryReq));
        exportDataRequest.setTypeCd(ReportType.BUSINESS_DRAW_CASH);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("财务", "批量导出会员提现记录", "批量导出会员提现记录");
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 设置账户信息 优化，合并请求
     *
     * @param customerDrawCashVOs
     */
    private void setAccountInfoByCustomers(List<CustomerDrawCashVO> customerDrawCashVOs) {
        List<String> customerIds =
                customerDrawCashVOs.stream().map(CustomerDrawCashVO::getCustomerId).collect(Collectors.toList());
        CustomerDetailStatusByCustomerIdsRequest request = new CustomerDetailStatusByCustomerIdsRequest();
        request.setCustomerIds(customerIds);
        CustomerDetailStatusResponse response = customerDetailQueryProvider.getCustomerStatus(request).getContext();
        Map<String, CustomerStatusBaseVO> customerStatusMap
                = response.getList().stream().collect(Collectors.toMap(CustomerStatusBaseVO::getCustomerId,
                Function.identity(), (k1, k2) -> k1));


        CustomerFundsByCustomerIdListRequest fundsReq = new CustomerFundsByCustomerIdListRequest();
        fundsReq.setCustomerIds(customerIds);
        List<CustomerFundsForEsVO> fundsList =
                customerFundsQueryProvider.getByCustomerIdListForEs(fundsReq).getContext().getLists();
        Map<String, String> fundsMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fundsList)) {
            fundsMap.putAll(fundsList.stream().filter(s -> StringUtils.isNotBlank(s.getCustomerFundsId()))
                    .collect(Collectors.toMap(CustomerFundsForEsVO::getCustomerId,
                            CustomerFundsForEsVO::getCustomerFundsId)));
        }

        //填充账号状态以及禁用理由
        customerDrawCashVOs.forEach(v -> {
            AccountStatus status = AccountStatus.DISABLE;
            CustomerStatusBaseVO customerStatusBaseVO = customerStatusMap.get(v.getCustomerId());
            if (Objects.nonNull(customerStatusBaseVO) && Objects.nonNull(customerStatusBaseVO.getCustomerStatus())) {
                status = AccountStatus.valueOf(customerStatusBaseVO.getCustomerStatus().name());
                v.setForbidReason(customerStatusBaseVO.getForbidReason());
            }
            v.setAccountStatus(status);
            // 会员账号中间4位*号
            v.setCustomerAccount(v.getCustomerAccount()
                    .replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            v.setCustomerFundsId(fundsMap.get(v.getCustomerId()));
        });
    }

    /**
     * 提现记录校验
     * @param cash
     */
    private void validCustomerDrawCash(CustomerDrawCashVO cash) {
        if (FinishStatus.SUCCESS.equals(cash.getFinishStatus()) || DrawCashStatus.SUCCESS.equals(cash.getDrawCashStatus())) {
            throw new SbcRuntimeException(MarketingErrorCodeEnum.K080201);
        }
    }
}
