package com.wanmi.sbc.marketing.api.response.discount;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullDiscountLevelVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p></p>
 * Date: 2018-11-20
 * @author Administrator
 */
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketingFullDiscountByMarketingIdResponse extends BasicResponse {

    private static final long serialVersionUID = 344234160041244459L;

    @Schema(description = "营销满折多级优惠列表")
    private List<MarketingFullDiscountLevelVO> marketingFullDiscountLevelVOList;
}
