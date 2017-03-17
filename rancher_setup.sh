#!/bin/bash

#0.Start Rancher Server
docker run -d --name rancher-server --restart=unless-stopped -p 18080:8080 rancher/server
echo "sleep 120s"
sleep 120s


#TODO wait for rancher server start successful
#http://localhost:18080/v2-beta/projects/1a5

#1.setup base info
#If you know you IP you can set it by manual
RANCHER_IP=$(ifconfig eth0 | egrep -o "inet addr:[^ ]*" | grep -o "[0-9.]*")
RANCHER_PORT="18080"
echo $RANCHER_URL

export RANCHER_URL="http://$RANCHER_IP:$RANCHER_PORT"

RANCHER_ACCOUNT_AKEY="publickey"
RANCHER_ACCOUNT_SKEY="secretkey"
AS_KEY="$RANCHER_ACCOUNT_AKEY:$RANCHER_ACCOUNT_SKEY"

ADMIN_USER="admin"
ADMIN_PASS="admin123"

PROJECT_ID=$(curl -su $AS_KEY "$RANCHER_URL/v2-beta/projects" | jq -r ".data[0].id")

#2.Initial Env Key, and export to ENV
ENV_KEY_CONTENT=$(curl -sX POST -H 'Content-Type: application/json' -d '{ "description": "This is a initial env key", "name": "InitialEnv" }' \
 "${RANCHER_URL}/v2-beta/projects/${PROJECT_ID}/apiKeys")
export RANCHER_ACCESS_KEY=$(echo $ENV_KEY_CONTENT | jq -r ".publicValue")
export RANCHER_SECRET_KEY=$(echo $ENV_KEY_CONTENT | jq -r ".secretValue")
echo "Env Key is $RANCHER_ACCESS_KEY:$RANCHER_SECRET_KEY"

#3.Create a Token before create Admin user 
curl -X POST \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
-d "{\"accountId\":\"1a1\", \"description\":\"First initial\", \"name\":\"initial key\", \"publicValue\":\"$RANCHER_ACCOUNT_AKEY\", \"secretValue\":\"$RANCHER_ACCOUNT_SKEY\"}" \
"http://localhost:18080/v2-beta/apikeys"

#4.Create Admin User
echo "create rancher admin user $ADMIN_USER/$ADMIN_PASS"
curl -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' \
-d "{\"accessMode\":\"unrestricted\", \"enabled\":true, \"name\":\"kala888\", \"password\":\"$ADMIN_PASS\", \"username\":\"$ADMIN_USER\"}" \
"http://localhost:18080/v2-beta/localauthconfig"

#5.Intial the registrationTokens
curl -su $AS_KEY  -X POST -H 'Content-Type: application/json' -d '{ "description": "Initial", "name": "firstToken"}' \
"$RANCHER_URL/v2-beta/projects/$PROJECT_ID/registrationtokens"

#6.Start Docker agent
TOKEN=`curl -su $AS_KEY "$RANCHER_URL/v2-beta/projects/$PROJECT_ID/registrationtokens" | jq -r '.data[0].token'`
DOCKER_IMAGE=`curl -su $AS_KEY "$RANCHER_URL/v2-beta/projects/$PROJECT_ID/registrationtokens" | jq -r '.data[0].image'`
DOCKER_AGENT_CMD=`curl -su $AS_KEY "$RANCHER_URL/v2-beta/projects/$PROJECT_ID/registrationtokens" | jq -r '.data[0].command'`
echo "Start a rancher agent, $DOCKER_AGENT_CMD"
docker run  -d --privileged -v /var/run/docker.sock:/var/run/docker.sock \
 -v /var/lib/rancher:/var/lib/rancher $DOCKER_IMAGE \
 "$RANCHER_URL/v1/scripts/$TOKEN"

echo "sleep 60s"
sleep 60s

cd graylog && rancher-compose up -d && cd ..
cd mongodb && rancher-compose up -d && cd ..
cd apps-node && rancher-compose up -d && cd ..
cd apps-spring && rancher-compose up -d && cd ..
cd graylog && rancher-compose up -d && cd ..
cd kong && rancher-compose up -d && cd ..







