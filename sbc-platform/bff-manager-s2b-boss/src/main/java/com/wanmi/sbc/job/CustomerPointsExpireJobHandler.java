package com.wanmi.sbc.job;

import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.provider.customer.CustomerQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailQueryProvider;
import com.wanmi.sbc.customer.api.provider.points.CustomerPointsDetailSaveProvider;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsExpireResponse;
import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.setting.api.provider.SystemPointsConfigQueryProvider;
import com.wanmi.sbc.setting.api.response.SystemPointsConfigQueryResponse;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务Handler（Bean模式）
 * <p>
 * 定期扣除用户过期积分
 */
@Component
@Slf4j
public class CustomerPointsExpireJobHandler {

    @Autowired
    private SystemPointsConfigQueryProvider systemPointsConfigQueryProvider;

    @Autowired
    private CustomerQueryProvider customerQueryProvider;

    @Autowired
    private CustomerPointsDetailQueryProvider customerPointsDetailQueryProvider;

    @Autowired
    private CustomerPointsDetailSaveProvider customerPointsDetailSaveProvider;

    @XxlJob(value = "customerPointsExpireJobHandler")
    public void execute() throws Exception {
        // 1.查询积分配置
        SystemPointsConfigQueryResponse pointsConfig = systemPointsConfigQueryProvider.querySystemPointsConfig().getContext();
        int pointsExpireMonth = pointsConfig.getPointsExpireMonth();
        //过期日第二天才扣除用户过期积分
        int pointsExpireDay = pointsConfig.getPointsExpireDay() + 1;
        // 1.1 判断当天是否为去年积分过期的日子
        if (pointsExpireMonth == LocalDate.now().getMonthValue() && pointsExpireDay == LocalDate.now().getDayOfMonth()) {
            // 2. 查询用户id列表
            List<String> customerIds = customerQueryProvider.listCustomerId().getContext().getCustomerIdList();
            // 2.1 创建线程池，暂时是每50个用户分配一个线程
            ExecutorService executor = Executors.newFixedThreadPool(50);
            // 2.2 遍历用户列表
            customerIds.forEach(customerId -> {
                executor.submit(() -> {
                    // 3.查询用户即将过期积分
                    CustomerPointsExpireResponse response = customerPointsDetailQueryProvider
                            .queryWillExpirePointsForCronJob(new CustomerGetByIdRequest(customerId))
                            .getContext();
                    if (response.getWillExpirePoints() > 0) {
                        customerPointsDetailSaveProvider.add(CustomerPointsDetailAddRequest.builder()
                                .customerId(customerId)
                                .type(OperateType.DEDUCT)
                                .serviceType(PointsServiceType.EXPIRE)
                                .points(response.getWillExpirePoints())
                                .build());
                    }
                });
            });
            executor.shutdown();
            //等待线程池结束
            try {
                if (!executor.awaitTermination(Constants.NUM_24, TimeUnit.HOURS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
