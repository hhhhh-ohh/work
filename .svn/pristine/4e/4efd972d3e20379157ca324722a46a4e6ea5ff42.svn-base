package com.wanmi.sbc.crm.api.response.manualrecommendgoods;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.crm.bean.vo.ManualRecommendGoodsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）手动推荐商品管理信息response</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsByIdResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 手动推荐商品管理信息
     */
    @Schema(description = "手动推荐商品管理信息")
    private ManualRecommendGoodsVO manualRecommendGoodsVO;
}
