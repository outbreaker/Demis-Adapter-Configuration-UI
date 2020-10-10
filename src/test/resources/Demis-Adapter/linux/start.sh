#!/bin/sh

JAVA=jre-11/bin/java
CLIENTDIR=../client

$JAVA -version

$JAVA -jar -Dfile.encoding=UTF-8 $CLIENTDIR/demis-adapter-1.1.0.jar -apiConf=$CLIENTDIR/demis-adapter-api.properties -conf=$CLIENTDIR/app.properties


