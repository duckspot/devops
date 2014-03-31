copy target\roadie-1.0-SNAPSHOT-jar-with-dependencies.jar .\roadie.jar
@java -jar roadie.jar %*
@rem %dev_path%\var\devops\finish.bat