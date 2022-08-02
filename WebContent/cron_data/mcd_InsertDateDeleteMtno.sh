#!/bin/sh
date
echo $SHELL;
export CLASSPATH=/apps/mcd/WEB-INF/lib/sqljdbc.jar:/apps/mcd/WEB-INF/lib/mysql-connector-java-5.1.5-bin.jar:/apps/mcd/WEB-INF/lib/servlet-api.jar:/apps/mcd/WEB-INF/classes;
export LANG=ko_KR.eucKR;
pid=`ps -A | grep mcd_InsertDateDeleteMtno | wc -l`;
echo $pid;
if [ $pid -lt 3 ]
  then
     echo "MCDONALD InsertDateDeleteMtno process is not active!!! "
     /usr/local/java/bin/java  risk.demon.InsertDateDeleteMtno
   else
     echo "MCDONALD InsertDateDeleteMtno process is Active!!! ";
fi

