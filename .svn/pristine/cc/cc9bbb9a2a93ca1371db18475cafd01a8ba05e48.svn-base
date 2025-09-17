package com.wanmi.sbc.marketing;

import com.alibaba.fastjson2.JSONObject;
import com.wanmi.sbc.marketing.newplugin.MarketingPluginAdapter;
import com.wanmi.sbc.marketing.pluginconfig.service.MarketingPluginConfigService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanggaolei
 * @className com.wanmi.sbc.marketing.PluginTest
 * @description
 * @date 2021/5/31 15:11
 */
@SpringBootTest
@Slf4j
public class PluginTest {
    @Autowired MarketingPluginAdapter marketingPluginAdapter;
    @Autowired MarketingPluginConfigService marketingPluginConfigService;

    @Test
    public void goodsDetail() {
        log.info("test");
        marketingPluginAdapter.goodsDetail(null);
    }

    @Test
    public void getList() {
        log.info("getList");
        List list = marketingPluginConfigService.getListByCache();
        log.info(JSONObject.toJSONString(list));
    }

}
