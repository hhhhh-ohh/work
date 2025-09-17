# nacos
## pc、mobile配置内容分别加上如下内容 ip为服务器ip地址,自己配置一下
#sentinel限流
feign.sentinel.enabled=true
spring.cloud.sentinel.transport.dashboard=${ip}:7000
spring.cloud.sentinel.datasource.flow.nacos.server-addr=${ip}:8848
spring.cloud.sentinel.datasource.flow.nacos.data-id=${spring.application.name}-sentinel-flow
spring.cloud.sentinel.datasource.flow.nacos.group-id=DEFAULT_GROUP
spring.cloud.sentinel.datasource.flow.nacos.rule-type=flow


## 新增nacos配置文件sbc-mobile-sentinel-flow
Data ID: sbc-mobile-sentinel-flow
Group: DEFAULT_GROUP


## 新增nacos配置文件sbc-pc-sentinel-flow
Data ID: sbc-pc-sentinel-flow
Group: DEFAULT_GROUP

# 接口限流配置里面的json内容可以请求/config/create/sentinelConfigure这个接口生成
#接口入参
# {
#      "interfaceList":["/login","/goods/skuListFront"],   //接口数组
#      "countList":["0","2000"],                           //每个接口对应的限流值 
#      "flowIdList":["10001","10002"]                      //flowid
#  }

#安装sentinel-dashboard