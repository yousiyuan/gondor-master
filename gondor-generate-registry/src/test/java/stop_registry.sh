#!/bin/sh
PID=$(cat tpid)
kill -9 $PID  && echo "Eureka注册中心服务端已经停止..."

