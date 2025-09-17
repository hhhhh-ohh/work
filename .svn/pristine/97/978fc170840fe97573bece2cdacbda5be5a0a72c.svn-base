package com.wanmi.sbc.goods.api.request.goods;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.PluginType;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * GoodsModifyPositiveFeedbackRequest
 * 更新商品好评率Request
 * @author lvzhenwei
 * @date 2019/4/11 15:50
 **/
@Data
public class GoodsModifyEvaluateNumRequest extends BaseRequest {

    private static final long serialVersionUID = -6884236326728820792L;

    /**
     * 商品编号
     */
    @NotNull
    private String goodsId;

    /**
     * 商品评分
     */
    @NotNull
    private Integer evaluateScore;

    private PluginType pluginType;
}
