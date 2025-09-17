package com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>手动推荐商品管理列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 手动推荐商品管理列表结果
     */
    @Schema(description = "手动推荐商品管理列表结果")
    private List<ManualRecommendGoodsVO> manualRecommendGoodsVOList;
}
