package com.wanmi.sbc.goods.api.request.groupongoodsinfo;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>拼团活动商品信息表分页查询请求参数</p>
 *
 * @author groupon
 * @date 2019-05-15 14:49:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrouponGoodsInfoSimplePageRequest extends BaseQueryRequest {
    private static final long serialVersionUID = 5794652344512329593L;

    /**
     * 拼团分类ID
     */
    @Schema(description = "拼团分类ID")
    private String grouponCateId;

    /**
     * 商品名称
     */
    @Schema(description = "商品名称")
    private String goodsName;

    /**
     * 是否精选
     */
    @Schema(description = "是否精选")
    private Boolean sticky = Boolean.FALSE;

    /**
     * 店铺id
     */
    @Schema(description = "店铺Id")
    private Long storeId;

    /**
     * 是否需要显示规格明细
     */
    @Schema(description = "是否显示规格")
    private Boolean havSpecTextFlag;

    /**
     * 商品skuId
     */
    @Schema(description = "批量商品skuId")
    private List<String> goodsInfoIds;

    /**
     * 排序标识
     * 0:销量倒序
     * 1:好评数倒序
     * 2:评论率倒序
     * 3:排序号倒序
     * 4:排序号倒序
     * 5:成才数倒序
     */
    @Schema(description = "排序标识")
    private Integer sortFlag;

    /**
     * 是否展示预热的拼团
     */
    @Schema(description = "是否展示预热的拼团")
    private Boolean showPre;

}