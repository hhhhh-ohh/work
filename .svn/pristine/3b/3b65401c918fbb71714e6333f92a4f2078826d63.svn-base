package com.wanmi.sbc.job;

import com.wanmi.sbc.goods.api.provider.goodstobeevaluate.GoodsTobeEvaluateQueryProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author lvzhenwei
 * @Description 商品自动评价定时任务
 * @Date 14:56 2019/4/10
 * @Param
 * @return
 **/
@Component
@Slf4j
public class AutoGoodsEvaluateJobHandler {

    @Autowired
    private GoodsTobeEvaluateQueryProvider goodsTobeEvaluateQueryProvider;

    @XxlJob(value = "autoGoodsEvaluateJobHandler")
    public void execute() throws Exception {
        goodsTobeEvaluateQueryProvider.autoGoodsEvaluate();
    }
}
