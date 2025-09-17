package com.wanmi.sbc.goods.api.request.cate;

import com.wanmi.sbc.common.base.BaseRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>根据动态条件查询请求结构</p>
 * Created by daiyitian on 2018/11/15.
 */
@Schema
@Data
public class ContractCateListByConditionRequest extends BaseRequest {

    private static final long serialVersionUID = 5952480557146621527L;

    /**
     * 店铺id
     */
    @Schema(description = "店铺id")
    private Long storeId;

    /**
     * 商品分类标识
     */
    @Schema(description = "商品分类标识")
    private Long cateId;

    /**
     * 商品分类标识列表
     */
    @Schema(description = "商品分类标识列表")
    private List<Long> cateIds;
}
