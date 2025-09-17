package com.wanmi.sbc.customer.api.request.storeevaluatenum;

import java.math.BigDecimal;

import com.wanmi.sbc.customer.api.request.CustomerBaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * <p>店铺统计评分等级人数统计列表查询请求参数</p>
 *
 * @author liutao
 * @date 2019-03-04 10:55:28
 */
@Schema
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreEvaluateNumListRequest extends CustomerBaseRequest {
    private static final long serialVersionUID = 1L;

    /**
     * 批量查询-id 主键List
     */
    @Schema(description = " 批量查询-id 主键List")
    private List<String> numIdList;

    /**
     * id 主键
     */
    @Schema(description = "id 主键")
    private String numId;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

    /**
     * 优秀评分数（5星-4星）
     */
    @Schema(description = "优秀评分数（5星-4星）")
    private Long excellentNum;

    /**
     * 中等评分数（3星）
     */
    @Schema(description = "中等评分数（3星）")
    private Long mediumNum;

    /**
     * 差的评分数（1星-2星）
     */
    @Schema(description = "差的评分数（1星-2星）")
    private Long differenceNum;

    /**
     * 综合评分
     */
    @Schema(description = "综合评分")
    private BigDecimal sumCompositeScore;

    /**
     * 评分周期 0：30天，1：90天，2：180天
     */
    @Schema(description = "评分周期 0：30天，1：90天，2：180天")
    private Integer scoreCycle;

    /**
     * 统计类型 0：商品评分，1：服务评分，2：物流评分
     */
    @Schema(description = "统计类型 0：商品评分，1：服务评分，2：物流评分")
    private Integer numType;

}