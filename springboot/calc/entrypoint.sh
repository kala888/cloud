#!/usr/bin/env bash
PROFILE=${ACTIVE_PROFILE:=rancher}

RANCHER_STACK_NAME=$(curl http://rancher-metadata/latest/self/conainer/stack/name)
RANCHER_SERVIVE_NAME=$(curl http://rancher-metadata/latest/self/conainer/service/name)
RANCHER_PRIMARY_IP=$(curl http://rancher-metadata/latest/self/container/primary_ip)
RANCHER_HOSTNAME=${RANCHER_STACK_NAME.RACHER_SERVICE_NAME}

echo ACTIVE_PROFILE=${PROFILE}
echo HOST=${RANCHER_HOSTNAME}
echo PRIMARY_IP=${RANCHER_PRIMARY_IP}

java -Xmx512m -Djava.security.egd=file:/dev/./urandom -jar /app.jar \
--spring.profiles.active=${ACTIVE_PROFILE} \
--eureka.instance.hostname${HOST} \
--eureka.instance.ip-address=${PRIMARY_IP}
