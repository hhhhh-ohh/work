package com.wanmi.sbc.job;

import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.StoreType;
import com.wanmi.sbc.job.service.SettlementAnalyseJobService;
import com.wanmi.sbc.order.api.request.settlement.SettlementAnalyseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RestController;
/**
 * @type JobTestController.java
 * @desc
 * @author zhanggaolei
 * @date 2023/4/11 14:34
 * @version
 */
@RestController
public class JobTestController {
    @Autowired SettlementAnalyseJobService settlementAnalyseJobService;

    @GetMapping("/test/settlement")
    public BaseResponse test(){
        settlementAnalyseJobService.analyseSettlement(
                SettlementAnalyseRequest.builder()
                        .param(null)
                        .storeType(StoreType.SUPPLIER)
                        .build());

        return BaseResponse.SUCCESSFUL();
    }
}
