package com.wanmi.sbc.job.linkedmall;

import com.wanmi.sbc.common.enums.ThirdPlatformType;
import com.wanmi.sbc.common.enums.VASConstants;
import com.wanmi.sbc.order.api.provider.thirdplatformreturn.ThirdPlatformReturnOrderProvider;
import com.wanmi.sbc.order.api.request.linkedmall.ThirdPlatformReturnOrderSyncRequest;
import com.wanmi.sbc.util.CommonUtil;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务Handler（Bean模式）
 * linkedMall退单定时任务
 */
@Component
@Slf4j
public class LinkedMallReturnOrderJobHandler {

    @Autowired
    private ThirdPlatformReturnOrderProvider thirdPlatformReturnOrderProvider;

    @Autowired
    private CommonUtil commonUtil;

    /**
     * linkedMall订单定时任务
     */
    @XxlJob(value = "LinkedMallReturnOrderJobHandler")
    public void execute() throws Exception {
        boolean flag = commonUtil.findVASBuyOrNot(VASConstants.THIRD_PLATFORM_LINKED_MALL);
        if (!flag) {
            return;
        }
        XxlJobHelper.log("linkedmall退单定时任务执行 " + LocalDateTime.now());
        long start = System.currentTimeMillis();
        log.info("linkedmall退单定时任务执行 {}", LocalDateTime.now());
        thirdPlatformReturnOrderProvider.syncStatus(ThirdPlatformReturnOrderSyncRequest.builder().thirdPlatformType(ThirdPlatformType.LINKED_MALL).build());
        log.info("linkedmall退单定时任务执行，耗时：{}ms ", (System.currentTimeMillis() - start));
    }
}
