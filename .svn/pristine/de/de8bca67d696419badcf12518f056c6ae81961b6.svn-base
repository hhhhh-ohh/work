package com.wanmi.sbc.communityleader;

import com.wanmi.ares.enums.ReportType;
import com.wanmi.ares.provider.CommunityQueryProvider;
import com.wanmi.ares.request.export.ExportDataRequest;
import com.wanmi.ares.view.community.CommunityOverviewView;
import com.wanmi.sbc.aop.EmployeeCheck;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.redis.util.RedisUtil;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderPageRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderCommissionStatisticsResponse;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderPageResponse;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.elastic.api.provider.communityleader.EsCommunityLeaderQueryProvider;
import com.wanmi.sbc.elastic.api.request.communityleader.EsCommunityLeaderPageRequest;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.report.ExportCenter;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 2023年07月24日15:47:20
 * @author wangchao 
 */
@Tag(name = "CommunityLeaderCommissionController", description = "社区团长佣金接口")
@Slf4j
@RestController
@Validated
@RequestMapping("/communityLeader-commission")
public class CommunityLeaderCommissionController {

    @Autowired
    private RedisUtil redisService;

    @Autowired
    private OperateLogMQUtil operateLogMQUtil;

    @Autowired
    private EsCommunityLeaderQueryProvider esCommunityLeaderQueryProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private CommunityStatisticsQueryProvider communityStatisticsQueryProvider;

    @Autowired
    private ExportCenter exportCenter;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Resource
    private CommonUtil commonUtil;

    @Autowired
    private CommunityQueryProvider communityQueryProvider;


    /**
     * 分页获取社区团长佣金记录
     * @param esCommunityLeaderPageRequest
     * @return
     */
    @Operation(summary = "S2B 平台端-分页获取社区团长佣金记录")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ReturnSensitiveWords(functionName = "f_boss_find_community_leader_page_word")
    public BaseResponse<CommunityLeaderPageResponse> findCommunityLeaderCommission(@RequestBody EsCommunityLeaderPageRequest esCommunityLeaderPageRequest) {
        //默认按创建时间降序 暂时不走ES
        CommunityLeaderPageRequest request = KsBeanUtil.convert(esCommunityLeaderPageRequest,CommunityLeaderPageRequest.class);
        //查询已审核或者禁用中团长数据
        request.setIsCheck(Constants.yes);
        BaseResponse<CommunityLeaderPageResponse> page = communityLeaderQueryProvider.page(request);


        List<CommunityLeaderVO> esCommunityLeaderVOS = page.getContext().getCommunityLeaderPage().getContent();
        if(CollectionUtils.isNotEmpty(esCommunityLeaderVOS)){
            //处理团长佣金统计
            List<String> ids = esCommunityLeaderVOS.stream().map(CommunityLeaderVO::getLeaderId).collect(Collectors.toList());
            Map<String, CommunityStatisticsVO> communityStatisticsVOMap = communityStatisticsQueryProvider.findByLeaderIdsGroup(
                    CommunityStatisticsListRequest.builder().idList(ids).build()).getContext().getCommunityStatisticsVOMap();


            //获取用户注销标识
            List<String> customerIds = esCommunityLeaderVOS
                    .stream()
                    .map(CommunityLeaderVO::getCustomerId)
                    .collect(Collectors.toList());
            Map<String, LogOutStatus> map = customerCacheService.getLogOutStatus(customerIds);

            esCommunityLeaderVOS.forEach(v -> {
                v.setLogOutStatus(map.get(v.getCustomerId()));
                CommunityStatisticsVO vo = communityStatisticsVOMap.get(v.getLeaderId());
                if(null != vo){
                    v.setCommissionReceived(vo.getCommissionReceived());
                    v.setCommissionReceivedPickup(vo.getCommissionReceivedPickup());
                    v.setCommissionReceivedAssist(vo.getCommissionReceivedAssist());
                    v.setCommissionPending(vo.getCommissionPending());
                    v.setCommissionPendingPickup(vo.getCommissionPendingPickup());
                    v.setCommissionPendingAssist(vo.getCommissionPendingAssist());
                }

            });
        }

        return page;
    }

