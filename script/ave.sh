#!/bin/sh

WHERE=`pwd | cut -d"/" -f7`
AVE=`cat output.dat | awk '{sum = sum + $2}END{print sum/NR }'`
MAX=`sort -k 2 -n  output.dat | tail -1 | cut -d" " -f2,3`
echo ${WHERE} ${AVE} ${MAX}