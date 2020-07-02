#!/bin/sh
nohup java -jar gondor-registry.jar --spring.profiles.active=dev --server.port=8300 --eureka.instance.hostname=192.168.1.122:8300 --eureka.client.service-url.defaultZone=http://192.168.1.121:8300/eureka/ &
echo $! > tpid
echo "Eureka注册中心启动成功"
