package com.wanmi.sbc.communityactivity;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityProvider;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityAddRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityModifyRequest;
import com.wanmi.sbc.marketing.bean.dto.CommunityCommissionLeaderRelDTO;
import com.wanmi.sbc.marketing.bean.dto.CommunitySkuRelDTO;
import com.wanmi.sbc.marketing.bean.enums.*;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.request.MarketingMutexValidateRequest;
import com.wanmi.sbc.marketing.service.MarketingBaseService;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "社区团购活动表管理API", description =  "CommunityActivityController")
@RestController
@RequestMapping(value = "/communityActivity")
public class CommunityActivityController {

    @Autowired
    private CommunityActivityProvider communityActivityProvider;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired private MarketingBaseService marketingBaseService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @MultiSubmit
    @Operation(summary = "新增社区团购活动表")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid CommunityActivityAddRequest addReq) {
        addReq.setStoreId(commonUtil.getStoreId());
        this.checkLeader(addReq);
        marketingBaseService.mutexValidateByAdd(addReq.getStoreId(), addReq.getStartTime(), addReq.getEndTime(),
                addReq.getSkuList().stream().map(CommunitySkuRelDTO::getSkuId).filter(Objects::nonNull).collect(Collectors.toList()));
        communityActivityProvider.add(addReq);
        operateLogMqUtil.convertAndSend("应用", "新增社区团购活动", "新增活动：".concat(addReq.getActivityName()));
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "修改社区团购活动表")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid CommunityActivityModifyRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        CommunityActivityByIdRequest idRequest = CommunityActivityByIdRequest.builder()
                .activityId(modifyReq.getActivityId()).build();
        CommunityActivityVO activity = communityActivityQueryProvider.getById(idRequest).getContext().getCommunityActivityVO();
        if(!CommunityTabStatus.STARTED.equals(activity.getActivityStatus())){
            this.checkLeader(KsBeanUtil.convert(modifyReq, CommunityActivityAddRequest.class));
        }
        //验证营销互斥
        MarketingMutexValidateRequest validateRequest = new MarketingMutexValidateRequest();
        validateRequest.setStoreId(modifyReq.getStoreId());
        validateRequest.setCrossBeginTime(modifyReq.getStartTime());
        validateRequest.setCrossEndTime(modifyReq.getEndTime());
        validateRequest.setSkuIds(modifyReq.getSkuList().stream().map(CommunitySkuRelDTO::getSkuId).filter(Objects::nonNull).collect(Collectors.toList()));
        validateRequest.setNotSelfId(Objects.toString(modifyReq.getActivityId()));
        validateRequest.setCommunityIdFlag(Boolean.TRUE);
        marketingBaseService.mutexValidate(validateRequest);
        communityActivityProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("应用", "编辑社区团购活动", "编辑活动：".concat(modifyReq.getActivityName()));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 验证团长是否注销
     * @param addRequest 参数
     */
    private void checkLeader(CommunityActivityAddRequest addRequest) {
        List<String> pickupIds = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(addRequest.getSalesTypes())) {
            if (addRequest.getSalesTypes().contains(CommunitySalesType.SELF)
                    && CommunitySalesRangeType.CUSTOM.equals(addRequest.getSalesRange())) {
                pickupIds.addAll(addRequest.getSalesRangeContext());
            }
            if (addRequest.getSalesTypes().contains(CommunitySalesType.LEADER)
                    && CommunityLeaderRangeType.CUSTOM.equals(addRequest.getLeaderRange())) {
                pickupIds.addAll(addRequest.getLeaderRangeContext());
            }
        }
        if (CommunityCommissionFlag.PICKUP.equals(addRequest.getCommissionFlag())
                && CollectionUtils.isNotEmpty(addRequest.getCommissionLeaderList())) {
            pickupIds.addAll(addRequest.getCommissionLeaderList()
                    .stream().map(CommunityCommissionLeaderRelDTO::getPickupPointId).collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(pickupIds)) {
            CommunityLeaderPickupPointListRequest listRequest = CommunityLeaderPickupPointListRequest.builder().pickupPointIdList(pickupIds).build();
            List<CommunityLeaderPickupPointVO> pickupPointList = communityLeaderPickupPointQueryProvider.list(listRequest).getContext().getCommunityLeaderPickupPointList();
            if (pickupPointList.stream().anyMatch(s -> !LeaderCheckStatus.CHECKED.equals(s.getCheckStatus()))) {
                throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "存在未通过审核的团长，请重新选择");
            }

            List<String> customerIds = pickupPointList.stream().map(CommunityLeaderPickupPointVO::getCustomerId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(customerIds)) {
                Collection<LogOutStatus> statuses = customerCacheService.getLogOutStatus(customerIds).values();
                if (CollectionUtils.containsAny(statuses, Arrays.asList(LogOutStatus.LOGGED_OUT, LogOutStatus.LOGGING_OFF))) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "所选择自提点相应的团长已注销或已禁用");
                }
            }
        }
    }
}
