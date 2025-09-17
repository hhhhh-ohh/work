安装sentinel-dashboard
mkdir /data/sentinel-dashboard
上传jar包sentinel-dashboard-1.7.2.jar

启动命令
java -Dserver.port=7000 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.7.2.jar

访问地址控制台 ip+-Dserver.port  例如 127.0.0.1:7000

默认用户名和密码都是sentinel

在服务应用的env文件末尾中加上(env目录：/data/s2b/mobile/conf，其他项目也类似)
"-Dcsp.sentinel.dashboard.server=ip:7000"

用户可以通过如下参数进行配置：
-Dsentinel.dashboard.auth.username=sentinel 用于指定控制台的登录用户名为 sentinel；
-Dsentinel.dashboard.auth.password=123456 用于指定控制台的登录密码为 123456；如果省略这两个参数，默认用户和密码均为 sentinel；
-Dserver.servlet.session.timeout=7200 用于指定 Spring Boot 服务端 session 的过期时间，如 7200 表示 7200 秒；60m 表示 60 分钟，默认为 30 分钟；