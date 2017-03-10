
#!/bin/bash
export repo="kala888"
echo "remove all demo application docker images"
#docker rm $(docker ps -a -q)
docker rmi $(docker images | grep "$repo/demo-"|awk '{print $1}')
cd nodejs/plus && docker build -t "$repo/demo-plus" . && cd ../..
cd springboot && gradle clean bootRe && cd ..
cd springboot/config && docker build -t "$repo/demo-config" . && cd ../.. 
cd springboot/calc && docker build -t "$repo/demo-calc" . && cd ../..
cd springboot/lookup && docker build -t "$repo/demo-lookup" . && cd ../..
