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
 * @description
 * @author    张文昌
 * @date      2021/7/15 11:43
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PoiListResponse implements Serializable {

    private static final long serialVersionUID = -8244616964537236818L;

    /**
     * 搜索POI信息列表
     */
    @Schema(description = "搜索POI信息列表")
    List<PoiVO> pois;
}
