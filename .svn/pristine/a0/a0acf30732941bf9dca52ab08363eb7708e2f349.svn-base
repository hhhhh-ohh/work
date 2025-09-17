package com.wanmi.sbc.common.canary;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import com.alibaba.cloud.nacos.loadbalancer.NacosLoadBalancer;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhanggaolei
 * @className CustomNaocsLoadBalancer
 * @description
 * @date 2022/10/24 11:07
 **/
public class CustomNaocsLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final Logger log = LoggerFactory.getLogger(NacosLoadBalancer.class);

    private final String serviceId;

    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final NacosDiscoveryProperties nacosDiscoveryProperties;

    public CustomNaocsLoadBalancer(
            ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
            String serviceId, NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.serviceId = serviceId;
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
                .getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get().next().map(this::getInstanceResponse);
    }

    private Response<ServiceInstance> getInstanceResponse(
            List<ServiceInstance> serviceInstances) {
        if (serviceInstances.isEmpty()) {
            log.warn("No servers available for service: " + this.serviceId);
            return new EmptyResponse();
        }

        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            String version = this.nacosDiscoveryProperties.getMetadata().get("version");

            List<ServiceInstance> instancesToChoose = serviceInstances;
            if (StringUtils.isNotBlank(clusterName)) {
                List<ServiceInstance> sameClusterInstances = serviceInstances.stream()
                        .filter(serviceInstance -> {
                            String cluster = serviceInstance.getMetadata()
                                    .get("nacos.cluster");
                            return StringUtils.equals(cluster, clusterName);
                        }).filter(serviceInstance -> {
                            String metaVersion = serviceInstance.getMetadata().get("version");
                            return verifyVersion(version,metaVersion);
                        }).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                    instancesToChoose = sameClusterInstances;
                }
            }
            else {
                log.warn(
                        "A cross-cluster call occurs，name = {}, clusterName = {}, instance = {}",
                        serviceId, clusterName, serviceInstances);
            }

            ServiceInstance instance = NacosBalancer
                    .getHostByRandomWeight3(instancesToChoose);

            return new DefaultResponse(instance);
        }
        catch (Exception e) {
            log.warn("NacosLoadBalancer error", e);
            return null;
        }

    }

    private boolean verifyVersion(String thisVersion, String otherVersion) {
        if (StringUtils.isEmpty(thisVersion)) {
            return true;
        }
        if (StringUtils.isEmpty(otherVersion)) {
            return false;
        }
        String[] thisArr = thisVersion.split("\\,");
        String[] otherArr = otherVersion.split("\\,");
        return org.apache.commons.collections4.CollectionUtils.intersection(Arrays.asList(thisArr), Arrays.asList(otherArr)).size()
                > 0;
    }

}
