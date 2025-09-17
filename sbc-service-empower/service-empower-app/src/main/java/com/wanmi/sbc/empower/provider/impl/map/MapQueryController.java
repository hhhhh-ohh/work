package com.wanmi.sbc.empower.provider.impl.map;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.MapType;
import com.wanmi.sbc.empower.api.provider.map.MapQueryProvider;
import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.request.map.GetPoiRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.empower.api.response.map.PoiListResponse;
import com.wanmi.sbc.empower.map.base.MapBaseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @className MapQueryController
 * @description 地图服务
 * @author 张文昌
 * @date 2021/7/15 16:08
 */
@RestController
public class MapQueryController implements MapQueryProvider {

    @Autowired
    private Map<String, MapBaseServiceInterface> mapBaseServiceInterfaceMap;

    /**
     * @description 搜索POI信息列表
     * @author 张文昌
     * @date 2021/7/15 16:08
     * @param request
     * @return
     */
    @Override
    public BaseResponse<PoiListResponse> getPoi(@RequestBody GetPoiRequest request) {
        MapBaseServiceInterface mapBaseServiceInterface =
                mapBaseServiceInterfaceMap.get(request.getMapType().getDesc());
        return BaseResponse.success(mapBaseServiceInterface.getPoi(request));
    }

    /**
     * @description 根据经纬度反查地址
     * @author 张文昌
     * @date 2021/7/23 13:48
     * @param request
     * @return
     */
    @Override
    public BaseResponse<GetAddressByLocationResponse> getAddressByLocation(
            @RequestBody GetAddressByLocationRequest request) {
        MapBaseServiceInterface mapBaseServiceInterface =
                mapBaseServiceInterfaceMap.get(request.getMapType().getDesc());
        return BaseResponse.success(mapBaseServiceInterface.getAddressByLocation(request));
    }

    /**
     * 搜索周边poi
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse<PoiListResponse> getAroundPoi(@RequestBody GetPoiRequest request) {
        MapBaseServiceInterface mapBaseServiceInterface =
                mapBaseServiceInterfaceMap.get(request.getMapType().getDesc());
        return BaseResponse.success(mapBaseServiceInterface.getAroundPoi(request));
    }

    @Override
    public BaseResponse initGaoDeDistrict() {
        MapBaseServiceInterface mapBaseServiceInterface =
                mapBaseServiceInterfaceMap.get(MapType.GAO_DE.getDesc());
        mapBaseServiceInterface.initGaoDeDistrict();
        return BaseResponse.SUCCESSFUL();
    }
}
