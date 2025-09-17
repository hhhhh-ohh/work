package com.wanmi.sbc.empower.sm.op.request;

import com.wanmi.sbc.empower.api.request.sm.recommend.RecommendGoodsRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className RecommendQueryReq
 * @description OP数谋推荐出商品请求类
 * @date 2022/11/17 14:52
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpRecommendGoodsRequest implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;
    /**
     * 0: 热门推荐  1：基于商品相关性推荐 2：基于用户兴趣推荐
     */
    private Integer tacticsType;

    /**
     * 用户Id
     */
    private String customerId;

    /**
     * 目标商品
     */
    private List<String> relationGoodsIds;

    /**
     * 第几页
     */
    private Integer pageNum = 0;

    /**
     * 每页显示多少条
     */
    private Integer pageSize = 10;

    public static OpRecommendGoodsRequest mapper(RecommendGoodsRequest request) {
        return OpRecommendGoodsRequest.builder()
                .customerId(request.getCustomerId())
                .relationGoodsIds(request.getRelationGoodsIds())
                .pageNum(request.getPageNum())
                .pageSize(request.getPageSize())
                .build();
    }
}