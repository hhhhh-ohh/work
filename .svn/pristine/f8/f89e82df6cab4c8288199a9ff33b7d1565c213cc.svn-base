package com.wanmi.sbc.init;

import com.wanmi.sbc.setting.api.provider.openapisetting.OpenApiSettingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 开放平台配置信息初始化Redis
 *
 * @author wur
 * @date: 2021/4/27 11:05
 */
@Slf4j
@Order(9)
@Component
public class OpenApiSettingRunner implements CommandLineRunner {

    @Autowired private OpenApiSettingProvider openApiSettingProvider;

    @Override
    public void run(String... args)  {
        log.info("==============开放平台配置信息初始化Redis，开始====================");
        try{
            openApiSettingProvider.initOpenApiCache();
        } catch (Exception e) {
            log.error("系统初始化（公司信息、默认素材分类等）, 错误信息", e);
        }
        log.info("========开放平台配置信息初始化Redis，完成==============");
    }
}
