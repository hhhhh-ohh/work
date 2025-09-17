package com.wanmi.sbc.crm.api.response.recommendgoodsmanage;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.RecommendGoodsManageVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）商品推荐管理信息response</p>
 * @author lvzhenwei
 * @date 2020-11-18 14:07:44
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendGoodsManageByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品推荐管理信息
     */
    @Schema(description = "商品推荐管理信息")
    private RecommendGoodsManageVO recommendGoodsManageVO;
}
