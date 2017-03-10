# Cloud Service
1. build war from source code.
   docker-compose -f build-all.yml up
2. startup containers
   docker-compose up
3. check services   
   http://localhost:1111


## Services

## app1
* provide add service
* curl http://localhost:8080/add?a=13&b=2
* swagger2：http://localhost:8080/swagger-ui.html

## app2
* provide three service:
** iplookup(lookup your ip)
* curl http://localhost:8180/iplookup
** add service which will call app1
* curl http://localhost:8180/remote/add?a=1&b=4
** and get the configuration from cfg-server
* curl http://localhost:8180/from
* swagger2: http://localhost:8180/swagger-ui.html

## cfg-server
* configuration service which get the configuration from github

## reg-server
* Netflix Eureka(registry)

## api-gateway
* route application base-on zuul
* curl http://localhost:5555/api/hero/from?accessToken=111
* curl http://localhost:5555/api/hero/iplookup?accessToken=111
* curl http://localhost:5555/api/hero/warped/add?a=1&b=4&accessToken=111
* curl http://localhost:5555/api/calc/add?a=8&b=4&accessToken=111

## swagger2
* API documentation
