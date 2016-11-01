#!/bin/bash

set -e

. assert.sh

echo "Tests du calcul de la lumière indirecte"

for testfile in `ls TEST9/*.test`
do
   imagefile=${testfile%.test}.png
   assert "./raytrace.sh $testfile" ""
   assert "./compare.sh $imagefile ${imagefile#TEST9/} | grep OK" "OK\n"
done

assert_end regression
