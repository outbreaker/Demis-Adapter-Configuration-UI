SET JAVADIR=jre11\bin
SET CLIENTDIR=..\client

REM java as new window
%JAVADIR%\java -Dfile.encoding=UTF-8 -jar %CLIENTDIR%\demis-adapter-1.6.3.jar -conf %CLIENTDIR%\app.properties -apiConf %CLIENTDIR%\demis-adapter-api.properties