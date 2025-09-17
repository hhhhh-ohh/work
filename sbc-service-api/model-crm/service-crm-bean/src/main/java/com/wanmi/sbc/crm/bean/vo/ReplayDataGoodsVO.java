package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplayDataGoodsVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

}
