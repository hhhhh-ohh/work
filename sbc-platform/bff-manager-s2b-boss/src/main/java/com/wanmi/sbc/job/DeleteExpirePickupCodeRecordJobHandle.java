package com.wanmi.sbc.job;

import com.wanmi.sbc.common.enums.OperateType;
import com.wanmi.sbc.common.util.Constants;
import com.wanmi.sbc.customer.api.request.customer.CustomerGetByIdRequest;
import com.wanmi.sbc.customer.api.request.points.CustomerPointsDetailAddRequest;
import com.wanmi.sbc.customer.api.response.points.CustomerPointsExpireResponse;
import com.wanmi.sbc.customer.bean.enums.PointsServiceType;
import com.wanmi.sbc.order.api.provider.pickupcoderecord.PickupCodeRecordProvider;
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
 * @author lvzhenwei
 * @className DeleteExpirePickupCodeRecordJobHandle
 * @description 删除已过期的提货码
 * @date 2023/4/19 4:40 下午
 **/
@Component
@Slf4j
public class DeleteExpirePickupCodeRecordJobHandle {

    @Autowired
    PickupCodeRecordProvider pickupCodeRecordProvider;

    @XxlJob(value = "deleteExpirePickupCodeRecordJobHandle")
    public void execute() throws Exception {
        pickupCodeRecordProvider.deleteExpirePickupCodeRecord();
    }
}
