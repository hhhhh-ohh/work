//package com.wanmi.config;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
//import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancerClientConfiguration;
////import com.alibaba.cloud.nacos.ribbon.ConditionalOnRibbonNacos;
////import com.alibaba.cloud.nacos.ribbon.NacosRibbonClientConfiguration;
////import com.netflix.client.config.IClientConfig;
////import com.netflix.loadbalancer.ServerList;
//import com.wanmi.sbc.common.canary.ConditionalOnNacosCanaryEnabled;
////import com.wanmi.sbc.common.canary.CustomNacosServerList;
//import com.wanmi.sbc.common.canary.CustomNaocsLoadBalancer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
////import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
//import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
//import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
///**
// * @author zhanggaolei
// * @className CustomNacosRibbonClientConfiguration
// * @description 自定义nacos负载均衡策略
// * @date 2021/6/3 16:29
// **/
//@Configuration(proxyBeanMethods = false)
////@AutoConfigureBefore(NacosRibbonClientConfiguration.class)
////@ConditionalOnRibbonNacos
//@ConditionalOnNacosCanaryEnabled
//@AutoConfigureBefore(NacosLoadBalancerClientConfiguration.class)
//@ConditionalOnDiscoveryEnabled
//public class CustomNacosRibbonClientConfiguration {
//
////    @Autowired
////    private PropertiesFactory propertiesFactory;
//
////    @Bean
////    public ServerList<?> ribbonServerList(IClientConfig config,
////                                          NacosDiscoveryProperties nacosDiscoveryProperties) {
////        if (this.propertiesFactory.isSet(ServerList.class, config.getClientName())) {
////            ServerList serverList = this.propertiesFactory.get(ServerList.class, config,
////                    config.getClientName());
////            return serverList;
////        }
////        CustomNacosServerList serverList = new CustomNacosServerList(nacosDiscoveryProperties);
////        serverList.initWithNiwsConfig(config);
////        return serverList;
////    }
//    @Bean
//    public ReactorLoadBalancer<ServiceInstance> nacosLoadBalancer(Environment environment,
//                                                                  LoadBalancerClientFactory loadBalancerClientFactory,
//                                                                  NacosDiscoveryProperties nacosDiscoveryProperties) {
//        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
//        return new CustomNaocsLoadBalancer(
//                loadBalancerClientFactory.getLazyProvider(name,
//                        ServiceInstanceListSupplier.class),
//                name, nacosDiscoveryProperties);
//    }
//}
