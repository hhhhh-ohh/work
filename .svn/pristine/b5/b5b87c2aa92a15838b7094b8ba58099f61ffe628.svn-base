FROM harbor.kstore.shop/sbc/wm-jdk:17.0.9

ARG jarVersion
COPY service-elastic-app/target/service-elastic-app-$jarVersion.jar /data/server/bin/sbc.jar
COPY service-elastic-app/target/3rd-lib /data/server/3rd-lib
COPY service-elastic-app/target/resources /data/server/resources

RUN ln -s /data/server/bin/sbc.jar /data/server/bin/server.jar && \
    echo "spring.cloud.nacos.config.server-addr=\${NACOS_ADDRESS:=127.0.0.1:8848}" > /data/server/conf/application.properties && \
    echo "spring.application.name=sbc-service-elastic" >> /data/server/conf/application.properties && \
    echo "spring.config.import[0]=optional:nacos:sbc-service-elastic.properties" >> /data/server/conf/application.properties && \
    echo "spring.config.import[1]=optional:nacos:variable.properties" >> /data/server/conf/application.properties && \
    echo "spring.config.import[2]=optional:nacos:sbc-seata-conf.properties" >> /data/server/conf/application.properties

ENTRYPOINT ["/bin/bash"]
CMD ["/data/server/bin/startup.sh"]
