# Cloud Service

## How to run this demo
1.install docker & rancher cli
2. cd cloudservice-demo/rancher
3. 

test case 1 : curl -s 'http://localhost/api/v1/direct/plus?a=1&b=3'
test case 2 : curl -s 'http://localhost/api/v1/plus?a=1&b=3' | jq .
test case 3 : curl -s 'http://localhost/api/v1/multiply?a=2&b=3' | jq .
test case 4 : curl -s 'http://localhost/api/v1/iplookup' | jq .
test case 5 : oauth2 test for "/api/v1/me"
    # get app client token
    APP_TOKEN=$(curl -su "app-client:111222" -d '{"grant_type":"client_credentials","client_id":"client-app"}' 'http://localhost:7200/oauth/token?grant_type=client_credentials' | jq -r ".access_token")
    echo $APP_TOKEN
    # create a test account  kala888/111222
    curl -X POST -H "Authorization:Bearer $APP_TOKEN" -H "Content-Type:application/json" \
    -d '{"scope":"server","username":"kala888","password":"111222"}' \
    'http://localhost:7200/users'
    # get user access token
    USER_TOKEN=$(curl -su "browser:" \
    -d grant_type=password -d scope=ui -d username=kala888 -d password=111222 \
    "http://localhost:7200/oauth/token" |jq -r ".access_token")
    echo $USER_TOKEN
    # retrive user info
    curl -H "Authorization: Bearer $USER_TOKEN" 'http://localhost/api/v1/me' | jq .
test case 6 : google oauth2 with kong
