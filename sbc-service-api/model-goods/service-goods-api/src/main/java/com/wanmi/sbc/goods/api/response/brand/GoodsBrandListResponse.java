package com.wanmi.sbc.goods.api.response.brand;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandCenterVO;
import com.wanmi.sbc.goods.bean.vo.GoodsBrandVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 品牌查询响应
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsBrandListResponse extends BasicResponse {

    private static final long serialVersionUID = -5756627286521403028L;

    /**
     * 品牌列表
     */
    @Schema(description = "品牌列表")
    private List<GoodsBrandVO> goodsBrandVOList;

    /**
     * 品牌列表分组
     */
    @Schema(description = "品牌列表分组")
    private Map<String,List<GoodsBrandCenterVO>> goodsBrandVOListMap;
}
