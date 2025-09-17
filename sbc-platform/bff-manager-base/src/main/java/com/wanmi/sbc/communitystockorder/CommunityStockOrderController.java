package com.wanmi.sbc.communitystockorder;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.marketing.api.provider.communitystockorder.CommunityStockOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communitystockorder.CommunityStockOrderListRequest;
import com.wanmi.sbc.marketing.api.response.communitystockorder.CommunityStockOrderListResponse;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Tag(name =  "社区团购备货单管理API", description =  "CommunityStockOrderController")
@RestController
@RequestMapping(value = "/communityStockOrder")
public class CommunityStockOrderController {

    @Autowired
    private CommunityStockOrderQueryProvider communityStockOrderQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "列表查询社区团购备货单")
    @PostMapping("/list")
    public BaseResponse<CommunityStockOrderListResponse> getList(@RequestBody @Valid CommunityStockOrderListRequest listReq) {
        return communityStockOrderQueryProvider.list(listReq);
    }

    @Operation(summary = "导出社区团购备货单列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        CommunityActivityByIdRequest request = JSON.parseObject(decrypted, CommunityActivityByIdRequest.class);
        if (StringUtils.isBlank(request.getActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(operator.getPlatform());
        exportDataRequest.setOperator(operator);
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.COMMUNITY_STOCK_WORD);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
