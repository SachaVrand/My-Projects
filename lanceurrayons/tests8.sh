#!/bin/bash

set -e

. assert.sh

echo "Tests du calcul d'images avec intersections (formes)"

for testfile in `ls TEST8/*.test`
do
   imagefile=${testfile%.test}.png
   assert "./raytrace.sh $testfile" ""
   assert "./compare.sh $imagefile ${imagefile#TEST8/}" "OK\n0\n"
done

assert_end regression
