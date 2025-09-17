package com.wanmi.sbc.marketing.api.response.marketingsuits;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingSuitsVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>组合商品主表列表结果</p>
 * @author zhk
 * @date 2020-04-01 20:54:00
 */
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingSuitsListResponse extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 组合商品主表列表结果
     */
    @Schema(description = "组合商品主表列表结果")
    private List<MarketingSuitsVO> marketingSuitsVOList;
}
