package com.wanmi.sbc.empower.api.request.map;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.MapType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

/**
 * @className GetPoiRequest
 * @description 查询poi列表
 * @author    张文昌
 * @date      2021/7/22 17:31
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPoiRequest implements Serializable {

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
     * 查询关键字, 规则： 多个关键字用“|”分割
     *
     * <p>若不指定city，并且搜索的为泛词（例如“美食”）的情况下，返回的内容为城市列表以及此城市内有多少结果符合要求。
     */
    @Schema(description = "查询关键字，多个关键字用“|”分割(查询周边poi时可以不传)", required =
            true)
    private String keywords;

    /**
     * 查询POI类型，当您的keywords和types都是空时，默认指定types为120000（商务住宅）&150000（交通设施服务）
     */
    @Schema(description = "查询POI类型", hidden = true)
    private String types;

    /**
     * 查询城市
     */
    @Schema(description = "查询城市(查询周边poi时可以不传)", required = true)
    private String city;

    /**
     * 仅返回指定城市数据，可选值：true/false
     */
    @Schema(description = "仅返回指定城市数据", hidden = true)
    private Boolean citylimit;

    /**
     * 是否按照层级展示子POI数据
     */
    @Schema(description = "仅返回指定城市数据", hidden = true)
    private Integer children = 1;

    /**
     * 每页记录数据
     */
    @Schema(description = "每页记录数据", hidden = true)
    private Integer offset = 20;

    /**
     * 当前页数
     */
    @Schema(description = "当前页数", hidden = true)
    private Integer page = 1;

    /**
     * 返回结果控制，此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息
     */
    @Schema(description = "返回结果控制", hidden = true)
    private String extensions = "all";

    /**
     * 中心点坐标
     */
    @Schema(description = "中心点坐标，规则： 经度和纬度用\",\"分割，经度在前，纬度在后，经纬度小数点后不得超过6位(查询周边poi必传)")
    private String location;

    /**
     * 查询半径，取值范围:0-50000。规则：大于50000按默认值，单位：米
     *
     */
    @Schema(description = "查询半径,取值范围:0-50000。规则：大于50000按默认值(查询周边poi时用)")
    private String radius;
}
