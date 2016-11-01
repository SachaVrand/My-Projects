#!/bin/bash
IMAGE1=$1
IMAGE2=$2

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

# javac -d $MYPATH/bin $MYPATH/src/compare/Compare.java
java -cp $MYPATH/bin compare.Compare $IMAGE1 $IMAGE2
