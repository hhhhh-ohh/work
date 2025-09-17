package com.wanmi.sbc.account.finance.record.model.response;

import com.wanmi.sbc.account.finance.record.model.entity.Settlement;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SettlementView extends Settlement implements Serializable {

    private static final long serialVersionUID = -5255242666273615425L;
    /**
     * 商铺名称
     */
    private String storeName;

    /**
     * 结算单号
     */
    private String settlementCode;

    /**
     * 商家编码
     */
    private String companyCode;

}
