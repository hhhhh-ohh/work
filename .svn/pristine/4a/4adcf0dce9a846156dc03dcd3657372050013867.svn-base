package com.wanmi.sbc.elastic.base.clientconfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author edz
 * @className RestElasticSearchClientConfig
 * @description TODO
 * @date 2023/4/23 下午5:34
 **/
//@Configuration
public class RestElasticSearchClientConfig {

//    @Value("${spring.elasticsearch.rest.password}")
//    private String password;
//
//    @Value("${spring.elasticsearch.rest.username}")
//    private String username;
    /**
     * 修改客户端的keepalive时间为3分钟
     */
//    @Bean
//    public RestHighLevelClient restHighLevelClient(@Autowired RestClientBuilder restClientBuilder){
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        if(StringUtils.isNotBlank(password)){
//            //填充用户名密码
//            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
//        }
//        return new RestHighLevelClient(restClientBuilder.setHttpClientConfigCallback(requestConfig -> {
//                    if(StringUtils.isNotBlank(password)){
//                        requestConfig.setDefaultCredentialsProvider(credentialsProvider);
//                    }
//                    requestConfig.setKeepAliveStrategy((response, context) -> TimeUnit.MINUTES.toMillis(3));
//                    return requestConfig;
//                }
//            )
//        );
//    }
}
