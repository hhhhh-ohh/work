package com.wanmi.sbc.init;

import com.wanmi.sbc.init.service.VASCommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 增值服务_初始化数据
 *
 * @version: 1.0
 */
@Slf4j
@Order(7)
@Component
public class VASInitRunner implements CommandLineRunner {

    @Autowired private VASCommonService vasCommonService;

    @Override
    public void run(String... args) {
        try {
            vasCommonService.init();
        } catch (Exception e) {
            log.error("增值服务初始化, 错误信息", e);
        }
    }
}
