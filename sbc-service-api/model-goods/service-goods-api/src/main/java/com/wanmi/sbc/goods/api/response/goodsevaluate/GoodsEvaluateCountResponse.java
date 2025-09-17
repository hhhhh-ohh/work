package com.wanmi.sbc.goods.api.response.goodsevaluate;

import com.wanmi.sbc.common.base.BasicResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * program: sbc-micro-service
 * 店铺评价统计
 *
 * @date 2019-04-04 16:25
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsEvaluateCountResponse extends BasicResponse {

    private static final long serialVersionUID = -1916604369378288934L;

    /***
     * 评价总数
     */
    @Schema(description = "评价总数")
    private Long evaluateConut;

    /***
     * 晒单总数
     */
    @Schema(description = "晒单总数")
    private Long postOrderCount;

    /***
     * 好评率
     */
    @Schema(description = "好评率")
    private String praise;

    /**
     * goodsId
     */
    @Schema(description = "goodsId")
    private String goodsId;
}