package com.wanmi.sbc.vas.api.response.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.CommodityScoringAlgorithmVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>商品评分算法列表结果</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityScoringAlgorithmListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品评分算法列表结果
     */
    @Schema(description = "商品评分算法列表结果")
    private List<CommodityScoringAlgorithmVO> commodityScoringAlgorithmVOList;

    /**
     * 类目偏好标签
     */
    @Schema(description = "类目偏好标签")
    private Long cateTagId;

    /**
     * 品类偏好标签
     */
    @Schema(description = "品类偏好标签")
    private Long topCateTagId;

    /**
     * 品牌偏好标签
     */
    @Schema(description = "品牌偏好标签")
    private Long brandTagId;

    /**
     * 店铺偏好标签
     */
    @Schema(description = "店铺偏好标签")
    private Long storeTagId;
}
