package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Pattern;

import lombok.Data;

import java.io.Serializable;

/**
 * 二次签约品牌更新请求
 * @author wangchao
 */
@Schema
@Data
public class ContractBrandAuditSaveDTO implements Serializable {

    private static final long serialVersionUID = -6331255434013334237L;

    /**
     * 签约品牌分类
     */
    @Schema(description = "签约品牌分类")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5_a-zA-Z0-9_]{1,30}$")
    private Long contractBrandId;


    /**
     * 品牌名称
     */
    @Schema(description = "品牌名称")
    private String name;

    /**
     * 品牌别名
     */
    @Schema(description = "品牌别名")
    private String nickName;

    /**
     * 品牌logo
     */
    @Schema(description = "品牌logo")
    private String logo;

    /**
     * 店铺主键
     */
    @Schema(description = "店铺主键")
    private Long storeId;


    /**
     * 品牌主键
     */
    @Schema(description = "品牌主键")
    private Long brandId;

    /**
     * 待审核品牌主键
     */
    @Schema(description = "待审核品牌主键")
    private Long checkBrandId;


    /**
     * 授权图片路径
     */
    @Schema(description = "授权图片路径")
    private String authorizePic;
}
