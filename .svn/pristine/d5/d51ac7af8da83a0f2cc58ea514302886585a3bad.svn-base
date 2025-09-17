package com.wanmi.sbc.mq.report.service.base;


import com.wanmi.sbc.common.base.Operator;
import com.wanmi.sbc.common.sensitiveword.annotation.ReturnSensitiveWords;
import com.wanmi.sbc.customer.api.provider.distribution.DistributionCommissionQueryProvider;
import com.wanmi.sbc.customer.api.request.distribution.DistributionCommissionExportRequest;
import com.wanmi.sbc.customer.bean.vo.DistributionCommissionForPageVO;
import com.wanmi.sbc.mq.report.entity.ExportData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @return
 * @description 分销员佣金
 * @author shy
 * @date 2022/7/21 9:53 上午
 */

@Service
@Slf4j
public class DistributionCommissionBaseService {

    @Autowired
    private DistributionCommissionQueryProvider distributionCommissionQueryProvider;

    @ReturnSensitiveWords(functionName = "f_boss_distribution_commission_export_sign_word")
    public List<DistributionCommissionForPageVO> queryExport(Operator operator, DistributionCommissionExportRequest queryReq){
        List<DistributionCommissionForPageVO> dataRecords = distributionCommissionQueryProvider.findDistributionCommissionExport(queryReq).getContext().getRecordList();
        return dataRecords;
    }
}
