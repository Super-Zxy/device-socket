#!/bin/bash

FWDIR="$(cd `dirname $0`/..; pwd)"
if [ ! -d ${FWDIR}/java.pid ]; then
    touch ${FWDIR}/java.pid
fi

OSUSER=$(id -nu)
PSNUM=$(cat ${FWDIR}/java.pid)
if [[ "$PSNUM" -ne "" ]]; then
    echo ${APP_NAME}" has been started! stop first."
    exit;
fi

echo "device-socket  starting......"
nohup java -Dlogging.path=${FWDIR}/log -jar ${FWDIR}/device-socket-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &

echo $! > ${FWDIR}/java.pid
exit; 
