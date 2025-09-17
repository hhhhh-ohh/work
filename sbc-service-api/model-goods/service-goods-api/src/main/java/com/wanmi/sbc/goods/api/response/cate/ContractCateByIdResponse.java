package com.wanmi.sbc.goods.api.response.cate;

import com.wanmi.sbc.common.base.BasicResponse;
import com.wanmi.sbc.goods.bean.vo.GoodsCateVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>根据主键查询签约分类响应类</p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
public class ContractCateByIdResponse extends BasicResponse {

    private static final long serialVersionUID = -2945284489899631070L;

    /**
     * 签约分类主键
     */
    @Schema(description = "签约分类主键")
    private Long contractCateId;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;

    /**
     * 商品分类
     */
    @Schema(description = "商品分类")
    private GoodsCateVO goodsCate;

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
