package com.wanmi.sbc.job.linkedmall;

import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.util.CommonUtil;
import com.wanmi.sbc.vas.api.provider.linkedmall.address.LinkedMallAddressProvider;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务Handler
 * 第三方地址增量映射
 *
 * @author dyt
 */
@Component
@Slf4j
public class LinkedMallAddressMappingJobHandler {

    @Autowired
    private LinkedMallAddressProvider linkedMallAddressProvider;

    @Autowired
    private CommonUtil commonUtil;

    @XxlJob(value = "linkedMallAddressMappingJobHandler")
    public void execute() throws Exception {
        init();
    }

    @Async
    public void init() {
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        if (flag) {
            XxlJobHelper.log("第三方地址增量映射执行 " + LocalDateTime.now());
            linkedMallAddressProvider.mapping();
        }
    }
}
