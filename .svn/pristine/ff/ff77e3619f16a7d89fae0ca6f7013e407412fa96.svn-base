package com.wanmi.sbc.goods.api.response.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置分页结果</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSalePageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 限售配置分页结果
     */
    @Schema(description = "限售配置分页结果")
    private MicroServicePage<GoodsRestrictedSaleVO> goodsRestrictedSaleVOPage;
}
