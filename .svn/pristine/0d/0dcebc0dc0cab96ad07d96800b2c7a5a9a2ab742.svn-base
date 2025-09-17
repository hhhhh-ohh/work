package com.wanmi.sbc.marketing.api.response.newplugin;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.enums.GoodsStatus;
import com.wanmi.sbc.goods.bean.vo.MarketingPluginLabelVO;
import com.wanmi.sbc.marketing.bean.dto.GoodsInfoMarketingCacheDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhanggaolei
 * @className GoodsInfoDetailPluginResponse
 * @description
 * @date 2021/6/26 10:22
 **/
@Data
public class GoodsInfoDetailPluginResponse extends BasicResponse {

    /**
     * 商品编号（SKU）
     */
    private String goodsInfoId;

    /**
     * 营销活动标签
     */
    private List<MarketingPluginLabelVO> marketingLabels;

    /**
     * 插件计算出的价格
     */
    private BigDecimal pluginPrice;

    /**
     * 商品状态
     */
    private GoodsStatus goodsStatus;

    /**
     * 预热活动
     */
    @Schema(description = "预热活动")
    private List<GoodsInfoMarketingCacheDTO> preMarketings;
}
