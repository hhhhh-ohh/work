package com.wanmi.sbc.pickupsetting;

import com.google.common.collect.Maps;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.*;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.common.util.GeoHashUtils;
import com.wanmi.sbc.customer.api.provider.communitypickup.CommunityLeaderPickupPointQueryProvider;
import com.wanmi.sbc.customer.api.provider.store.StoreQueryProvider;
import com.wanmi.sbc.customer.api.request.communitypickup.CommunityLeaderPickupPointListRequest;
import com.wanmi.sbc.customer.api.request.store.ListNoDeleteStoreByIdsRequest;
import com.wanmi.sbc.customer.api.request.store.StoreBaseInfoByIdRequest;
import com.wanmi.sbc.customer.api.response.communitypickup.CommunityLeaderPickupPointListResponse;
import com.wanmi.sbc.customer.api.response.store.ListNoDeleteStoreByIdsResponse;
import com.wanmi.sbc.customer.api.response.store.StoreBaseResponse;
import com.wanmi.sbc.customer.bean.enums.LeaderCheckStatus;
import com.wanmi.sbc.customer.bean.vo.CommunityLeaderPickupPointVO;
import com.wanmi.sbc.customer.bean.vo.StoreVO;
import com.wanmi.sbc.marketing.api.provider.communityactivity.CommunityActivityQueryProvider;
import com.wanmi.sbc.marketing.api.request.communityactivity.CommunityActivityByIdRequest;
import com.wanmi.sbc.marketing.bean.enums.CommunityLeaderRangeType;
import com.wanmi.sbc.marketing.bean.enums.CommunityLogisticsType;
import com.wanmi.sbc.marketing.bean.enums.CommunitySalesRangeType;
import com.wanmi.sbc.marketing.bean.vo.CommunityActivityVO;
import com.wanmi.sbc.pickupsetting.request.CommunityPickupIckListReq;
import com.wanmi.sbc.pickupsetting.request.PickupSettingBatchQueryStateRequest;
import com.wanmi.sbc.pickupsetting.response.CommunityPickupIckListResp;
import com.wanmi.sbc.setting.api.provider.pickupsetting.PickupSettingQueryProvider;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.request.pickupsetting.PickupSettingListRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingConfigResponse;
import com.wanmi.sbc.setting.api.response.pickupsetting.PickupSettingListResponse;
import com.wanmi.sbc.setting.bean.vo.PickupSettingVO;
import com.wanmi.sbc.setting.bean.vo.PlatformAddressVO;
import com.wanmi.sbc.util.CommonUtil;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Tag(name =  "pickup_setting管理API", description =  "PickupSettingController")
@RestController
@Validated
@RequestMapping(value = "/pickup")
public class PickupSettingController {

    @Autowired
    private PickupSettingQueryProvider pickupSettingQueryProvider;

    @Autowired
    private StoreQueryProvider storeQueryProvider;

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired private CommonUtil commonUtil;

    @Autowired
    private CommunityLeaderPickupPointQueryProvider communityLeaderPickupPointQueryProvider;

    @Autowired
    private CommunityActivityQueryProvider communityActivityQueryProvider;

    @Operation(summary = "查询是否配置自提")
    @GetMapping("/state")
    public BaseResponse pickupSettingConfigShow(StoreBaseInfoByIdRequest storeBaseInfoByIdRequest) {
        // 查询boss端是否配置自提
        PickupSettingConfigResponse response = pickupSettingQueryProvider.pickupSettingConfigShow().getContext();
        // 查询supplier端是否配置自提
        StoreBaseResponse storeBaseResponse = storeQueryProvider.getStoreBaseInfoById(storeBaseInfoByIdRequest).getContext();
        // 查询boss端是否配置自提自营商家开关
        if (Objects.nonNull(storeBaseResponse.getCompanyType()) && 0 == storeBaseResponse.getCompanyType()){
            Integer selfMerchantStatus = response.getSelfMerchantStatus();
            if (Objects.isNull(selfMerchantStatus) || Constants.ONE != selfMerchantStatus){
                return BaseResponse.success(0);
            }
        }else {
            // 查询boss端是否配置自提第三方商家开关
            Integer thirdMerchantStatus = response.getThirdMerchantStatus();
            if (Objects.isNull(thirdMerchantStatus) || Constants.ONE != thirdMerchantStatus){
                return BaseResponse.success(0);
            }
        }

        if (Objects.nonNull(storeBaseResponse.getPickupState())) {
            return BaseResponse.success(storeBaseResponse.getPickupState());
        }
        return BaseResponse.success(0);
    }

