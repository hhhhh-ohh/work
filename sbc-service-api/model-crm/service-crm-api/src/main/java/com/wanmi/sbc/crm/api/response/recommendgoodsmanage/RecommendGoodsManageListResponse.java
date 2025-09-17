package com.wanmi.sbc.crm.api.response.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RecommendGoodsManageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>商品推荐管理列表结果</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品推荐管理列表结果
     */
    @Schema(description = "商品推荐管理列表结果")
    private List<RecommendGoodsManageVO> recommendGoodsManageVOList;
}
