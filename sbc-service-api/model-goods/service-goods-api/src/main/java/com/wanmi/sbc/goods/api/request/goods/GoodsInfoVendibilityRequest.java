package com.wanmi.sbc.goods.api.request.goods;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wur
 * @className GoodsInfoVendibilityRequest
 * @description TODO
 * @date 2023/8/11 15:14
 **/
@Schema
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoodsInfoVendibilityRequest implements Serializable {

    private static final long serialVersionUID = 5621833007962215905L;

    /**
     *  供应商商品Id
     */
    private List<String> goodsInfoIdList;

    /**
     * 供应商商品Id
     */
    private List<String> goodsIdList;
}