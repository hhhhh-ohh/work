package com.wanmi.sbc.goods.api.request.marketing;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.goods.bean.vo.GoodsMarketingVO;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhanggaolei
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsMarketingSyncRequest extends BaseRequest {

    private static final long serialVersionUID = 0;

    /**
     * 商品营销实体
     */
    @Schema(description = "商品营销实体")
    private List<GoodsMarketingVO> goodsMarketings;
    
    private List<String> delSkuIds;

    @NotNull
    private String customerId;
}
