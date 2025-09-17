package com.wanmi.sbc.marketing.api.response.reduction;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.vo.MarketingFullReductionLevelVO;

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
public class MarketingFullReductionByMarketingIdResponse extends BasicResponse {

    private static final long serialVersionUID = 7788913689870500867L;

    @Schema(description = "满减营销级别列表")
    private List<MarketingFullReductionLevelVO> marketingFullReductionLevelVOList;
}
