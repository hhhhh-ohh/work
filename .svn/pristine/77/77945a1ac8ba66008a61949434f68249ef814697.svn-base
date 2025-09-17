package com.wanmi.sbc.empower.api.provider.map;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.empower.api.request.map.GetAddressByLocationRequest;
import com.wanmi.sbc.empower.api.request.map.GetPoiRequest;
import com.wanmi.sbc.empower.api.response.map.GetAddressByLocationResponse;
import com.wanmi.sbc.empower.api.response.map.PoiListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @className MapQueryProvider
 * @description 地图服务
 * @author    张文昌
 * @date      2021/7/15 15:15
 */
@FeignClient(value = "${application.empower.name}", contextId = "MapQueryProvider")
public interface MapQueryProvider {

    /**
     * @description 根据关键字搜索poi
     * @author 张文昌
     * @date   2021/7/15 15:19
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/map/getPoi")
    BaseResponse<PoiListResponse> getPoi(@RequestBody GetPoiRequest request);

    /**
     * @description 根据关键字搜索地址
     * @author 张文昌
     * @date   2021/7/15 15:19
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/map/get-address-by-ocation")
    BaseResponse<GetAddressByLocationResponse> getAddressByLocation(@RequestBody GetAddressByLocationRequest request);

    /**
     * @description 搜索周边poi
     * @author 张文昌
     * @date   2021/7/27 15:19
     * @param request
     * @return
     */
    @PostMapping("/empower/${application.empower.version}/map/get-around-poi")
    BaseResponse<PoiListResponse> getAroundPoi(@RequestBody GetPoiRequest request);

    @PostMapping("/empower/${application.empower.version}/map/init-gaode-district")
    BaseResponse initGaoDeDistrict();
}
