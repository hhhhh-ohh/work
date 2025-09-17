package com.wanmi.sbc.map;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.provider.map.MapQueryProvider;
import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.request.map.GetPoiRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.empower.api.response.map.PoiListResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
/**
 * @className MapController
 * @description 地图服务
 * @author    张文昌
 * @date      2021/7/15 16:09
 */
@Tag(name = "MapController", description = "地图服务 API")
@RestController
@RequestMapping("/map")
@Validated
public class MapController {

    @Autowired
    private MapQueryProvider mapQueryProvider;

    /**
     * 获取搜索POI信息列表
     * @return
     */
    @Operation(summary = "搜索POI信息列表")
    @RequestMapping(value = "/poiList", method = RequestMethod.POST)
    public BaseResponse<PoiListResponse> poiList(@RequestBody GetPoiRequest request){
        return mapQueryProvider.getPoi(request);
    }

    /**
     * 根据经纬度反查地址
     * @return
     */
    @Operation(summary = "根据经纬度反查地址")
    @RequestMapping(value = "/address-by-location", method = RequestMethod.POST)
    public BaseResponse<GetAddressByLocationResponse> poiListByLocation(@RequestBody GetAddressByLocationRequest request){
        return mapQueryProvider.getAddressByLocation(request);
    }

    /**
     * 获取搜索周边POI信息列表
     * @return
     */
    @Operation(summary = "搜索周边POI信息列表")
    @RequestMapping(value = "/aroundPoiList", method = RequestMethod.POST)
    public BaseResponse<PoiListResponse> aroundPoiList(@RequestBody GetPoiRequest request){
        return mapQueryProvider.getAroundPoi(request);
    }
}
