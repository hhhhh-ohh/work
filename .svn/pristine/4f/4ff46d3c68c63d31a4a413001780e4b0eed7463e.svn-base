package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.customer.bean.vo.StoreVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据商品SKU编号查询请求
 * Created by liguang on 2022-03-03 17:57:36.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeConfirmGoodsRequest extends BaseRequest {

    private static final long serialVersionUID = 7264471634952829036L;

    @Schema(description = "批量SKU编号")
    @Builder.Default
    private List<String> skuIds = Lists.newArrayList();

    @Schema(description = "是否需要显示规格明细")
    @Builder.Default
    private Boolean isHavSpecText = Boolean.FALSE;

    @Schema(description = "是否计算区间价")
    @Builder.Default
    private Boolean isHavIntervalPrice = Boolean.FALSE;

    @Schema(description = "查询redis 库存")
    @Builder.Default
    private Boolean isHavRedisStock = Boolean.FALSE;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    @Builder.Default
    private Boolean showLabelFlag = Boolean.FALSE;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    @Builder.Default
    private Boolean showSiteLabelFlag = Boolean.FALSE;
}
