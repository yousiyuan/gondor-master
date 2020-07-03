1、关闭防火墙
systemctl stop firewalld.service
systemctl disable firewalld.service

2、修改 ip
nmtui

3、重启网关
systemctl restart network

4、安装命令
(1) yum -y install lrzsz
-rz 上传文件
-sz [filename] 下载文件
(2) yum -y install openssh-clients
● 跨服务器传文件
scp 2029-jgmes-aps-application-1.0.jar root@120.79.244.12:/root/jgMesStartJar
● 跨服务器传文件夹
scp -r /usr/local/tools root@192.168.1.129:/usr/local
scp -r /usr/local/jdk1.8.0_231 root@192.168.1.129:/usr/local
scp -r /usr/local/apollo-configservice root@192.168.1.129:/usr/local
scp -r /usr/local/apollo-adminservice root@192.168.1.129:/usr/local
(3) yum -y install unzip zip
unzip 123.zip -d /usr/local  解压文件到指定路径

5、安装配置jdk
export JAVA_HOME=/usr/local/jdk1.8.0_231
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:${PATH}

6、分别解压安装apollo-configservice和apollo-adminservice
(1) 解压apollo-configservice-1.6.1-github.zip到指定目录
● 修改配置文件，对应的数据库分别是dev、fat、uat、pro
/usr/local/apollo-configservice/config/application-github.properties
● 启动服务
/usr/local/apollo-configservice/scripts/startup.sh
● 停止服务
/usr/local/apollo-configservice/scripts/shutdown.sh
(2) 解压apollo-adminservice-1.6.1-github.zip到指定目录
● 修改配置文件，对应的数据库分别是dev、fat、uat、pro
/usr/local/apollo-adminservice/config/application-github.properties
● 启动服务
/usr/local/apollo-adminservice/scripts/startup.sh
● 停止服务
/usr/local/apollo-adminservice/scripts/shutdown.sh
(3) 都启动成功后，访问Apollo的注册中心地址
DEV
http://192.168.1.126:8080/
FAT
http://192.168.1.127:8080/
UAT
http://192.168.1.128:8080/
PRO
http://192.168.1.129:8080/

7、解压安装 apollo-portal
(1) 解压apollo-portal-1.6.1-github.zip到指定目录
(2) 修改apollo-portal配置信息
● 修改配置文件apollo-env.properties，配置Apollo的meta service信息
/usr/local/apollo-portal/config/apollo-env.properties
● 修改配置文件application-github.properties，对应的数据库是ApolloPortalDB
/usr/local/apollo-portal/config/application-github.properties
(3) 使用apollo-portal
● 启动服务
/usr/local/apollo-portal/scripts/startup.sh
● 停止服务
/usr/local/apollo-portal/scripts/shutdown.sh
(4) 访问apollo-portal
http://192.168.1.125:8070/

8、注意事项
● 重要参考【APOLLO分布式部署指南】
https://github.com/ctripcorp/apollo/releases
● 系统使用文档
https://github.com/ctripcorp/apollo/wiki/Apollo使用指南
https://github.com/ctripcorp/apollo/wiki/Java客户端使用指南#1241-environment
● 启动顺序
必须按照 config service ==> admin service ==> portal 的顺序启动Apollo
