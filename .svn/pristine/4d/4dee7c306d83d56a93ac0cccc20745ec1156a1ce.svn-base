package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * GoodsInfoSpecDetailRelVO
 *
 * @author lipeng
 * @dateTime 2018/11/9 下午2:36
 */
@Schema
@Data
public class GoodsInfoSpecDetailSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -1677333626079989870L;

    /**
     * SKU与规格值关联ID
     */
    @Schema(description = "SKU与规格值关联ID")
    private Long specDetailRelId;

    /**
     * 规格值自定义名称
     * 分词搜索
     */
    @Schema(description = "规格值自定义名称，分词搜索")
    private String detailName;
}
