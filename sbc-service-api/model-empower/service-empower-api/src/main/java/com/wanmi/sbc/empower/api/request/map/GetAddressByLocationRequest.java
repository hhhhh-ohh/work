package com.wanmi.sbc.empower.api.request.map;

import com.wanmi.sbc.common.enums.MapType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className GetAddressByLocationRequest
 * @description 根据经纬度反差地址
 * @author    张文昌
 * @date      2021/7/22 17:32
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressByLocationRequest implements Serializable {

    /**
     * 地图类型
     */
    @Schema(description = "地图类型", required = true)
    private MapType mapType;

    /**
     * 请求服务权限标识，用户在高德地图官网申请Web服务API类型KEY
     */
    @Schema(description = "请求服务权限标识", hidden = true)
    private String key;

    /**
     * 经纬度坐标，传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。如果需要解析多个经纬度的话，请用"|"进行间隔，并且将 batch 参数设置为
     * true，最多支持传入 20 对坐标点。每对点坐标之间用"|"分割。
     */
    @Schema(description = "请求服务权限标识")
    private String location;

    /**
     * 搜索半径，radius取值范围在0~3000，默认是1000。单位：米
     *
     */
    @Schema(description = "搜索半径")
    private String radius;

    /**
     * 返回附近POI类型
     */
    @Schema(description = "查询POI类型", hidden = true)
    private String poitype;


    /**
     * 返回结果控制，此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息
     */
    @Schema(description = "返回结果控制", hidden = true)
    private String extensions = "all";

    /**
     * batch=true为批量查询。batch=false为单点查询
     */
    @Schema(description = "batch=true为批量查询。batch=false为单点查询", hidden = true)
    private Boolean batch = Boolean.FALSE;
}
