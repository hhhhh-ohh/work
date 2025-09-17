package com.wanmi.sbc.giftcard;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.giftcard.UserGiftCardProvider;
import com.wanmi.sbc.marketing.api.request.giftcard.UserGiftCardQueryRequest;
import com.wanmi.sbc.marketing.api.response.giftcard.UserGiftCardInfoPageResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author lvzhenwei
 * @className UserGiftCardController
 * @description 会员礼品卡管理API
 * @date 2022/12/13 10:07 上午
 **/
@Tag(name =  "会员礼品卡管理API", description =  "UserGiftCardController")
@RestController
@Validated
@RequestMapping(value = "/userGiftCard")
public class UserGiftCardController {

    @Autowired private UserGiftCardProvider userGiftCardProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired private ExportCenter exportCenter;

    @Operation(summary = "用户礼品卡分页查询")
    @RequestMapping(value = "/getUserGiftCardPage", method = RequestMethod.POST)
    public BaseResponse<UserGiftCardInfoPageResponse> getUserGiftCardPage(@RequestBody @Validated UserGiftCardQueryRequest request) {
        if(StringUtils.isBlank(request.getCustomerId())){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        request.putSort("acquireTime", "desc");
        request.putSort("userGiftCardId", "desc");
        return userGiftCardProvider.getUserGiftCardInfoPage(request);
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
        exportDataRequest.setTypeCd(ReportType.USER_GIFT_CARD_DETAIL);
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
