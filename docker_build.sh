
#!/bin/bash
export repo="kala888"
source env_set.sh
echo "remove all demo application docker images"
#docker rm $(docker ps -a -q)
docker rmi $(docker images | grep "$repo/demo-"|awk '{print $1}')

#1. build mongodb docker image
cd mongodb && docker build -t "$repo/demo-mongo" . && cd ..

#2. build nodejs app image 
cd nodejs/plus && docker build -t "$repo/demo-plus" . && cd ../..

#3. build springboot app images 
cd springboot && gradle clean bootRe && cd ..
cd springboot/uaa && docker build -t "$repo/demo-uaa" . && cd ../.. 
cd springboot/calc && docker build -t "$repo/demo-calc" . && cd ../..
cd springboot/lookup && docker build -t "$repo/demo-lookup" . && cd ../..
cd springboot/manage && docker build -t "$repo/demo-manage" . && cd ../..



