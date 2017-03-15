# docker-compose down
# sh rmi-images.sh
# # docker-compose -f docker-compose.yml -f docker-compose.dev.yml up
# cd springboot && gradle bootRe && cd ..
# docker-compose up -d

# echo "sleep 90s wait all service start"
# sleep 90s

curl 'http://localhost:8001/apis' --data-binary '{"name":"plus","request_path":"/api/v1/direct/plus","upstream_url":"http://plus:4000/plus","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive'
curl 'http://localhost:8001/apis' --data-binary '{"name":"calc_plus","request_path":"/api/v1/plus","upstream_url":"http://calc:4100/calc/plus","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive' 
curl 'http://localhost:8001/apis' --data-binary '{"name":"calc_multiply","request_path":"/api/v1/multiply","upstream_url":"http://calc:4100/calc/multiply","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive' 
curl 'http://localhost:8001/apis' --data-binary '{"name":"iplookup","request_path":"/api/v1/iplookup","upstream_url":"http://lookup:4200/iplookup","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive' 
curl 'http://localhost:8001/apis' --data-binary '{"name":"from","request_path":"/api/v1/from","upstream_url":"http://lookup:4200/from","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive' 
curl 'http://localhost:8001/apis' --data-binary '{"name":"me","request_path":"/api/v1/me","upstream_url":"http://lookup:4300/me","strip_request_path":true}' --compressed  -X PUT -H 'Content-Type: application/json;charset=UTF-8' -H 'Connection: keep-alive' 
