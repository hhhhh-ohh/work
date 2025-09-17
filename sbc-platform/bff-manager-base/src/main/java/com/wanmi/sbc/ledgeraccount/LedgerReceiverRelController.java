package com.wanmi.sbc.ledgeraccount;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.Platform;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.ledger.LakalaProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelQueryProvider;
import com.wanmi.sbc.customer.api.request.ledger.LakalaAccountFunctionRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelBindRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelByIdRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelCheckRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerBindState;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import com.wanmi.sbc.customer.bean.enums.LedgerReceiverType;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelVO;
import com.wanmi.sbc.elastic.api.provider.ledger.EsLedgerBindInfoQueryProvider;
import com.wanmi.sbc.elastic.api.request.ledger.EsLedgerBindInfoPageRequest;
import com.wanmi.sbc.elastic.api.response.ledger.EsLedgerBindInfoPageResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;


@Tag(name =  "分账绑定关系管理API", description =  "LedgerReceiverRelController")
@RestController
@Validated
@RequestMapping(value = "/ledgerreceiverrel")
public class LedgerReceiverRelController {

    @Autowired
    private LakalaProvider lakalaProvider;

    @Autowired
    private EsLedgerBindInfoQueryProvider esLedgerBindInfoQueryProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private LedgerAccountBaseService ledgerAccountBaseService;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    @Autowired
    private LedgerReceiverRelQueryProvider ledgerReceiverRelQueryProvider;

    @Operation(summary = "分页查询分账绑定关系")
    @PostMapping("/page")
    @ReturnSensitiveWords(functionName = "f_ledger_bind_info_sign_word")
    public BaseResponse<EsLedgerBindInfoPageResponse> page(@RequestBody @Valid EsLedgerBindInfoPageRequest pageReq) {
        ledgerAccountBaseService.checkGatewayOpen();
        if (Platform.SUPPLIER.equals(commonUtil.getOperator().getPlatform())) {
            pageReq.setSupplierId(commonUtil.getCompanyInfoId());
            if (LedgerReceiverType.PROVIDER.toValue() == pageReq.getReceiverType()) {
                pageReq.setFilterBindStates(Collections.singletonList(LedgerBindState.UNBOUND.toValue()));
            }
        }
        pageReq.putSort("bindTime", SortType.DESC.toValue());
        pageReq.putSort("receiverId", SortType.DESC.toValue());
        return esLedgerBindInfoQueryProvider.page(pageReq);
    }

    @Operation(summary = "绑定分账关系")
    @PostMapping("/bind")
    public BaseResponse bind(@RequestBody @Valid LedgerReceiverRelBindRequest bindRequest){
        LedgerReceiverRelVO rel = ledgerReceiverRelQueryProvider
                .getById(LedgerReceiverRelByIdRequest.builder()
                        .id(bindRequest.getReceiverRelId())
                        .build())
                .getContext().getLedgerReceiverRelVO();
        //分销员绑定需勾选同意协议
        if (LedgerReceiverType.DISTRIBUTION.toValue() == rel.getReceiverType()
                && !Boolean.TRUE.equals(bindRequest.getAcceptFlag())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        ledgerAccountBaseService.checkGatewayOpen();
        lakalaProvider.excuteFunction(LakalaAccountFunctionRequest.builder()
                .receiverRelId(bindRequest.getReceiverRelId())
                .type(LedgerFunctionType.BIND_ACCOUNT).build());
        return BaseResponse.SUCCESSFUL();
    }


    @Operation(summary = "导出分账绑定关系列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        EsLedgerBindInfoPageRequest listReq = JSON.parseObject(decrypted, EsLedgerBindInfoPageRequest.class);
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(JSONObject.toJSONString(listReq));
        exportDataRequest.setTypeCd(ReportType.LEDGER_RECEIVER_REL);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "分页查询未绑定的分账绑定关系")
    @PostMapping("/unBind/page")
    @ReturnSensitiveWords(functionName = "f_ledger_bind_info_sign_word")
    public BaseResponse<EsLedgerBindInfoPageResponse> unBindPage(@RequestBody @Valid EsLedgerBindInfoPageRequest pageReq) {
        ledgerAccountBaseService.checkGatewayOpen();
        pageReq.setReceiverType(LedgerReceiverType.PROVIDER.toValue());
        pageReq.setSupplierId(commonUtil.getCompanyInfoId());
        pageReq.setFilterBindStates(
                Lists.newArrayList(
                        LedgerBindState.BINDING.toValue(), LedgerBindState.CHECKING.toValue()));
        pageReq.putSort("bindTime", SortType.DESC.toValue());
        pageReq.putSort("receiverId", SortType.DESC.toValue());
        return esLedgerBindInfoQueryProvider.page(pageReq);
    }

    @Operation(summary = "驳回分销员分账绑定关系")
    @PostMapping("/check")
    public BaseResponse checkBindInfo(@RequestBody @Valid LedgerReceiverRelCheckRequest ledgerReceiverRelCheckRequest){
        ledgerReceiverRelCheckRequest.setSupplierId(commonUtil.getCompanyInfoId());
        return ledgerReceiverRelProvider.checkBindInfo(ledgerReceiverRelCheckRequest);
    }

}
