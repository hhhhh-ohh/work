package com.wanmi.sbc.marketing.api.response.marketingsuitssku;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsSkuVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合活动关联商品sku表新增结果</p>
 * @author zhk
 * @date 2020-04-02 10:51:12
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsSkuAddResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 已新增的组合活动关联商品sku表信息
     */
    @Schema(description = "已新增的组合活动关联商品sku表信息")
    private MarketingSuitsSkuVO marketingSuitsSkuVO;
}
