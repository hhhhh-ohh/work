package com.wanmi.sbc.job;

import com.wanmi.sbc.empower.api.provider.map.MapQueryProvider;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务Handler（Bean模式）
 * 初始化平台地址
 */
@Component
@Slf4j
public class PlatformAddressInitJobHandler {

    @Autowired
    private MapQueryProvider mapQueryProvider;

    @XxlJob(value = "PlatformAddressInitJobHandler")
    public void execute(String param) throws Exception {
        log.info("初始化平台地址定时任务执行 {}", LocalDateTime.now());
        // 自动确认收货前24小时
        try {
            mapQueryProvider.initGaoDeDistrict();
        } catch (Exception e){
            log.error("初始化平台地址异常", e);
        }
        log.info("初始化平台地址定时任务执行结束 {}", LocalDateTime.now());
    }

}
