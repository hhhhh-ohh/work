package com.wanmi.sbc.marketing.api.response.newplugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelDetailVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsCartPluginResponse
 * @description TODO
 * @date 2021/6/26 10:22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCartPluginResponse extends BasicResponse {

    /**
     * sku对应的营销标签
     * key:goodsInfoId
     * value:List<MarketingPluginLabelDetailVO>
     */
   private Map<String,List<MarketingPluginLabelDetailVO>> skuMarketingLabelMap;

    /**
     * sku对应是否可以原价购买标示
     */
   private Map<String,Integer> originalPriceFlagMap;

}
