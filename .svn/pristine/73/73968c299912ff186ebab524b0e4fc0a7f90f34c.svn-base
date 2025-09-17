package com.wanmi.sbc.job;

import cn.hutool.core.collection.CollUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.DeleteFlag;
import com.wanmi.sbc.common.enums.SortType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.crm.api.provider.autotag.AutoTagProvider;
import com.wanmi.sbc.crm.api.request.autotag.AutoTagPageRequest;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelProvider;
import com.wanmi.sbc.customer.api.provider.payingmembercustomerrel.PayingMemberCustomerRelQueryProvider;
import com.wanmi.sbc.customer.api.provider.payingmemberlevel.PayingMemberLevelQueryProvider;
import com.wanmi.sbc.customer.api.request.payingmembercustomerrel.PayingMemberDegradationRequest;
import com.wanmi.sbc.customer.api.response.payingmembercustomerrel.PayingMemberCustomerRelExpiraResponse;
import com.wanmi.sbc.customer.api.response.payingmemberlevel.PayingMemberLevelListNewResponse;
import com.wanmi.sbc.customer.bean.vo.PayingMemberCustomerRelVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelBaseVO;
import com.wanmi.sbc.customer.bean.vo.PayingMemberLevelVO;
import com.wanmi.sbc.order.api.provider.trade.TradeQueryProvider;
import com.wanmi.sbc.order.api.request.trade.CustomerTradeListRequest;
import com.wanmi.sbc.order.api.request.trade.TradeListAllRequest;
import com.wanmi.sbc.order.api.response.trade.TradeListAllResponse;
import com.wanmi.sbc.order.bean.dto.TradeQueryDTO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class PayingMemberJobHandler {

    @Autowired
    private PayingMemberCustomerRelQueryProvider payingMemberCustomerRelQueryProvider;

    @Autowired
    private PayingMemberCustomerRelProvider payingMemberCustomerRelProvider;

    @Autowired
    private TradeQueryProvider tradeQueryProvider;

    @Autowired
    private PayingMemberLevelQueryProvider payingMemberLevelQueryProvider;

    /**
     * 降级定时任务
     * @throws Exception
     */
    @XxlJob(value = "degradationLevelJobHandler")
    public void execute() throws Exception {
        //查询到期了的会员
        BaseResponse<PayingMemberCustomerRelExpiraResponse> expiration =
                payingMemberCustomerRelQueryProvider.findExpiration();
        List<PayingMemberCustomerRelVO> payingMemberCustomerRelVOList = expiration.getContext().getPayingMemberCustomerRelVOList();
        if(CollUtil.isEmpty(payingMemberCustomerRelVOList)){
            return;
        }


        BaseResponse<PayingMemberLevelListNewResponse> payingMemberLevelListNewResponseBaseResponse =
                payingMemberLevelQueryProvider.listAllPayingMemberLevelNew();

        List<PayingMemberLevelBaseVO> payingMemberLevelVOList =
                payingMemberLevelListNewResponseBaseResponse.getContext().getPayingMemberLevelVOList();


        //判断会员有效期内消费金额
        for(PayingMemberCustomerRelVO payingMemberCustomerRelVO:payingMemberCustomerRelVOList){
            String customerId = payingMemberCustomerRelVO.getCustomerId();

            CustomerTradeListRequest tradeListAllRequest = CustomerTradeListRequest.builder()
                    .customerId(customerId)
                    .build();
            BaseResponse<TradeListAllResponse> tradeListAllResponse =
                    tradeQueryProvider.getListByCustomerId(tradeListAllRequest);

            //计算消费金额
            BigDecimal totalPay = BigDecimal.ZERO;
            tradeListAllResponse.getContext().getTradeVOList().stream()
                    .forEach(tradeVO -> {
                        BigDecimal payCash = tradeVO.getTradePrice().getTotalPayCash();
                        totalPay.add(payCash);
                    });

            //判断用户当前等级
            PayingMemberLevelBaseVO payingMemberLevelBaseVO = payingMemberLevelVOList.stream()
                    .filter(payingMemberLevelVO -> payingMemberLevelVO.getLevelId().equals(payingMemberCustomerRelVO.getLevelId()))
                    .findFirst().get();

            String payingMemberLevelName = payingMemberLevelBaseVO.getPayingMemberLevelName();



            Integer levelId = null;

            BigDecimal basePay = BigDecimal.ZERO;
            if(payingMemberLevelName.equals("V1")){
                basePay = BigDecimal.valueOf(500);
            } else if(payingMemberLevelName.equals("V2")){
                basePay = BigDecimal.valueOf(1000);

                levelId = payingMemberLevelVOList.stream()
                        .filter(payingMemberLevelVO ->
                                payingMemberLevelVO.getPayingMemberLevelName().equals("V1")).findFirst()
                        .get().getLevelId();
            } else if(payingMemberLevelName.equals("V3")){
                levelId = payingMemberLevelVOList.stream()
                        .filter(payingMemberLevelVO ->
                                payingMemberLevelVO.getPayingMemberLevelName().equals("V2")).findFirst()
                        .get().getLevelId();

                basePay = BigDecimal.valueOf(2000);
            }
            if(totalPay.compareTo(basePay) >= 0){
                continue;
            }



            //降级
            PayingMemberDegradationRequest request = PayingMemberDegradationRequest.builder()
                    .customerId(customerId)
                    .levelId(levelId)
                    .build();
            payingMemberCustomerRelProvider.degradation(request);
        }


    }
}
