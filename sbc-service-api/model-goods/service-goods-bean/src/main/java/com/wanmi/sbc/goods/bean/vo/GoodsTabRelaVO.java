package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>商品详情模板关联VO</p>
 * @author: sunkun
 * @Date: 2018-10-16
 */
@Schema
@Data
public class GoodsTabRelaVO extends BasicResponse {

    private static final long serialVersionUID = 5847296274783294383L;

    @Schema(description = "id")
    private String id;

    /**
     * spu标识
     */
    @Schema(description = "spu标识")
    private String goodsId;

    /**
     * 详情模板id
     */
    @Schema(description = "详情模板id")
    private Long tabId;

    /**
     * 内容详情
     */
    @Schema(description = "内容详情")
    private String tabDetail;
}
