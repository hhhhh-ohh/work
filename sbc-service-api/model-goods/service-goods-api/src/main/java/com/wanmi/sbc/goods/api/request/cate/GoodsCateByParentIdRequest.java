package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseQueryRequest;
import com.wanmi.sbc.goods.bean.enums.GoodsType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 黄昭
 * @className GoodsCateByParentIdRequest
 * @description 根据等级Id获取商品分类
 * @date 2022/4/14 11:29
 **/
@Data
@Schema
public class GoodsCateByParentIdRequest extends BaseQueryRequest implements Serializable{


    private static final long serialVersionUID = 2294540016112128016L;

    /**
     * 父类Id
     */
    @Schema(description = "父类Id")
    private Long cateParentId;

    @Schema(description = "查询子分类标识")
    private Boolean hasSonFlag = Boolean.FALSE;

    /**
     * 商品类型
     */
    @Schema(description = "商品类型")
    private GoodsType goodsType;
}