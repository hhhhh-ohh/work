package com.wanmi.sbc.job;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.wanmi.sbc.common.base.BaseResponse;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.growthvalue.CustomerGrowthValueProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerIdsByMembershipExpiredTimeRequest;
import com.wanmi.sbc.customer.api.request.growthvalue.CustomerGrowthValueAddRequest;
import com.wanmi.sbc.customer.api.response.customer.CustomerIdListResponse;
import com.wanmi.sbc.customer.bean.enums.GrowthValueServiceType;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class AutoDownCustomerLevelJobHandler {

    @Autowired
    private CustomerGrowthValueProvider customerGrowthValueProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;


    @XxlJob(value = "autoDownCustomerLevelJobHandler")
    public void execute() throws Exception {
        String param = XxlJobHelper.getJobParam();
        log.info("会员到期降级任务-autoDownCustomerLevelJobHandler start:{}", param);
        LocalDateTime startTime = LocalDate.now().atStartOfDay();
        LocalDateTime endTime = LocalDate.now().plusDays(1).atStartOfDay();
        if (StrUtil.isNotBlank(param)) {
            try {
                LocalDate parsedDate = LocalDate.parse(param, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                startTime = parsedDate.atStartOfDay();
                endTime = parsedDate.plusDays(1).atStartOfDay();
            } catch (Exception e) {
                log.error("日期参数解析失败: {}", param, e);
                throw new RuntimeException("无效的日期参数: " + param, e);

            }
        }
        CustomerIdListResponse customerIdListResponse = customerQueryProvider.CustomerIdsByMembershipExpiredTimeRequest(CustomerIdsByMembershipExpiredTimeRequest.builder().startTime(startTime).endTime(endTime).build()).getContext();
        List<String> customerIdList = customerIdListResponse.getCustomerIdList();
        if (CollUtil.isEmpty(customerIdList)){
            log.info("无会员到期");
            return;
        }
        log.info("会员到期:{}", customerIdList);
        for (String customerId : customerIdList) {
            customerGrowthValueProvider.increaseGrowthValue(CustomerGrowthValueAddRequest.builder().customerId(customerId)
                    .type(OperateType.GROWTH)
                    .serviceType(GrowthValueServiceType.DOWNLEVEL)
                    .opTime(LocalDateTime.now())
                    .build());
        }
        log.info("会员到期降级任务处理完成");
    }

}
