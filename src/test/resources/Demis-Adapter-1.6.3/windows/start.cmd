SET JAVADIR=jre11\bin
SET CLIENTDIR=../client

REM java as new window
%JAVADIR%\java -jar -Dfile.encoding=UTF-8 %CLIENTDIR%/demis-adapter-1.1.0.jar -apiConf=%CLIENTDIR%/demis-adapter-api.properties -conf=%CLIENTDIR%/app.properties
