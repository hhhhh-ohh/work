package com.wanmi.sbc.goods.cate.request;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签约分类拖拽排序请求
 * Created by chenli on 2018/9/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCateSortRequest extends BaseRequest {

    private static final long serialVersionUID = 9212490315850621538L;

    /**
     * 商品分类标识
     */
    @NotNull
    private Long cateId;


    /**
     * 商品分类排序顺序
     */
    @NotNull
    private Integer cateSort;
}
