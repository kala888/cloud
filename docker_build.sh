
#!/bin/bash
export repo="kala888"
source env_set.sh
echo "remove all demo application docker images"
#docker rm $(docker ps -a -q)
docker rmi $(docker images | grep "$repo/demo-"|awk '{print $1}')

cd mongodb && docker build -t "$repo/demo-mongo" . && cd ..

cd nodejs/plus && docker build -t "$repo/demo-plus" . && cd ../..

cd springboot && gradle clean bootRe && cd ..
cd springboot/config && docker build -t "$repo/demo-config" . && cd ../.. 
cd springboot/calc && docker build -t "$repo/demo-calc" . && cd ../..
cd springboot/lookup && docker build -t "$repo/demo-lookup" . && cd ../..
cd springboot/auth && docker build -t "$repo/demo-auth" . && cd ../..
cd springboot/manage && docker build -t "$repo/demo-manage" . && cd ../..

cd mongodb && docker build -t "$repo/demo-mongo" . && cd ../..


