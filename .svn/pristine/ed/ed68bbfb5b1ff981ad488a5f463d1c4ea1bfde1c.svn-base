package com.wanmi.sbc.goods.bean.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema
public class WechatAuditDTO implements Serializable {

    /**
     * 一级类目
     */
    @Schema(description = "一级类目")
    @NotNull
    private Long level1;

    /**
     * 二级类目
     */
    @Schema(description = "二级类目")
    @NotNull
    private Long level2;

    /**
     * 三级类目
     */
    @Schema(description = "三级类目")
    @NotNull
    private Long level3;

    private List<Long> cateIds;

    /**
     * 微信类目资质类型,0:不需要,1:必填,2:选填
     */
    @NotNull
    private Integer qualificationType;

    @Size(max = 10,message = "最多上传10张")
    private List<String> certificateUrls;

    @Schema(description = "商品资质")
    @Size(max = 5,message = "最多上传5张")
    private List<String> productQualificationUrls;
}
