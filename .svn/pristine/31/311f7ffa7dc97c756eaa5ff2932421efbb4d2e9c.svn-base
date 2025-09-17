package com.wanmi.sbc.order.orderperformance.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 季节服装价格配置类
 * 支持Nacos配置中心动态刷新
 */
@Data
@ConfigurationProperties
@Component
public class SeasonClothingPriceConfig {

    // 小学价格
    // 春秋装_上衣
    @Value("${season-clothing-price.elementary-spring-top-price:7.0}")
    private BigDecimal elementarySpringTopPrice;
    // 春秋装_裤子
    @Value("${season-clothing-price.elementary-spring-pants-price:8.0}")
    private BigDecimal elementarySpringPantsPrice;
    // 夏装_上衣
    @Value("${elementary-summer-top-price:1.5}")
    private BigDecimal elementarySummerTopPrice;
    // 夏装_裤子
    @Value("${elementary-summer-pants-price:1.5}")
    private BigDecimal elementarySummerPantsPrice;
    // 冬装_上衣
    @Value("${elementary-winter-top-price:6.0}")
    private BigDecimal elementaryWinterTopPrice;
    // 冬装_裤子
    @Value("${elementary-winter-pants-price:2.0}")
    private BigDecimal elementaryWinterPantsPrice;


    // 中学价格
    // 春秋装_上衣
    @Value("${middle-spring-top-price:3.0}")
    private BigDecimal middleSpringTopPrice;
    // 春秋装_裤子
    @Value("${middle-spring-pants-price:2.0}")
    private BigDecimal middleSpringPantsPrice;
    // 夏装_上衣
    @Value("${middle-summer-top-price:2.0}")
    private BigDecimal middleSummerTopPrice;
    // 夏装_裤子
    @Value("${middle-summer-pants-price:2.0}")
    private BigDecimal middleSummerPantsPrice;
    // 冬装_上衣
    @Value("${middle-winter-top-price:6.5}")
    private BigDecimal middleWinterTopPrice;
    // 冬装_裤子
    @Value("${middle-winter-pants-price:2.5}")
    private BigDecimal middleWinterPantsPrice;

}
