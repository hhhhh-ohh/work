package com.wanmi.sbc.giftcard;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.DefaultFlag;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailProvider;
import com.wanmi.sbc.marketing.api.provider.giftcard.GiftCardDetailQueryProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardCancelRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailByIdRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailPageRequest;
import com.wanmi.sbc.marketing.api.request.giftcard.GiftCardDetailQueryBalanceRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardCancelResultResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailByIdResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailPageResponse;
import com.wanmi.sbc.marketing.api.response.giftcard.GiftCardDetailQueryBalanceResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "礼品卡详情管理API", description =  "GiftCardDetailController")
@RestController
@Validated
@RequestMapping(value = "/giftCardDetail")
public class GiftCardDetailController {

    @Autowired
    private GiftCardDetailQueryProvider giftCardDetailQueryProvider;

    @Autowired
    private GiftCardDetailProvider giftCardDetailProvider;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private ExportCenter exportCenter;

    @ReturnSensitiveWords(functionName = "f_gift_card_detail_list_sign_word")
    @Operation(summary = "分页查询礼品卡详情")
    @PostMapping("/page")
    public BaseResponse<GiftCardDetailPageResponse> getPage(@RequestBody @Valid GiftCardDetailPageRequest pageReq) {
        pageReq.setDelFlag(DeleteFlag.NO);
        pageReq.putSort("giftCardNo", "desc");
        return giftCardDetailQueryProvider.page(pageReq);
    }

    @Operation(summary = "根据id查询礼品卡详情")
    @GetMapping("/{giftCardNo}")
    public BaseResponse<GiftCardDetailByIdResponse> getById(@PathVariable String giftCardNo) {
        if (giftCardNo == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        GiftCardDetailByIdRequest idReq = new GiftCardDetailByIdRequest();
        idReq.setGiftCardNo(giftCardNo);
        return giftCardDetailQueryProvider.getById(idReq);
    }

    @Operation(summary = "礼品卡销卡操作")
    @PutMapping("/cancelCard")
    public BaseResponse<GiftCardCancelResultResponse> cancelCard(@RequestBody @Valid GiftCardCancelRequest request) {
        request.setCancelDesc("手动销卡");
        request.setCancelPerson(commonUtil.getOperatorId());
        request.setTradePersonType(DefaultFlag.YES);
        return giftCardDetailProvider.cancelCard(request);
    }

    @Operation(summary = "获取礼品卡余额")
    @PostMapping("/queryBalance")
    public BaseResponse<GiftCardDetailQueryBalanceResponse> queryBalance(@RequestBody @Valid GiftCardDetailQueryBalanceRequest request) {
        return giftCardDetailProvider.queryBalance(request);
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
        exportDataRequest.setTypeCd(ReportType.GIFT_CARD_DETAIL);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

}
