#!/bin/bash

EXE_TYPE=("law-server" )

EXE_VERSION="0.0.1-SNAPSHOT"

for TYPE in ${EXE_TYPE[*]}
do
    echo "EXE TYPE is $TYPE"
    
    EXE_JAR_NAME="$EXE_JAR_HOME/$TYPE-$EXE_VERSION.jar"
    
    echo "<<<<STOPPING $TYPE ... "
    
    PID=`ps -ef|grep "$EXE_JAR_NAME"|grep -v "grep"|head -1|awk '{print $2}'`

    if kill -0 $PID > /dev/null 2>&1; then
        echo "$TYPE is already running as process $PID".
        else
        echo "$TYPE has not been Started!".
        continue
    fi

    kill -TERM $PID

    SLEEP=5
    while [ $SLEEP -ge 0 ]
    do
    kill -9 $PID >/dev/null 2>&1
    if [ $? -gt 0 ]
    then
        echo "<<<<$TYPE $PID STOPPED"
        break
    fi
    if [ $SLEEP -gt 0 ]
    then
        sleep 1
    fi
    if [ $SLEEP -eq 0 ]
    then
        echo "<<<<$TYPE $PID DID not stop in time!"
    fi
    SLEEP=`expr $SLEEP - 1 `
    done
done
exit 0