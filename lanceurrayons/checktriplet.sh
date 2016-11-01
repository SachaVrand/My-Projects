#!/bin/bash

# Pour connaître l'emplacement du script compare.sh
MYPATH=$(dirname "$0")

java -cp $MYPATH/bin tests.TestCalculVectoriel "$1"

