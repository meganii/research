#!/bin/sh

for FILE in `\ls | grep ^${1}`
do
    cd ${FILE}
    THE=`pwd | cut -d"/" -f7 | cut -d"_" -f1`
    STD=`pwd | cut -d"/" -f7 | cut -d"_" -f2`
    AVE=`for((i=0;i<10;i++));do cat result$i.dat | awk '{sum = sum + $2}END{print sum/NR }';done | awk '{sum = sum + $1}END{print sum/NR}'`
    echo ${THE} ${STD} ${AVE}
    cd ..
done