package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplayDataGoodsBrandVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品品牌id
     */
    private Long goodsBrandId;

    /**
     * 商品品牌名称
     */
    private String goodsBrandName;

}
