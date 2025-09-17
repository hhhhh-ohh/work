package com.wanmi.sbc.communityactivity;

import com.wanmi.sbc.account.api.provider.funds.CustomerFundsQueryProvider;
import com.wanmi.sbc.account.api.request.funds.CustomerFundsByCustomerIdRequest;
import com.wanmi.sbc.account.api.response.funds.CustomerFundsByCustomerIdResponse;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.communityactivity.response.LeaderCenterResponse;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.customer.api.constant.CustomerLabel;
import com.wanmi.sbc.customer.api.provider.communityleader.CommunityLeaderQueryProvider;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.request.communityleader.CommunityLeaderByIdRequest;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.response.communityleader.CommunityLeaderByIdResponse;
import com.wanmi.sbc.customer.api.response.customer.CustomerGetByIdResponse;
import com.wanmi.sbc.customer.bean.enums.EnterpriseCheckState;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderVO;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsCustomerQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communitystatistics.CommunityStatisticsQueryProvider;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsCustomerListRequest;
import com.wanmi.sbc.marketing.api.request.communitystatistics.CommunityStatisticsListRequest;
import com.wanmi.sbc.marketing.bean.vo.CommunityStatisticsVO;
import com.wanmi.sbc.order.api.provider.leadertradedetail.LeaderTradeDetailQueryProvider;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailListRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.bean.vo.IepSettingVO;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Objects;


@Tag(name =  "社区团购团长表管理API", description =  "CommunityLeaderController")
@RestController
@RequestMapping(value = "/communityLeader")
public class CommunityLeaderBaseController {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CommunityStatisticsQueryProvider communityStatisticsQueryProvider;

    @Autowired
    private CommunityStatisticsCustomerQueryProvider statisticsCustomerQueryProvider;

    @Autowired
    private LeaderTradeDetailQueryProvider leaderTradeDetailQueryProvider;

    @Autowired
    private CustomerFundsQueryProvider customerFundsQueryProvider;

    @Autowired
    private CommunityLeaderQueryProvider communityLeaderQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    /**
     * 根据团长id查询团长信息（无需登录）
     * @return
     */
    @Operation(summary = "根据团长id查询团长信息（无需登录）")
    @RequestMapping(value = "/detail/{leaderId}", method = RequestMethod.GET)
    public BaseResponse<LeaderCenterResponse> getLeaderDetail(@PathVariable String leaderId) {

        if(StringUtils.isBlank(leaderId)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }

        //查询团长信息
        CommunityLeaderVO communityLeaderVO = communityLeaderQueryProvider.getById(
                CommunityLeaderByIdRequest.builder()
                        .leaderId(leaderId).build()).getContext().getCommunityLeaderVO();

        //查询会员信息
        String customerId = communityLeaderVO.getCustomerId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();

        //组装数据
        LeaderCenterResponse response =
                LeaderCenterResponse.builder()
                        .leaderId(communityLeaderVO.getLeaderId())
                        .leaderName(getLeaderNameSign(communityLeaderVO.getLeaderName()))
                        .leaderDescription(communityLeaderVO.getLeaderDescription())
                        .headImg(customer.getHeadImg())
                        .checkStatus(communityLeaderVO.getCheckStatus())
                        .disableReason(communityLeaderVO.getDisableReason())
                        .assistFlag(communityLeaderVO.getAssistFlag())
                        .build();

        return BaseResponse.success(response);
    }

