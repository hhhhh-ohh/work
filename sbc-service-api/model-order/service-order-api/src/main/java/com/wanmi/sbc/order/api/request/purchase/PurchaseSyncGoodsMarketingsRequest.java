package com.wanmi.sbc.order.api.request.purchase;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.marketing.bean.vo.MarketingViewVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * author: sunkun
 * Date: 2018-11-30
 */
@Data
@Schema
public class PurchaseSyncGoodsMarketingsRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "营销信息")
    private Map<String, List<MarketingViewVO>> goodsMarketingMap;

    @Schema(description = "客户id")
    private String customerId;
}
