package com.wanmi.sbc.finance;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.account.api.provider.finance.record.AccountRecordQueryProvider;
import com.wanmi.sbc.account.api.request.finance.record.*;
import com.wanmi.sbc.account.bean.enums.AccountRecordType;
import com.wanmi.sbc.account.bean.enums.PayWay;
import com.wanmi.sbc.account.bean.vo.AccountDetailsVO;
import com.wanmi.sbc.account.bean.vo.AccountGatherVO;
import com.wanmi.sbc.account.bean.vo.AccountRecordVO;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.enums.QueryPayType;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>财务对账记录Rest</p>
 * Created by of628-wenzhi on 2017-12-12-上午11:13.
 */
@Tag(name = "AccountRecordController", description = "财务对账记录 Api")
@RestController
@Validated
@RequestMapping("/finance/bill")
@Slf4j
public class AccountRecordController {

    @Autowired
    private AccountRecordQueryProvider accountRecordQueryProvider;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 收入列表
     *
     * @param request 请求参数结构
     * @return 分页后的列表
     */
    @Operation(summary = "收入列表")
    @RequestMapping(value = "/income", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<AccountRecordVO>> pageIncome(@RequestBody @Valid
                                                                              AccountRecordPageRequest
                                                                              request) {
        request.setSupplierId(commonUtil.getCompanyInfoId());
        request.setAccountRecordType(AccountRecordType.INCOME);
        return BaseResponse.success(accountRecordQueryProvider.pageAccountRecord(request).getContext()
                .getAccountRecordVOPage());
    }

    /**
     * 收入支付方式汇总
     *
     * @param request 请求参数结构
     * @return 各支付方式金额汇总记录
     */
    @Operation(summary = "收入支付方式汇总")
    @RequestMapping(value = "/income/gross", method = RequestMethod.POST)
    public BaseResponse<List<AccountGatherVO>> incomeSummarizing(@RequestBody @Valid AccountGatherListRequest request) {
        request.setSupplierId(commonUtil.getCompanyInfoId());
        request.setAccountRecordType(AccountRecordType.INCOME);
        return BaseResponse.success(accountRecordQueryProvider.listAccountGather(request).getContext()
                .getAccountGatherVOList());
    }

    /**
     * 收入明细
     *
     * @param request 请求参数结构
     * @return 分页后的收入明细记录
     */
    @Operation(summary = "收入明细")
    @RequestMapping(value = "/income/details", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_income_details_sign_word")
    public BaseResponse<MicroServicePage<AccountDetailsVO>> incomeDetails(@RequestBody @Valid
                                                                                  AccountDetailsPageRequest request) {
        request.setAccountRecordType(AccountRecordType.INCOME);
        MicroServicePage<AccountDetailsVO> accountDetailsVOPage = accountRecordQueryProvider
                .pageAccountDetails(request).getContext()
                .getAccountDetailsVOPage();

        List<String> customerIds = accountDetailsVOPage.getContent()
                .stream()
                .map(AccountDetailsVO::getCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        accountDetailsVOPage.getContent().forEach(page -> {
            //数据越权校验
            commonUtil.checkStoreId(page.getStoreId());
            page.setLogOutStatus(map.get(page.getCustomerId()));
        });
        return BaseResponse.success(accountDetailsVOPage);
    }

    /**
     * 导出收入明细
     *
     * @return
     */
    @Operation(summary = "导出收入明细")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/income/details/export/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportIncomeDetails(@PathVariable String encrypted) {
        exportDetails(encrypted, AccountRecordType.INCOME);
        return BaseResponse.SUCCESSFUL();
    }


    /**
     * 退款列表
     *
     * @param request 请求参数结构
     * @return 分页后的列表
     */
    @Operation(summary = "退款列表")
    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    public BaseResponse<MicroServicePage<AccountRecordVO>> pageRefund(@RequestBody @Valid AccountRecordPageRequest
                                                                              request) {
        request.setSupplierId(commonUtil.getCompanyInfoId());
        request.setAccountRecordType(AccountRecordType.REFUND);
        return BaseResponse.success(accountRecordQueryProvider.pageAccountRecord(request).getContext()
                .getAccountRecordVOPage());
    }

    /**
     * 退款支付方式汇总
     *
     * @param request 请求参数结构
     * @return 各退款方式金额汇总记录
     */
    @Operation(summary = "退款支付方式汇总")
    @RequestMapping(value = "/refund/gross", method = RequestMethod.POST)
    public BaseResponse<List<AccountGatherVO>> refundSummarizing(@RequestBody @Valid AccountGatherListRequest request) {
        request.setSupplierId(commonUtil.getCompanyInfoId());
        request.setAccountRecordType(AccountRecordType.REFUND);
        return BaseResponse.success(accountRecordQueryProvider.listAccountGather(request).getContext()
                .getAccountGatherVOList());
    }

    /**
     * 退款明细
     *
     * @param request 请求参数结构
     * @return 分页后的退款明细记录
     */
    @Operation(summary = "退款明细")
    @RequestMapping(value = "/refund/details", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_refund_details_sign_word")
    public BaseResponse<MicroServicePage<AccountDetailsVO>> refundDetails(@RequestBody @Valid
                                                                                  AccountDetailsPageRequest request) {
        request.setAccountRecordType(AccountRecordType.REFUND);
        MicroServicePage<AccountDetailsVO> page = accountRecordQueryProvider.pageAccountDetails(request).getContext()
                .getAccountDetailsVOPage();
        List<String> customerIds = page.getContent().stream().map(AccountDetailsVO::getCustomerId).collect(Collectors.toList());
        Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);
        page.getContent().forEach(v->v.setLogOutStatus(map.get(v.getCustomerId())));
        return BaseResponse.success(page);
    }

    /**
     * 导出退款明细
     *
     * @return
     */
    @Operation(summary = "导出退款明细")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @RequestMapping(value = "/refund/details/export/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportRefundDetails(@PathVariable String encrypted) {
        exportDetails(encrypted, AccountRecordType.REFUND);
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 获取所有支付方式
     *
     * @return 支付方式List key:枚举类型PayWay(支付方式)的name  value:枚举类型PayWay(支付方式)的中文描述
     */
    @Operation(summary = "获取所有支付方式,支付方式List key:枚举类型PayWay(支付方式)的name  value:枚举类型PayWay(支付方式)的中文描述")
    @RequestMapping(value = "/pay-methods", method = RequestMethod.GET)
    public BaseResponse<Map<String, String>> payWays() {
        return BaseResponse.success(Arrays.stream(PayWay.values()).collect(Collectors.toMap(PayWay::toValue, PayWay::getDesc)));
    }

    private void exportDetails(String encrypted, AccountRecordType type) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        AccountDetailsExportRequest request = JSON.parseObject(decrypted, AccountDetailsExportRequest.class);
        request.setAccountRecordType(type);

        if(StringUtils.isNotBlank(request.getQueryType())){
            request.setQueryPayType(QueryPayType.values()[Integer.valueOf(request.getQueryType())]);
        }

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_FINANCE_BILL_DETAIL);
        exportDataRequest.setParam(JSONObject.toJSONString(request));
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
    }

    /**
     * 财务对账导出
     * @param encrypted
     * @throws Exception
     */
    @Operation(summary = "财务对账导出")
    @Parameter(name = "encrypted", description = "加密", required = true)
    @GetMapping(value = "/exportIncome/{encrypted}")
    public BaseResponse exportIncome(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        AccountRecordToExcelRequest request = JSON.parseObject(decrypted, AccountRecordToExcelRequest.class);
        request.setSupplierId(commonUtil.getCompanyInfoId());
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_FINANCE_BILL);
        exportDataRequest.setParam(JSONObject.toJSONString(request));
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

}
