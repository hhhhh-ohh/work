package com.wanmi.sbc.empower.api.response.map;

import com.wanmi.sbc.empower.bean.vo.PoiVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @className MapResponse
 * @description 经纬度反查地址信息
 * @author    张文昌
 * @date      2021/7/23 11:43
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAddressByLocationResponse implements Serializable {

    private static final long serialVersionUID = -8244616964537236818L;

    @Schema(description = "省名称")
    private String province;

    @Schema(description = "省份编码")
    private String pcode;

    @Schema(description = "城市名称")
    private String city;

    @Schema(description = "城市编码")
    private String citycode;

    @Schema(description = "所在区")
    private String district;

    @Schema(description = "区域编码")
    private String adcode;

    @Schema(description = "所在乡镇/街道")
    private String township;

    @Schema(description = "乡镇街道编码")
    private String towncode;

    @Schema(description = "结构化地址信息")
    private String formattedAddress;

    /**
     * POI信息列表
     */
    @Schema(description = "POI信息列表")
    List<PoiVO> pois;
}
