package com.wanmi.sbc.vas.api.response.recommend.manualrecommendgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.vas.bean.vo.recommend.ManualRecommendGoodsVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>手动推荐商品管理新增结果</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的手动推荐商品管理信息
     */
    @Schema(description = "已新增的手动推荐商品管理信息")
    private ManualRecommendGoodsVO manualRecommendGoodsVO;
}
