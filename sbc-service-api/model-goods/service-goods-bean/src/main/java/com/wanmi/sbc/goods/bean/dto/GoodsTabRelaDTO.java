package com.wanmi.sbc.goods.bean.dto;

import com.wanmi.sbc.common.annotation.CanEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import lombok.Data;

/**
 * <p>商品详情模板关联实体</p>
 * @author: sunkun
 * @Date: 2018-10-16
 */
@Schema
@Data
public class GoodsTabRelaDTO implements Serializable {

    private static final long serialVersionUID = -4468105154141495740L;

    /**
     * 主键
     */
    @Schema(description = "主键")
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
    @CanEmpty
    private String tabDetail;
}
