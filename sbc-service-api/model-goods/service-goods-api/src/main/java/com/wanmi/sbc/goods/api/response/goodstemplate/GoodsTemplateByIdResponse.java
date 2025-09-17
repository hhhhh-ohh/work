package com.wanmi.sbc.goods.api.response.goodstemplate;

import com.wanmi.sbc.goods.bean.vo.GoodsTemplateVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>根据id查询任意（包含已删除）GoodsTemplate信息response</p>
 * @author 黄昭
 * @date 2022-09-29 14:06:41
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsTemplateByIdResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * GoodsTemplate信息
     */
    @Schema(description = "GoodsTemplate信息")
    private GoodsTemplateVO goodsTemplateVO;
}
