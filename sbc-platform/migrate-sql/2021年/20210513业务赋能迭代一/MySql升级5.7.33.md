本地升级5.7.33
升级前准备
1.准备安装包
      01、从其他服务器拷贝  
      从远程传输到本地，安装包目录： cd /htdocs/software/
      scp root@172.19.26.2:/htdocs/software/mysql-5.7.33-linux-glibc2.12-x86_64.tar.gz /htdocs/software/     
      输入密码，服务器密码，一般000000
      02、从官网下载
      首先进入安装包目录  cd /htdocs/software/
      wget https://dev.mysql.com/get/Downloads/MySQL-5.7/mysql-5.7.33-linux-glibc2.12-x86_64.tar.gz

2.准备安装目录，因为是5.7.33版本的mysql，所以文件夹命名data5733
      mkdir /data5733

1.停机备份MySQL5.6
/data/mysql/bin/mysqladmin -u root -p -S /data/mysql/mysql.sock shutdown
输入数据库密码，一般是Wmi@2019
备份数据目录
cp -r /data/mysql/data /data/mysql/data.56

2.安装MySQL5.7
首先进入安装包目录  cd /htdocs/software/

1.解压软件包并制作软连接
   01、解压
   tar xf mysql-5.7.33-linux-glibc2.12-x86_64.tar.gz
   02、重命名
   mv mysql-5.7.33-linux-glibc2.12-x86_64 mysql-5.7.33
   03、制作软连接
   ln -s /htdocs/software/mysql-5.7.33 /data5733/mysql5733

3.使用MySQL5.7挂5.6的数据目录并启动
修改MySQL5.6配置basedir指向MySQL5.7
# **指定basedir为MySQL5.7软件安装目录**
##################不用复制，##################中是整体复制
##########################################
cat >/data5733/my.cnf<<EOF
[mysqld]
user=mysql
basedir=/data5733/mysql5733
datadir=/data/mysql/data
socket=/data/mysql/mysql.sock
port=3306
server_id=1
innodb_fast_shutdown=0
EOF
############################################

安全模式启动MySQL5.7
/data5733/mysql5733/bin/mysqld_safe --defaults-file=/data5733/my.cnf --skip-grant-tables --skip-networking &
命令验证
ps aux|grep mysql5733
如果类似以下输出，则正常启动了
mysql    18401  1.4  2.3 4929736 719344 ?      Ssl  4月19  14:35 /data5733/mysql5733/bin/mysqld --defaults-file=/data5733/my.cnf

4.升级系统表
/data5733/mysql5733/bin/mysql_upgrade -u root -p -S /data/mysql/mysql.sock
输入密码：Wmi@2019
# 屏幕输出一列OK，最后两行有Upgrade process completed successfully表示升级系统表成功！

5.重启数据库到正常状态
停止MySQL5.7
/data5733/mysql5733/bin/mysqladmin -u root -p -S /data/mysql/mysql.sock shutdown
修改systemd启动脚本的启动目录
##################不用复制，##################中是整体复制
################################################################
cat >/etc/systemd/system/mysql.service<<EOF
[Unit]
Description=MySQL Server
Documentation=man:mysqld(8)
Documentation=http://dev.mysql.com/doc/refman/en/using-systemd.html
After=network.target
After=syslog.target
[Install]
WantedBy=multi-user.target
[Service]
User=mysql
Group=mysql
ExecStart=/data5733/mysql5733/bin/mysqld --defaults-file=/data5733/my.cnf
LimitNOFILE = 5000
EOF
################################################################

6.将服务器配置文件拷贝到当前启动配置
cp /etc/my.cnf /data5733/my.cnf
vim /data5733/my.cnf
输入i进入编辑模式，将basedir   = /data/mysql 替换成 basedir=/data5733/mysql5733，将innodb_additional_mem_pool_size = 16M替换成#innodb_additional_mem_pool_size = 16M，按esc，进入退出模式，按:wq回车保存
若是想不保存退出编辑模式，按esc，然后按:q!回车
cat /data5733/my.cnf进行查看

7.通过命令启动
systemd启动MySQL5.7
systemctl start mysql.service
查看启动日志
tail -500f /data/mysql/data/bogon.err
tail -500f /data/mysql/data/error.log
通过以下命令验证
netstat -lntup|grep mysql
看看是否输出有3306这个端口，如果类似以下输出，则正常启动了
tcp6 0 0 :::3306 :::* LISTEN 2218/mysqld

8.连接并查看
登录查看数据库
/data5733/mysql5733/bin/mysql -u root -p -S /data/mysql/mysql.sock
输入密码：Wmi@2019

9.若是停掉mysql服务，可以通过命令停掉
systemctl stop mysql.service

10.若是无法启动，查询是否存在mysql.service
1.systemctl show mysql.service
可以看到并不是我们文件中的路径

2.解决，先拷贝一个我们的mysql.service文件
cp /etc/systemd/system/mysql.service /etc/systemd/system/mysqld.service

3.执行systemctl start mysqld.service刷新系统文件

4.然后再执行
systemctl show mysql.service

systemctl 启动MySQL5.7
systemctl start mysql.service
systemctl 停止MySQL5.7
systemctl stop mysql.service

11.目录更新成最初5.6的方式

首先停机systemctl stop mysql.service

1.备份数据库
mv /data/mysql /data/mysql_bak
2.复制mysql5.7
cp -r /htdocs/software/mysql-5.7.33 /data
3.重命名
mv /data/mysql-5.7.33 /data/mysql
4.数据复制过来
cp -r /data/mysql_bak/data /data/mysql
5.备份启动文件
cp /etc/systemd/system/mysql.service /etc/systemd/system/mysql_bak.service
6.修改启动文件
vim /etc/systemd/system/mysql.service
ExecStart改成
ExecStart=/data/mysql/bin/mysqld --defaults-file=/etc/my.cnf
7.配置文件更改
vim /etc/my.cnf
输入i进入编辑模式，将innodb_additional_mem_pool_size = 16M替换成#innodb_additional_mem_pool_size = 16M，按esc，进入退出模式，按:wq回车保存
若是想不保存退出编辑模式，按esc，然后按:q!回车
8.创建mysql傀儡用户并授权
chown -R mysql.mysql /data/mysql
9.启动mysql
service mysql start
10.停掉mysql
service mysql stop

wiki地址
http://wiki.dev.wanmi.com/pages/viewpage.action?pageId=53478363