    /**
     * 社区团长佣金统计
     * @return
     */
    @Operation(summary = "S2B 平台端-社区团长佣金统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public BaseResponse<CommunityOverviewView> statistics() {
        CommunityOverviewView view = communityQueryProvider.bossOverview();
        return BaseResponse.success(view);
    }

    /**
     * 导出社区团长佣金记录
     * @param encrypted
     */
    @Operation(summary = "导出社区团长佣金记录")
    @EmployeeCheck(customerIdField = "employeeCustomerIds")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse export(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted), StandardCharsets.UTF_8);

        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.COMMUNITY_LEADER_COMMISSION);
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        operateLogMQUtil.convertAndSend("财务", "批量导出社区团长佣金", "批量导出社区团长佣金");

        return BaseResponse.SUCCESSFUL();
    }



    /**
     * 获取社区团长佣金明细
     * @param leaderId
     * @return
     */
    @Operation(summary = "获取社区团长佣金明细")
    @RequestMapping(value = "/customer-id/{leaderId}", method = RequestMethod.GET)
    @ReturnSensitiveWords(functionName = "f_boss_get_info_by_leader_id_sign_word")
    public BaseResponse<CommunityLeaderByIdResponse> findCommunityLeaderCommissionDetail(@PathVariable String leaderId) {

        //查询当前团长明细
        CommunityLeaderByIdResponse response = communityLeaderQueryProvider.getById(
                CommunityLeaderByIdRequest.builder().leaderId(leaderId).build()).getContext();

        CommunityLeaderVO communityLeaderVO = response.getCommunityLeaderVO();
        //查询当前团长统计
        List<CommunityStatisticsVO> voList = communityStatisticsQueryProvider.findByLeaderIdGroup(
                CommunityStatisticsListRequest.builder()
                        .leaderId(leaderId).build())
                .getContext().getCommunityStatisticsVOList();

        if(Objects.nonNull(communityLeaderVO)){
            if(CollectionUtils.isNotEmpty(voList) && null != voList.get(0)){
                CommunityStatisticsVO vo = voList.get(0);
                communityLeaderVO.setCommissionReceived(vo.getCommissionReceived());
                communityLeaderVO.setCommissionReceivedPickup(vo.getCommissionReceivedPickup());
                communityLeaderVO.setCommissionReceivedAssist(vo.getCommissionReceivedAssist());
                communityLeaderVO.setCommissionPending(vo.getCommissionPending());
                communityLeaderVO.setCommissionPendingPickup(vo.getCommissionPendingPickup());
                communityLeaderVO.setCommissionPendingAssist(vo.getCommissionPendingAssist());
            }

            //查询当前会员是否已注销
            LogOutStatus logOutStatus = customerCacheService.getCustomerLogOutStatus(
                    communityLeaderVO.getCustomerId());
            communityLeaderVO.setLogOutStatus(logOutStatus);

        }

        return BaseResponse.success(response);
    }


    /**
     * 导出团长佣金明细
     * @param encrypted
     */
    @Operation(summary = "导出团长佣金明细")
    @Parameter(name = "encrypted", description = "解密", required = true)
    @RequestMapping(value = "/export/detail/params/{encrypted}", method = RequestMethod.GET)
    public BaseResponse exportDetail(@PathVariable String encrypted) {
        String decrypted = new String(Base64.getUrlDecoder().decode(encrypted.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        Operator operator = commonUtil.getOperator();
        ExportDataRequest exportDataRequest = new ExportDataRequest();
        exportDataRequest.setAdminId(operator.getAdminId());
        exportDataRequest.setPlatform(commonUtil.getOperator().getPlatform());
        exportDataRequest.setParam(decrypted);
        exportDataRequest.setTypeCd(ReportType.COMMUNITY_LEADER_COMMISSION_DETAIL);
        exportDataRequest.setBuyAnyThirdChannelOrNot(commonUtil.buyAnyThirdChannelOrNot());
        exportDataRequest.setOperator(commonUtil.getOperator());
        exportCenter.sendExport(exportDataRequest);
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "团长佣金详情")
    @GetMapping(value = "/info")
    public BaseResponse<CommunityOverviewView> commissionInfo(){
        CommunityOverviewView view = communityQueryProvider.bossOverview();
        return BaseResponse.success(view);
    }
}
