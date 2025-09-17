package com.wanmi.sbc.job.electronic;

import com.wanmi.sbc.marketing.api.provider.electroniccoupon.ElectronicCouponProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuyunpeng
 * @className ElectronicCardNumSyncJobHandler
 * @description 卡券各状态数量统计 每小时
 * @date 2022/2/9 3:47 下午
 **/
@Slf4j
@Component
public class ElectronicCardNumSyncJobHandler {

    @Autowired
    private ElectronicCouponProvider electronicCouponProvider;

    @XxlJob(value="electronicCardNumSyncJobHandler")
    public void execute() throws Exception {
        electronicCouponProvider.cardStateStatistical();
    }
}
