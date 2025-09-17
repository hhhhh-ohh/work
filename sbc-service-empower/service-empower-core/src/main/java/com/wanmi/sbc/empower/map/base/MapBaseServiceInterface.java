package com.wanmi.sbc.empower.map.base;

import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.request.map.GetPoiRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.empower.api.response.map.PoiListResponse;
/**
 * @className MapBaseServiceInterface
 * @description 地图接口
 * @author    张文昌
 * @date      2021/7/27 14:27
 */
public interface MapBaseServiceInterface {

    /**
     * 查询poi列表
     * @param mapBaseRequest
     * @return
     */
    PoiListResponse getPoi(GetPoiRequest mapBaseRequest);

    /**
     * 根据经纬度反查地址
     * @param getAddressByLocationRequest
     * @return
     */
    GetAddressByLocationResponse getAddressByLocation(GetAddressByLocationRequest getAddressByLocationRequest);

    /**
     * 查询周边POI
     * @param mapBaseRequest
     * @return
     */
    PoiListResponse getAroundPoi(GetPoiRequest mapBaseRequest);

    /**
     * 高德地址初始化
     * @return
     */
    void initGaoDeDistrict();
}
