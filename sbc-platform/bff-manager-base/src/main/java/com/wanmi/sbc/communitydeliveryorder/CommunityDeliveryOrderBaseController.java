package com.wanmi.sbc.communitydeliveryorder;

import com.alibaba.fastjson2.JSON;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderListRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.marketing.api.provider.communitydeliveryorder.CommunityDeliveryOrderQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderListRequest;
import com.wanmi.sbc.marketing.api.request.communitydeliveryorder.CommunityDeliveryOrderPageRequest;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderListResponse;
import com.wanmi.sbc.marketing.api.response.communitydeliveryorder.CommunityDeliveryOrderPageResponse;
import com.wanmi.sbc.marketing.bean.enums.DeliveryOrderSummaryType;
import com.wanmi.sbc.marketing.bean.vo.CommunityDeliveryOrderVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Tag(name =  "社区团购发货单管理API", description =  "CommunityDeliveryOrderBaseController")
@RestController
@RequestMapping(value = "/communityDeliveryOrder")
public class CommunityDeliveryOrderBaseController {

    @Autowired
    private CommunityDeliveryOrderQueryProvider communityDeliveryOrderQueryProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CommonUtil commonUtil;


    @ReturnSensitiveWords(functionName = "f_community_delivery_order_word")
    @Operation(summary = "分页查询社区团购发货单")
    @PostMapping("/page")
    public BaseResponse<CommunityDeliveryOrderPageResponse> getPage(@RequestBody @Valid CommunityDeliveryOrderPageRequest pageReq) {
        pageReq.putSort("createTime", "asc");
        BaseResponse<CommunityDeliveryOrderPageResponse> response = communityDeliveryOrderQueryProvider.page(pageReq);
        List<CommunityDeliveryOrderVO> orderVOList = response.getContext().getCommunityDeliveryOrderVOPage().getContent();
        if (DeliveryOrderSummaryType.LEADER.equals(pageReq.getType()) && CollectionUtils.isNotEmpty(orderVOList)) {
            List<String> leaderIds = orderVOList.stream().map(CommunityDeliveryOrderVO::getLeaderId).collect(Collectors.toList());
            Map<String, String> leaderAccountMap = communityLeaderQueryProvider.list(CommunityLeaderListRequest.builder()
                            .leaderIdList(leaderIds).build()).getContext()
                    .getCommunityLeaderList().stream().collect(Collectors.toMap(CommunityLeaderVO::getLeaderId, CommunityLeaderVO::getLeaderAccount));
            orderVOList.stream().filter(o -> leaderAccountMap.containsKey(o.getLeaderId())).forEach(o -> o.setLeaderAccount(leaderAccountMap.get(o.getLeaderId())));
        }
        return response;
    }

    @ReturnSensitiveWords(functionName = "f_community_delivery_order_word")
    @Operation(summary = "列表查询社区团购发货单")
    @PostMapping("/list")
    public BaseResponse<CommunityDeliveryOrderListResponse> getList(@RequestBody @Valid CommunityDeliveryOrderListRequest listReq) {
        listReq.putSort("createTime", "asc");
        return communityDeliveryOrderQueryProvider.list(listReq);
    }

    @Operation(summary = "导出社区团购发货单列表")
    @GetMapping("/export/{encrypted}")
    public BaseResponse exportData(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        CommunityDeliveryOrderListRequest tradeExportRequest = JSON.parseObject(decrypted, CommunityDeliveryOrderListRequest.class);
        if (StringUtils.isBlank(tradeExportRequest.getActivityId()) || tradeExportRequest.getType() == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(operator.getPlatform());
        exportDataRequest.setOperator(operator);
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.COMMUNITY_DELIVERY_WORD);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }
}
