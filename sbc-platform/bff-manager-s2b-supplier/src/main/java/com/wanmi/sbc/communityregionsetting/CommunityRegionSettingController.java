package com.wanmi.sbc.communityregionsetting;

import com.wanmi.sbc.common.annotation.MultiSubmit;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.LogOutStatus;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.communitypickup.service.CommunityLeaderPickupPointService;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.service.CustomerCacheService;
import com.wanmi.sbc.marketing.api.provider.communityregionsetting.CommunityRegionSettingProvider;
import com.wanmi.sbc.marketing.api.provider.communityregionsetting.CommunityRegionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.*;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingListResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingPageResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingUsedAreaResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.util.OperateLogMQUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Tag(name =  "社区团购区域设置表管理API", description =  "CommunityRegionSettingController")
@RestController
@RequestMapping(value = "/communityRegionSetting")
public class CommunityRegionSettingController {

    @Autowired
    private CommunityRegionSettingQueryProvider communityRegionSettingQueryProvider;

    @Autowired
    private CommunityRegionSettingProvider communityRegionSettingProvider;

    @Autowired
    private CommunityLeaderPickupPointService communityLeaderPickupPointService;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CustomerCacheService customerCacheService;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private OperateLogMQUtil operateLogMqUtil;

    @Operation(summary = "分页查询社区团购区域设置表")
    @PostMapping("/page")
    public BaseResponse<CommunityRegionSettingPageResponse> getPage(@RequestBody @Valid CommunityRegionSettingPageRequest pageReq) {
        pageReq.putSort("regionId", "desc");
        pageReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<CommunityRegionSettingPageResponse> response = communityRegionSettingQueryProvider.page(pageReq);
        communityLeaderPickupPointService.fillPointsName(response.getContext().getCommunityRegionSettingPage().getContent());
        return response;
    }

    @Operation(summary = "列表查询社区团购区域设置表")
    @PostMapping("/list")
    public BaseResponse<CommunityRegionSettingListResponse> getList(@RequestBody @Valid CommunityRegionSettingListRequest listReq) {
        listReq.putSort("regionId", "desc");
        listReq.setStoreId(commonUtil.getStoreId());
        BaseResponse<CommunityRegionSettingListResponse> response = communityRegionSettingQueryProvider.list(listReq);
        communityLeaderPickupPointService.fillPointsName(response.getContext().getCommunityRegionSettingList());
        return response;
    }

    @Operation(summary = "根据id查询社区团购区域设置表")
    @GetMapping("/{regionId}")
    public BaseResponse<CommunityRegionSettingByIdResponse> getById(@PathVariable Long regionId) {
        if (regionId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityRegionSettingByIdRequest idReq = new CommunityRegionSettingByIdRequest();
        idReq.setRegionId(regionId);
        idReq.setStoreId(commonUtil.getStoreId());
        return communityRegionSettingQueryProvider.getById(idReq);
    }

    @MultiSubmit
    @Operation(summary = "新增社区团购区域设置表")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid CommunityRegionSettingAddRequest addReq) {
        addReq.setStoreId(commonUtil.getStoreId());
        this.checkLeader(addReq.getPickupPointIdList());
        communityRegionSettingProvider.add(addReq);
        operateLogMqUtil.convertAndSend("应用", "新增社区团购区域设置", "新增社区团购区域：".concat(addReq.getRegionName()));
        return BaseResponse.SUCCESSFUL();
    }

    @MultiSubmit
    @Operation(summary = "修改社区团购区域设置表")
    @PutMapping("/modify")
    public BaseResponse modify(@RequestBody @Valid CommunityRegionSettingModifyRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        this.checkLeader(modifyReq.getPickupPointIdList());
        communityRegionSettingProvider.modify(modifyReq);
        operateLogMqUtil.convertAndSend("应用", "编辑社区团购区域设置", "编辑社区团购区域：".concat(modifyReq.getRegionName()));
        return BaseResponse.SUCCESSFUL();
    }

    @Operation(summary = "获取已被使用地区和自提点")
    @PostMapping("/used")
    public BaseResponse<CommunityRegionSettingUsedAreaResponse> used(@RequestBody @Valid CommunityRegionSettingUsedAreaRequest modifyReq) {
        modifyReq.setStoreId(commonUtil.getStoreId());
        return communityRegionSettingQueryProvider.getUsedArea(modifyReq);
    }

    @MultiSubmit
    @Operation(summary = "根据id删除社区团购区域设置表")
    @DeleteMapping("/{regionId}")
    public BaseResponse deleteById(@PathVariable Long regionId) {
        if (regionId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityRegionSettingByIdRequest idReq = new CommunityRegionSettingByIdRequest();
        idReq.setRegionId(regionId);
        idReq.setStoreId(commonUtil.getStoreId());
        CommunityRegionSettingVO settingVO = communityRegionSettingQueryProvider.getById(idReq).getContext().getCommunityRegionSettingVO();
        if (settingVO == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityRegionSettingDelByIdRequest delByIdReq = new CommunityRegionSettingDelByIdRequest();
        delByIdReq.setRegionId(regionId);
        communityRegionSettingProvider.deleteById(delByIdReq);
        operateLogMqUtil.convertAndSend("应用", "删除社区团购区域设置", "删除社区团购区域：".concat(settingVO.getRegionName()));
        return BaseResponse.SUCCESSFUL();
    }

    /**
     * 验证团长是否注销
     * @param pickupIds 参数
     */
    private void checkLeader(List<String> pickupIds) {
        if (CollectionUtils.isNotEmpty(pickupIds)) {
            CommunityLeaderPickupPointListRequest listRequest = CommunityLeaderPickupPointListRequest.builder().pickupPointIdList(pickupIds).build();
            List<String> customerIds = communityLeaderPickupPointQueryProvider.list(listRequest).getContext().getCommunityLeaderPickupPointList()
                    .stream().map(CommunityLeaderPickupPointVO::getCustomerId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(customerIds)) {
                Collection<LogOutStatus> statuses = customerCacheService.getLogOutStatus(customerIds).values();
                if (CollectionUtils.containsAny(statuses, Arrays.asList(LogOutStatus.LOGGED_OUT, LogOutStatus.LOGGING_OFF))) {
                    throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "所选择自提点相应的团长已注销或已禁用");
                }
            }
        }
    }
}
