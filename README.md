# Cloud Service

## How to run this demo
1.install docker & rancher cli
2. cd cloudservice-demo/rancher
3. 

curl http://localhost/api/v1/direct/plus?a=1&b=3
curl http://localhost/api/v1/plus?a=1&b=3
curl http://localhost/api/v1/multiply?a=2&b=3
curl http://localhost/api/v1/iplookup



## Services
consul ui : http://localhost:8500
kong-dashboard : http://localhost:7400
spring-admin dashboard : http://localhost:7300


## a-service
* provide add service
* curl http://${a-service-ip}:5100/add?a=13&b=2
* swagger2：http://${a-service-ip}:5100/swagger-ui.html

## b-service
* provide three service:
** iplookup(lookup your ip)
* curl http://${b-service-ip}:5200/iplookup
** add service which will call app1
* curl http://${b-service-ip}:5200/remote/add?a=1&b=4
** add service which will call app1 without parameter
* curl http://${b-service-ip}:5200/plus1and2
* swagger2: http://${b-service-ip}:5200/swagger-ui.html

## nodejs-service
* curl http://${nodejs-ip}:1337/



## api-gateway
* 1. registry api to kong

* route application
* * curl "http://localhost/v1/calc?a=1&b=111"
* * curl "http://localhost/v1/plus1and2"
* * curl "http://localhost/v1/b/iplookup"
* * curl "http://localhost/v1/b/remote/add?a=1&b=222"

## swagger2
* API documentation










curl -u "${CATTLE_ACCESS_KEY}:${CATTLE_SECRET_KEY}" \
-X POST \
-H 'Accept: application/json' \
-H 'Content-Type: application/json' \
-d '{"accountId":"1a1", "description":"Rancher Admin", "name":"kala", "publicValue":"pub123"}' \
'http://localhost:18080/v2-beta/apikeys'