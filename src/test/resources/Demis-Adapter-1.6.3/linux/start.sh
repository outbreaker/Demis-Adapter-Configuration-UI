#!/bin/sh

JAVA=jre-11/bin/java
CLIENTDIR=../client

$JAVA -version

$JAVA -Dfile.encoding=UTF-8 -jar $CLIENTDIR/demis-adapter-1.6.3.jar –conf $CLIENTDIR/app.properties –apiConf $CLIENTDIR/demis-adapter-api.properties