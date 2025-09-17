FROM harbor.kstore.shop/sbc/wm-jdk:17.0.9

ARG jarVersion
COPY target/dbreplay$jarVersion.jar /data/server/bin/sbc.jar
COPY mongo /data/server/conf/mongo

RUN ln -s /data/server/bin/sbc.jar /data/server/bin/server.jar

ENTRYPOINT ["/bin/bash"]
CMD ["/data/server/bin/startup.sh"]
