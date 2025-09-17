package com.wanmi.sbc.giftcard;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardBillQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardBillPageRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardBillPageResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "礼品卡交易流水管理API", description =  "GiftCardBillController")
@RestController
@RequestMapping(value = "/giftCardBill")
public class GiftCardBillController {

    @Autowired
    private GiftCardBillQueryProvider giftCardBillQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CommonUtil commonUtil;

    @ReturnSensitiveWords(functionName = "f_gift_card_bill_list_sign_word")
    @Operation(summary = "分页查询礼品卡交易流水")
    @PostMapping("/page")
    public BaseResponse<GiftCardBillPageResponse> getPage(@RequestBody @Valid GiftCardBillPageRequest pageReq) {
        pageReq.putSort("tradeTime", "desc");
        return giftCardBillQueryProvider.page(pageReq);
    }

    @Operation(summary = "导出记录表列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted, HttpServletResponse response) {

        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        Operator operator = commonUtil.getOperator();

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setOperator(operator);
        exportDataRequest.setTypeCd(ReportType.GIFT_CARD_BILL);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
