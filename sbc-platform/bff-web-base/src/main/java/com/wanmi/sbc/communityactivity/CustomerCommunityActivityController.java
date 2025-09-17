package com.wanmi.sbc.communityactivity;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.communityactivity.response.CommunityActivityGetSiteByIdResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivityPageSiteResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivitySiteVO;
import com.wanmi.sbc.communityactivity.response.CommunityActivityTradePageSiteResponse;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.communityactivity.service.CommunityActivityTradeDetailService;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesType;
import com.wanmi.sbc.marketing.bean.enums.CommunityTabStatus;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailPageRequest;
import com.wanmi.sbc.order.bean.vo.CommunitySimpleTradeVO;
import com.wanmi.sbc.util.CommonUtil;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;


@Tag(name =  "普通会员社区团购活动表管理API", description =  "CommunityActivityBaseController")
@RestController
@RequestMapping(value = "/customer/communityActivity")
public class CustomerCommunityActivityController {

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Autowired
    private CommunityActivityTradeDetailService communityActivityTradeDetailService;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "未登陆分页查询社区团购活动表")
    @PostMapping("/unlogin/page")
    public BaseResponse<CommunityActivityPageSiteResponse> unloginPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        return this.getPage(pageReq);
    }

    @Operation(summary = "分页查询社区团购活动表")
    @PostMapping("/page")
    public BaseResponse<CommunityActivityPageSiteResponse> getPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        communityActivityService.checkOpen();
        if (CommunityTabStatus.STARTED.equals(pageReq.getTabType())) {
            pageReq.putSort("endTime", "asc");
        } else if (CommunityTabStatus.NOT_START.equals(pageReq.getTabType())) {
            pageReq.putSort("startTime", "asc");
        } else if (CommunityTabStatus.ENDED.equals(pageReq.getTabType())) {
            pageReq.putSort("endTime", "desc");
        }
        pageReq.setSkuRelFlag(Boolean.TRUE);
        pageReq.setSaleRelFlag(Boolean.TRUE);
        pageReq.setCommissionRelFlag(Boolean.TRUE);
        pageReq.setSalesTypes(Collections.singletonList(CommunitySalesType.LEADER));
        MicroServicePage<CommunityActivityVO> page = communityActivityQueryProvider.page(pageReq).getContext().getCommunityActivityPage();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByCustomer(page.getContent(), commonUtil.getOperatorId());
        //填充跟团记录
        communityActivityTradeDetailService.fillTradeList(voList, null);
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, null);
        CommunityActivityPageSiteResponse siteResponse = CommunityActivityPageSiteResponse.builder()
                .communityActivityPage(new MicroServicePage<>(voList, pageReq.getPageable(), page.getTotal()))
                .build();
        return BaseResponse.success(siteResponse);
    }

    @Operation(summary = "未登陆分页查询社区团购活动表")
    @GetMapping("/unlogin/{activityId}")
    public BaseResponse<CommunityActivityGetSiteByIdResponse> unloginById(@PathVariable String activityId) {
        return this.getById(activityId);
    }

    @Operation(summary = "根据id查询社区团购活动表")
    @GetMapping("/{activityId}")
    public BaseResponse<CommunityActivityGetSiteByIdResponse> getById(@PathVariable String activityId) {
        if (activityId == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        communityActivityService.checkOpen();
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(activityId);
        idReq.setSkuRelFlag(Boolean.TRUE);
        idReq.setCommissionRelFlag(Boolean.TRUE);
        idReq.setSaleRelFlag(Boolean.TRUE);
        CommunityActivityByIdResponse response = communityActivityQueryProvider.getById(idReq).getContext();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByCustomer(
                Collections.singletonList(response.getCommunityActivityVO()), commonUtil.getOperatorId());
        communityActivityService.fillStore(voList.get(0));
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, null);
        return BaseResponse.success(CommunityActivityGetSiteByIdResponse.builder().communityActivity(voList.get(0)).build());
    }

    @Operation(summary = "根据活动id查询跟团记录表")
    @PostMapping("/tradePage")
    public BaseResponse<CommunityActivityTradePageSiteResponse> tradePage(@RequestBody @Valid LeaderTradeDetailPageRequest pageReq) {
        if (StringUtils.isBlank(pageReq.getCommunityActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        communityActivityService.checkOpen();
        MicroServicePage<CommunitySimpleTradeVO> page = communityActivityTradeDetailService.getTradeList(pageReq);
        return BaseResponse.success(CommunityActivityTradePageSiteResponse.builder().communitySimpleTradePage(page).build());
    }
}
