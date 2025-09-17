package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 二次签约分类更新请求
 * @author wangchao
 */
@Schema
@Data
public class ContractCateAuditSaveDTO implements Serializable {

    private static final long serialVersionUID = -1181819043894211016L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Long contractCateId;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商品分类标识
     */
    @Schema(description = "商品分类标识")
    private Long cateId;

    /**
     * 分类扣率
     */
    @Schema(description = "分类扣率")
    private BigDecimal cateRate;

    /**
     * 资质图片路径
     */
    @Schema(description = "资质图片路径")
    private String qualificationPics;
}
