package com.wanmi.sbc.common.annotation;


import com.wanmi.sbc.common.configure.log.RequestLogRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhanggaolei
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RequestLogRegistryPostProcessor.class)//由这个类来扫描的
public @interface EnabledRequestLog {
}
