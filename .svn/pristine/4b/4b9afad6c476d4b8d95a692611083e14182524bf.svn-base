package com.wanmi.sbc.job;

import com.google.common.collect.Lists;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.elastic.api.provider.customer.EsCustomerDetailProvider;
import com.wanmi.sbc.elastic.api.request.customer.EsCustomerDetailInitRequest;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordProvider;
import com.wanmi.sbc.order.api.provider.payingmemberrecord.PayingMemberRecordQueryProvider;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordListRequest;
import com.wanmi.sbc.order.api.request.payingmemberrecord.PayingMemberRecordPageRequest;
import com.wanmi.sbc.order.bean.vo.PayingMemberRecordVO;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 付费会员更新状态定时任务
 */

@Component
@Slf4j
public class PayingMemberUpdateStateJobHandler {

    @Autowired
    private PayingMemberRecordQueryProvider payingMemberRecordQueryProvider;

    @Autowired
    private PayingMemberRecordProvider payingMemberRecordProvider;

    @Autowired
    private EsCustomerDetailProvider esCustomerDetailProvider;



    @XxlJob(value="PayingMemberUpdateStateJobHandler")
    public void execute() throws Exception {
        PayingMemberRecordPageRequest request = PayingMemberRecordPageRequest.builder()
                .levelState(NumberUtils.INTEGER_ZERO)
                .delFlag(DeleteFlag.NO)
                .build();
        request.setPageNum(Constants.ZERO);
        request.setPageSize(Constants.NUM_1000);
        findPayingMemberRecords(request,LocalDate.now());
    }


    private void findPayingMemberRecords(PayingMemberRecordPageRequest request,LocalDate now){
        //分页查询已生效的记录
        List<PayingMemberRecordVO> payingMemberRecordVOS = payingMemberRecordQueryProvider.page(request)
                .getContext().getPayingMemberRecordVOPage().getContent();
        if (CollectionUtils.isNotEmpty(payingMemberRecordVOS)){
            //找出过期的记录id集合
            List<String> recordIds = payingMemberRecordVOS.parallelStream().filter(payingMemberRecordVO -> {
                LocalDate expirationDate = payingMemberRecordVO.getExpirationDate();
                return now.isAfter(expirationDate);
            }).map(PayingMemberRecordVO::getRecordId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(recordIds)) {
                payingMemberRecordProvider.autoUpdateState(PayingMemberRecordListRequest.builder()
                        .recordIdList(recordIds)
                        .build());
            }
            request.setPageNum(request.getPageNum() + 1);
            findPayingMemberRecords(request,now);
        }
        EsCustomerDetailInitRequest esCustomerDetailInitRequest=new EsCustomerDetailInitRequest();
        esCustomerDetailInitRequest.setIdList(Lists.newArrayList(request.getCustomerId()));
        esCustomerDetailProvider.init(esCustomerDetailInitRequest);
    }
}
