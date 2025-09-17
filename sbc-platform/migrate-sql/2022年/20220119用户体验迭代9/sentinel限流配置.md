##1、nacos配置配置
### pc、mobile配置内容分别加上如下内容 ip为服务器ip地址
```properties
#sentinel限流
feign.sentinel.enabled=true
spring.cloud.sentinel.transport.dashboard=${ip}:7000
spring.cloud.sentinel.datasource.flow.nacos.server-addr=${ip}:8848
spring.cloud.sentinel.datasource.flow.nacos.data-id=${spring.application.name}-sentinel-flow
spring.cloud.sentinel.datasource.flow.nacos.group-id=DEFAULT_GROUP
spring.cloud.sentinel.datasource.flow.nacos.rule-type=flow
```

### 新增配置sbc-mobile-sentinel-flow
配置内容：参照mobile-sentinel-flow内容
```
Data ID: sbc-mobile-sentinel-flow
Group: DEFAULT_GROUP
```

### 新增配置sbc-pc-sentinel-flow
配置内容：参照pc-sentinel-flow内容
```
Data ID: sbc-pc-sentinel-flow
Group: DEFAULT_GROUP
```
##2、安装sentinel-dashboard