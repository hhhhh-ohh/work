package com.wanmi.sbc.job;

import com.wanmi.sbc.goods.api.provider.storetobeevaluate.StoreTobeEvaluateQueryProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author lvzhenwei
 * @Description 店铺服务自动评价定时任务
 * @Date 14:17 2019/4/9
 * @Param
 * @return
 **/
@Component
@Slf4j
public class AutoStoreEvaluateJobHandler {

    @Autowired
    private StoreTobeEvaluateQueryProvider storeTobeEvaluateQueryProvider;

    @XxlJob(value = "autoStoreEvaluateJobHandler")
    public void execute() throws Exception {
        storeTobeEvaluateQueryProvider.autoStoreEvaluate();
    }
}