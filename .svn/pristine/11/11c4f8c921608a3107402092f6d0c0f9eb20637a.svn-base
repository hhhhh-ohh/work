package com.wanmi.sbc.job.ledger;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.KsBeanUtil;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelProvider;
import com.wanmi.sbc.customer.api.provider.ledgerreceiverrel.LedgerReceiverRelRecordQueryProvider;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverBatchRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordPageRequest;
import com.wanmi.sbc.customer.api.request.ledgerreceiverrel.LedgerReceiverRelRecordQueryRequest;
import com.wanmi.sbc.customer.bean.vo.LedgerReceiverRelRecordVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xuyunpeng
 * @className LedgerReceiverRecordJobHandler
 * @description
 * @date 2022/7/14 4:10 PM
 **/
@Component
public class LedgerReceiverRecordJobHandler{

    private static final Integer PAGE_SIZE = 100;

    @Autowired
    private LedgerReceiverRelRecordQueryProvider ledgerReceiverRelRecordQueryProvider;

    @Autowired
    private LedgerReceiverRelProvider ledgerReceiverRelProvider;

    private static Logger log = LoggerFactory.getLogger(LedgerReceiverRecordJobHandler.class);

    @XxlJob(value = "ledgerReceiverRecordJobHandler")
    public void execute() throws Exception {
        String s = XxlJobHelper.getJobParam();
        List<String> ids = Lists.newArrayList( s.split(","));

        LedgerReceiverRelRecordQueryRequest queryRequest = new LedgerReceiverRelRecordQueryRequest();
        queryRequest.setIdList(ids);
        Long count = ledgerReceiverRelRecordQueryProvider.count(queryRequest).getContext();
        long pageTotal = count % PAGE_SIZE > 0 ? count / PAGE_SIZE + 1 : count / PAGE_SIZE;

        LedgerReceiverRelRecordPageRequest pageRequest = KsBeanUtil.convert(queryRequest, LedgerReceiverRelRecordPageRequest.class);
        pageRequest.putSort("createTime", SortType.ASC.toValue());
        pageRequest.setPageSize(PAGE_SIZE);
        for(int i = 0; i < pageTotal; i++) {
            log.info("分账绑定关系补偿，第{}页，共{}页", i, pageTotal);
            pageRequest.setPageNum(i);
            List<LedgerReceiverRelRecordVO> recordVOS = ledgerReceiverRelRecordQueryProvider.page(pageRequest)
                    .getContext().getLedgerReceiverRelRecordVOPage().getContent();
            recordVOS.forEach(record -> {
                ledgerReceiverRelProvider.batchAddByAccountId(LedgerReceiverBatchRequest.builder().accountId(record.getAccountId()).build());
            });

        }
    }
}
