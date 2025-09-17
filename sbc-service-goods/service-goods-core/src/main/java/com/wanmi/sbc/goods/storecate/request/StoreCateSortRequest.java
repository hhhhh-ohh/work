package com.wanmi.sbc.goods.storecate.request;

import com.wanmi.sbc.common.base.BaseRequest;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: chenli
 * @Date:  2018/9/13
 * @Description: 店铺分类排序request
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreCateSortRequest extends BaseRequest {

    /**
     * 店铺分类标识
     */
    @NotNull
    private Long storeCateId;


    /**
     * 店铺分类排序顺序
     */
    @NotNull
    private Integer cateSort;
}
