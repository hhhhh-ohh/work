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
 * <p>手动推荐商品管理修改结果</p>
 * @author lvzhenwei
 * @date 2020-11-23 10:51:47
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManualRecommendGoodsModifyResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已修改的手动推荐商品管理信息
     */
    @Schema(description = "已修改的手动推荐商品管理信息")
    private ManualRecommendGoodsVO manualRecommendGoodsVO;
}
