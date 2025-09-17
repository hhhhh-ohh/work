package com.wanmi.sbc.marketing.provider.impl.communityregionsetting;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.marketing.api.provider.communityregionsetting.CommunityRegionSettingQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityregionsetting.*;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingByIdResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingListResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingPageResponse;
import com.wanmi.sbc.marketing.api.response.communityregionsetting.CommunityRegionSettingUsedAreaResponse;
import com.wanmi.sbc.marketing.bean.vo.CommunityRegionSettingVO;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionAreaSetting;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionLeaderSetting;
import com.wanmi.sbc.marketing.communityregionsetting.model.root.CommunityRegionSetting;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionAreaSettingService;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionLeaderSettingService;
import com.wanmi.sbc.marketing.communityregionsetting.service.CommunityRegionSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>社区拼团区域设置表查询服务接口实现</p>
 *
 * @author dyt
 * @date 2023-07-20 14:19:23
 */
@RestController
@Validated
public class CommunityRegionSettingQueryController implements CommunityRegionSettingQueryProvider {
    @Autowired
    private CommunityRegionSettingService communityRegionSettingService;

    @Autowired
    private CommunityRegionLeaderSettingService communityRegionLeaderSettingService;

    @Autowired
    private CommunityRegionAreaSettingService communityRegionAreaSettingService;

    @Override
    public BaseResponse<CommunityRegionSettingPageResponse> page(@RequestBody @Valid CommunityRegionSettingPageRequest communityRegionSettingPageReq) {
        CommunityRegionSettingQueryRequest queryReq = KsBeanUtil.convert(communityRegionSettingPageReq, CommunityRegionSettingQueryRequest.class);
        Page<CommunityRegionSetting> communityRegionSettingPage = communityRegionSettingService.page(queryReq);
        Page<CommunityRegionSettingVO> newPage = communityRegionSettingPage.map(entity -> communityRegionSettingService.wrapperVo(entity));
        communityRegionAreaSettingService.fillRegion(newPage.getContent());
        communityRegionLeaderSettingService.fillRegion(newPage.getContent());
        MicroServicePage<CommunityRegionSettingVO> microPage = new MicroServicePage<>(newPage, communityRegionSettingPageReq.getPageable());
        CommunityRegionSettingPageResponse finalRes = new CommunityRegionSettingPageResponse(microPage);
        return BaseResponse.success(finalRes);
    }

    @Override
    public BaseResponse<CommunityRegionSettingListResponse> list(@RequestBody @Valid CommunityRegionSettingListRequest communityRegionSettingListReq) {
        CommunityRegionSettingQueryRequest queryReq = KsBeanUtil.convert(communityRegionSettingListReq, CommunityRegionSettingQueryRequest.class);
        List<CommunityRegionSetting> communityRegionSettingList = communityRegionSettingService.list(queryReq);
        List<CommunityRegionSettingVO> newList = communityRegionSettingList.stream().map(entity -> communityRegionSettingService.wrapperVo(entity)).collect(Collectors.toList());
        communityRegionAreaSettingService.fillRegion(newList);
        communityRegionLeaderSettingService.fillRegion(newList);
        return BaseResponse.success(new CommunityRegionSettingListResponse(newList));
    }

    @Override
    public BaseResponse<CommunityRegionSettingByIdResponse> getById(@RequestBody @Valid CommunityRegionSettingByIdRequest communityRegionSettingByIdRequest) {
        CommunityRegionSetting communityRegionSetting =
                communityRegionSettingService.getOne(communityRegionSettingByIdRequest.getRegionId(), communityRegionSettingByIdRequest.getStoreId());
        CommunityRegionSettingVO vo = communityRegionSettingService.wrapperVo(communityRegionSetting);
        List<CommunityRegionSettingVO> vos = Collections.singletonList(vo);
        communityRegionAreaSettingService.fillRegion(vos);
        communityRegionLeaderSettingService.fillRegion(vos);
        return BaseResponse.success(new CommunityRegionSettingByIdResponse(vos.get(0)));
    }

    @Override
    public BaseResponse<CommunityRegionSettingUsedAreaResponse> getUsedArea(@RequestBody @Valid CommunityRegionSettingUsedAreaRequest request) {
        CommunityRegionSettingUsedAreaResponse response = new CommunityRegionSettingUsedAreaResponse();
        //查询已被使用的团长/自提点
        CommunityRegionLeaderSettingQueryRequest leaderQueryRequest = CommunityRegionLeaderSettingQueryRequest.builder()
                .notRegionId(request.getRegionId()).storeId(request.getStoreId()).build();
        List<String> pickupPointIds = communityRegionLeaderSettingService.list(leaderQueryRequest).stream()
                .map(CommunityRegionLeaderSetting::getPickupPointId).collect(Collectors.toList());
        //查询已被使用的地区
        CommunityRegionAreaSettingQueryRequest areaRequest = CommunityRegionAreaSettingQueryRequest.builder()
                .notRegionId(request.getRegionId()).storeId(request.getStoreId()).build();
        List<String> areaIds = communityRegionAreaSettingService.list(areaRequest).stream().map(CommunityRegionAreaSetting::getAreaId).collect(Collectors.toList());
        response.setPickupPointIdList(pickupPointIds);
        response.setAreaIds(areaIds);
        return BaseResponse.success(response);
    }

}

