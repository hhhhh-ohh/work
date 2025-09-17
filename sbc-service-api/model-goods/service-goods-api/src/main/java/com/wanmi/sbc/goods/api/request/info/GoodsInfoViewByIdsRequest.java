package com.wanmi.sbc.goods.api.request.info;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.common.enums.DeleteFlag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 根据商品SKU编号查询请求
 * Created by daiyitian on 2017/3/24.
 */
@Schema
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfoViewByIdsRequest extends BaseRequest {

    private static final long serialVersionUID = -910214634291125783L;

    /**
     * 批量SKU编号
     */
    @Schema(description = "批量SKU编号")
    private List<String> goodsInfoIds;


    /**
     * 是否需要显示规格明细
     * 0:否,1:是
     */
    @Schema(description = "是否需要显示规格明细", contentSchema = com.wanmi.sbc.common.enums.DefaultFlag.class)
    private Integer isHavSpecText;

    /**
     * 店铺ID
     */
    @Schema(description = "店铺ID")
    private Long storeId;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除")
    private DeleteFlag deleteFlag;

    @Schema(description = "是否需要返回标签数据 true:需要，false或null:不需要")
    private Boolean showLabelFlag;

    @Schema(description = "当showLabelFlag=true时，true:返回开启状态的标签，false或null:所有标签")
    private Boolean showSiteLabelFlag;

    /**
     * 组合购详情标志
     */
    @Schema(description = "组合购详情标志")
    private Boolean goodsSuitsFlag;

    /**
     * 是否营销查询商品
     */
    @Schema(description = "是否营销查询商品")
    private Boolean isMarketing;

    /**
     * 是否按库存开关展示（部分场景无需按开关展示）
     */
    @Schema(description = "是否按库存开关展示")
    private Boolean stockViewFlag = Boolean.TRUE;
}
