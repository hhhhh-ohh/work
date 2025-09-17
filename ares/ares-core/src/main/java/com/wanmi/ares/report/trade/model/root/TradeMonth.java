package com.wanmi.ares.report.trade.model.root;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * trade_month
 * @author 
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TradeMonth extends TradeBase {

    /**
     * 统计月份
     */
    private String month;

}