#!/bin/sh

MAX=`less ${1} | head -1 | cut -d" " -f2`
SUM=$MAX
for i in `seq 2 1 9999`
do
    TMP=`sed -n "${i}p" ${1} | cut -d" " -f2`
    if [ `echo "${TMP} > ${MAX}" | bc` -gt 0 ]; then 
	MAX=$TMP
    fi
    SUM=`echo "${SUM}+${MAX}" | bc`
    echo $i $MAX
done
#AVE=`${SUM}/10000`
#echo ${AVE}