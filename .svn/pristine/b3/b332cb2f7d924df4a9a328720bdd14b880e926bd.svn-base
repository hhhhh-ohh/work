package com.wanmi.sbc.goods.api.response.goodsrestrictedsale;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsRestrictedSaleVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>限售配置列表结果</p>
 * @author baijz
 * @date 2020-04-08 11:20:28
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsRestrictedSaleListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 限售配置列表结果
     */
    @Schema(description = "限售配置列表结果")
    private List<GoodsRestrictedSaleVO> goodsRestrictedSaleVOList;
}
