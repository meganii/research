#!/bin/sh/

# 現世代までの最大評価値を各世代で足し合わせ、その平均を取る。

for d in `ls | grep ^${1}`
do
    cd ${d}
    THE=`pwd | cut -d"/" -f7 | cut -d"_" -f1`
    STD=`pwd | cut -d"/" -f7 | cut -d"_" -f2`
    AVE=`for((i=0;i<10;i++));do awk 'BEGIN{max=0;sum=0}{if ($2>max) max = $2 ; sum = sum + max}END{print max,sum/NR}' result$i.dat ;done | awk '{sum = sum + $2}END{print sum/NR}'`
    echo ${THE} ${STD} ${AVE}
    cd ..
done