package com.wanmi.sbc.job;

import com.wanmi.sbc.customer.api.provider.goodsfootmark.GoodsFootmarkSaveProvider;
import com.wanmi.sbc.customer.api.request.goodsfootmark.GoodsFootmarkDelByTimeRequest;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 *
 *
 * @author zhangrukun
 * @date: 2022/5/30 8:49
 */

@Component
@Slf4j
public class FootMarkSyncJobHandler {

    @Autowired
    private GoodsFootmarkSaveProvider goodsFootmarkSaveProvider;

    @XxlJob(value = "footMarkSyncJobHandler")
    @Transactional(rollbackFor = Exception.class)
    public void execute(String param) throws Exception {
        //清除3个月之后的失效数据
        GoodsFootmarkDelByTimeRequest request =
                GoodsFootmarkDelByTimeRequest.builder().updateTimeEnd(LocalDate.now().minusDays(90)).build();
        goodsFootmarkSaveProvider.deleteByUpdateTime(request);
        log.info("我的足迹数据清除成功");
    }
}
