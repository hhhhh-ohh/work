package com.wanmi.sbc.customer.ledger;

import com.google.common.collect.Lists;
import com.wanmi.sbc.customer.api.request.ledger.LedgerRequest;
import com.wanmi.sbc.customer.api.request.ledgererrorrecord.LedgerErrorRecordQueryRequest;
import com.wanmi.sbc.customer.bean.enums.LedgerErrorState;
import com.wanmi.sbc.customer.bean.enums.LedgerFunctionType;
import com.wanmi.sbc.customer.ledgererrorrecord.model.root.LedgerErrorRecord;
import com.wanmi.sbc.customer.ledgererrorrecord.service.LedgerErrorRecordService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyunpeng
 * @className LakalaJobService
 * @description
 * @date 2022/7/10 12:31 PM
 **/
@Service
public class LedgerJobService {

    private static final Integer MAX_NUM = 5;

    private static final Integer PAGE_SIZE = 100;

    @Autowired
    private LedgerErrorRecordService ledgerErrorRecordService;

    @Autowired
    private LedgerFunctionFactory ledgerFunctionFactory;

    public void ledgerJob(String param) {
        List<String> ids = new ArrayList<>();
        if (StringUtils.isNotBlank(param)) {
            String[] params = param.split(",");
            ids = Lists.newArrayList(params);
        }
        List<Integer> states = Lists.newArrayList(LedgerErrorState.UNDO.toValue(), LedgerErrorState.FAIL.toValue());
        LedgerErrorRecordQueryRequest queryRequest = new LedgerErrorRecordQueryRequest();
        queryRequest.setIdList(ids);
        queryRequest.setStateList(states);
        if (CollectionUtils.isEmpty(ids)) {
            queryRequest.setRetryCount(MAX_NUM);
        }
        //查询处理量
        Long count = ledgerErrorRecordService.count(queryRequest);
        Long pageNum = count % PAGE_SIZE > 0 ? count / PAGE_SIZE + 1 : count / PAGE_SIZE;

        queryRequest.setPageSize(PAGE_SIZE);
        for (int i = 0; i < pageNum; i++) {
            queryRequest.setPageNum(i);
            List<LedgerErrorRecord> records = ledgerErrorRecordService.page(queryRequest).getContent();
            records.forEach(record -> {
                excuteService(record);
            });
        }
    }

    /**
     *
     * @param record
     */
    @Transactional
    public void excuteService(LedgerErrorRecord record) {
        ledgerErrorRecordService.modifyState(record.getId(), LedgerErrorState.DOING);
        LedgerFunction ledgerFunction = ledgerFunctionFactory.create(LedgerFunctionType.fromValue(record.getType()));
        ledgerFunction.excute(LedgerRequest.builder().businessId(record.getBusinessId()).build());
    }
}
