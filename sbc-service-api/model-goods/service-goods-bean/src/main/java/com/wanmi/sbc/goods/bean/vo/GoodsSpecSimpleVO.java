package com.wanmi.sbc.goods.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * 商品规格实体类
 * Created by dyt on 2017/4/11.
 */
@Schema
@Data
public class GoodsSpecSimpleVO extends BasicResponse {

    private static final long serialVersionUID = -7695783917097360240L;

    /**
     * 规格ID
     */
    @Schema(description = "规格ID")
    private Long specId;

    /**
     * 商品ID
     */
    @Schema(description = "商品ID")
    private String goodsId;

    /**
     * 规格名称
     */
    @Schema(description = "规格名称")
    private String specName;

    /**
     * 多个规格值ID
     * 查询时，遍平化响应
     */
    @Schema(description = "多个规格值ID，查询时，遍平化响应")
    private List<Long> specDetailIds;

}
