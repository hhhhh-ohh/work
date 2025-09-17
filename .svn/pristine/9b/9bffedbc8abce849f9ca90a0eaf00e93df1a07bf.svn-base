FROM harbor.kstore.shop/sbc/wm-jdk:17.0.9

ARG jarVersion
COPY service-empower-app/target/service-empower-app-$jarVersion.jar /data/server/bin/sbc.jar

RUN ln -s /data/server/bin/sbc.jar /data/server/bin/server.jar && \
    echo "spring.cloud.nacos.config.server-addr=\${NACOS_ADDRESS:=127.0.0.1:8848}" > /data/server/conf/application.properties && \
    echo "spring.application.name=sbc-service-empower" >> /data/server/conf/application.properties && \
    echo "spring.cloud.nacos.config.group=DEFAULT_GROUP" >> /data/server/conf/application.properties && \
    echo "spring.config.import[0]=optional:nacos:sbc-service-empower.properties" >> /data/server/conf/application.properties && \
    echo "spring.config.import[1]=optional:nacos:common.properties?&refreshEnabled=true" >> /data/server/conf/application.properties && \
    echo "spring.config.import[2]=optional:nacos:variable.properties" >> /data/server/conf/application.properties && \
    echo "spring.config.import[3]=optional:nacos:sbc-seata-conf.properties" >> /data/server/conf/application.properties

ENTRYPOINT ["/bin/bash"]
CMD ["/data/server/bin/startup.sh"]
