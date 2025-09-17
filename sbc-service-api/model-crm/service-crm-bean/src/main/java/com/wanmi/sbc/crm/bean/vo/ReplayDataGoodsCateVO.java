package com.wanmi.sbc.crm.bean.vo;

import com.wanmi.sbc.common.base.BasicResponse;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplayDataGoodsCateVO extends BasicResponse {
    private static final long serialVersionUID = 1L;

    /**
     * 商品类目ID
     */
    private Long goodsCateId;

    /**
     * 商品类目名称
     */
    private String goodsCateName;
}
