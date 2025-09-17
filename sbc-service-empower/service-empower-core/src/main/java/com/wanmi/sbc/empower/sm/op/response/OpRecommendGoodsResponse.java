package com.wanmi.sbc.empower.sm.op.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wur
 * @className RecommendQueryReq
 * @description OP数谋推荐出商品响应
 * @date 2022/11/17 14:52
 **/
@Data
@Schema
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpRecommendGoodsResponse implements Serializable {

    private static final long serialVersionUID = -8015726253741444133L;

    /**
     *
     */
    private String spuId;
    /**
     *
     */
    private String skuId;
    /**
     * 店铺Id
     */
    private String shopId;
    /**
     *
     */
    private String skuName;
    /**
     *
     */
    private String skuImg;
    /**
     *
     */
    private String price;
}