#!/bin/bash
if test -z "$MONGODB_PASSWORD"; then
    echo "MONGODB_PASSWORD not defined"
    exit 1
fi

if test -z "$MONGODB_USER"; then
    echo "MONGODB_USER not defined"
    exit 1
fi

if test -z "$MONGODB_DATABASE"; then
    echo "MONGODB_DATABASE not defined"
    exit 1
fi

auth="-u $MONGODB_USER -p $MONGODB_PASSWORD"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('$MONGODB_USER')) { db.createUser({ user: '$MONGODB_USER', pwd: '$MONGODB_PASSWORD', roles: [ {role:'readWrite', db:'$MONGODB_DATABASE'} ]}) }"
until mongo $MONGODB_DATABASE --eval "$create_user" || mongo $MONGODB_DATABASE $auth --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "$INIT_DUMP"; then
    echo "execute dump file"
	until mongo $MONGODB_DATABASE $auth $INIT_DUMP; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb mongod --auth "$@"
