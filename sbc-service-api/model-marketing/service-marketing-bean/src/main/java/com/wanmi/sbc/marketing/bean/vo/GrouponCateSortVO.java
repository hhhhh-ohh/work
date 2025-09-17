package com.wanmi.sbc.marketing.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenli
 * @Date: 2019/5/16
 * @Description: 拼团分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrouponCateSortVO extends BasicResponse {

    private static final long serialVersionUID = 5936316733974341463L;

    /**
     * 拼团分类标识
     */
    @Schema(description = "拼团分类Id")
    private String grouponCateId;


    /**
     * 拼团分类排序顺序
     */
    @Schema(description = "拼团分类排序顺序")
    private Integer cateSort;
}
