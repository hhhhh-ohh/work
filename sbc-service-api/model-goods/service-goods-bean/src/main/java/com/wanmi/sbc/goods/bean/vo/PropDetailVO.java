package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * es商品属性选项详情
 *
 * @auther wc
 * @create 2021年04月25日16:28:46
 */
@Data
@Schema
public class PropDetailVO extends BasicResponse {

    /**
     * 属性值id
     */
    @Schema(description = "属性值id")
    private Long detailId;

    /**
     * 属性id外键
     */
    @Schema(description = "属性id外键")
    private Long propId;

    /**
     * 属性值
     */
    @Schema(description = "属性值")
    private String detailName;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer detailSort;

    /**
     * 属性值
     */
    @Schema(description = "属性值")
    private String detailNameValue;

    /**
     * 属性名拼音
     */
    @Schema(description = "属性名拼音")
    private String detailPinYin;
}
