package com.wanmi.sbc.goods.api.response.goodstemplate;

import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsTemplateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>GoodsTemplate分页结果</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplatePageResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * GoodsTemplate分页结果
     */
    @Schema(description = "GoodsTemplate分页结果")
    private MicroServicePage<GoodsTemplateVO> goodsTemplateVOPage;
}
