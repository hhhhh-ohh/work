package com.wanmi.sbc.configure;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wanmi.sbc.common.util.XssUtils;
import com.wanmi.sbc.common.util.auth.SecretClassLoader;
import com.wanmi.sbc.common.util.auth.Type;
import com.wanmi.sbc.filter.XssFilter;
import com.wanmi.sbc.intercepter.BadWordInterceptor;
import com.wanmi.sbc.intercepter.BossApiIntercepter;
import com.wanmi.sbc.intercepter.DefaultInterceptor;
import com.wanmi.sbc.intercepter.RefererInterceptor;
import jakarta.servlet.Filter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * mvc configuration
 * Created by jinwei on 31/3/2017.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${jwt.excluded-urls}")
    String jwtExcludedUrls;

    @Value("${api.excluded-urls}")
    String apiExcludedUrls;

    @Value("${jwt.excluded-rest-urls}")
    String jwtExcludedRestUrls;

    @Value("${api.excluded-rest-urls}")
    String apiExcludedRestUrls;

    @Value("${swagger.enable}")
    boolean enable;

    @Value("${api.badWords.addPath-rest-urls}")
    String apiBadWordsAddPathUrls;

    @Value("${api.badWords.excludePath-rest-urls}")
    String apiBadWordsExcludePathUrls;

    @Value("${api.badWords.allPath-flag}")
    boolean apiBadWordsAllPathFlag;

    @Value("#{'${cors.allowedOrigins:*}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${xss.param-names}'.split(',')}")
    private List<String> paramNameList;

    @Value("${open.referer.check}")
    boolean openRefererCheck;

    @Value("#{'${referer.check.url}'.split(',')}")
    List<String> refererCheckUrlList;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }

    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new JsonHtmlXssDeserializer(String.class));
        objectMapper.registerModule(module);
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(allowedOrigins);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new CorsFilter(source));

        registrationBean.setFilter(xssFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter("excludeFieldsName", "x-pingplusplus-signature,applyList," +
                "QRCodeImgSrc,arrList,skus,ids,skuIds,items,province,user-agent,signPubKeyCert,fund_bill_list");
        registrationBean.setName("xssFilter");
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public Filter xssFilter() {
        return new XssFilter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOriginPatterns(allowedOrigins.stream().toArray(String[]::new))
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    BossApiIntercepter bossApiIntercepter() {
        return new BossApiIntercepter(jwtExcludedRestUrls, apiExcludedRestUrls);
    }

    @Bean
    BadWordInterceptor badWordInterceptor() {
        return new BadWordInterceptor(apiBadWordsAddPathUrls, apiBadWordsAllPathFlag);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(refererInterceptor());
        registry.addInterceptor(generateCommonInterceptor());
        registry.addInterceptor(bossApiIntercepter())
                .excludePathPatterns(jwtExcludedUrls.split(",")).excludePathPatterns(apiExcludedUrls.split(","));

        if (StringUtils.isNotBlank(apiBadWordsAddPathUrls) || Objects.equals(Boolean.TRUE, apiBadWordsAllPathFlag)) {
            if (Objects.equals(Boolean.TRUE, apiBadWordsAllPathFlag)) {
                //拦截全部路径
                JSONObject apiBadWordsexcludePathUrlsMap = JSONObject.parseObject(apiBadWordsExcludePathUrls);
                registry.addInterceptor(badWordInterceptor()).addPathPatterns("/**").excludePathPatterns(apiBadWordsexcludePathUrlsMap.keySet().toArray
                        (new String[apiBadWordsexcludePathUrlsMap.keySet().size()]));
            } else {
                JSONObject apiBadWordsAddPathUrlsMap = JSONObject.parseObject(apiBadWordsAddPathUrls);
                registry.addInterceptor(badWordInterceptor()).addPathPatterns(apiBadWordsAddPathUrlsMap.keySet().toArray
                        (new String[apiBadWordsAddPathUrlsMap.keySet().size()]));
            }
        }
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Bean
    RefererInterceptor refererInterceptor() {
        return new RefererInterceptor(openRefererCheck, refererCheckUrlList);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (enable) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    private HandlerInterceptor generateCommonInterceptor(){
        HandlerInterceptor handlerInterceptor = null;
        try {
            SecretClassLoader mcl = new SecretClassLoader();
            Class<?> cla ;
            Constructor<?> constructor ;
            try {
                cla = Class.forName(new String(Type.getType(),"UTF-8"), true, mcl);
                constructor = cla.getConstructor();
                handlerInterceptor = (HandlerInterceptor)constructor.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return handlerInterceptor;
        } catch (Exception e) {
            handlerInterceptor = new DefaultInterceptor();
        }catch (Throwable t){
            handlerInterceptor = new DefaultInterceptor();
        }
        return handlerInterceptor;
    }

    /**
     * 对入参的json进行转义
     */
    class JsonHtmlXssDeserializer extends JsonDeserializer<String> {

        public JsonHtmlXssDeserializer(Class<String> string) {
            super();
        }

        @Override
        public Class<String> handledType() {
            return String.class;
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            String value = jsonParser.getValueAsString();
            if(CollectionUtils.isEmpty(paramNameList)) {
                return value;
            }
            String currentName = jsonParser.getCurrentName();
            if (Objects.nonNull(value) && paramNameList.contains(currentName)) {
                return StringEscapeUtils.escapeHtml4(value);
            }
            //含<可能是富文本内容，则做正则替换
            if(Objects.nonNull(value) && value.contains("<") && value.contains(">")) {
                return value.replaceAll(XssUtils.XSS_REGEX, "");
            }
            return value;
        }
    }
}
