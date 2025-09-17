package com.wanmi.sbc.marketing.api.response.marketingsuitssku;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.common.base.MicroServicePage;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合活动关联商品sku表分页结果</p>
 * @author zhk
 * @date 2020-04-02 10:51:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsSkuPageResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 组合活动关联商品sku表分页结果
     */
    @Schema(description = "组合活动关联商品sku表分页结果")
    private MicroServicePage<MarketingSuitsSkuVO> marketingSuitsSkuVOPage;
}
