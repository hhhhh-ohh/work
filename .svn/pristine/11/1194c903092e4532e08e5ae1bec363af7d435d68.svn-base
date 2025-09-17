package com.wanmi.sbc.job;

import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.common.util.GeneratorService;
import com.wanmi.sbc.order.api.provider.paycallbackresult.PayCallBackResultQueryProvider;
import com.wanmi.sbc.order.api.provider.paytimeseries.PayTimeSeriesQueryProvider;
import com.wanmi.sbc.order.api.request.paycallbackresult.PayCallBackResultPageRequest;
import com.wanmi.sbc.order.api.request.paytimeseries.PayTimeSeriesListRequest;
import com.wanmi.sbc.order.api.response.paycallbackresult.PayCallBackResultPageResponse;
import com.wanmi.sbc.order.api.response.paytimeseries.PayTimeSeriesListResponse;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.pay.PayCallBackService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName PayCallBackJobHandle
 * @Description 支付回调出错补偿定时任务
 * @Author lvzhenwei
 * @Date 2020/7/6 11:21
 **/
@Component
@Slf4j
public class PayCallBackJobHandle {

    private static final int ERROR_MAX_NUM = 5;

    private static final int PAGE_SIZE = 100;

    private static final String TODO = "0";

    private static final String HANDLING = "1";

    private static final String FAILED = "3";

    private static final String ORDER_CODE = "4";

    @Autowired
    private PayCallBackResultQueryProvider payCallBackResultQueryProvider;

    @Autowired
    private PayCallBackService payCallBackService;

    @Autowired
    private PayTimeSeriesQueryProvider payTimeSeriesQueryProvider;

    @XxlJob(value = "payCallBackJobHandler")
    public void execute() throws Exception {
        String paramStr = XxlJobHelper.getJobParam();
        PayCallBackResultPageRequest payCallBackResultPageRequest = new PayCallBackResultPageRequest();
        payCallBackResultPageRequest.setPageSize(PAGE_SIZE);
        if (StringUtils.isNotBlank(paramStr)) {
            String[] paramStrArr = paramStr.split("&");
            String type = paramStrArr[0];
            if (type.equals(TODO)) {
                payCallBackResultPageRequest.setResultStatus(PayCallBackResultStatus.TODO);
            } else if (type.equals(HANDLING)) {
                payCallBackResultPageRequest.setResultStatus(PayCallBackResultStatus.HANDLING);
                if (paramStrArr.length > 1 && StringUtils.isNotBlank(paramStrArr[1])) {
                    String endTime = paramStrArr[1];
                    payCallBackResultPageRequest.setCreateTimeEnd(DateUtil.parse(endTime, DateUtil.FMT_TIME_1));
                } else {
                    payCallBackResultPageRequest.setCreateTimeEnd(LocalDateTime.now().minusMinutes(30));
                }
            } else if (type.equals(FAILED)) {
                //查询处理失败的支付回调记录，并且失败的次数《=5次
                payCallBackResultPageRequest.setResultStatus(PayCallBackResultStatus.FAILED);
                payCallBackResultPageRequest.setErrorNum(ERROR_MAX_NUM);
            } else if (type.equals(ORDER_CODE)) {
                if (paramStrArr.length > 1 && StringUtils.isNotBlank(paramStrArr[1])) {
                    String orderCodeStr = paramStrArr[1];
                    List<String> requestIds = new ArrayList<>();
                    List<String> ids = new ArrayList<>();
                    Collections.addAll(ids, orderCodeStr.split(","));
                    List<String> pmIdList = new ArrayList<>();
                    List<String> businessIdList = new ArrayList<>();
                    // 过滤掉付费会员的id
                    ids.stream()
                            .forEach(
                                    t -> {
                                        if (t.startsWith(
                                                GeneratorService._PREFIX_PAY_MEMBER_RECORD_ID)) {
                                            pmIdList.add(t);
                                        } else {
                                            businessIdList.add(t);
                                        }
                                    });
                    //按照订单id查询支付单id
                    if (CollectionUtils.isNotEmpty(businessIdList)) {
                        PayTimeSeriesListResponse response =
                                payTimeSeriesQueryProvider
                                        .list(
                                                PayTimeSeriesListRequest.builder()
                                                        .businessIdList(businessIdList)
                                                        .build())
                                        .getContext();
                        if (response != null) {
                            response.getPayTimeSeriesVOList()
                                    .forEach(
                                            t -> {
                                                requestIds.add(t.getPayNo());
                                            });
                        }
                    }
                    if (CollectionUtils.isNotEmpty(pmIdList)) {
                        requestIds.addAll(pmIdList);
                    }
                    payCallBackResultPageRequest.setBusinessIds(requestIds);
                }
            }
        } else {
            //查询处理失败的支付回调记录，并且失败的次数《=5次
            payCallBackResultPageRequest.setResultStatus(PayCallBackResultStatus.FAILED);
            payCallBackResultPageRequest.setErrorNum(ERROR_MAX_NUM);
            payCallBackResultInfo(payCallBackResultPageRequest);
            //查询未处理状态的数据
            payCallBackResultPageRequest.setResultStatus(PayCallBackResultStatus.TODO);
        }
        payCallBackResultInfo(payCallBackResultPageRequest);
    }

    /**
     * @return void
     * @Author lvzhenwei
     * @Description 获取对应需要处理的回调数据
     * @Date 11:46 2020/7/29
     * @Param [payCallBackResultPageRequest]
     **/
    public void payCallBackResultInfo(PayCallBackResultPageRequest payCallBackResultPageRequest) {
        PayCallBackResultPageResponse payCallBackResultPageResponse = payCallBackResultQueryProvider.page(payCallBackResultPageRequest).getContext();
        //分页处理
        long totalPages = payCallBackResultPageResponse.getPayCallBackResultVOPage().getTotal();
        int size = payCallBackResultPageResponse.getPayCallBackResultVOPage().getSize();
        int pageNum = (int) Math.ceil(((double) totalPages / size));
        payCallBackService.payCallBackHandle(payCallBackResultPageResponse);
        if (totalPages > 1) {
            for (int num = 1; num < pageNum - 1; num++) {
                payCallBackResultPageRequest.setPageNum(num);
                payCallBackResultPageResponse = payCallBackResultQueryProvider.page(payCallBackResultPageRequest).getContext();
                payCallBackService.payCallBackHandle(payCallBackResultPageResponse);
            }
        }
    }
}
