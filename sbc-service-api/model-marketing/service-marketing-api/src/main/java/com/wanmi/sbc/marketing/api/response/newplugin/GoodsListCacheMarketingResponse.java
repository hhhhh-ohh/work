package com.wanmi.sbc.marketing.api.response.newplugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import com.wanmi.sbc.marketing.bean.enums.MarketingPluginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsListCacheMarketingResponse
 * @description TODO
 * @date 2021/6/26 10:22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListCacheMarketingResponse extends BasicResponse {

    private static final long serialVersionUID = -3734558778559667422L;

    /**
     * 商品编号（SKU）
     */
    private Map<String, Map<MarketingPluginType, List<GoodsInfoMarketingCacheDTO>>> retMap;
}
