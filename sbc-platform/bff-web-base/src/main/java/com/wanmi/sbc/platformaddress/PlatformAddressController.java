package com.wanmi.sbc.platformaddress;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.setting.api.provider.platformaddress.PlatformAddressQueryProvider;
import com.wanmi.sbc.setting.api.provider.systemconfig.SystemConfigQueryProvider;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressListRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressQueryRequest;
import com.wanmi.sbc.setting.api.request.platformaddress.PlatformAddressVerifyRequest;
import com.wanmi.sbc.setting.api.response.platformaddress.AddressJsonResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressListGroupResponse;
import com.wanmi.sbc.setting.api.response.platformaddress.PlatformAddressListResponse;
import com.wanmi.sbc.setting.bean.enums.AddrLevel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(description= "平台地址信息管理API", name = "PlatformAddressController")
@RestController
@Validated
@RequestMapping(value = "/platformaddress")
public class PlatformAddressController {

    @Autowired
    private PlatformAddressQueryProvider platformAddressQueryProvider;

    @Autowired
    private SystemConfigQueryProvider systemConfigQueryProvider;

    @Operation(summary = "省份列表")
    @GetMapping("/province")
    public BaseResponse<PlatformAddressListResponse> getProvince() {
        return platformAddressQueryProvider.list(PlatformAddressListRequest.builder().delFlag(DeleteFlag.NO)
                .addrLevel(AddrLevel.PROVINCE).leafFlag(true).build());
    }

    @Operation(summary = "城市列表")
    @Parameter(name = "addrId", description = "省份地址id", required = true)
    @GetMapping("/city/{addrId}")
    public BaseResponse<PlatformAddressListResponse> getCity(@PathVariable String addrId) {
        return platformAddressQueryProvider.list(PlatformAddressListRequest.builder().delFlag(DeleteFlag.NO)
                .addrLevel(AddrLevel.CITY).leafFlag(true).addrParentId(addrId).build());
    }

    @Operation(summary = "区县列表")
    @Parameter(name = "addrId", description = "城市地址id", required = true)
    @GetMapping("/district/{addrId}")
    public BaseResponse<PlatformAddressListResponse> getDistrict(@PathVariable String addrId) {
        return platformAddressQueryProvider.list(PlatformAddressListRequest.builder().delFlag(DeleteFlag.NO)
                .addrLevel(AddrLevel.DISTRICT).leafFlag(true).addrParentId(addrId).build());
    }

    @Operation(summary = "乡镇街道列表")
    @Parameter(name = "addrId", description = "区县地址id", required = true)
    @GetMapping("/street/{addrId}")
    public BaseResponse<PlatformAddressListResponse> getStreet(@PathVariable String addrId) {
        return platformAddressQueryProvider.list(PlatformAddressListRequest.builder().delFlag(DeleteFlag.NO)
                .addrLevel(AddrLevel.STREET).leafFlag(true).addrParentId(addrId).build());
    }

    @Operation(summary = "列表查询省市区")
    @PostMapping("/listByIds")
    public BaseResponse<PlatformAddressListResponse> listByIds(@RequestBody PlatformAddressListRequest request) {
        //没有任何id的情况下直接返回空集
        if(CollectionUtils.isEmpty(request.getAddrIdList()) && CollectionUtils.isEmpty(request.getAddrParentIdList())){
            return BaseResponse.success(PlatformAddressListResponse.builder().platformAddressVOList(Collections.emptyList()).build());
        }
        request.setDelFlag(DeleteFlag.NO);
        return platformAddressQueryProvider.list(request);
    }

    @Operation(summary = "仅查询省市")
    @GetMapping("/listLimitCity")
    public BaseResponse<PlatformAddressListResponse> listByArea() {
        return platformAddressQueryProvider.list(PlatformAddressListRequest.builder().delFlag(DeleteFlag.NO)
                .addrLevels(Arrays.asList(AddrLevel.PROVINCE, AddrLevel.CITY)).build());
    }

    @Operation(summary = "校验是否需要完善地址,true表示需要完善，false表示不需要完善")
    @PostMapping("/verifyAddress")
    public BaseResponse<Boolean> verifyAddress(@RequestBody PlatformAddressVerifyRequest request) {
        return platformAddressQueryProvider.verifyAddress(request);
    }

    @Operation(summary = "初始化地址信息")
    @GetMapping("/initAddressJson")
    public BaseResponse<AddressJsonResponse> initAddressJson(){
        PlatformAddressListRequest platformAddressListReq = new PlatformAddressListRequest();
        return platformAddressQueryProvider.addressJsonList(platformAddressListReq);
    }

    @Operation(summary = "列表查询城市-首字母聚合")
    @PostMapping("/city/list/group")
    public BaseResponse<PlatformAddressListGroupResponse> cityListGroup() {
        return platformAddressQueryProvider.listGroupByPinYin(
                PlatformAddressQueryRequest.builder().delFlag(DeleteFlag.NO).addrLevel(AddrLevel.CITY).build());
    }

    @Operation(summary = "热门城市")
    @GetMapping("/popular/list")
    public BaseResponse<PlatformAddressListResponse> popularCityList() {
        String hotCityStr = systemConfigQueryProvider.findContextByConfigTypeAndConfigKey().getContext().getContext();
        //Json转化
        List<JSONObject> hotCityJsonList = JSON.parseArray(hotCityStr, JSONObject.class);
        List<String> cityNameList = new ArrayList<>();
        Map<String,Integer> citySortMap = new HashMap<>();
        //遍历
        if(CollectionUtils.isNotEmpty(hotCityJsonList)){
            hotCityJsonList.forEach(a -> {
                cityNameList.add(a.get("cityName").toString());
                citySortMap.put(a.get("cityName").toString(),Integer.valueOf(a.get("sort").toString()));
            });
        }
        //查询热门城市的List
        PlatformAddressListResponse response = platformAddressQueryProvider.list(PlatformAddressListRequest.builder().cityNameList(cityNameList).addrLevel(AddrLevel.CITY).build()).getContext();
        if (Objects.nonNull(response)) {
           response.getPlatformAddressVOList().forEach(platformAddressVO->{
                platformAddressVO.setSortNo(citySortMap.get(platformAddressVO.getAddrName()));
            });
        }

        return BaseResponse.success(response);
    }

    @Operation(summary = "城市列表")
    @Parameter(name = "addrId", description = "省份地址id", required = true)
    @PostMapping("/address")
    public BaseResponse<PlatformAddressListGroupResponse> getAddress(@RequestBody PlatformAddressQueryRequest request) {
        //AddrLevel: 0：省  1：市  2：县区  3：街道
        //查省信息，addrId = 0,AddrLevel=0
        //查市信息：addrId：省id,AddrLevel=1
        //查区信息：addrId：市id,AddrLevel=2
        //查街道信息：addrId：区id,AddrLevel=3
        request.setDelFlag(DeleteFlag.NO);
//        request.setAddrParentId(addrId);
//        request.setAddrLevel(AddrLevel.PROVINCE);
        return platformAddressQueryProvider.listGroupByPinYin(request);
    }



}
