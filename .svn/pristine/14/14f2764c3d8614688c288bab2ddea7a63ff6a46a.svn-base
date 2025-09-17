package com.wanmi.sbc.communityassist;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.enums.CommonErrorCodeEnum;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.exception.SbcRuntimeException;
import com.wanmi.sbc.communityactivity.response.CommunityActivityGetSiteByIdResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivityPageSiteResponse;
import com.wanmi.sbc.communityactivity.response.CommunityActivitySiteVO;
import com.wanmi.sbc.communityactivity.response.CommunityActivityTradePageSiteResponse;
import com.wanmi.sbc.communityactivity.service.CommunityActivityService;
import com.wanmi.sbc.communityactivity.service.CommunityActivityTradeDetailService;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.provider.communityassist.CommunityAssistRecordProvider;
import com.wanmi.sbc.marketing.api.provider.communityassist.CommunityAssistRecordQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityPageRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordAddRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordByIdRequest;
import com.wanmi.sbc.marketing.api.request.communityassist.CommunityAssistRecordListRequest;
import com.wanmi.sbc.marketing.api.response.communityactivity.CommunityActivityByIdResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.marketing.bean.vo.CommunityAssistRecordVO;
import com.wanmi.sbc.order.api.request.leadertradedetail.LeaderTradeDetailPageRequest;
import com.wanmi.sbc.order.bean.vo.CommunitySimpleTradeVO;
import com.wanmi.sbc.util.CommonUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Tag(description= "社区团购活动帮卖转发记录管理API", name = "CommunityAssistRecordController")
@RestController
@RequestMapping(value = "/communityAssistRecord")
public class CommunityAssistRecordController {

    @Autowired
    private CommunityAssistRecordQueryProvider communityAssistRecordQueryProvider;

    @Autowired
    private CommunityAssistRecordProvider communityAssistRecordProvider;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Autowired
    private CommunityActivityService communityActivityService;

    @Autowired
    private CommunityActivityTradeDetailService communityActivityTradeDetailService;

    @Autowired
    private CommonUtil commonUtil;

    @Operation(summary = "分页查询社区团购活动帮卖转发记录")
    @PostMapping("/page")
    public BaseResponse<CommunityActivityPageSiteResponse> getPage(@RequestBody @Valid CommunityActivityPageRequest pageReq) {
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO pointVO = commonUtil.getLeader();
        CommunityAssistRecordListRequest listRequest = CommunityAssistRecordListRequest.builder()
                .leaderId(pointVO.getLeaderId()).build();
        listRequest.putSort("createTime", SortType.DESC.toValue());
        List<CommunityAssistRecordVO> recordList = communityAssistRecordQueryProvider.list(listRequest).getContext().getCommunityAssistRecordVOList();
        if(CollectionUtils.isEmpty(recordList)) {
            CommunityActivityPageSiteResponse siteResponse = CommunityActivityPageSiteResponse.builder()
                    .communityActivityPage(new MicroServicePage<>(Collections.emptyList(), pageReq.getPageable(), 0))
                    .build();
            return BaseResponse.success(siteResponse);
        }
        pageReq.setSkuRelFlag(Boolean.TRUE);
        pageReq.setSaleRelFlag(Boolean.TRUE);
        pageReq.setCommissionRelFlag(Boolean.TRUE);
        pageReq.setActivityIdList(recordList.stream().map(CommunityAssistRecordVO::getActivityId).collect(Collectors.toList()));
        MicroServicePage<CommunityActivityVO> page = communityActivityQueryProvider.page(pageReq).getContext().getCommunityActivityPage();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByLeader(page.getContent(), pointVO);
        //填充跟团记录
        communityActivityTradeDetailService.fillTradeList(voList, pointVO.getLeaderId());
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, pointVO.getLeaderId());
        CommunityActivityPageSiteResponse siteResponse = CommunityActivityPageSiteResponse.builder()
                .communityActivityPage(new MicroServicePage<>(voList, pageReq.getPageable(), page.getTotal()))
                .build();
        return BaseResponse.success(siteResponse);
    }

    @Operation(summary = "根据id查询社区团购活动帮卖转发记录")
    @GetMapping("/{id}")
    public BaseResponse<CommunityActivityGetSiteByIdResponse> getById(@PathVariable String id) {
        if (id == null) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO point = commonUtil.getLeader();

        CommunityAssistRecordByIdRequest recordByIdRequest = new CommunityAssistRecordByIdRequest();
        recordByIdRequest.setId(id);
        CommunityAssistRecordVO recordVO = communityAssistRecordQueryProvider.getById(recordByIdRequest).getContext().getCommunityAssistRecordVO();
        if (!recordVO.getLeaderId().equals(point.getLeaderId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(recordVO.getActivityId());
        idReq.setSkuRelFlag(Boolean.TRUE);
        idReq.setCommissionRelFlag(Boolean.TRUE);
        idReq.setSaleRelFlag(Boolean.TRUE);
        CommunityActivityByIdResponse response = communityActivityQueryProvider.getById(idReq).getContext();
        List<CommunityActivitySiteVO> voList = communityActivityService.getByLeader(
                Collections.singletonList(response.getCommunityActivityVO()), point);
        communityActivityService.fillStore(voList.get(0));
        //填充跟团数
        communityActivityTradeDetailService.fillTradeTotal(voList, point.getLeaderId());
        return BaseResponse.success(CommunityActivityGetSiteByIdResponse.builder().communityActivity(voList.get(0)).build());
    }

    @Operation(summary = "新增社区团购活动帮卖转发记录")
    @PostMapping("/add")
    public BaseResponse add(@RequestBody @Valid CommunityAssistRecordAddRequest addReq) {
        communityActivityService.checkOpen();
        CommunityLeaderPickupPointVO pointVO = commonUtil.getLeader();
        CommunityActivityByIdRequest idReq = new CommunityActivityByIdRequest();
        idReq.setActivityId(addReq.getActivityId());
        idReq.setSaleRelFlag(Boolean.TRUE);
        CommunityActivityVO communityActivity = communityActivityQueryProvider.getById(idReq).getContext().getCommunityActivityVO();
        if(!communityActivityService.getAssistCanFlag(communityActivity, pointVO)){
            throw new SbcRuntimeException(CommonErrorCodeEnum.K999999, "很报歉，该活动无法转发帮卖");
        }
        addReq.setStoreId(communityActivity.getStoreId());
        addReq.setLeaderId(pointVO.getLeaderId());
        return communityAssistRecordProvider.add(addReq);
    }

    @Operation(summary = "根据活动id查询跟团记录表")
    @PostMapping("/tradePage")
    public BaseResponse<CommunityActivityTradePageSiteResponse> tradePage(@RequestBody @Valid LeaderTradeDetailPageRequest pageReq) {
        if (StringUtils.isBlank(pageReq.getCommunityActivityId())) {
            throw new SbcRuntimeException(CommonErrorCodeEnum.K000009);
        }
        communityActivityService.checkOpen();
        pageReq.setLeaderId(commonUtil.getLeader().getLeaderId());
        MicroServicePage<CommunitySimpleTradeVO> page = communityActivityTradeDetailService.getTradeList(pageReq);
        return BaseResponse.success(CommunityActivityTradePageSiteResponse.builder().communitySimpleTradePage(page).build());
    }
}
