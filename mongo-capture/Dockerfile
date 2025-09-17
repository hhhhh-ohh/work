FROM harbor.kstore.shop/sbc/wm-jdk:17.0.9

COPY target/mongo-capture.jar /data/server/bin/sbc.jar

RUN ln -s /data/server/bin/sbc.jar /data/server/bin/server.jar && \
    sed -i "s/application.properties/application.yml/g" /data/server/conf/env.conf

ENTRYPOINT ["/bin/bash"]
CMD ["/data/server/bin/startup.sh"]
