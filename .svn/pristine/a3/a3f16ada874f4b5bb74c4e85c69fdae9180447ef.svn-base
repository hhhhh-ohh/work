package com.wanmi.sbc.vas.api.request.recommend.analysis;

import com.wanmi.sbc.common.base.BaseRequest;
import com.wanmi.sbc.vas.bean.enums.recommen.PositionType;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RecommendPositionAnalysisQueryRequest
 * @Description 推荐坑位效果分析查询request
 * @Author lvzhenwei
 * @Date 2021/04/02 16:48
 **/
@Schema
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendPositionAnalysisQueryRequest extends BaseRequest {

    private static final long serialVersionUID = 1L;

    /**
     * 终端
     */
    @Schema(description = "终端类型，1:PC，2:H5， 3:APP，4:小程序")
    private Integer terminalType;

    /**
     * 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方
     */
    @Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方")
    private PositionType type;

    /**
     * 坑位（大数据平台用）
     */
    @Schema(description = "坑位")
    private Integer location;

    /**
     * 推荐策略  0：热门推荐；1：商品相关性推荐；2：基于用户兴趣推荐
     */
    @Schema(description = "推荐策略  0：热门推荐；1：商品相关性推荐；2：基于用户兴趣推荐")
    private Integer recommendType;

    /**
     * 查询类型
     */
    @Schema(description = "查询类型：0：按天，1：按周，2：按月")
    @NotNull
    private Integer selectType;

    /**
     * 查询开始时间
     */
    @Schema(description = "查询开始时间")
    @NotBlank
    private String beginDate;

    /**
     * 查询结束时间
     */
    @Schema(description = "查询结束时间")
    @NotBlank
    private String endDate;

    /**
     * 排序字段
     */
    @Schema(description = "排序字段")
    private String sortName;

    /**
     * 排序类型
     */
    @Schema(description = "排序类型")
    private String sortType;

}
