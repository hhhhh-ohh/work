package com.wanmi.sbc.page;

import com.wanmi.sbc.common.cache.CacheConstants;
import com.wanmi.sbc.common.cache.CacheType;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

/**
 * 魔方首页面缓存Controller
 */
@RestController
@Validated
@RequestMapping("/magic-page")
@Tag(name = "MagicPageController", description = "mobile 查询缓存的html页面信息bff")
@Slf4j
public class MagicPageController {

    Logger logger = LoggerFactory.getLogger(MagicPageController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${magic.page.main.config}")
    private String magicPageMainConfig;

    /**
     * 获取魔方首页配置信息的缓存，缓存的是 https://app-render.xxxx/magic/d2cStore/000000/weixin/index 的返回值
     * 两级缓存，实际配置在魔方应用中。业务数据库缓存一份，本接口再缓存1分钟
     *
     * @return 返回魔方首页配置内容
     */
    @Operation(summary = "获取魔方首页配置信息")
    @RequestMapping(value = "/main-config", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Cacheable(key = "'MAGIC_PAGE_MAIN_CONFIG'",value = CacheConstants.GLOBAL_CACHE_NAME)
    public String getMainConfig() {
        String mainConfig = restTemplate.getForObject(magicPageMainConfig, String.class);
        logger.info("获取并缓存魔方首页配置信息");
        return mainConfig;
    }

}
