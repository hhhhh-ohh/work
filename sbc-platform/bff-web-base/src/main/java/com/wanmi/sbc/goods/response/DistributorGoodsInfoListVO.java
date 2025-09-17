package com.wanmi.sbc.goods.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhanggaolei
 * @className DistributorGoodsInfoListVO
 * @description TODO
 * @date 2021/8/26 8:09 下午
 **/
@Data
public class DistributorGoodsInfoListVO extends GoodsInfoListVO {

    /**
     * 是否已关联分销员
     */
    @Schema(description = "是否已关联分销员，0：否，1：是")
    private Integer joinDistributior;
}
