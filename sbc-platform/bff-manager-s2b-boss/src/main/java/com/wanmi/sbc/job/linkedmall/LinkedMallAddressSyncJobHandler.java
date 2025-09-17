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
 * 第三方地址初始化自动映射任务
 *
 * @author dyt
 */
@Component
@Slf4j
public class LinkedMallAddressSyncJobHandler {

    @Autowired
    private LinkedMallAddressProvider linkedMallAddressProvider;

    @Autowired
    private CommonUtil commonUtil;

    @XxlJob(value = "linkedMallAddressSyncJobHandler")
    public void execute() throws Exception {
        init();
    }

    @Async
    public void init() {
        //处理初始化需要较长时间，会导致微服务超时，所以用异步
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        if (flag) {
            XxlJobHelper.log("第三方地址初始化自动映射执行 " + LocalDateTime.now());
            linkedMallAddressProvider.init();
        }
    }
}