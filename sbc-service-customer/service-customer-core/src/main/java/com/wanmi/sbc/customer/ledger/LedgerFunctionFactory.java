package com.wanmi.sbc.customer.ledger;

import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author xuyunpeng
 * @className LedgerFunctionFactory
 * @description
 * @date 2022/7/10 8:19 PM
 **/
@Component
public class LedgerFunctionFactory {

    @Autowired
    private Map<String, LedgerFunction> ledgerFunctionMap;

    public LedgerFunction create(LedgerFunctionType type) {
        return ledgerFunctionMap.get(type.getLedgerFunction());
    }
}
