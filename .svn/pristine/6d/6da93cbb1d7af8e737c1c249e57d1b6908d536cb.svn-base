package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>签约分类</p>
 * author: sunkun
 * Date: 2018-11-05
 */
@Schema
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCateVO extends BasicResponse {

    private static final long serialVersionUID = 7622478728423615696L;

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
     * 平台类目id
     */
    @Schema(description = "平台类目id")
    private Long cateId;

    /**
     * 平台类目名称
     */
    @Schema(description = "平台类目名称")
    private String cateName;

    /**
     * 上级平台类目名称(一级/二级)
     */
    @Schema(description = "上级平台类目名称(一级/二级)")
    private String parentGoodCateNames;

    /**
     * 分类扣率
     */
    @Schema(description = "分类扣率")
    private BigDecimal cateRate;

    /**
     * 平台扣率
     */
    @Schema(description = "平台扣率")
    private BigDecimal platformCateRate;

    /**
     * 资质图片路径
     */
    @Schema(description = "资质图片路径")
    private String qualificationPics;

    /**
     * 平台分类
     */
    @Schema(description = "平台分类")
    private GoodsCateVO goodsCate;
}