    /**
     * 查询当前登录的团长详情
     * @return
     */
    @Operation(summary = "查询当前登录的团长详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public BaseResponse<LeaderCenterResponse> getLeaderDetail() {
        communityActivityService.checkOpen();
        //查询团长信息
        CommunityLeaderVO communityLeaderVO = commonUtil.getCommunityLeader();

        //查询会员信息
        String customerId = commonUtil.getOperatorId();
        CustomerGetByIdResponse customer = customerQueryProvider.getCustomerById(new CustomerGetByIdRequest
                (customerId)).getContext();

        //查询企业会员信息
        if (EnterpriseCheckState.CHECKED.equals(customer.getEnterpriseCheckState())){
            communityLeaderVO.getCustomerLabelList().add(CustomerLabel.EnterpriseCustomer);
            IepSettingVO iepSettingInfo = commonUtil.getIepSettingInfo();
            communityLeaderVO.setEnterpriseCustomerName(iepSettingInfo.getEnterpriseCustomerName());
            communityLeaderVO.setEnterpriseCustomerLogo(iepSettingInfo.getEnterpriseCustomerLogo());
        }

        //拓客人数
        Long inviteNum = statisticsCustomerQueryProvider.count(
                CommunityStatisticsCustomerListRequest.builder()
                        .leaderId(communityLeaderVO.getLeaderId())
                        .build()).getContext();

        //跟团人数
        Long followNum = leaderTradeDetailQueryProvider.findFollowNum(
                LeaderTradeDetailListRequest.builder()
                        .leaderId(communityLeaderVO.getLeaderId())
                        .build()).getContext();

        CustomerFundsByCustomerIdRequest request = new CustomerFundsByCustomerIdRequest();
        request.setCustomerId(commonUtil.getOperatorId());
        CustomerFundsByCustomerIdResponse idResponse
                = customerFundsQueryProvider.getByCustomerId(request).getContext();

        //组装数据
        LeaderCenterResponse response =
                LeaderCenterResponse.builder()
                        .leaderId(communityLeaderVO.getLeaderId())
                        .leaderName(communityLeaderVO.getLeaderName())
                        .leaderDescription(communityLeaderVO.getLeaderDescription())
                        .headImg(customer.getHeadImg())
                        .checkStatus(communityLeaderVO.getCheckStatus())
                        .disableReason(communityLeaderVO.getDisableReason())
                        .assistFlag(communityLeaderVO.getAssistFlag())
                        .customerLabelList(communityLeaderVO.getCustomerLabelList())
                        .enterpriseCustomerLogo(communityLeaderVO.getEnterpriseCustomerLogo())
                        .enterpriseCustomerName(getLeaderNameSign(communityLeaderVO.getEnterpriseCustomerName()))
                        .inviteNum(inviteNum)
                        .followNum(followNum)
                        .balanceTotal(idResponse.getAccountBalance())
                        .logOutStatus(communityLeaderVO.getLogOutStatus())
                        .build();

        return BaseResponse.success(response);
    }

    /**
     * 查询当前登录的团长社区团购统计数据
     * @return
     */
    @Operation(summary = "查询当前登录的团长社区团购统计数据")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public BaseResponse<CommunityStatisticsVO> getLeaderStatistics() {
        communityActivityService.checkOpen();

        //查询团长信息
        CommunityLeaderVO leaderVO = commonUtil.getCommunityLeader();

        //查询当前团长统计
        List<CommunityStatisticsVO> voList = communityStatisticsQueryProvider.findByLeaderIdGroup(
                CommunityStatisticsListRequest.builder()
                        .leaderId(leaderVO.getLeaderId()).build())
                .getContext().getCommunityStatisticsVOList();

        //查询团长信息
        CommunityStatisticsVO statisticsVO = new CommunityStatisticsVO();
        if(CollectionUtils.isNotEmpty(voList) && null != voList.get(0)){
            CommunityStatisticsVO vo = voList.get(0);
            //帮卖订单数
            statisticsVO.setAssistOrderNum(vo.getAssistOrderNum());
            //帮卖订单总额
            statisticsVO.setAssistOrderTotal(vo.getAssistOrderTotal());
            //已入账自提佣金 = 服务佣金
            statisticsVO.setCommissionReceivedPickup(vo.getCommissionReceivedPickup());
            //已入账帮卖佣金 = 帮卖佣金
            statisticsVO.setCommissionReceivedAssist(vo.getCommissionReceivedAssist());
            //服务订单数
            statisticsVO.setPickupServiceOrderNum(vo.getPickupServiceOrderNum());
            //服务订单金额
            statisticsVO.setPickupServiceOrderTotal(vo.getPickupServiceOrderTotal());
        }

        return BaseResponse.success(statisticsVO);
    }

    /**
     * 团长名称脱敏
     * @param leaderName
     * @return
     */
    public String getLeaderNameSign(String leaderName) {
        if (StringUtils.isBlank(leaderName)) {
            return leaderName;
        }
        if (leaderName.length() == 11) {
            return StringUtils.substring(leaderName, 0, 3).concat("****").concat(StringUtils.substring(leaderName, 7));
        } else {
            return StringUtils.substring(leaderName, 0, 1).concat("****");
        }
    }
}
