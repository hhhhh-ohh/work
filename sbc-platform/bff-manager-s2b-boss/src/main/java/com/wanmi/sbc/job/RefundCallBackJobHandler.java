package com.wanmi.sbc.job;

import com.wanmi.sbc.common.util.DateUtil;
import com.wanmi.sbc.order.api.provider.refundcallbackresult.RefundCallBackResultQueryProvider;
import com.wanmi.sbc.order.api.request.refundcallbackresult.RefundCallBackResultPageRequest;
import com.wanmi.sbc.order.api.response.refundcallbackresult.RefundCallBackResultPageResponse;
import com.wanmi.sbc.order.bean.enums.PayCallBackResultStatus;
import com.wanmi.sbc.order.bean.vo.RefundCallBackResultVO;
import com.wanmi.sbc.pay.RefundCallBackService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 退款回调补偿定时任务
 */
@Component
@Slf4j
public class RefundCallBackJobHandler {

    private static final int ERROR_MAX_NUM = 5;

    private static final int PAGE_SIZE = 100;

    private static final String TODO = "0";

    private static final String HANDLING = "1";

    private static final String FAILED = "3";

    private static final String ORDER_CODE = "4";

    @Autowired
    private RefundCallBackResultQueryProvider refundCallBackResultQueryProvider;

    @Autowired
    private RefundCallBackService refundCallBackService;

    @XxlJob(value = "RefundCallBackJobHandler")
    public void execute() throws Exception {
        String paramStr = XxlJobHelper.getJobParam();
        RefundCallBackResultPageRequest request = new RefundCallBackResultPageRequest();
        request.setPageSize(PAGE_SIZE);
        if (StringUtils.isNotBlank(paramStr)) {
            String[] paramStrArr = paramStr.split("&");
            String type = paramStrArr[0];
            if (type.equals(TODO)) {
                request.setResultStatus(PayCallBackResultStatus.TODO);
            } else if (type.equals(HANDLING)) {
                if (paramStrArr.length > 1 && StringUtils.isNotBlank(paramStrArr[1])) {
                    String endTime = paramStrArr[1];
                    request.setCreateTimeEnd(DateUtil.parse(endTime, DateUtil.FMT_TIME_1));
                } else {
                    request.setCreateTimeEnd(LocalDateTime.now().minusMinutes(30));
                }
                request.setResultStatus(PayCallBackResultStatus.HANDLING);
            } else if (type.equals(FAILED)) {
                //查询处理失败的支付回调记录，并且失败的次数《=5次
                request.setErrorNum(ERROR_MAX_NUM);
                request.setResultStatus(PayCallBackResultStatus.FAILED);
            } else if (type.equals(ORDER_CODE)) {
                if (paramStrArr.length > 1 && StringUtils.isNotBlank(paramStrArr[1])) {
                    List<String> businessIds = new ArrayList<>();
                    String orderCodeStr = paramStrArr[1];
                    Collections.addAll(businessIds, orderCodeStr.split(","));
                    request.setBusinessIds(businessIds);
                }
            }
            refundCallBackResultInfo(request);
        } else {
            //查询处理失败的支付回调记录，并且失败的次数《=5次
            request.setResultStatus(PayCallBackResultStatus.FAILED);
            request.setErrorNum(ERROR_MAX_NUM);
            refundCallBackResultInfo(request);
            //查询未处理状态的数据
            request.setResultStatus(PayCallBackResultStatus.TODO);
            refundCallBackResultInfo(request);
        }
    }

    /**
     * 获取对应需要处理的回调数据
     *
     * @param request
     */
    public void refundCallBackResultInfo(RefundCallBackResultPageRequest request) {
        Boolean flag = Boolean.TRUE;
        int pageNum = NumberUtils.INTEGER_ZERO;
        try {
            while (flag) {
                request.setPageNum(pageNum);
                RefundCallBackResultPageResponse response = refundCallBackResultQueryProvider.page(request).getContext();
                List<RefundCallBackResultVO> refundCallBackResultVOS = response.getPage().getContent();
                if (CollectionUtils.isEmpty(refundCallBackResultVOS)) {
                    flag = Boolean.FALSE;
                    log.info("==========退款回调补偿结束，结束pageNum:{}==============", pageNum);
                } else {
                    refundCallBackService.refundCallBackHandle(response);
                    log.info("==========退款回调补偿结束，当前pageNum:{}==============", pageNum);
                    pageNum++;
                }
            }
        } catch (Exception e) {
            log.info("==========退款回调补偿结束，异常pageNum:{}==============", pageNum);
        }
    }
}