    @Operation(summary = "查询是否配置自提批量查询")
    @PostMapping("/batchQueryState")
    public BaseResponse batchQueryStateConfigShow(@RequestBody @Valid PickupSettingBatchQueryStateRequest request) {
        //视频好不支持自提点
        if (request.getIsChannelsFlag() && commonUtil.findVASBuyOrNot(VASConstants.VAS_WECHAT_CHANNELS)) {
            return BaseResponse.SUCCESSFUL();
        }
        // 查询boss端是否配置自提
        PickupSettingConfigResponse response = pickupSettingQueryProvider.pickupSettingConfigShow().getContext();

        Map<Long, Integer> storeMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(request.getStoreIds())) {
            List<StoreVO> storeVOS =
                    storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest.builder().storeIds(request.getStoreIds()).build()).getContext().getStoreVOList();

            for (StoreVO storeVO : storeVOS) {
                if(storeVO.getStoreType() != StoreType.O2O){
                    // 查询boss端是否配置自提自营商家开关
                    if (Objects.nonNull(storeVO.getCompanyType()) && BoolFlag.NO == storeVO.getCompanyType()){
                        Integer selfMerchantStatus = response.getSelfMerchantStatus();
                        if (Objects.isNull(selfMerchantStatus) || Constants.ONE != selfMerchantStatus){
                            storeMap.put(storeVO.getStoreId(), 0);
                        }else {
                            storeMap.put(storeVO.getStoreId(), storeVO.getPickupStateVo());
                        }
                    }else {
                        // 查询boss端是否配置自提第三方商家开关
                        Integer thirdMerchantStatus = response.getThirdMerchantStatus();
                        if (Objects.isNull(thirdMerchantStatus) || Constants.ONE != thirdMerchantStatus){
                            storeMap.put(storeVO.getStoreId(), 0);
                        }else {
                            storeMap.put(storeVO.getStoreId(), storeVO.getPickupStateVo());
                        }
                    }
                }else {
                    Integer storeStatus = response.getStoreStatus();
                    if (Objects.isNull(storeStatus) || Constants.ONE != storeStatus){
                        storeMap.put(storeVO.getStoreId(), 0);
                    }else {
                        storeMap.put(storeVO.getStoreId(), storeVO.getPickupStateVo());
                    }
                }

            }
        }
        return BaseResponse.success(storeMap);
    }

    @Operation(summary = "列表查询")
    @PostMapping("/list")
    public BaseResponse<PickupSettingListResponse> getList(@RequestBody @Valid PickupSettingListRequest listReq) {
        listReq.setDelFlag(DeleteFlag.NO);
        listReq.putSort("isDefaultAddress", "desc");
        BaseResponse<PickupSettingListResponse> vo = pickupSettingQueryProvider.list(listReq);
        if (CollectionUtils.isNotEmpty(vo.getContext().getPickupSettingVOList())) {
            getInfo(vo.getContext().getPickupSettingVOList());
        }

        //计算自提点与收货地址的距离
        BigDecimal receivingLongitude = listReq.getReceivingLongitude();
        BigDecimal receivingLatitude = listReq.getReceivingLatitude();
        //查询高德地图设置
        Integer mapStatus = pickupSettingQueryProvider.getWhetherOpenMap().getContext().getMapStatus();
        if(Objects.nonNull(mapStatus) && mapStatus == 1 && receivingLongitude != null && receivingLatitude != null){
            //过滤出没有经纬度的自提点(不含默认自提点)
            List<PickupSettingVO> pickupSettingVOListWithoutLonOrLat = vo.getContext().getPickupSettingVOList().stream()
                    .filter(p -> (p.getLongitude() == null || p.getLatitude() == null) && p.getIsDefaultAddress() == 0).collect(Collectors.toList());
            //过滤出有经纬度自提点(不含默认自提点)，并计算出距离
            List<PickupSettingVO> pickupSettingVOListWithLonOrLat = vo.getContext().getPickupSettingVOList().stream()
                    .filter(p -> p.getLongitude() != null && p.getLatitude() != null && p.getIsDefaultAddress() == 0)
                    .peek(p ->{
                        double distance = GeoHashUtils.getDistance(receivingLongitude.doubleValue(), receivingLatitude.doubleValue(),
                                p.getLongitude().doubleValue(), p.getLatitude().doubleValue());
                        p.setDistance(Math.round(distance));
                    })
                    .sorted(Comparator.comparing(PickupSettingVO::getDistance)).collect(Collectors.toList());
            //过滤出默认自提点排第一位并且计算出距离
            List<PickupSettingVO> newPickupSettingVOList  = vo.getContext().getPickupSettingVOList().stream()
                    .filter(p -> p.getIsDefaultAddress() == 1)
                    .peek(p ->{
                        if(Objects.nonNull(p.getLongitude()) && Objects.nonNull(p.getLatitude())){
                            double distance = GeoHashUtils.getDistance(receivingLongitude.doubleValue(), receivingLatitude.doubleValue(),
                                    p.getLongitude().doubleValue(), p.getLatitude().doubleValue());
                            p.setDistance(Math.round(distance));
                        }
                    }).collect(Collectors.toList());
            newPickupSettingVOList.addAll(pickupSettingVOListWithLonOrLat);
            newPickupSettingVOList.addAll(pickupSettingVOListWithoutLonOrLat);
            vo.getContext().setPickupSettingVOList(newPickupSettingVOList);
        }
        return vo;
    }

    public void getInfo(List<PickupSettingVO> pickupSettingList) {

        if (CollectionUtils.isNotEmpty(pickupSettingList)) {
            List<String> addrIds = new ArrayList<>();
            pickupSettingList.forEach(detail -> {
                addrIds.add(Objects.toString(detail.getProvinceId()));
                addrIds.add(Objects.toString(detail.getCityId()));
                addrIds.add(Objects.toString(detail.getAreaId()));
                addrIds.add(Objects.toString(detail.getStreetId()));
            });
            //根据Id获取地址信息
            List<PlatformAddressVO> voList = platformAddressQueryProvider.list(PlatformAddressListRequest.builder()
                    .addrIdList(addrIds).build()).getContext().getPlatformAddressVOList();

            Map<String, String> addrMap = new HashMap<>();
            if (CollectionUtils.isNotEmpty(voList)) {
                addrMap = voList.stream().collect(Collectors.toMap(PlatformAddressVO::getAddrId, PlatformAddressVO::getAddrName));
            }

            BaseResponse<ListNoDeleteStoreByIdsResponse> response = storeQueryProvider.listNoDeleteStoreByIds(ListNoDeleteStoreByIdsRequest
                    .builder().storeIds(pickupSettingList.stream().map(PickupSettingVO::getStoreId).collect(Collectors.toList())).build());

            for (PickupSettingVO pickupSetting : pickupSettingList) {

                List<StoreVO> storeVOList = response.getContext().getStoreVOList();
                if (CollectionUtils.isNotEmpty(storeVOList)) {
                    Optional<StoreVO> optional = storeVOList.stream().filter(storeVO -> pickupSetting.getStoreId().equals(storeVO.getStoreId())).findFirst();
                    optional.ifPresent(storeVO -> pickupSetting.setStoreName(storeVO.getStoreName()));
                }

                AuditStatus auditStatus = AuditStatus.fromValue(pickupSetting.getAuditStatus());
                pickupSetting.setAuditStatusName(auditStatus.name());
                String provinceName = addrMap.get(Objects.toString(pickupSetting.getProvinceId()));
                String cityName = addrMap.get(Objects.toString(pickupSetting.getCityId()));
                String areaName = addrMap.get(Objects.toString(pickupSetting.getAreaId()));
                String streetName = addrMap.get(Objects.toString(pickupSetting.getStreetId()));
                pickupSetting.setAddress(provinceName + "/" + cityName + "/" + areaName + "/" + streetName);
                pickupSetting.setIsDefaultAddressName(DefaultFlag.fromValue(pickupSetting.getIsDefaultAddress()).name());
                pickupSetting.setProvinceName(provinceName);
                pickupSetting.setCityName(cityName);
                pickupSetting.setAreaName(areaName);
                pickupSetting.setStreetName(streetName);
            }
        }
    }

    @Operation(summary = "查询高德地图设置")
    @GetMapping("/map/config-show")
    public BaseResponse<PickupSettingConfigResponse> whetherOpenMap() {
        return pickupSettingQueryProvider.getWhetherOpenMap();
    }

    @Operation(summary = "查询社区团购自提点")
    @PostMapping("/community/list")
    public BaseResponse<CommunityPickupIckListResp> getCommunityPickupIckList(@RequestBody @Valid CommunityPickupIckListReq req) {
        CommunityActivityVO communityActivityVO =
                communityActivityQueryProvider.getById(CommunityActivityByIdRequest.builder()
                        .activityId(req.getActivityId()).saleRelFlag(Boolean.TRUE).build()).getContext().getCommunityActivityVO();

        if (!communityActivityVO.getLogisticsTypes().contains(CommunityLogisticsType.PICKUP))
            return BaseResponse.success(CommunityPickupIckListResp.builder().logisticsTypes(Collections.singletonList(CommunityLogisticsType.EXPRESS)).build());
        CommunityLeaderPickupPointListRequest communityLeaderPickupPointListRequest =
                CommunityLeaderPickupPointListRequest.builder().checkStatus(LeaderCheckStatus.CHECKED).build();
        //计算自提点与收货地址的距离
        BigDecimal receivingLongitude = req.getReceivingLongitude();
        BigDecimal receivingLatitude = req.getReceivingLatitude();
        if (Objects.isNull(req.getLeaderId())){
            if (CommunitySalesRangeType.AREA.equals(communityActivityVO.getSalesRange())){
                List<String> areas = communityActivityVO.getSalesRangeContext();
                List<CommunityLeaderPickupPointVO> communityLeaderPickupPointList =
                        communityLeaderPickupPointQueryProvider.list(communityLeaderPickupPointListRequest).getContext().getCommunityLeaderPickupPointList();
                if (CollectionUtils.isNotEmpty(communityLeaderPickupPointList)){
                    communityLeaderPickupPointList = communityLeaderPickupPointList.stream().filter(i ->
                            areas.contains(i.getPickupProvinceId().toString())
                                    || areas.contains(i.getPickupAreaId().toString())
                                    || areas.contains(i.getPickupCityId().toString())
                                    || areas.contains(i.getPickupStreetId().toString())).collect(Collectors.toList());
                    List<PickupSettingVO> pickupSettingVOList = this.pickupInfo(communityLeaderPickupPointList,
                            receivingLongitude, receivingLatitude);
                    return BaseResponse.success(CommunityPickupIckListResp.builder()
                            .pickupSettingVOList(pickupSettingVOList)
                            .logisticsTypes(communityActivityVO.getLogisticsTypes())
                            .build());
                }

            } if (CommunitySalesRangeType.CUSTOM.equals(communityActivityVO.getSalesRange())){
                List<String> ids = communityActivityVO.getSalesRangeContext();
                CommunityLeaderPickupPointListResponse response =
                        communityLeaderPickupPointQueryProvider.list(CommunityLeaderPickupPointListRequest.builder()
                                .checkStatus(LeaderCheckStatus.CHECKED)
                                .pickupPointIdList(ids).build())
                                .getContext();
                List<PickupSettingVO> pickupSettingVOList = this.pickupInfo(response.getCommunityLeaderPickupPointList(),
                        receivingLongitude, receivingLatitude);
                return BaseResponse.success(CommunityPickupIckListResp.builder()
                        .pickupSettingVOList(pickupSettingVOList)
                        .logisticsTypes(communityActivityVO.getLogisticsTypes())
                        .build());
            }
        } else {
            if (CommunityLeaderRangeType.AREA.equals(communityActivityVO.getLeaderRange())){
                List<String> areas = communityActivityVO.getLeaderRangeContext();
                List<CommunityLeaderPickupPointVO> communityLeaderPickupPointList =
                        communityLeaderPickupPointQueryProvider.list(CommunityLeaderPickupPointListRequest.builder()
                                .checkStatus(LeaderCheckStatus.CHECKED)
                                .leaderId(req.getLeaderId()).build()).getContext().getCommunityLeaderPickupPointList();
                if (CollectionUtils.isNotEmpty(communityLeaderPickupPointList)){
                    communityLeaderPickupPointList = communityLeaderPickupPointList.stream().filter(i ->
                            areas.contains(i.getPickupProvinceId().toString())
                                    || areas.contains(i.getPickupAreaId().toString())
                                    || areas.contains(i.getPickupCityId().toString())
                                    || areas.contains(i.getPickupStreetId().toString())).collect(Collectors.toList());
                    List<PickupSettingVO> pickupSettingVOList = this.pickupInfo(communityLeaderPickupPointList,
                            receivingLongitude, receivingLatitude);
                    return BaseResponse.success(CommunityPickupIckListResp.builder()
                            .pickupSettingVOList(pickupSettingVOList)
                            .logisticsTypes(communityActivityVO.getLogisticsTypes())
                            .build());
                }

            } if (CommunityLeaderRangeType.CUSTOM.equals(communityActivityVO.getLeaderRange())){
                List<String> ids = communityActivityVO.getLeaderRangeContext();
                CommunityLeaderPickupPointListResponse response =
                        communityLeaderPickupPointQueryProvider.list(CommunityLeaderPickupPointListRequest.builder()
                                .checkStatus(LeaderCheckStatus.CHECKED)
                                .pickupPointIdList(ids).leaderId(req.getLeaderId()).build())
                                .getContext();
                List<PickupSettingVO> pickupSettingVOList = this.pickupInfo(response.getCommunityLeaderPickupPointList(),
                        receivingLongitude, receivingLatitude);
                return BaseResponse.success(CommunityPickupIckListResp.builder()
                        .pickupSettingVOList(pickupSettingVOList)
                        .logisticsTypes(communityActivityVO.getLogisticsTypes())
                        .build());
            }
            communityLeaderPickupPointListRequest.setLeaderId(req.getLeaderId());
        }
        CommunityLeaderPickupPointListResponse response =
                communityLeaderPickupPointQueryProvider.list(communityLeaderPickupPointListRequest).getContext();
        List<PickupSettingVO> pickupSettingVOList = this.pickupInfo(response.getCommunityLeaderPickupPointList(),
                receivingLongitude, receivingLatitude);
        return BaseResponse.success(CommunityPickupIckListResp.builder()
                .pickupSettingVOList(pickupSettingVOList)
                .logisticsTypes(communityActivityVO.getLogisticsTypes())
                .build());
    }

    public List<PickupSettingVO> pickupInfo(List<CommunityLeaderPickupPointVO> communityLeaderPickupPointList,
                                   BigDecimal receivingLongitude,
                   BigDecimal receivingLatitude){
        //查询高德地图设置
        Integer mapStatus = pickupSettingQueryProvider.getWhetherOpenMap().getContext().getMapStatus();
        if(Objects.nonNull(mapStatus) && mapStatus == 1 && receivingLongitude != null && receivingLatitude != null){
            List<CommunityLeaderPickupPointVO> noDistance =
                    communityLeaderPickupPointList.stream()
                            .filter(g -> Objects.isNull(g.getLng()) || Objects.isNull(g.getLat()))
                            .collect(Collectors.toList());
            communityLeaderPickupPointList = communityLeaderPickupPointList.stream()
                    .filter(g -> Objects.nonNull(g.getLng()) && Objects.nonNull(g.getLat())).peek(p ->{
                        double distance = GeoHashUtils.getDistance(receivingLongitude.doubleValue(), receivingLatitude.doubleValue(),
                                p.getLng().doubleValue(), p.getLat().doubleValue());
                        p.setDistance(Math.round(distance));
                    }).sorted(Comparator.comparing(CommunityLeaderPickupPointVO::getDistance)).collect(Collectors.toList());
            communityLeaderPickupPointList.addAll(noDistance);
        }
        List<PickupSettingVO> pickupSettingVOList = new ArrayList<>();
        communityLeaderPickupPointList.forEach(i -> {
            PickupSettingVO pickupSettingVO = new PickupSettingVO();
            pickupSettingVO.setPickupPointId(i.getPickupPointId());
            pickupSettingVO.setName(i.getPickupPointName());
            pickupSettingVO.setPhone(i.getContactNumber());
            pickupSettingVO.setProvinceId(i.getPickupProvinceId());
            pickupSettingVO.setCityId(i.getPickupCityId());
            pickupSettingVO.setAreaId(i.getPickupAreaId());
            pickupSettingVO.setStreetId(i.getPickupStreetId());
            pickupSettingVO.setAddress(i.getAddress());
            pickupSettingVO.setPickupAddress(i.getFullAddress());
            pickupSettingVO.setRemark(i.getPickupTime());
            pickupSettingVO.setImageUrl(i.getPickupPhotos());
            pickupSettingVO.setLongitude(i.getLng());
            pickupSettingVO.setLatitude(i.getLat());
            pickupSettingVO.setEnableStatus(1);
            pickupSettingVO.setDistance(i.getDistance());
            pickupSettingVOList.add(pickupSettingVO);
        });
        return pickupSettingVOList;
    }
}
