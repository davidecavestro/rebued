#!/bin/bash
#Lancia l'applicazione.

if [ -z "$JAVA_HOME" ] ; then
    export JAVA_HOME=/usr/local/java
    echo "Could not find a JDK. Using default value $JAVA_HOME"
    
fi


instdir=`dirname "$0"`

JAVACMD=$JAVA_HOME/bin/java
JARPATH=$instdir/../urbe.jar

par1=$1
par2=$2
par3=$3

"$JAVACMD" -Xmx64m -jar $JARPATH $par1 $par2 $par3

