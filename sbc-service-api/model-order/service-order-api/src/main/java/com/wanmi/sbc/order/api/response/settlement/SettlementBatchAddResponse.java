package com.wanmi.sbc.order.api.response.settlement;

import com.wanmi.sbc.account.bean.vo.LakalaSettlementVO;import com.wanmi.sbc.account.bean.vo.SettlementVO;


import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 结算新增响应请求
 */
@Schema
@Data
public class SettlementBatchAddResponse  implements Serializable {

    private static final long serialVersionUID = 3318532060752029607L;

    private SettlementVO settlementVO;

    private LakalaSettlementVO lakalaSettlementVO;

    private SettlementType settlementType;
    /**
     * 店铺名称
     */
    @Schema(description = "店铺名称")
    private String storeName;

   public enum SettlementType{
        NORMAL,
        LAKALA
    }
}


