package com.wanmi.sbc.order.api.response.purchase;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsInfoVO;
import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-12-06
 */
@Data
@Schema
public class PurchaseGetGoodsMarketingResponse extends BasicResponse {

    private static final long serialVersionUID = 1L;

    @Schema(description = "单品营销信息map，key为单品id，value为营销列表")
    private Map<String, List<MarketingViewVO>> map;

    @Schema(description = "单品信息列表")
    private List<GoodsInfoVO> goodsInfos;
}
