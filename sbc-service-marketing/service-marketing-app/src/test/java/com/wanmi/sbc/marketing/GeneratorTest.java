package com.wanmi.sbc.marketing;

import com.wanmi.sbc.marketing.bean.constant.Constant;
import com.wanmi.sbc.marketing.util.common.MarketingGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class GeneratorTest {

    @Autowired MarketingGeneratorService generatorService;

    @Test
    public void generateGiftCardNoTest() {
        log.info("generateGiftCardNoTest");
        for (int i = 0; i < 100; i++) {
            String no = generatorService.generateGiftCardNo(Constant.SERVICE_ID, Constant.MID);
            log.info(no);
        }
    }
}
