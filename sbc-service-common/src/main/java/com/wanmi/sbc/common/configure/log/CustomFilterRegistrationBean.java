package com.wanmi.sbc.common.configure.log;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * @author zhanggaolei
 */
public class CustomFilterRegistrationBean extends FilterRegistrationBean {
    public CustomFilterRegistrationBean(RequestLogProperties  requestLogProperties){
        super();
        setFilter(new RequestLogFilter());
        addInitParameter("NEED_RESULT", String.valueOf(requestLogProperties.isNeedParam()));
        addInitParameter("NEED_PARAM", String.valueOf(requestLogProperties.isNeedParam()));
        addInitParameter("MAX_RESULT_LENGTH", String.valueOf(requestLogProperties.getMaxResultLength()));
        addInitParameter("MAX_BODY_LENGTH", String.valueOf(requestLogProperties.getMaxBodyLength()));
        addInitParameter("EXCLUDE_PATTERNS", requestLogProperties.getExcludePatterns());
        addInitParameter("EXCLUDE_METHODS", requestLogProperties.getExcludeMethods());
        addInitParameter("LOG_TYPE", String.valueOf(requestLogProperties.getLogType()));
        addInitParameter("EXCLUDE_HEADERS", requestLogProperties.getExcludeHeaders());
        addInitParameter("INCLUDE_HEADERS", requestLogProperties.getIncludeHeaders());
        addInitParameter("NEED_BLUR_PASSWORD_FOR_PARAM", String.valueOf(requestLogProperties.isNeedBlurPasswordForParam()));
        addInitParameter("PASSWORD_FIELD_REGEX", requestLogProperties.getPasswordFieldRegex());
        addInitParameter("SITE_OWNER_KEY", requestLogProperties.getSiteOwnerKey());
        addInitParameter("REQUEST_ID_KEY", requestLogProperties.getRequestIdKey());
        addInitParameter("SESSION_ID_KEY", requestLogProperties.getSessionIdKey());

        addUrlPatterns(requestLogProperties.getUrlPatterns());
        setOrder(requestLogProperties.getOrder());
    }
}
