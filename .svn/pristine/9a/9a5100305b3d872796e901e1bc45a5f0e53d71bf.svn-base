package com.wanmi.sbc.empower.bean.vo.channel.linkedmall;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author hanwei
 * @className LinkedMallGoodsStockVO
 * @description
 * @date 2021/5/25 14:08
 **/
@Data
public class LinkedMallGoodsStockVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long itemId;

    private String lmItemId;

    private List<SkuStock> skuList;

    @Data
    public static class SkuStock {

        private Long skuId;

        private Long stock;
    }
}