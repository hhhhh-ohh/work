package com.wanmi.sbc.marketing.api.response.newplugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginSimpleLabelVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhanggaolei
 * @className GoodsListPluginResponse
 * @description
 * @date 2021/6/26 10:22
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListPluginResponse extends BasicResponse {

    /**
     * sku对应的营销标签
     * key:goodsInfoId
     * value:List<MarketingPluginSimpleLabelVO>
     */
   private Map<String,List<MarketingPluginSimpleLabelVO>> skuMarketingLabelMap;

}
