package com.wanmi.sbc.empower.api.request.sm.recommend;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
*
 * @description  添加商品
 * @author  wur
 * @date: 2022/4/1 14:26
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class RecommendGoodsRequest implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     * 坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方
     */
    @Schema(description = "坑位类型，0：购物车，1：商品详情，2：商品列表；3：个人中心；4：会员中心；5：收藏商品；6：支付成功页；7：分类；8:魔方")
    private int positionType;

    /**
     * 目标商品  用于：基于商品相关性推荐
     * 如果坑位是：基于商品相关性推荐 此参数空返回热门推荐
     */
    @Schema(description = "目标商品Id")
    private List<String> relationGoodsIds;

    /**
     * 用户Id  用于：基于用户兴趣推荐
     * 如果坑位是：基于用户兴趣推荐 此参数空返回热门推荐
     */
    @Schema(description = "用户Id")
    private String customerId;

    /**
     *
     */
    @Schema(description = "页码")
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    @Schema(description = "页面大小")
    private Integer pageSize = 10;

}
