package com.wanmi.sbc.distribution;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionInviteNewQueryProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerDetailPageRequest;
import com.wanmi.sbc.customer.api.request.customer.DistributionInviteNewExportRequest;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCustomerPageRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerDetailPageResponse;
import com.wanmi.sbc.customer.api.response.distribution.DistributionCustomerPageResponse;
import com.wanmi.sbc.customer.bean.vo.DistributionInviteNewForPageVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.distributioninvitenew.EsDistributionInviteNewQueryProvider;
import com.wanmi.sbc.elastic.api.request.distributioninvitenew.EsDistributionInviteNewPageRequest;
import com.wanmi.sbc.elastic.api.response.distributioninvitenew.EsDistributionInviteNewPageResponse;
import com.wanmi.sbc.marketing.bean.vo.DistributionRecordVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分销员API
 */
@Tag(name = "DistributionInviteNewController", description = "邀新记录接口")
@Slf4j
@RestController
@Validated
@RequestMapping("/distribution-invite-new")
public class DistributionInviteNewController {

    @Autowired
    private  DistributionInviteNewQueryProvider distributionInviteNewQueryProvider;

    @Autowired
    private  CustomerQueryProvider customerQueryProvider;

    @Autowired
    private DistributionCustomerQueryProvider distributionCustomerQueryProvider;

    @Autowired
    private EsDistributionInviteNewQueryProvider esDistributionInviteNewQueryProvider;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    /**
     * 分页查询邀请记录
     * @param distributionInviteNewPageRequest
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResponse<EsDistributionInviteNewPageResponse> findDistributionInviteNewRecord(@RequestBody EsDistributionInviteNewPageRequest distributionInviteNewPageRequest) {
        BaseResponse<EsDistributionInviteNewPageResponse> page = esDistributionInviteNewQueryProvider.findDistributionInviteNewRecord(distributionInviteNewPageRequest);
        //获取受邀人注销状态
        List<String> invitedNewCustomerIds = page.getContext().getRecordList()
                .stream()
                .map(DistributionInviteNewForPageVO::getInvitedNewCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> invitedNewCustomerMap = customerCacheService.getLogOutStatus(invitedNewCustomerIds);
        page.getContext().getRecordList().forEach(v->v.setInvitedNewCustomerLogOutStatus(invitedNewCustomerMap.get(v.getInvitedNewCustomerId())));

        //获取邀请人
        List<String> requestCustomerIds = page.getContext().getRecordList()
                .stream()
                .map(DistributionInviteNewForPageVO::getRequestCustomerId)
                .collect(Collectors.toList());
        Map<String, LogOutStatus> requestCustomerMap = customerCacheService.getLogOutStatus(requestCustomerIds);
        page.getContext().getRecordList().forEach(v->v.setRequestCustomerLogOutStatus(requestCustomerMap.get(v.getRequestCustomerId())));
        return page;
    }

    /**
     * 根据受邀人账号或名称联想查询会员信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/newInvited/list", method = RequestMethod.POST)
    public BaseResponse<CustomerDetailPageResponse> inviteNewList(@RequestBody CustomerDetailPageRequest request) {
        return customerQueryProvider.page(request);
    }

    /**
     * 根据分销员账号或名称联想会员信息
     */
    @RequestMapping(value = "/distributionCustomer/list", method = RequestMethod.POST)
    public BaseResponse<DistributionCustomerPageResponse> distributionCustomer(@RequestBody DistributionCustomerPageRequest request) {
        return distributionCustomerQueryProvider.page(request);
    }


    /**
     * 导出邀新记录
     * @param encrypted
     * @param
     */
    @Operation(summary = "导出邀新记录")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);
        DistributionInviteNewExportRequest queryReq = JSON.parseObject(decrypted, DistributionInviteNewExportRequest.class);
        // 按注册时间降序
        queryReq.setSortColumn("registerTime");
        queryReq.setSortType("DESC");

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setTypeCd(ReportType.BUSINESS_INVITE_RECORD);
        exportDataRequest.setParam(JSONObject.toJSONString(queryReq));
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("营销", "批量导出邀新记录", "批量导出邀新记录");
        return BaseResponse.SUCCESSFUL();
    }

}
