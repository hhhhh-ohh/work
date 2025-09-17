package com.wanmi.sbc.vas.api.response.commodityscoringalgorithm;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.CommodityScoringAlgorithmVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>商品评分算法新增结果</p>
 * @author Bob
 * @date 2021-03-03 14:27:46
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityScoringAlgorithmAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商品评分算法信息
     */
    @Schema(description = "已新增的商品评分算法信息")
    private CommodityScoringAlgorithmVO commodityScoringAlgorithmVO;
}
