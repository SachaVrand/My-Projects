#!/bin/bash

MYPATH=$(dirname "$0")

if [ ! "$MYPATH/bin" ]; then
	mkdir $MYPATH/bin
fi

find $MYPATH/src -name *.java -print >$MYPATH/tocompile

javac -d $MYPATH/bin @$MYPATH/tocompile

jar cmf manifest.mf raytracer.jar -C $MYPATH/bin .

