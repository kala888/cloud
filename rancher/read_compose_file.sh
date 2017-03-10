export COMPOSE_DATA=""
cat data.dat | while read line
do
     COMPOSE_DATA="${COMPOSE_DATA}\\r\\n${line}"
done
echo $COMPOSE_DATA
