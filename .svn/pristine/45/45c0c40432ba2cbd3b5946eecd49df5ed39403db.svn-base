package com.wanmi.sbc.crm.api.response.goodsrelatedrecommend;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.GoodsRelatedRecommendVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品相关性推荐新增结果</p>
 * @author lvzhenwei
 * @date 2020-11-24 16:13:10
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRelatedRecommendAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的商品相关性推荐信息
     */
    @Schema(description = "已新增的商品相关性推荐信息")
    private GoodsRelatedRecommendVO goodsRelatedRecommendVO;
}
