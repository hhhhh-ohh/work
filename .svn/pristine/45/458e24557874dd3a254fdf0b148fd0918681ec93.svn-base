package com.wanmi.sbc.configure;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 * @author qingfeng.zhao
 * @date 2022/3/26
 * @apiNote
 */
@Configuration
public class SpringFoxSwaggerConfig {
//    @Bean
//    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(
//            WebEndpointsSupplier webEndpointsSupplier,
//            ServletEndpointsSupplier servletEndpointsSupplier,
//            ControllerEndpointsSupplier controllerEndpointsSupplier,
//            EndpointMediaTypes endpointMediaTypes,
////            CorsEndpointProperties corsProperties,
//            WebEndpointProperties webEndpointProperties,
//            Environment environment) {
//        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
//        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
//        allEndpoints.addAll(webEndpoints);
//        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
//        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
//        String basePath = webEndpointProperties.getBasePath();
//        EndpointMapping endpointMapping = new EndpointMapping(basePath);
//        boolean shouldRegisterLinksMapping =
//                webEndpointProperties.getDiscovery().isEnabled()
//                        && (org.springframework.util.StringUtils.hasText(basePath)
//                                || ManagementPortType.get(environment)
//                                        .equals(ManagementPortType.DIFFERENT));
//        return new WebMvcEndpointHandlerMapping(
//                endpointMapping,
//                webEndpoints,
//                endpointMediaTypes,
//                null,
//                new EndpointLinksResolver(allEndpoints, basePath),
//                shouldRegisterLinksMapping,
//                null);
//    }
}
