#!/bin/bash

EXE_TYPE=("law-server" )

EXE_VERSION="0.0.1-SNAPSHOT"

EXE_PARAM="--spring.profiles.active=prod,native"

#JAVA_HOMR
if [ "$JAVA_HOME" != "" ]; then
  JAVA="$JAVA_HOME/bin/java"
else
  JAVA=java
fi

for TYPE in ${EXE_TYPE[*]}
do
    echo "EXE TYPE is $TYPE"
    
    EXE_JAR_NAME="$EXE_VERSION/$TYPE-$EXE_VERSION.jar"

    echo "<<<<STARTING $TYPE ... "
    
    PID=`ps -ef|grep "$EXE_JAR_NAME"|grep -v "grep"|head -1|awk '{print $2}'`

    if kill -0 $PID > /dev/null 2>&1; then
        echo "$TYPE is already running as process $PID".
        continue
    fi

    echo "nohup $JAVA -jar $EXE_JAR_NAME $EXE_PARAM &"
    nohup $JAVA -jar $EXE_JAR_NAME $EXE_PARAM >/dev/null 2>&1 &


done
exit 0