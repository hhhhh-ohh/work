//package com.wanmi.sbc.common.canary;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.ribbon.NacosServer;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractServerList;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.MapUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author zhanggaolei
// * @className CustomNacosServerList
// * @description
// * @date 2021/6/3 9:55
// */
//public class CustomNacosServerList extends AbstractServerList<NacosServer> {
//
//    private NacosDiscoveryProperties discoveryProperties;
//
//    private String serviceId;
//
//    public CustomNacosServerList(NacosDiscoveryProperties discoveryProperties) {
//        this.discoveryProperties = discoveryProperties;
//    }
//
//    @Override
//    public List<NacosServer> getInitialListOfServers() {
//        return getServers();
//    }
//
//    @Override
//    public List<NacosServer> getUpdatedListOfServers() {
//        return getServers();
//    }
//
//    private List<NacosServer> getServers() {
//        try {
//            String group = discoveryProperties.getGroup();
//            List<Instance> instances =
//                    discoveryProperties
//                            .namingServiceInstance()
//                            .selectInstances(serviceId, group, true);
//            return instancesToServerList(instances);
//        } catch (Exception e) {
//            throw new IllegalStateException(
//                    "Can not get service instances from nacos, serviceId=" + serviceId, e);
//        }
//    }
//
//    private List<NacosServer> instancesToServerList(List<Instance> instances) {
//        List<NacosServer> result = new ArrayList<>();
//        if (com.alibaba.nacos.client.naming.utils.CollectionUtils.isEmpty(instances)) {
//            return result;
//        }
//        for (Instance instance : instances) {
//            NacosServer nacosServer = new NacosServer(instance);
//            if (filter(nacosServer)) {
//                result.add(nacosServer);
//            }
//        }
//
//        return result;
//    }
//
//    public String getServiceId() {
//        return serviceId;
//    }
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//        this.serviceId = iClientConfig.getClientName();
//    }
//
//    private boolean filter(NacosServer nacosServer) {
//        if (nacosServer == null) {
//            return false;
//        } else {
//            Map<String, String> map = nacosServer.getMetadata();
//            if (MapUtils.isNotEmpty(map)) {
//                String metaVersion = map.get("version");
//                if (!verifyVersion(discoveryProperties.getMetadata().get("version"), metaVersion)) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    private boolean verifyVersion(String thisVersion, String otherVersion) {
//        if (StringUtils.isEmpty(thisVersion)) {
//            return true;
//        }
//        if (StringUtils.isEmpty(otherVersion)) {
//            return false;
//        }
//        String[] thisArr = thisVersion.split("\\,");
//        String[] otherArr = otherVersion.split("\\,");
//        return CollectionUtils.intersection(Arrays.asList(thisArr), Arrays.asList(otherArr)).size()
//                > 0;
//    }
//}
