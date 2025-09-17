package com.wanmi.sbc.empower.legder;

import com.wanmi.sbc.empower.bean.enums.LedgerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LedgerServiceFactory {


    //自动注入到map
    @Autowired
    private Map<String, LedgerService> ledgerServiceMap;

    public LedgerService create(LedgerType ledgerType){
        return ledgerServiceMap.get(ledgerType.getLedgerService());
    }
}
